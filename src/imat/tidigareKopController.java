package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Flow;

public class tidigareKopController extends AnchorPane {


    private MainViewController mainViewController;
    @FXML
    protected FlowPane productCardCarousel;
    @FXML
    protected ScrollPane tidigareKopScroll;
    protected IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML protected AnchorPane menuAnchor;
    @FXML protected AnchorPane tidigareKopItemAnchor;
    @FXML protected FlowPane  flowpane;

    private ArrayList<tidigareKopItemController> tidigareKopItemList;


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




    protected void initalize() {

        tidigareKopItemList = new ArrayList<>();






//        this.menuAnchor.getChildren().add(new MenyController(this.mainViewController));
        List<Order> orders = new ArrayList<>(iMatDataHandler.getOrders());
        orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        for (Order order : orders) {
              tidigareKopItemController itemController = new tidigareKopItemController(this.mainViewController, order);
              this.tidigareKopItemList.add(itemController);
              this.flowpane.getChildren().add(itemController);

        }

    }

//    protected void addNewOrder(Order order) {
//        tidigareKopItemController itemController = new tidigareKopItemController(this.mainViewController, order);
//        this.tidigareKopItemList.add(itemController);
//        this.flowpane.getChildren().(itemController);
//    }

    public void updateTidigareKop()
    {
        for (tidigareKopItemController item : tidigareKopItemList)
        {
            item.updateTidigareKopItem();
        }


    }
}
