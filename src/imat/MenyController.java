package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenyController extends AnchorPane {

    @FXML private AnchorPane menyKategori;
    @FXML private AnchorPane menyListItem;

    public MenyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("meny.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.requestFocus();

        this.initialize();
    }

    @FXML
    public void initialize() {

    }

}
