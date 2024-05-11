package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenyController extends AnchorPane {

    @FXML private AnchorPane menyKategori;
    @FXML private AnchorPane menyListItem;
    @FXML private Button showAllButton;
    private MainViewController mainViewController;

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

        this.initialize();
    }

    @FXML
    public void initialize() {

    }

}
