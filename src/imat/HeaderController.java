package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderController extends AnchorPane {

    @FXML
    private AnchorPane tidigareKop;
    @FXML
    private AnchorPane dinaUppgifter;
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
    private Button dinUppgifterButton;
    @FXML
    private Button varukorgenButton;
    @FXML
    private ImageView tidigareKopBild;
    @FXML
    private ImageView dinaUppgifterBild;
    @FXML
    private ImageView varukorgenBild;


    private MainViewController mainViewController;

    public HeaderController(MainViewController mainViewController, String headerType) {
        String fxmlFile;
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



    }

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
        if (searchBar != null) {
            searchBar.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    onSearch(new Label(searchBar.getText()));
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
                this.mainViewController.homePageAnchor.setVisible(true);
                this.mainViewController.sokResultatAnchor.setVisible(false);
            });
        }

        prepareMenuSlideAnimation();
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
                    onSearch(label);
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

    private void onSearch(Label label) {
        String labelText = label.getText();
        // Save labelText to a variable or use it directly here
        this.mainViewController.sokResultatLabel.setText("Sökresultat för " + '"' + labelText.toLowerCase() + '"');
        searchBar.setText("");
        this.mainViewController.sokResultatAnchor.setVisible(true);
        this.mainViewController.homePageAnchor.setVisible(false);
        this.mainViewController.searchAnchor.getChildren().add(new ProductScrollpaneController(this.iMatDataHandler.findProducts(labelText)));
    }

//mouse pressed, exited och entered.

    private void prepareMenuSlideAnimation() {
        mainViewController.prepareSlideMenuAnimation(mainViewController.varukorgCloseButton, varukorgenButton);
    }

}