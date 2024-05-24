
package imat;

import java.net.URL;
import java.util.*;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable, ShoppingCartListener{

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

    @FXML protected AnchorPane uppgifterAnchor;

    protected HeaderController mainHeader;
    protected HeaderController iMatButtonHeader;
    protected HeaderController withoutVarukorgHeaderUppgifter;
    protected HeaderController withoutVarukorgHeaderUtcheckning;


    @FXML private AnchorPane utcheckningAnchor;
    @FXML private Button toShoppingCartButton;

    @FXML protected AnchorPane tidigareKopAnchor;

    @FXML protected AnchorPane detailViewAnchorPane;
    @FXML protected AnchorPane detailViewParentAnchorPane;

    private HashMap<Integer, ProductCard> productCardHashMap;
    private HashMap<Integer, DetailedProductCard> detailedProductCardHashMap;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private UppgifterController uppgifterController;

    protected UtcheckningController utcheckningController;

    @FXML protected AnchorPane completionAnchor;

    @FXML protected Button toUtcheckningButton;
    @FXML protected Tooltip tooltipVarukorgPopUP;

    protected tidigareKopController tidigareKopController;

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
        // productCardTest.getChildren().add(detailedProductCardHashMap.get(1)); // test


        this.tidigareKopController = new tidigareKopController(this);

        this.tidigareKopAnchor.getChildren().add(tidigareKopController);


        uppgifterAnchor.getChildren().add(uppgifterController);

        utcheckningController = new UtcheckningController(this);
        utcheckningAnchor.getChildren().add(utcheckningController);

        uppgifterController.addUppgifterListener(utcheckningController);

        setUpShoppingCart();
        updateVaraAvlang();
        updateTotalPrice();

        closeVarukorg(); // håll den stängd som default

        this.homePageAnchor.toFront();
        this.anchorHeader.toFront();
        this.anchorMeny.toFront();
        this.varukorgPopupAnchor.toFront();

        iMatDataHandler.getShoppingCart().addShoppingCartListener(this);

        updateVarukorgenButton();
    }

    public void backToOGPage(){
        utcheckningAnchor.toBack();
    }

    protected void initProductCardHashMap()
    {
        productCardHashMap = new HashMap<Integer, ProductCard>();
        detailedProductCardHashMap = new HashMap<Integer, DetailedProductCard>();


        for(Product product : iMatDataHandler.getProducts())
        {
            ShoppingItem shoppingItem = new ShoppingItem(product, 0);
            ProductCard productCard = new ProductCard(this, shoppingItem);
            ProductCard tmpProductCard = new ProductCard(this, shoppingItem);

            DetailedProductCard detailedProductCard = new DetailedProductCard(this, shoppingItem, tmpProductCard);
            productCardHashMap.put(product.getProductId(), productCard);
            detailedProductCardHashMap.put(product.getProductId(), detailedProductCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(productCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(tmpProductCard);

        }
        for (ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems())
        {
            ProductCard productCard = new ProductCard(this, shoppingItem);
            ProductCard tmpProductCard = new ProductCard(this, shoppingItem);
            DetailedProductCard detailedProductCard = new DetailedProductCard(this, shoppingItem, tmpProductCard);
            productCardHashMap.put(shoppingItem.getProduct().getProductId(), productCard);
            detailedProductCardHashMap.put(shoppingItem.getProduct().getProductId(), detailedProductCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(productCard);
            iMatDataHandler.getShoppingCart().addShoppingCartListener(tmpProductCard);
        }

    }

    public HashMap<Integer, ProductCard> getProductMap()
    {
        return productCardHashMap;
    }

    public HashMap<Integer, DetailedProductCard> getDetailedProductMap()
    {
        return detailedProductCardHashMap;
    }

    public ProductCard createProductCard(int productId)
    {
        ShoppingItem shoppingItem = productCardHashMap.get(productId).getShoppingItem();
        ProductCard productCard = new ProductCard(this, shoppingItem);
        iMatDataHandler.getShoppingCart().addShoppingCartListener(productCard);
        return productCard;
    }

    protected void backToHomePage() {
        this.homePageAnchor.toFront();
        this.anchorHeader.toFront();
        this.anchorMeny.toFront();
        this.varukorgPopupAnchor.toFront();
    }

    @FXML
    public void toStartSearchButton() {
        backToHomePage();
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

    @FXML
    public void startShoppingOnClick() {
        showProductController.showProducts("Alla varor");
    }
    private void setUpShoppingCart() {
    }

    public void showDetailPane(ProductCard productCard)
    {
        // kod för att lägga fram detailplanen
        int index = productCard.getShoppingItem().getProduct().getProductId();
        detailViewParentAnchorPane.toFront();
        detailViewParentAnchorPane.setDisable(false);
        detailViewParentAnchorPane.setVisible(true);
        detailedProductCardHashMap.get(index).getProductCard().hideInfoButton();
        detailViewAnchorPane.getChildren().add(detailedProductCardHashMap.get(index));

    }

    public void hideDetailPane()
    {
        // kod för att ta bort detailplanen
        detailViewParentAnchorPane.toBack();
        detailViewParentAnchorPane.setDisable(true);
        detailViewParentAnchorPane.setVisible(false);
        detailViewAnchorPane.getChildren().clear();
    }

    //körs för att uppdatera vara avlång listan
    protected void updateVaraAvlang() {
        varaAvlangFlowPane.getChildren().clear();
        for (ShoppingItem shoppingItem : reverseArrayList(iMatDataHandler.getShoppingCart().getItems())) {
            varaAvlangFlowPane.getChildren().add(new VaraAvlang(shoppingItem, this));
        }
    }

    private ArrayList<ShoppingItem> reverseArrayList(List<ShoppingItem> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<ShoppingItem> revArrayList = new ArrayList<ShoppingItem>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    //kalla den för att uppdatera totalpriset i varukorgen
    protected void updateTotalPrice() {
        totalPrice.setText(String.valueOf(round2(iMatDataHandler.getShoppingCart().getTotal(),2) + " kr"));
    }

    public static float round2(double number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
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
        if(!iMatDataHandler.getShoppingCart().getItems().isEmpty()) {
            utcheckningAnchor.toFront();
            utcheckningController.updateVarukorgFlowpane();
            closeVarukorg();
            utcheckningController.openVarukorgPage();
            utcheckningController.fillInDefaults();
        }
    }

    @FXML //används i varukorgen för att kunna klicka utanför o stänga fönstret
    public void mouseTrap(Event event) {
        event.consume();
    }

    @FXML private AnchorPane varukorgPopup;


    @FXML private void goToBread() {
        menyController.menyListItemClicked("Skafferi", "Bröd");
    }


    private boolean firstPrepare = true;
    protected void prepareSlideMenuAnimation(Button closeButton, Button openButton) {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), varukorgPopup);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), varukorgPopup);

        if (firstPrepare) {
            varukorgPopup.setTranslateX(554); //behöver ändras första gången
            firstPrepare = false;
        }

        closeButton.setOnAction((ActionEvent evt) -> {
            if (!(varukorgPopup.getTranslateX() != 0)) {
                closeNav.setToX(+(varukorgPopup.getWidth()));
                closeNav.setOnFinished(event -> closeVarukorg());
                closeNav.play();
            }
        });

        if (openButton != null) {
            openButton.setOnAction((ActionEvent evt) -> {
                System.out.println(varukorgPopup.getTranslateX());
                openVarukorg();
                if (varukorgPopup.getTranslateX() != 0) {
                    System.out.println("opening");
                    openVarukorg();
                    openNav.play();
                }
            });

        }
    }


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

    private void updateVarukorgenButton() {
        if(iMatDataHandler.getShoppingCart().getItems().isEmpty()) {
            toUtcheckningButton.getStyleClass().clear();
            toUtcheckningButton.getStyleClass().addAll("button", "till-varukogen-button-disabled");
            tooltipVarukorgPopUP.setText("Inga varor i varukorgen");

        } else {
            toUtcheckningButton.getStyleClass().clear();
            toUtcheckningButton.getStyleClass().addAll("button", "till-varukogen-button");
            tooltipVarukorgPopUP.setText("Gå till varukorgen");
        }
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateVarukorgenButton();
    }
}