package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class DetailedProductCard extends AnchorPane
{
    @FXML
    private FlowPane productCardFlowPane;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ecoLabel;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea infoTextArea;


    private ShoppingItem shoppingItem;
    private Product product;
    private IMatDataHandler imatDataHandler;


    public DetailedProductCard(ShoppingItem shoppingItem, ProductCard productCard)
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detailed_product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        imatDataHandler = IMatDataHandler.getInstance();
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();

        productCardFlowPane.getChildren().add(productCard);
        initialize();
    }


    private void initialize()
    {
        ProductDetail detail = imatDataHandler.getDetail(product);
        infoTextArea.setText(detail.getContents());


        productNameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()) + product.getUnit());
        ecoLabel.setText(product.isEcological() ? "Ekologisk" : "Ej ekologisk");



    }


}