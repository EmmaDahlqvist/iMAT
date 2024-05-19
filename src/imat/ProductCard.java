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


public class ProductCard extends AnchorPane implements ShoppingCartListener
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
    private IMatDataHandler iMatDataHandler;
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

        this.parentController = parentController;
        iMatDataHandler = IMatDataHandler.getInstance();
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();
        initializeProduct();


    }

    private void initializeProduct()
    {
        updateShoppingItem();

        productNameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()) + " kr");

        Image image = iMatDataHandler.getFXImage(product, 207, 193);
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
            shoppingItem.setAmount(shoppingItem.getAmount() + 1);
            iMatDataHandler.getShoppingCart().addItem(shoppingItem);
        }
        else
        {
            iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(product, 1));
        }

    }

    private void removeFromShoppingCart()
    {
        if (shoppingItem.getAmount() > 1 )
        {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            iMatDataHandler.getShoppingCart().removeItem(shoppingItem);  // tag bort och lägg till
            iMatDataHandler.getShoppingCart().addItem(shoppingItem);     // så att lyssnare hör
        }

        else
        {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            iMatDataHandler.getShoppingCart().removeItem(shoppingItem);
        }

    }


    public void shoppingCartChanged(CartEvent evt)
    {
        if (shoppingItem.getAmount() == 0)
        {
            showPurchaseButton();
        }
        else
        {
            showIncrementButtons();
            numberOfItemsLabel.setText(String.valueOf((int) shoppingItem.getAmount()));
        }
    }

    public void updateShoppingItem()
    {
        if (shoppingItem.getAmount() == 0)
        {
            showPurchaseButton();
        }
        else
        {
            showIncrementButtons();
            numberOfItemsLabel.setText(String.valueOf((int) shoppingItem.getAmount()));
        }
    }

    public ShoppingItem getShoppingItem()
    {
        return shoppingItem;
    }

    @FXML
    public void showDetailPane()
    {
        parentController.showDetailPane(this);
    }





}
