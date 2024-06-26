package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

import static imat.MainViewController.round2;

public class UtcheckningController extends AnchorPane implements ShoppingCartListener, UppgifterListener{

    @FXML private AnchorPane anchorHeader;
    @FXML private AnchorPane wizardAnchor;

    // varukorgs page
    @FXML private AnchorPane varukorgWizardAnchor;
    @FXML private ScrollPane varukorgScrollPaneUtcheckning;
    @FXML private FlowPane varukorgFlowPaneUtcheckning;
    @FXML private Label varukorgTotalVarukostnadLabel;
    @FXML private Label varukorgTotalKostnadLabel;
    @FXML private Button varukorgNextButton;

    // personuppgifter page
    @FXML private AnchorPane personuppgifterAnchor;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phonenumberTextField;
    @FXML private TextField epostTextField;
    @FXML private TextField adressTextField;
    @FXML private TextField postnummerTextField;
    @FXML private TextField portkodTextField;
    @FXML private Button personuppgifterNextButton;

    // leverans page
    @FXML private AnchorPane leveransuppgifterAnchor;
    @FXML private ComboBox leveransdagComboBox;

    @FXML private RadioButton leveranstidTenRadioButton;
    @FXML private RadioButton leveranstidTwelveRadioButton;
    @FXML private RadioButton leveranstidTwoRadioButton;
    @FXML private RadioButton leveranstidFourRadioButton;
    String selectedLeveranstid;
    String leveransdag = "Idag";
    @FXML private TextArea meddelandeTextArea;
    @FXML private Button leveransNextButton;


    // betalnings page
    @FXML private AnchorPane betalningAnchor;
    @FXML private TextField kortnummer1;
    @FXML private TextField kortnummer2;
    @FXML private TextField kortnummer3;
    @FXML private TextField kortnummer4;

    @FXML private TextField dateMonth;
    @FXML private TextField dateYear;
    @FXML private TextField cvc;
    @FXML private Label betalningTotalVarukostnadLabel;
    @FXML private Label betalningTotalKostnadLabel;
    @FXML private Button betalningNextButton;

    //confirmation page
    @FXML private AnchorPane confirmationAnchor;
    @FXML private Button changePersonuppgifterButton;
    @FXML private Label confirmationNameLabel;
    @FXML private Label confirmationPhoneNumberLabel;
    @FXML private Label confirmationEpostLabel;
    @FXML private Label confirmationAdressLabel;
    @FXML private Button changeLeveransButton;
    @FXML private Label confirmationLeveransLabel;
    @FXML private Button changeBetalningButton;
    @FXML private Label confirmationCardNumberLabel;
    @FXML private Label confirmationCardDateLabel;
    @FXML private Label confirmationTotalVarukostnadLabel;
    @FXML private Label confirmationTotalKostnadLabel;
    public Button[] utcheckningNextButtons;
    protected WizardController wizardController;
    protected MainViewController mainViewController;

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

        this.wizardController = new WizardController(this);

        wizardAnchor.getChildren().add(wizardController);
        this.anchorHeader.getChildren().add(mainViewController.withoutVarukorgHeaderUtcheckning);

        //fillInDefaults();

//        kortnummerFocus(kortnummer1, kortnummer2);
//        kortnummerFocus(kortnummer2, kortnummer3);
//        kortnummerFocus(kortnummer3, kortnummer4);
//        kortnummerFocus(kortnummer4, dateMonth);

        monthYearFocus(dateMonth, dateYear);
        monthYearFocus(dateYear, cvc);

        setFocusNextButton(firstNameTextField, lastNameTextField);
        setFocusNextButton(lastNameTextField, phonenumberTextField);
        setFocusNextButton(phonenumberTextField, epostTextField);
        setFocusNextButton(epostTextField, adressTextField);
        setFocusNextButton(adressTextField, postnummerTextField);
        setFocusNextButton(postnummerTextField, portkodTextField);
        setFocusNextButton(portkodTextField, firstNameTextField);

