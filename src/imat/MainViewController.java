
package imat;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable {

    @FXML
    Label pathLabel;
    @FXML
    private AnchorPane anchorHeader;

    @FXML private FlowPane varaAvlangFlowPane;

    @FXML private ScrollPane varaAvlangScrollpane;

    @FXML private Label totalPrice;


    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);
        anchorHeader.getChildren().add(new HeaderController());


        setUpShoppingCart();
        updateVaraAvlang();
        updateTotalPrice();


        for(Product product : iMatDataHandler.getProducts()) {
            System.out.println(product);

        }

    }

    private void setUpShoppingCart() {
        //lite test
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(1), 3));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(2), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(4), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(3), 1));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(10), 2));

    }

    //körs för att uppdatera vara avlång listan
    protected void updateVaraAvlang() {
        varaAvlangFlowPane.getChildren().clear();
        for (ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems()) {
            varaAvlangFlowPane.getChildren().add(new VaraAvlang(shoppingItem, this));
        }
    }

    //kalla den för att uppdatera totalpriset i varukorgen
    protected void updateTotalPrice() {
        totalPrice.setText(String.valueOf(iMatDataHandler.getShoppingCart().getTotal() + " kr"));
    }



}