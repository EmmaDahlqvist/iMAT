package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.*;

public class MenyController extends AnchorPane {

    @FXML private AnchorPane menyKategori;
    @FXML private AnchorPane menyListItem;
    @FXML private Button showAllButton;
    @FXML private VBox menyItemsVBox;
    private MainViewController mainViewController;
    private List<String> mainCategoryList = new ArrayList<String>(Arrays.asList("Grönt & kryddor", "Frukt & bär", "Kött & mejeri", "Drycker & sötsaker", "Skafferi"));
    private static Map<String, List<String>> categoryRelationMap;
    static {
        categoryRelationMap = new HashMap<String, List<String>>();
        categoryRelationMap.put("Grönt & kryddor", new ArrayList<String>(Arrays.asList("Grönsaker", "Kål", "Rotfrukter", "Kryddor")));
        categoryRelationMap.put("Frukt & bär", new ArrayList<String>(Arrays.asList("Frukter", "Bär", "Citrusfrukter", "Exotiska frukter", "Meloner")));
        categoryRelationMap.put("Kött & mejeri", new ArrayList<String>(Arrays.asList("Kött", "Fisk", "Mejeri & ägg")));
        categoryRelationMap.put("Drycker & sötsaker", new ArrayList<String>(Arrays.asList("Kalla drycker", "Varma drycker", "Sötsaker")));
        categoryRelationMap.put("Skafferi", new ArrayList<String>(Arrays.asList("Bröd", "Bakning", "Pasta", "Potatis & ris", "Baljväxter", "Nötter & frön")));
    }
    private static Map<String, ProductCategory> categoryEnumMap;
    static {
        categoryEnumMap = new HashMap<String, ProductCategory>();
        categoryEnumMap.put("Grönsaker", ProductCategory.VEGETABLE_FRUIT);
        categoryEnumMap.put("Kål", ProductCategory.CABBAGE);
        categoryEnumMap.put("Rotfrukter", ProductCategory.ROOT_VEGETABLE);
        categoryEnumMap.put("Kryddor", ProductCategory.HERB);
        categoryEnumMap.put("Frukter", ProductCategory.FRUIT);
        categoryEnumMap.put("Bär", ProductCategory.BERRY);
        categoryEnumMap.put("Citrusfrukter", ProductCategory.CITRUS_FRUIT);
        categoryEnumMap.put("Exotiska frukter", ProductCategory.EXOTIC_FRUIT);
        categoryEnumMap.put("Meloner", ProductCategory.MELONS);
        categoryEnumMap.put("Kött", ProductCategory.MEAT);
        categoryEnumMap.put("Fisk", ProductCategory.FISH);
        categoryEnumMap.put("Mejeri & ägg", ProductCategory.DAIRIES);
        categoryEnumMap.put("Kalla drycker", ProductCategory.COLD_DRINKS);
        categoryEnumMap.put("Varma drycker", ProductCategory.HOT_DRINKS);
        categoryEnumMap.put("Sötsaker", ProductCategory.SWEET);
        categoryEnumMap.put("Bröd", ProductCategory.BREAD);
        categoryEnumMap.put("Bakning", ProductCategory.FLOUR_SUGAR_SALT);
        categoryEnumMap.put("Pasta", ProductCategory.PASTA);
        categoryEnumMap.put("Potatis & ris", ProductCategory.POTATO_RICE);
        categoryEnumMap.put("Baljväxter", ProductCategory.POD);
        categoryEnumMap.put("Nötter & frön", ProductCategory.NUTS_AND_SEEDS);
    }

    public MenyController(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("meny.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();

//        this.initialize();
    }

    @FXML
    public void initialize() {
        for (String mainCategory : mainCategoryList) {
//            System.out.println(mainCategoryList);
//            System.out.println(categoryRelationMap.get(mainCategory));
//            System.out.println(categoryEnumMap.get("Kött"));
            menyItemsVBox.getChildren().add(new MenyItem(mainCategory, categoryRelationMap.get(mainCategory), this));
        }
    }

}
