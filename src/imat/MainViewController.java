
package imat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable {

    @FXML
    Label pathLabel;
    @FXML
    private AnchorPane anchorHeader;
    @FXML
    Button beginShoppingButton;


    @FXML
    private FlowPane varaAvlangFlowPane;

    @FXML
    private ScrollPane varaAvlangScrollpane;

    @FXML
    private Label totalPrice;

    @FXML
    protected Button varukorgCloseButton;

    @FXML private AnchorPane varukorgPopupAnchor;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();


        anchorHeader.getChildren().add(new HeaderController(this));




        setUpShoppingCart();
        updateVaraAvlang();
        updateTotalPrice();

        closeVarukorg(); // håll den stängd som default
    }

    private void setUpShoppingCart() {
        //lite test för o fylla varukurgen kan tas bort sen
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(1), 3));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(2), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(4), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(3), 1));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(10), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(11), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(12), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(13), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(14), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(15), 2));
        iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(iMatDataHandler.getProduct(16), 2));

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

    @FXML //stäng varukorgen
    private void closeVarukorg() {
        varukorgPopupAnchor.setVisible(false);
        varukorgPopupAnchor.setManaged(false);
    }

    @FXML
    private void closeVarukorgAnimation(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), varukorgPopup);
        closeNav.setToX(+(varukorgPopup.getWidth()));
        closeNav.setOnFinished(event -> closeVarukorg());
        closeNav.play();
    }

    @FXML
    protected void openVarukorg() {
        varukorgPopupAnchor.setVisible(true);
        varukorgPopupAnchor.setManaged(true);
    }

    @FXML //används i varukorgen för att kunna klicka utanför o stänga fönstret
    public void mouseTrap(Event event) {
        event.consume();
    }

    @FXML private AnchorPane varukorgPopup;




    private boolean firstPrepare = true;
    protected void prepareSlideMenuAnimation(Button closeButton, Button openButton) {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), varukorgPopup);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), varukorgPopup);

        if(firstPrepare) {
            varukorgPopup.setTranslateX(554); //behöver ändras första gången
            firstPrepare = false;
        }

        closeButton.setOnAction((ActionEvent evt) ->{
            if(!(varukorgPopup.getTranslateX() != 0)) {
                closeNav.setToX(+(varukorgPopup.getWidth()));
                closeNav.setOnFinished(event -> closeVarukorg());
                closeNav.play();
            }
        }) ;

        openButton.setOnAction((ActionEvent evt)->{
            System.out.println(varukorgPopup.getTranslateX());
            openVarukorg();
            if(varukorgPopup.getTranslateX()!=0){
                System.out.println("opening");
                openVarukorg();
                openNav.play();
            }
        });
    }


}