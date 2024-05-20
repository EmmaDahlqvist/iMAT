
package imat;

import java.net.URL;
import java.util.List;
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
import java.util.HashMap;

public class MainViewController implements Initializable {

    @FXML Label pathLabel;
    @FXML protected AnchorPane anchorHeader;

    @FXML protected AnchorPane anchorMeny;
    protected MenyController menyController;

    @FXML Button beginShoppingButton;
    @FXML protected AnchorPane searchAnchor;

    @FXML protected AnchorPane showProductsAnchor;
    protected ShowProductController showProductController;

    @FXML private FlowPane varaAvlangFlowPane;

    @FXML private ScrollPane varaAvlangScrollpane;

    @FXML private Label totalPrice;

    @FXML protected Button varukorgCloseButton;

    @FXML protected AnchorPane varukorgPopupAnchor;

    @FXML protected Label sokResultatLabel;
    @FXML protected AnchorPane sokResultatAnchor;
    @FXML protected AnchorPane homePageAnchor;
    @FXML private FlowPane productCardTest;

    @FXML private AnchorPane uppgifterAnchor;

    protected HeaderController mainHeader;
    protected HeaderController iMatButtonHeader;
    protected HeaderController withoutVarukorgHeaderUppgifter;
    protected HeaderController withoutVarukorgHeaderUtcheckning;


    @FXML private AnchorPane utcheckningAnchor;
    @FXML private Button toShoppingCartButton;

    @FXML protected AnchorPane tidigareKopAnchor;

    private HashMap<Integer, ProductCard> productCardHashMap;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private UppgifterController uppgifterController;

    private UtcheckningController utcheckningController;


    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        initProductCardHashMap();

        mainHeader = new HeaderController(this, "self");
        iMatButtonHeader = new HeaderController(this, "withImatMainButton");
        withoutVarukorgHeaderUppgifter = new HeaderController(this, "withoutVarukorgButton");
        withoutVarukorgHeaderUtcheckning = new HeaderController(this, "withoutVarukorgButton");
        uppgifterController = new UppgifterController(this);

        anchorHeader.getChildren().add(mainHeader);

        showProductController = new ShowProductController(this);
        showProductsAnchor.getChildren().add(showProductController);
        menyController = new MenyController(this, showProductController);
        anchorMeny.getChildren().add(menyController);



//        productCardTest.getChildren().add(productCardHashMap.get(1)); // test



        this.tidigareKopAnchor.getChildren().add(new tidigareKopController(this));


        uppgifterAnchor.getChildren().add(uppgifterController);

        utcheckningController = new UtcheckningController(this);
        utcheckningAnchor.getChildren().add(utcheckningController);

        setUpShoppingCart();
        updateVaraAvlang();
        updateTotalPrice();

        closeVarukorg(); // håll den stängd som default

        this.homePageAnchor.toFront();
        this.anchorHeader.toFront();
        this.anchorMeny.toFront();
        this.varukorgPopupAnchor.toFront();
    }


    private void initProductCardHashMap()
    {
        productCardHashMap = new HashMap<Integer, ProductCard>();

        for(Product product : iMatDataHandler.getProducts())
        {

            ProductCard productCard = new ProductCard(this, new ShoppingItem(product, 0));
            productCardHashMap.put(product.getProductId(), productCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(productCard);

        }
        for (ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems())
        {
            ProductCard productCard = new ProductCard(this, shoppingItem);
            productCardHashMap.put(shoppingItem.getProduct().getProductId(), productCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(productCard);
        }

    }

    public HashMap<Integer, ProductCard> getProductMap()
    {
        return productCardHashMap;
    }

    protected void backToHomePage() {
        this.homePageAnchor.toFront();
        this.anchorHeader.toFront();
        this.anchorMeny.toFront();
        this.varukorgPopupAnchor.toFront();
    }

    protected void headerMenyVarukorgToFront() {
        this.anchorHeader.toFront();
        this.anchorMeny.toFront();
        this.varukorgPopupAnchor.toFront();
    }

    protected void openUppgifter() {
        uppgifterAnchor.toFront();
        withoutVarukorgHeaderUppgifter.dinaUppgifterButton.getStyleClass().add("chosen-header-button");
        uppgifterController.fillInDefaults();
    }

    private void setUpShoppingCart() {

    }

    public void showDetailPane(ProductCard productCard)
    {
        // kod för att lägga fram detailplanen
    }

    public void hideDetailPane()
    {
        // kod för att ta bort detailplanen
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
        updateVaraAvlang();
        updateTotalPrice();
        varukorgPopupAnchor.setVisible(true);
        varukorgPopupAnchor.setManaged(true);
    }

    @FXML
    public void openUtcheckning() {
        utcheckningAnchor.toFront();
        utcheckningController.updateVarukorgFlowpane();
        closeVarukorg();
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

        if (openButton != null){
            openButton.setOnAction((ActionEvent evt)->{
                System.out.println(varukorgPopup.getTranslateX());
                openVarukorg();
                if(varukorgPopup.getTranslateX()!=0){
                    System.out.println("opening");
                    openVarukorg();
                    openNav.play();
                }
        });
    }}


    protected void sokPageToFront(){
        sokResultatAnchor.toFront();
        sokResultatAnchor.setVisible(true);
    }

    protected void sokPageToBack(){
        sokResultatAnchor.toBack();
        sokResultatAnchor.setVisible(false);
    }

    protected void homePageToFront(){
        homePageAnchor.toFront();
    }

    protected void homePageToBack(){
        homePageAnchor.toBack();
    }


}