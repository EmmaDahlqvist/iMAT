package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class WizardController extends AnchorPane {

    //@FXML private AnchorPane wizardAnchor;
    //wizardAnchor.getChildren().add(new WizardController());

    @FXML
    private Button wizardBackButton;
    @FXML
    private Button wizardStepOneButton;
    @FXML
    private Button wizardStepTwoButton;
    @FXML
    private Button wizardStepThreeButton;
    @FXML
    private Button wizardStepFourButton;
    @FXML
    private Button wizardStepFiveButton;
    @FXML
    private Button wizardNextButton;

    public Button[] wizardSteps;
    public int step = 1;

    public WizardController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wizard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //lägg till wizard-knapparna i listan
        wizardSteps = new Button[5];
        wizardSteps[0] = wizardStepOneButton;
        wizardSteps[1] = wizardStepTwoButton;
        wizardSteps[2] = wizardStepThreeButton;
        wizardSteps[3] = wizardStepFourButton;
        wizardSteps[4] = wizardStepFiveButton;

        //kod som gör nästa-knappen icke-klickbar
        wizardNextButton.getStyleClass().clear(); //tar bort allt eeeeeeh
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-non-clickable", "wizard-back-next-text");

        fillWizardCurrentAndPastSteps();

    }

    public void fillWizardCurrentAndPastSteps(){

        for (int i = 0; i < step; i++){
            Button buttonToLightUp = wizardSteps[i];

            buttonToLightUp.getStyleClass().clear();
            buttonToLightUp.getStyleClass().addAll("button","wizard-button-filled","wizard-button-hoverable");
        }

        wizardSteps[step].getStyleClass().clear();
        wizardSteps[step].getStyleClass().addAll("button","wizard-button","wizard-button-hoverable");
    }
}
