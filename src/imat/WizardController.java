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
    protected Button wizardBackButton;
    @FXML
    protected Button wizardStepOneButton;
    @FXML
    protected Button wizardStepTwoButton;
    @FXML
    protected Button wizardStepThreeButton;
    @FXML
    protected Button wizardStepFourButton;
    @FXML
    protected Button wizardStepFiveButton;
    @FXML
    protected Button wizardNextButton;

    @FXML
    protected Label varukorgLabel;
    @FXML
    protected Label personLabel;
    @FXML
    protected Label leveransLabel;
    @FXML
    protected Label betalaLabel;
    @FXML
    protected Label confirmLabel;


    public Button[] wizardSteps;
    public int step = 1;

    private UtcheckningController utcheckningController;

    public WizardController(UtcheckningController utcheckningController){
        this.utcheckningController = utcheckningController;

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
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");

        fillWizardCurrentAndPastSteps();

    }

    protected void resetWizardToDefault() {
        wizardStepOneButton.getStyleClass().clear();
        wizardStepOneButton.getStyleClass().addAll("button", "wizard-button", "wizard-button-text", "wizard-button-filled", "wizard-button-hoverable");
        wizardStepTwoButton.getStyleClass().clear();
        wizardStepTwoButton.getStyleClass().addAll("button", "wizard-button", "wizard-button-text", "wizard-button-hoverable");
        resetWizardButtonStyle(wizardStepThreeButton);
        resetWizardButtonStyle(wizardStepFourButton);
        resetWizardButtonStyle(wizardStepFiveButton);

        wizardBackButton.getStyleClass().clear();
        wizardBackButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");

        wizardNextButton.getStyleClass().clear();
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
    }

    private void resetWizardButtonStyle(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button", "wizard-button", "wizard-button-text");
    }

    public void fillWizardCurrentAndPastSteps(){

        for (int i = 0; i < step; i++){
            Button buttonToLightUp = wizardSteps[i];

            buttonToLightUp.getStyleClass().clear();
            buttonToLightUp.getStyleClass().addAll("button","wizard-button-filled","wizard-button-hoverable");
        }
    }

    public void hoverableNextStep(){
        wizardSteps[step].getStyleClass().clear();
        wizardSteps[step].getStyleClass().addAll("button","wizard-button","wizard-button-hoverable");
    }

    public void unhoverNextStep(){
        wizardSteps[step].getStyleClass().clear();
        wizardSteps[step].getStyleClass().addAll("button","wizard-button");
    }

    public void fillWizardButton(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button","wizard-button-filled","wizard-button-hoverable");
    }

    public void fillWizardNextButton(){
        wizardNextButton.getStyleClass().clear();
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
    }

    // Metoder för att öppna de olika stegen i utcheckningen
    public void openVarukorgPage(){
        utcheckningController.openVarukorgPage();
    }

    public void openPersonuppgifterPage(){
        utcheckningController.openPersonuppgifterPage();
    }
    public void openLeveransPage(){
        utcheckningController.openLeveransPage();
    }
    public void openBetalningsPage(){
        utcheckningController.openBetalningsPage();
    }

    public void openConfirmationPage(){
        utcheckningController.openConfirmationPage();
    }

    public void returnButtonFunc(){
        if (step == 1){
            utcheckningController.mainViewController.backToOGPage();
        } else if (step == 2) {
            openVarukorgPage();
        } else if (step == 3) {
            openPersonuppgifterPage();
        } else if (step == 4) {
            openLeveransPage();
        } else if (step == 5) {
            openBetalningsPage();
        }
    }

    public void nextButtonFunc(){
        if (step == 1){
            openPersonuppgifterPage();
        } else if (step == 2) {
            openLeveransPage();
        } else if (step == 3) {
            openBetalningsPage();
        } else if (step == 4) {
            openConfirmationPage();
        } else if (step == 5) {
            utcheckningController.openCompletionPage();
        }
    }
}