        setFocusNextButton(kortnummer1, kortnummer2);
        setFocusNextButton(kortnummer2, kortnummer3);
        setFocusNextButton(kortnummer3, kortnummer4);
        setFocusNextButton(kortnummer4, dateMonth);
        setFocusNextButton(dateMonth, dateYear);
        setFocusNextButton(dateYear, cvc);
        setFocusNextButton(cvc, kortnummer1);
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);

        utcheckningNextButtons = new Button[5];
        utcheckningNextButtons[0] = varukorgNextButton;
        utcheckningNextButtons[1] = personuppgifterNextButton;
        utcheckningNextButtons[2] = leveransNextButton;
        utcheckningNextButtons[3] = betalningNextButton;


        initRadioButtons();
    }

    public void initRadioButtons(){

        leveransdagComboBox.getItems().addAll("Idag", "Imorgon", "Övermorgon");

        leveransdagComboBox.getSelectionModel().select("Idag");

        leveransdagComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                leveransdag = newValue;
            }
        });

        ToggleGroup leveranstidToggleGroup = new ToggleGroup();

        leveranstidTenRadioButton.setToggleGroup(leveranstidToggleGroup);
        leveranstidTwelveRadioButton.setToggleGroup(leveranstidToggleGroup);
        leveranstidTwoRadioButton.setToggleGroup(leveranstidToggleGroup);
        leveranstidFourRadioButton.setToggleGroup(leveranstidToggleGroup);

        leveranstidTenRadioButton.setSelected(true);
        selectedLeveranstid = leveranstidTenRadioButton.getText();

        leveranstidToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                if(leveranstidToggleGroup.getSelectedToggle() != null){
                    RadioButton selected = (RadioButton) leveranstidToggleGroup.getSelectedToggle();
                    selectedLeveranstid = selected.getText();
                }
            }
        });
    }

    public void shoppingCartChanged(CartEvent evt)
    {
        updateVarukorgFlowpane();

        if(IMatDataHandler.getInstance().getShoppingCart().getItems().isEmpty()) {
            wizardController.unhoverNextStep(wizardController.wizardStepTwoButton);
            unfillNextButton(varukorgNextButton);
            wizardController.unfillNextButton();
        }
    }

    private void addToVarukorgFlowpane(ShoppingItem shoppingItem) {
        varukorgFlowPaneUtcheckning.getChildren().add(new VaraAvlang(shoppingItem, mainViewController));
    }

    protected void updateVarukorgFlowpane() {
        varukorgFlowPaneUtcheckning.getChildren().clear();
        for (ShoppingItem shoppingItem : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            varukorgFlowPaneUtcheckning.getChildren().add(new VaraAvlang(shoppingItem, mainViewController));
        }

        varukorgTotalVarukostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 0, 2))+ "kr");
        if(IMatDataHandler.getInstance().getShoppingCart().getTotal() == 0) {
            varukorgTotalKostnadLabel.setText( 0 + " kr");
        } else {
            varukorgTotalKostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 50, 2))+ "kr");
        }
    }


    private Customer customer = IMatDataHandler.getInstance().getCustomer();
    private CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();

    //public void fillPagesNextButton(){
      //  Button nextButtonToLightUp = utcheckningNextButtons[wizardController.step];

        //nextButtonToLightUp.getStyleClass().clear();
        //nextButtonToLightUp.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
    //}

    public void unfillNextButton(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button", "wizard-back-next-non-clickable", "wizard-back-next-text");
    }

    @FXML
    public void openVarukorgPage(){
        if(!IMatDataHandler.getInstance().getShoppingCart().getItems().isEmpty()) {
            wizardController.step = 0;
            wizardController.wizardNextButton.setVisible(true);
            wizardController.hoverableNextStep(wizardController.wizardStepTwoButton);
            fillNextButton(varukorgNextButton);
            wizardController.fillWizardNextButton();
            anchorHeader.getChildren().clear();
            anchorHeader.getChildren().add(mainViewController.withoutVarukorgHeaderUtcheckning);
            setBold(wizardController.varukorgLabel);
            wizardController.wizardBackTooltip.setText("Återvänd till föregående sida");
            varukorgWizardAnchor.toFront();
        }
    }

    private void setBold(Label label) {

        wizardController.varukorgLabel.setStyle("-fx-font-weight: normal");
        wizardController.personLabel.setStyle("-fx-font-weight: normal");
        wizardController.leveransLabel.setStyle("-fx-font-weight: normal");
        wizardController.betalaLabel.setStyle("-fx-font-weight: normal");
        wizardController.confirmLabel.setStyle("-fx-font-weight: normal");

        label.setStyle("-fx-font-weight: bold");
    }

    @FXML
    public void openPersonuppgifterPage(){
        wizardController.wizardNextButton.setVisible(true);
        if (!IMatDataHandler.getInstance().getShoppingCart().getItems().isEmpty()) {
            wizardController.step = 1;
            wizardController.fillWizardStep(wizardController.wizardStepTwoButton);
            anchorHeader.getChildren().clear();
            anchorHeader.getChildren().add(mainViewController.iMatButtonHeader);
            setBold(wizardController.personLabel);
            if (!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !phonenumberTextField.getText().isEmpty()&& !epostTextField.getText().isEmpty() && !adressTextField.getText().isEmpty() && !postnummerTextField.getText().isEmpty() ){
                fillNextButton(personuppgifterNextButton);
                wizardController.hoverableNextStep(wizardController.wizardStepThreeButton);
                wizardController.fillWizardNextButton();
            } else {
                wizardController.unfillNextButton();
            }
            wizardController.wizardBackTooltip.setText("Återvänd till föregående steg");
            personuppgifterAnchor.toFront();
        }
    }
    @FXML
    public void openLeveransPage(){
        wizardController.wizardNextButton.setVisible(true);
        //wizardController.wizardStepThreeButton.getStyleClass().add("wizard-button-hoverable");
        if(wizardController.step >= 1 && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !phonenumberTextField.getText().isEmpty()&& !epostTextField.getText().isEmpty() && !adressTextField.getText().isEmpty() && !postnummerTextField.getText().isEmpty()) {
            wizardController.step = 2;
            wizardController.fillWizardStep(wizardController.wizardStepThreeButton);
            setBold(wizardController.leveransLabel);
            wizardController.hoverableNextStep(wizardController.wizardStepFourButton);
            wizardController.fillWizardNextButton();
            leveransuppgifterAnchor.toFront();
        }
    }

    @FXML
    public void openBetalningsPage(){
        wizardController.wizardNextButton.setVisible(true);
        if (wizardController.step >= 2 && leveransdag != null) { // todo: lägg till krav som checkar att man valt ett alternativ från comboboxen
            wizardController.step = 3;
            wizardController.fillWizardStep(wizardController.wizardStepFourButton);
            setBold(wizardController.betalaLabel);

            betalningTotalVarukostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 0, 2))+ "kr");
            betalningTotalKostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 50, 2))+ "kr");
            if (!kortnummer1.getText().isEmpty() && !kortnummer2.getText().isEmpty() && !kortnummer3.getText().isEmpty() && !kortnummer4.getText().isEmpty() && !dateMonth.getText().isEmpty() && !dateYear.getText().isEmpty() && !cvc.getText().isEmpty()){
                fillNextButton(betalningNextButton);
                wizardController.hoverableNextStep(wizardController.wizardStepFiveButton);
            } else {
                wizardController.unfillNextButton();
            }
            betalningAnchor.toFront();
        }


    }

    @FXML
    public void openConfirmationPage(){
        if(wizardController.step >= 3 && !kortnummer1.getText().isEmpty() && !kortnummer2.getText().isEmpty() && !kortnummer3.getText().isEmpty() && !kortnummer4.getText().isEmpty() && !dateMonth.getText().isEmpty() && !dateYear.getText().isEmpty() && !cvc.getText().isEmpty()) {
            wizardController.step = 4;
            wizardController.wizardNextButton.setVisible(false);

            wizardController.fillWizardStep(wizardController.wizardStepFiveButton);

            confirmationNameLabel.setText(firstNameTextField.getText() + " " + lastNameTextField.getText());
            confirmationPhoneNumberLabel.setText(phonenumberTextField.getText());
            confirmationEpostLabel.setText(epostTextField.getText());
            confirmationAdressLabel.setText(adressTextField.getText());

            confirmationLeveransLabel.setText(leveransdag + " " + selectedLeveranstid);

            confirmationCardNumberLabel.setText("XXXX-XXXX-" + kortnummer3.getText() + "-" + kortnummer4.getText());
            confirmationCardDateLabel.setText(dateMonth.getText() + "/" + dateYear.getText());

            confirmationTotalVarukostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 0, 2))+ "kr");
            confirmationTotalKostnadLabel.setText(String.valueOf(round2(IMatDataHandler.getInstance().getShoppingCart().getTotal() + 50, 2)) + "kr");
            setBold(wizardController.confirmLabel);

            confirmationAnchor.toFront();
        }
    }

    public void openCompletionPage(){
        mainViewController.completionAnchor.toFront();
        mainViewController.completionAnchor.getChildren().add(new CompleteController(mainViewController, firstNameTextField.getText(),leveransdag, selectedLeveranstid, adressTextField.getText() ));

    }

    public void fillNextButton(Button button){
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button", "wizard-back-next-button", "wizard-back-next-text");
        //System.out.println("printing the next button: " + button.getText());
    }

    public void resetWizardToDefault() {
        wizardController.resetWizardToDefault();

    }


    @FXML
    public void placeOrder() {
        Order order = IMatDataHandler.getInstance().placeOrder();
        mainViewController.initProductCardHashMap();
        openCompletionPage();
        resetWizardToDefault();
        IMatDataHandler.getInstance().shutDown();
        mainViewController.tidigareKopController.flowpane.getChildren().clear();
        mainViewController.tidigareKopController.initalize();
    }

    protected void fillInDefaults() {

        if(customer.getFirstName() != null) {
            firstNameTextField.setText(customer.getFirstName());
        }
        if(customer.getLastName() != null) {
            lastNameTextField.setText(customer.getLastName());
        }
        if(customer.getEmail() != null) {
            epostTextField.setText(customer.getEmail());
        }
        if(customer.getAddress() != null) {
            adressTextField.setText(customer.getAddress());
        }
        if(customer.getPhoneNumber() != null) {
            phonenumberTextField.setText(customer.getPhoneNumber());
        }
        if(customer.getPostCode() != null) {
            portkodTextField.setText(customer.getPostCode());
        }
        if(customer.getPostAddress() != null) {
            postnummerTextField.setText(customer.getPostAddress());
        }


        if(creditCard.getCardNumber() != null){
            String[] number = creditCard.getCardNumber().split(" ");
            int amount = 0;
            for(String num : number) {
                if(amount == 0) {
                    kortnummer1.setText(num);
                } else if(amount == 1) {
                    kortnummer2.setText(num);
                } else if(amount == 2) {
                    kortnummer3.setText(num);
                } else if (amount == 3) {
                    kortnummer4.setText(num);
                    amount = -1;
                }
                amount++;
            }
        }

//        if(String.valueOf(creditCard.getValidMonth()) != null) {
//            dateMonth.setText(String.valueOf(creditCard.getValidMonth()));
//        }
//
//        if(String.valueOf(creditCard.getValidYear()) != null) {
//            dateYear.setText(String.valueOf(creditCard.getValidYear()));
//        }
//
//        if(String.valueOf(creditCard.getVerificationCode()) != null) {
//            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
//        }

        if(String.valueOf(creditCard.getValidMonth()) != null) {
            dateMonth.setText(String.valueOf(creditCard.getValidMonth()));
            if(creditCard.getValidMonth() == -1) {
                dateMonth.setText("");
            }
        }

        if(String.valueOf(creditCard.getValidYear()) != null) {
            dateYear.setText(String.valueOf(creditCard.getValidYear()));
            if(creditCard.getValidYear() == -1) {
                dateYear.setText("");
            }
        }

        if(String.valueOf(creditCard.getVerificationCode()) != null) {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
            if(creditCard.getVerificationCode() == -1) {
                cvc.setText("");
            }
        }

    }

    private void monthYearFocus(TextField textField, TextField nextTextField) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(((keyEvent.getCode().isDigitKey()))) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(textField.getText());
                        if (textField.getText().length() >= 2) { //4siffrigt
                            nextTextField.requestFocus();
                        }
                    } catch (Exception ex) {
                        System.out.println("det är ine en int hörru");
                    }
                }
            }
        });
    }

    private void kortnummerFocus(TextField textField, TextField nextTextField) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(((event.getCode().isDigitKey()))) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(textField.getText());
                        if (value >= 1000) { //4siffrigt

                            nextTextField.requestFocus();
                        }
                    } catch (Exception ex) {}
                }
            }
        });

    }

    private void setFocusNextButton(TextField field, TextField nextField) {
        field.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if(((keyEvent.getCode().isDigitKey()) && kortnummer1.isFocused())) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(kortnummer1.getText());
                        if (value >= 1000) { //4siffrigt

                            kortnummer2.requestFocus();
                        }
                    } catch (Exception ex) {}
                }

                if(((keyEvent.getCode().isDigitKey()) && kortnummer2.isFocused())) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(kortnummer2.getText());
                        if (value >= 1000) { //4siffrigt

                            kortnummer3.requestFocus();
                        }
                    } catch (Exception ex) {}
                }

                if(((keyEvent.getCode().isDigitKey()) && kortnummer3.isFocused())) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(kortnummer3.getText());
                        if (value >= 1000) { //4siffrigt

                            kortnummer4.requestFocus();
                        }
                    } catch (Exception ex) {}
                }

                if(((keyEvent.getCode().isDigitKey()) && kortnummer4.isFocused())) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(kortnummer4.getText());
                        if (value >= 1000) { //4siffrigt

                            dateMonth.requestFocus();
                        }
                    } catch (Exception ex) {}
                }



                //kortnummerFocus(kortnummer1, kortnummer2, keyEvent);
                //kortnummerFocus(kortnummer2, kortnummer3, keyEvent);
                //kortnummerFocus(kortnummer3, kortnummer4, keyEvent);

                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    nextField.requestFocus();
                }

                if( wizardController.step == 1 && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !phonenumberTextField.getText().isEmpty() && !epostTextField.getText().isEmpty() && !adressTextField.getText().isEmpty() && !postnummerTextField.getText().isEmpty()){
                    wizardController.hoverableNextStep(wizardController.wizardStepThreeButton);
                    fillNextButton(personuppgifterNextButton);
                    wizardController.fillWizardNextButton();
                }

                if(wizardController.step == 2 && !leveransdag.isEmpty() && !selectedLeveranstid.isEmpty()){
                    wizardController.hoverableNextStep(wizardController.wizardStepFourButton);
                    fillNextButton(leveransNextButton);
                    wizardController.fillWizardNextButton();
                }

                if(wizardController.step == 3 && !kortnummer1.getText().isEmpty() && !kortnummer2.getText().isEmpty() && !kortnummer3.getText().isEmpty() && !kortnummer4.getText().isEmpty() && !dateMonth.getText().isEmpty() && !dateYear.getText().isEmpty() && !cvc.getText().isEmpty()) {
                    wizardController.hoverableNextStep(wizardController.wizardStepFiveButton);
                    fillNextButton(betalningNextButton);
                    wizardController.fillWizardNextButton();
                }

                // kolla att man inte råkat ta bort en hel field i personupg
                if( wizardController.step == 1 && (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || phonenumberTextField.getText().isEmpty() || epostTextField.getText().isEmpty() || adressTextField.getText().isEmpty() || postnummerTextField.getText().isEmpty())){
                    wizardController.unhoverNextStep(wizardController.wizardStepThreeButton);
                    unfillNextButton(personuppgifterNextButton);
                    wizardController.unfillNextButton();
                }

                // kolla att man inte råkat ta bort en hel field i betalning
                if(wizardController.step == 3 && (kortnummer1.getText().isEmpty() || kortnummer2.getText().isEmpty() || kortnummer3.getText().isEmpty() || kortnummer4.getText().isEmpty() || dateMonth.getText().isEmpty() || dateYear.getText().isEmpty() || cvc.getText().isEmpty())){
                    wizardController.unhoverNextStep(wizardController.wizardStepFiveButton);
                    unfillNextButton(betalningNextButton);
                    wizardController.unfillNextButton();
                }
            }
        });
    }

    @Override
    public void updateUppgifter() {
        System.out.println("uppdaterar utcheckning");
        fillInDefaults();
    }
}
