package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.List;

public class HeaderController extends AnchorPane {

    @FXML
    private AnchorPane tidigareKop;
    @FXML
    private AnchorPane dinaUppgifter;
    @FXML
    private AnchorPane varuKorgen;
    @FXML
    private ImageView lgo;
    @FXML
    private TextField searchBar;
    @FXML
    GridPane container;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public HeaderController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();

        this.initialize();
    }

    @FXML
    public void initialize() {
        this.searchBar.setPromptText("Sök...");
        List<Product> products = iMatDataHandler.getProducts();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (container.getChildren().size() > 1) {
                container.getChildren().remove(1);
            }
            VBox dropDownMenu = populateDropDownMenu(newValue, products);
            container.add(dropDownMenu, 0, 1);
            GridPane.setValignment(dropDownMenu, VPos.TOP); // Align the VBox to the top of the row
        });
        searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                container.setVisible(true);
            } else {
                container.setVisible(false);
            }
        });
    }

    public static VBox populateDropDownMenu(String text, List<Product> products) {
        VBox dropDownMenu = new VBox();
        dropDownMenu.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        dropDownMenu.setAlignment(Pos.TOP_LEFT);

        int counter = 0;

        for (Product product : products) {
            if (!text.replace(" ", "").isEmpty() && product.getName().toUpperCase().contains(text.toUpperCase())) {
                if (counter >= 5) {
                    break;
                }
                Label label = new Label(product.getName());
                label.setFont(new Font("Amiko", 16));
                dropDownMenu.setAlignment(Pos.TOP_LEFT);
                dropDownMenu.getChildren().add(label);
                counter++;
            }
        }

        // Set the VBox's max height to be its preferred height
        dropDownMenu.setMaxHeight(Region.USE_PREF_SIZE);

        return dropDownMenu;
    }
}