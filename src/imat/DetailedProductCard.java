package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
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
    private Label numberOfItemsLabel;
    @FXML
    private Button closeButton;

    private ShoppingItem shoppingItem;
    private Product product;
    private IMatDataHandler imatDataHandler;




}
