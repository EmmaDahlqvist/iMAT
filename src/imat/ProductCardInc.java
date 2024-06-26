package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;


public class ProductCardInc extends AnchorPane
{
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label numberOfItemsLabel;
    @FXML private Button addItemButton;
    @FXML private Button removeItemButton;


    private Product product;
    private IMatDataHandler imatDataHandler;
    private int currentNumberOfItems;

    public ProductCardInc(Product product)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card_inc.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        imatDataHandler = IMatDataHandler.getInstance();
        this.product = product;
        initialize_product();


    }

    private void initialize_product()
    {
        currentNumberOfItems = 1;
        numberOfItemsLabel.setText(Integer.toString(currentNumberOfItems) + " st");
        productNameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()) + " kr");

        Image image = imatDataHandler.getFXImage(product, 207, 193);
        productImage.setImage(image);
    }

    private void incrementNumberOfItems()
    {
        currentNumberOfItems++;
        numberOfItemsLabel.setText(Integer.toString(currentNumberOfItems));
    }
    private void decrementNumberOfItems()
    {
        currentNumberOfItems--;
        numberOfItemsLabel.setText(Integer.toString(currentNumberOfItems));
    }

}
