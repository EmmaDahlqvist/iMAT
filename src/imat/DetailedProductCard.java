package imat;

import com.sun.tools.javac.Main;
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
    private Label ecoLabel;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea infoTextArea;
    @FXML
    private Button detailPaneCloseButton;
    @FXML
    private MainViewController parentController;

    private ShoppingItem shoppingItem;
    private Product product;
    private IMatDataHandler imatDataHandler;
    private ProductCard productCard;

    public DetailedProductCard(MainViewController parentController, ShoppingItem shoppingItem, ProductCard productCard)
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detailed_product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        imatDataHandler = IMatDataHandler.getInstance();
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();
        this.productCard = productCard;

        productCardFlowPane.getChildren().add(productCard);
        initialize();
    }


    private void initialize()
    {
        ProductDetail detail = imatDataHandler.getDetail(product);
        infoTextArea.setText(detail.getContents());


        productNameLabel.setText(product.getName());
        ecoLabel.setText(product.isEcological() ? "Ekologisk" : "Ej ekologisk");



    }

    public ProductCard getProductCard()
    {
        return productCard;
    }

    @FXML
    public void closeDetailPane()
    {
        parentController.hideDetailPane();
    }


}
