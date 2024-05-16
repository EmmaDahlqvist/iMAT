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
    private List<ShoppingItem> shoppingItemList;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public ProductScrollpaneController(List<Product> productList) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productScrollpane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.productList = productList;
        this.shoppingItemList = new ArrayList<ShoppingItem>();

        for (Product product : productList) {
            shoppingItemList.add(new ShoppingItem(product));
        }


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for(ShoppingItem shoppingItem : shoppingItemList) {
            ProductCard currentProductCard = new ProductCard(shoppingItem);
            productsFlowpane.getChildren().add(currentProductCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(currentProductCard);
        }
    }
}
