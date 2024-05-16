package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class tidigareKopController extends AnchorPane {


    private MainViewController mainViewController;
    @FXML
    protected FlowPane productCardCarousel;
    @FXML
    protected ScrollPane tidigareKopScroll;
    protected IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();


    public tidigareKopController(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tidigareKop.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        initalize();

    }

    private void simulatePreviousOrders(){

//TODO göra sepparat klass
    }
    private void initalize() {
        for (Order order : iMatDataHandler.getOrders()){
            for (ShoppingItem shoppingItem : order.getItems()){
                productCardCarousel.getChildren().add(new ProductCard(shoppingItem.getProduct()));
            }
        }
        for (Product product : this.iMatDataHandler.getProducts()){
            productCardCarousel.getChildren().add(new ProductCard(product));
        }


//        this.productCardCarousel.getChildren().add(new ProductScrollpaneController(
    }
//    private void simulateTidigareKop{
//
//    }

}
