package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

public class VaraAvlang extends AnchorPane{

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label totalPrice;

    @FXML private Button addButton;
    @FXML private Button removeButton;


    @FXML private TextField amountTextField;

    private Product product;
    private ShoppingItem shoppingItem;
    private MainViewController parentController;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    public VaraAvlang(ShoppingItem shoppingItem, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vara_avlang.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.product = shoppingItem.getProduct();
        this.shoppingItem = shoppingItem;

        this.parentController = parentController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
    }

    @FXML
    public void initialize() {
        setProductImage();
        setProductName();
        setProductPrice();
        setTotalPrice();
        setProductAmount();

        updateAmountTextField();

        double r = 20;
        removeButton.setShape(new Circle(r));
        removeButton.setMinSize(2*r, 2*r);
        removeButton.setMaxSize(2*r, 2*r);

        addButton.setShape(new Circle(r));
        addButton.setMinSize(2*r, 2*r);
        addButton.setMaxSize(2*r, 2*r);
    }

    private void setProductName() {
        this.productName.setText(product.getName());
    }

    private void setProductPrice() {
        this.productPrice.setText(product.getPrice() + " " + product.getUnit());
    }
    private void setProductImage() {
        this.productImage.setImage(iMatDataHandler.getFXImage(iMatDataHandler.getProduct(product.getProductId())));
    }

    private void updateAmount() {
        setProductAmount();
        setTotalPrice();
        parentController.updateTotalPrice();
    }

    private void setProductAmount() {
        this.amountTextField.setText(String.valueOf((int) shoppingItem.getAmount()));

    }

    private void setTotalPrice() {
        this.totalPrice.setText(String.valueOf(shoppingItem.getAmount()*product.getPrice() + " kr"));
    }

    @FXML
    private void addProduct() {
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        updateAmount();
    }

    @FXML
    private void removeProduct() {
        if(shoppingItem.getAmount() > 1 ) {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1); //minska amount
        } else {
            iMatDataHandler.getShoppingCart().removeItem(shoppingItem); //ta bort varan
            parentController.updateVaraAvlang();
        }
        updateAmount();
    }

    private void updateAmountTextField() {
        amountTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {

                } else {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(amountTextField.getText());
                        if(value >= 0) {
                            shoppingItem.setAmount(value);
                        }
                        if(value == 0) {
                            removeProduct();
                        }

                        updateAmount();
                    } catch (Exception ex) {
                        System.out.println("det är ine en int hörru");
                        updateAmount();
                    }
                }

            }
        });

        amountTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    addButton.requestFocus();
                }
            }
        });
    }


}
