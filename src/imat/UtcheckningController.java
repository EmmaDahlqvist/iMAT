package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

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
    @FXML private TextField portkodTextField;

    @FXML private AnchorPane leveransuppgifterAnchor;
    @FXML private RadioButton leveranstidTenRadioButton;
    @FXML private RadioButton leveranstidElevenRadioButton;
    @FXML private RadioButton leveranstidTwelveRadioButton;

    private MainViewController mainViewController;

    @FXML private TextField kortnummer1;
    @FXML private TextField kortnummer2;
    @FXML private TextField kortnummer3;
    @FXML private TextField kortnummer4;

    @FXML private TextField dateMonth;
    @FXML private TextField dateYear;
    @FXML private TextField cvc;

    private WizardController wizardController;

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

        this.wizardController = new WizardController();

        wizardAnchor.getChildren().add(wizardController);
        this.anchorHeader.getChildren().add(mainViewController.withoutVarukorgHeaderUtcheckning);

        fillInDefaults();

        kortnummerFocus(kortnummer1, kortnummer2);
        kortnummerFocus(kortnummer2, kortnummer3);
        kortnummerFocus(kortnummer3, kortnummer4);

        monthYearFocus(dateMonth, dateYear);
        monthYearFocus(dateYear, cvc);

        setFocusNextButton(firstNameTextField, lastNameTextField);
        setFocusNextButton(lastNameTextField, phonenumberTextField);
        setFocusNextButton(phonenumberTextField, epostTextField);
        setFocusNextButton(epostTextField, adressTextField);
        setFocusNextButton(adressTextField, postnummerTextField);
        setFocusNextButton(postnummerTextField, portkodTextField);

        setFocusNextButton(kortnummer1, kortnummer2);
        setFocusNextButton(kortnummer2, kortnummer3);
        setFocusNextButton(kortnummer3, kortnummer4);
        setFocusNextButton(kortnummer4, dateMonth);
        setFocusNextButton(dateMonth, dateYear);
        setFocusNextButton(dateYear, cvc);
    }

    private Customer customer = IMatDataHandler.getInstance().getCustomer();
    private CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();

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

        if(String.valueOf(creditCard.getValidMonth()) != null) {
            dateMonth.setText(String.valueOf(creditCard.getValidMonth()));
        }

        if(String.valueOf(creditCard.getValidYear()) != null) {
            dateYear.setText(String.valueOf(creditCard.getValidYear()));
        }

        if(String.valueOf(creditCard.getVerificationCode()) != null) {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
        }

    }

    private void monthYearFocus(TextField textField, TextField nextTextField) {
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(((keyEvent.getCode().isDigitKey()))) {
                    try { // testa om de är en int
                        Integer value = Integer.valueOf(textField.getText());
                        if (value >= 1000) { //4siffrigt
                            nextTextField.requestFocus();
                        }
                    } catch (Exception ex) {
                        System.out.println("det är ine en int hörru");
                    }
                }
            }
        });
    }

    private void setFocusNextButton(TextField field, TextField nextField) {
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    nextField.requestFocus();
                }

                if(kortnummer1.getText() != null && kortnummer2.getText() != null && kortnummer3.getText() != null && kortnummer4.getText() != null) {
                    if(dateMonth.getText() != null && dateYear.getText() != null && cvc.getText() != null) {
                        wizardController.step += 1;
                        wizardController.fillWizardCurrentAndPastSteps();
                    }
                }
            }
        });
    }
}
