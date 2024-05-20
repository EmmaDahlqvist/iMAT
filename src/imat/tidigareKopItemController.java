package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class tidigareKopItemController extends AnchorPane {


    private MainViewController mainViewController;
    @FXML
    protected FlowPane productCardCarousel;
    @FXML
    protected ScrollPane tidigareKopScroll;
    protected IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML protected AnchorPane menuAnchor;
    @FXML protected AnchorPane tidigareKopItemAnchor;
    @FXML protected Label datumLabel;
    @FXML protected Label priceLabel;
    @FXML protected ScrollPane scrollpane;
    @FXML protected Button kopieraTillVarukorgButton;
    Order order;




    public tidigareKopItemController(MainViewController mainViewController, Order order) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tidigareKopItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        this.order = order;
        initalize(order);

    }





    private void initalize(Order order) {
        productCardCarousel.setOrientation(Orientation.HORIZONTAL);

        String dateStr = order.getDate().toString();
        String formattedDate = dateStr.substring(0, dateStr.length() - 13);
        datumLabel.setText(formattedDate);
        int price = 0;



        for (ShoppingItem shoppingItem : order.getItems()){
            price += shoppingItem.getTotal();


            productCardCarousel.getChildren().add(mainViewController.getProductMap().get(shoppingItem.getProduct().getProductId()));
            productCardCarousel.setMinWidth(350 * productCardCarousel.getChildren().size() + 10*productCardCarousel.getChildren().size());}

        priceLabel.setText(price + " kr");


        kopieraTillVarukorgButton.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            for (ShoppingItem shoppingItem : order.getItems()){

                for (int i = 0; i < shoppingItem.getAmount(); i++)
                {
                    mainViewController.getProductMap().get(shoppingItem.getProduct().getProductId()).addToShoppingCart();
                    mainViewController.getProductMap().get(shoppingItem.getProduct().getProductId()).updateShoppingItem();
                }



            }
        });


        //TODO gör så att den öppnas vid klick av tidigare köp


//        this.productCardCarousel.getChildren().add(new ProductScrollpaneController(
    }

    public void updateTidigareKopItem()
    {
        productCardCarousel.getChildren().clear();

        for (ShoppingItem shoppingItem : order.getItems()){
            productCardCarousel.getChildren().add(mainViewController.getProductMap().get(shoppingItem.getProduct().getProductId()));
            productCardCarousel.setMinWidth(350 * productCardCarousel.getChildren().size() + 10*productCardCarousel.getChildren().size());}
    }



//    private void simulateTidigareKop{
//
//    }

}