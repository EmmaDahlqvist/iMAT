package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UtcheckningController extends AnchorPane {

    @FXML private AnchorPane headerAnchor;

    private MainViewController mainViewController;

    public UtcheckningController(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("utcheckning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.mainViewController = mainViewController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
