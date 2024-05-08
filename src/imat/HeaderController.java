package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
//            this.getChildren().add(node);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();

        this.initialize();
    }

    @FXML
    public void initialize() {
        this.searchBar.setPromptText("Sök...");
        List<Product> products = iMatDataHandler.getProducts(); // getProducts() should return your list of products

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (container.getChildren().size() > 1) { // if already contains a drop-down menu -> remove it
                container.getChildren().remove(1);
            }
            container.add(populateDropDownMenu(newValue, products), 0, 1); // then add the populated drop-down menu to the second row in the grid pane
        });
        searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // If the TextField is focused, show the VBox
                container.setVisible(true);
            } else {
                // If the TextField is not focused, hide the VBox
                container.setVisible(false);
            }
        });

    }

    public static VBox populateDropDownMenu(String text, List<Product> products) {
        VBox dropDownMenu = new VBox();
        dropDownMenu.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null))); // colors just for example
        dropDownMenu.setAlignment(Pos.TOP_LEFT); // align to the top left
        dropDownMenu.setMaxHeight(200); // set the maximum height
        dropDownMenu.setMinHeight(100); // set the minimum height

        int counter = 0; // initialize a counter

        for (Product product : products) { // loop through every Product in the list
            // if the given text is not empty and doesn't consists of spaces only, as well as it's a part of one (or more) of the product names
            if (!text.replace(" ", "").isEmpty() && product.getName().toUpperCase().contains(text.toUpperCase())) {
                if (counter >= 5) { // if counter is 5 or more, stop adding items
                    break;
                }
                Label label = new Label(product.getName()); // create a label and set the text to the product's name
                label.setFont(new Font("Amiko", 16)); // set the font and size of the text
                dropDownMenu.setAlignment(Pos.TOP_LEFT); // align the label to the top left
                // you can add listener to the label here if you want
                // your user to be able to click on the options in the drop-down menu
                dropDownMenu.getChildren().add(label); // add the label to the VBox
                counter++; // increment the counter
            }
        }

        return dropDownMenu; // at the end return the VBox (i.e. drop-down menu)
    }
}