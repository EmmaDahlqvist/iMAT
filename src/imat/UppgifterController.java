package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UppgifterController extends AnchorPane {


    @FXML private AnchorPane anchorHeader;

    @FXML private Button saveButton;
    private MainViewController mainViewController;
    public UppgifterController(MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uppgifter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.mainViewController = mainViewController;


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.anchorHeader.getChildren().add(mainViewController.withoutVarukorgHeaderUppgifter);

    }

    @FXML
    private void saveAndClose() {
        mainViewController.backToHomePage();
    }
}
