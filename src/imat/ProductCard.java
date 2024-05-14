package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;


public class ProductCard extends AnchorPane
{
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label numberOfItemsLabel;
    @FXML private Button purchaseButton;
    @FXML private Button incrementButton;
    @FXML private Button decrementButton;

    private ShoppingItem shoppingItem;
    private Product product;
    private IMatDataHandler imatDataHandler;
    private MainViewController parentController;


    public ProductCard(MainViewController parentController, ShoppingItem shoppingItem)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        imatDataHandler = IMatDataHandler.getInstance();
        this.parentController = parentController;
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();
        this.shoppingItem.setAmount(0);
        initializeProduct();


    }

    private void initializeProduct()
    {
        showPurchaseButton();

        productNameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()) + " kr");

        Image image = imatDataHandler.getFXImage(product, 207, 193);
        productImage.setImage(image);

        double r = 20;
        incrementButton.setShape(new Circle(r));
        incrementButton.setMinSize(2*r, 2*r);
        incrementButton.setMaxSize(2*r, 2*r);

        decrementButton.setShape(new Circle(r));
        decrementButton.setMinSize(2*r, 2*r);
        decrementButton.setMaxSize(2*r, 2*r);
    }

    private void showPurchaseButton()
    {
        purchaseButton.setDisable(false);
        purchaseButton.setOpacity(1);
        incrementButton.setDisable(true);
        incrementButton.setOpacity(0);
        decrementButton.setDisable(true);
        decrementButton.setOpacity(0);
        numberOfItemsLabel.setDisable(true);
        numberOfItemsLabel.setOpacity(0);
    }

    private void showIncrementButtons()
    {
        purchaseButton.setDisable(true);
        purchaseButton.setOpacity(0);
        incrementButton.setDisable(false);
        incrementButton.setOpacity(1);
        decrementButton.setDisable(false);
        decrementButton.setOpacity(1);
        numberOfItemsLabel.setDisable(false);
        numberOfItemsLabel.setOpacity(1);
    }

    @FXML
    private void incrementNumberOfItems()
    {
        addToShoppingCart();
        showIncrementButtons();
        numberOfItemsLabel.setText(String.valueOf((int) shoppingItem.getAmount()));
    }

    @FXML
    private void decrementNumberOfItems()
    {
        removeFromShoppingCart();

        if (shoppingItem.getAmount() == 0)
        {
            showPurchaseButton();
        }
        numberOfItemsLabel.setText(String.valueOf((int) shoppingItem.getAmount()));

    }


    private void addToShoppingCart()
    {
        if (shoppingItem.getAmount() == 0)
        {
            imatDataHandler.getShoppingCart().addItem(shoppingItem);
            shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        }
        else
        {
            shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        }

    }

    private void removeFromShoppingCart()
    {
        if(shoppingItem.getAmount() > 1 )
        {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
        }

        else
        {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            imatDataHandler.getShoppingCart().removeItem(shoppingItem);
        }

    }




}
