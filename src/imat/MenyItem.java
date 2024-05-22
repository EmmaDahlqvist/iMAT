package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenyItem extends AnchorPane {
    @FXML VBox menyListItemsVBox;
    @FXML ToggleButton menyItemToggle;
    @FXML Button menyItemButton;

    private MenyController parentController;
    private String category;
    private List<String> childCategoryList;
    private Map<String, MenyListItem> menyListItemMap = new HashMap<String, MenyListItem>();


    public MenyItem(String category, List<String> childCategoryList, MenyController menyController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menyMainItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.category = category;
        this.childCategoryList = childCategoryList;
        this.parentController = menyController;
        String lineAdjustment = "first";
        for (String childCategory : childCategoryList) {
            if (childCategory.equals(childCategoryList.get(childCategoryList.size() - 1))) { //bytte ut getLast mot detta
                lineAdjustment = "last";
            }
            MenyListItem menyListItem = new MenyListItem(category, childCategory, lineAdjustment, parentController);
            menyListItemMap.put(childCategory, menyListItem);
            lineAdjustment = "middle";
        }

        this.menyItemButton.setText(category);
//        populateMenyListItemVBox();
    }

    private void populateMenyListItemVBox() {
        for (String childCategory : childCategoryList) {
            menyListItemsVBox.getChildren().add(menyListItemMap.get(childCategory));
        }
    }
    private void clearMenyListItemVBox() {
        menyListItemsVBox.getChildren().clear();
    }

    @FXML
    public void toggleOnClick() {
        if (menyItemToggle.isSelected()) {
            populateMenyListItemVBox();
        } else {
            clearMenyListItemVBox();
        }
    }

    @FXML
    public void buttonOnClick() {
        parentController.menyItemClicked(category);
        if (!menyItemToggle.isSelected()) {
            menyItemToggle.setSelected(true);
            populateMenyListItemVBox();
        }
    }


}
