package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;


public class ProductCard extends AnchorPane
{
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Button purchaseButton;



    public ProductCard()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }


}
