package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowProductController extends AnchorPane {
    private MainViewController mainViewController;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    @FXML private AnchorPane anchorScroll;
    @FXML private Button toStartButton1;
    @FXML private Button toStartButton2;
    @FXML private Button toMainCategoryButton;
    @FXML private Label categoryLabel1;
    @FXML private Label categoryLabel2;
    @FXML private Label titleLabel;
    @FXML private HBox oneButtonHBox;
    @FXML private HBox twoButtonHBox;
    private String parentCategory;

    public ShowProductController(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showProducts.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();
    }

    @FXML
    public void initialize() {
//        toStartButton1.setText("Start >");
//        toStartButton2.setText("Start >");
//        System.out.println("hejigen");
//        anchorScroll.getChildren().add(new ProductScrollpaneController(iMatDataHandler.getProducts()));
    }

    private void setLabels(String title) {
        categoryLabel1.setText(title);
        categoryLabel2.setText(title);
        titleLabel.setText(title);
    }
    public void showProducts(String title) {
        setLabels(title);
        oneButtonHBox.toFront();
        anchorScroll.getChildren().add(new ProductScrollpaneController(mainViewController, iMatDataHandler.getProducts()));
    }

    public void showProducts(String title, List<ProductCategory> productCategoryList) {
        setLabels(title);
        oneButtonHBox.toFront();
        List<Product> productList = new ArrayList<Product>();
        for (ProductCategory productCategory : productCategoryList) {
            productList.addAll(iMatDataHandler.getProducts(productCategory));
        }
        anchorScroll.getChildren().add(new ProductScrollpaneController(mainViewController, productList));
    }

    public void showProducts(String mainCategory, String title, ProductCategory productCategory) {
        parentCategory = mainCategory;
        setLabels(title);
        toMainCategoryButton.setText(mainCategory + " >");
        twoButtonHBox.toFront();
        anchorScroll.getChildren().add(new ProductScrollpaneController(mainViewController, iMatDataHandler.getProducts(productCategory)));
    }

    @FXML
    private void startButtonOnClick() {
        mainViewController.backToHomePage();
    }

    @FXML
    private void toParentCategoryOnClick() {
        mainViewController.menyController.menyItemClicked(parentCategory);
    }

}
