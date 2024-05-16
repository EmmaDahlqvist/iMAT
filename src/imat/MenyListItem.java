package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenyListItem extends AnchorPane {
    private MenyController parentController;
    private String category;
    private String parentCategory;

    @FXML Line topLine;
    @FXML Line bottomLine;
    @FXML Line middleLine;
    @FXML Button menyListItemButton;

    public MenyListItem(String parentCategory, String category, String lineAdjustment, MenyController menyController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menyListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.category = category;
        this.parentCategory = parentCategory;
        this.parentController = menyController;

        this.menyListItemButton.setText(category);

        this.menyListItemButton.toFront();
        if (lineAdjustment.equals("first")) {
            this.topLine.toFront();
        } else if (lineAdjustment.equals("last")) {
            this.bottomLine.toFront();
        } else {
            this.middleLine.toFront();
        }

    }
}
