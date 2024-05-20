package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import se.chalmers.cse.dat216.project.*;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderController extends AnchorPane implements ShoppingCartListener {

    @FXML
    private AnchorPane tidigareKop;
    @FXML
    protected AnchorPane dinaUppgifter;
    @FXML
    private AnchorPane varuKorgen;
    @FXML
    private Button lgo;
@FXML
    private TextField searchBar;
    @FXML
    GridPane container;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML
    private Button tidigareKopButton;
    @FXML
    protected Button dinaUppgifterButton;
    @FXML
    private Button varukorgenButton;
    @FXML
    private ImageView tidigareKopBild;
    @FXML
    private ImageView dinaUppgifterBild;
    @FXML
    private ImageView varukorgenBild;

    private MainViewController mainViewController;

    @FXML private AnchorPane varaNotification;
    @FXML private Label totalVarorLabel;

    private String headerType;
    public HeaderController(MainViewController mainViewController, String headerType) {
        String fxmlFile;
        this.headerType = headerType;
        switch (headerType) {
            case "withoutVarukorgButton":
                fxmlFile = "header_without_varukorgbutton.fxml";
                break;
            case "withImatMainButton":
                fxmlFile = "header_with_imatmainbutton.fxml";
                break;
            default:
                fxmlFile = "header.fxml";
        }        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();

        if (fxmlFile != "withImatMainButton"){this.initialize();}

        iMatDataHandler.getShoppingCart().addShoppingCartListener(this);

    }

    private Label searchLabel;
    @FXML
    public void initialize() {
        List<Product> products = iMatDataHandler.getProducts();

        if (searchBar != null) {
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                if (container.getChildren().size() > 1) {
                    container.getChildren().remove(1);
                }
                VBox dropDownMenu = populateDropDownMenu(newValue, products);
                container.add(dropDownMenu, 0, 1);
                GridPane.setValignment(dropDownMenu, VPos.TOP); // Align the VBox to the top of the row
            });
            searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
                container.setVisible(newValue);
            });
        }
        if(searchButton != null) {
            searchButton.setCursor(Cursor.HAND);
            searchButton.setOnMouseClicked(event -> {
                searchLabel = new Label(searchBar.getText());
                onSearch();
            });
        }

        if (searchBar != null) {
            searchBar.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    searchLabel = new Label(searchBar.getText());
                    onSearch();
                }
            });

        }

        List<ImageView> imageViews = Arrays.asList(tidigareKopBild, dinaUppgifterBild, varukorgenBild);
        for (ImageView imageView : imageViews) {
            if (imageView != null) {
                imageView.setMouseTransparent(true);
            }
        }
        if (lgo != null) {
            lgo.setOnMouseClicked(event -> {
                backToHomePage();
            });
        }
        if (tidigareKopButton != null) {
            tidigareKopButton.setOnMouseClicked(event -> {
                openTidigareKop();
            });
        }

        updateVaraNotification();
        prepareMenuSlideAnimation();
    }

    protected void openTidigareKop() {
        if(checkForInterruption()) {
            buttonPushedNotification();
            methodInterrupted = "openTidigareKop";
        } else {
            switchInterruptionTrigger();
            mainViewController.backToHomePage();
            this.mainViewController.tidigareKopAnchor.toFront();
            tidigareKopController kopController = (tidigareKopController) this.mainViewController.tidigareKopAnchor.getChildren().get(0);
            kopController.updateTidigareKop();
            this.mainViewController.headerMenyVarukorgToFront();
        }
    }

    @FXML private ImageView searchButton;

    UppgifterController uppgifterController;
    protected void addButtonPushedListener(UppgifterController uppgifterController) {
        this.uppgifterController = uppgifterController;
    }

    private void buttonPushedNotification() {
        if(this.uppgifterController != null ) {
            uppgifterController.villDuSpara();
        }
    }

    protected String methodInterrupted = "";

    @FXML
    protected void backToHomePage() {
        if(checkForInterruption()) {
            buttonPushedNotification();
            methodInterrupted = "backToHomePage";
        } else {
            switchInterruptionTrigger();
            mainViewController.backToHomePage();
        }
    }

    private boolean checkForInterruption() {
        if(uppgifterController != null && !uppgifterController.interruptionTriggered) {
            switchInterruptionTrigger();
            return true;
        } else {
            return false;
        }
    }

    private void switchInterruptionTrigger() {
        if(uppgifterController != null) {
            uppgifterController.interruptionTriggered = !uppgifterController.interruptionTriggered;
        }
    }

    @FXML
    private void openUppgifter() {
        mainViewController.openUppgifter();
    }



    public VBox populateDropDownMenu(String text, List<Product> products) {
        VBox dropDownMenu = new VBox();
        dropDownMenu.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        dropDownMenu.setAlignment(Pos.TOP_LEFT);

        List<HBox> exactMatches = new ArrayList<>();
        List<HBox> containsMatches = new ArrayList<>();

        for (Product product : products) {
            if (!text.replace(" ", "").isEmpty()) {
                Label label = new Label(product.getName());
                label.setFont(new Font("Amiko", 16));

                // Wrap the label in a HBox
                HBox hbox = new HBox(label);
                hbox.getStyleClass().add("hover-label");
                hbox.setFillHeight(true);
                hbox.setAlignment(Pos.CENTER_LEFT);

                // Add a mouse click event to the HBox
                hbox.setOnMouseClicked(event -> {
                    searchLabel = label;
                    onSearch();
                });

                if (product.getName().equalsIgnoreCase(text)) {
                    exactMatches.add(hbox);
                } else if (product.getName().toUpperCase().contains(text.toUpperCase())) {
                    containsMatches.add(hbox);
                }
            }
        }

        // Add the exact matches first, then the contains matches
        // But only add up to 5 matches in total
        int count = 0;
        for (HBox match : exactMatches) {
            if (count >= 5) break;
            dropDownMenu.getChildren().add(match);
            count++;
        }
        for (HBox match : containsMatches) {
            if (count >= 5) break;
            dropDownMenu.getChildren().add(match);
            count++;
        }

        // Set the VBox's max height to be its preferred height
        dropDownMenu.setMaxHeight(Region.USE_PREF_SIZE);

        return dropDownMenu;
    }


    protected void onSearch() {
        if(checkForInterruption()) {
            buttonPushedNotification();
            methodInterrupted = "onSearch";
        } else {
            if(searchLabel != null){
                switchInterruptionTrigger();
                String labelText = searchLabel.getText();
                // Save labelText to a variable or use it directly here
                this.mainViewController.sokResultatLabel.setText("Sökresultat för " + '"' + labelText.toLowerCase() + '"');
                searchBar.setText("");
                this.mainViewController.sokResultatAnchor.toFront();
                this.mainViewController.headerMenyVarukorgToFront();
                this.mainViewController.sokResultatAnchor.setVisible(true);
                this.mainViewController.homePageAnchor.setVisible(true);
                this.mainViewController.searchAnchor.getChildren().add(new ProductScrollpaneController(mainViewController, this.iMatDataHandler.findProducts(labelText)));
            }
        }
    }

//mouse pressed, exited och entered.

    private void prepareMenuSlideAnimation() {
        mainViewController.prepareSlideMenuAnimation(mainViewController.varukorgCloseButton, varukorgenButton);
    }

    private void updateVaraNotification() {
        if(!headerType.equals("withoutVarukorgButton") && !headerType.equals("withImatMainButton")) {
            int amount = 0;
            for(ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems()) {
                amount += shoppingItem.getAmount();
            }
            System.out.println(amount);
            if(amount != 0) {
                totalVarorLabel.setText(String.valueOf(amount));
                varaNotification.setVisible(true);
            } else {
                varaNotification.setVisible(false);
            }

        }
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateVaraNotification();
    }
}