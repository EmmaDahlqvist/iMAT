package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WizardController extends AnchorPane {

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

    @FXML protected Tooltip wizardBackTooltip;
    @FXML protected Tooltip wizardNextTooltip;


    public Button[] wizardSteps;
    public int step = 0;

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
        //wizardSteps = new Button[5];
        //wizardSteps[0] = wizardStepOneButton;
        //wizardSteps[1] = wizardStepTwoButton;
        //wizardSteps[2] = wizardStepThreeButton;
        //wizardSteps[3] = wizardStepFourButton;
        //wizardSteps[4] = wizardStepFiveButton;

        //kod som gör nästa-knappen icke-klickbar
        wizardNextButton.getStyleClass().clear();
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
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

    public void hoverableNextStep(Button button){
        button.getStyleClass().add("wizard-button-hoverable");
    }

    public void unhoverNextStep(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button","wizard-button");
    }

    public void fillWizardStep(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button","wizard-button-filled","wizard-button-hoverable");
    }

    public void fillWizardNextButton(){
        wizardNextButton.getStyleClass().clear();
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
    }

    public void unfillNextButton(){
        wizardNextButton.getStyleClass().clear();
        wizardNextButton.getStyleClass().addAll("button", "wizard-back-next-non-clickable", "wizard-back-next-text");

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
        if (step == 0){
            utcheckningController.mainViewController.backToOGPage();
        } else if (step == 1) {
            openVarukorgPage();
        } else if (step == 2) {
            openPersonuppgifterPage();
        } else if (step == 3) {
            openLeveransPage();
        } else if (step == 4) {
            openBetalningsPage();
        }
    }

    public void nextButtonFunc(){
        if (step == 0){
            openPersonuppgifterPage();
        } else if (step == 1) {
            openLeveransPage();
        } else if (step == 2) {
            openBetalningsPage();
        } else if (step == 3) {
            openConfirmationPage();
        } else if (step == 4) {
            utcheckningController.openCompletionPage();
        }
    }
}
