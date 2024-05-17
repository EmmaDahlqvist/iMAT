package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UtcheckningController extends AnchorPane {

    @FXML private AnchorPane anchorHeader;
    @FXML private AnchorPane wizardAnchor;

    @FXML private AnchorPane personuppgifterAnchor;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phonenumberTextField;
    @FXML private TextField epostTextField;
    @FXML private TextField adressTextField;
    @FXML private TextField postnummerTextField;
    @FXML private TextField coTextField;
    @FXML private TextField portkodTextField;

    @FXML private AnchorPane leveransuppgifterAnchor;

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

        wizardAnchor.getChildren().add(new WizardController());
        this.anchorHeader.getChildren().add(mainViewController.withoutVarukorgHeaderUtcheckning);
    }
}
