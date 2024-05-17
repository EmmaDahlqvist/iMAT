package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductScrollpaneController extends AnchorPane {

    @FXML
    private FlowPane productsFlowpane;

    private List<Product> productList;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public ProductScrollpaneController(MainViewController mainViewController, List<Product> productList) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productScrollpane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.productList = productList;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for(Product product : productList) {
            productsFlowpane.getChildren().add(mainViewController.getProductMap().get(product.getProductId()));
        }
    }
}
