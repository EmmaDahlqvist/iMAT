package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;

public class UppgifterController extends AnchorPane {

    private ArrayList<UppgifterListener> uppgifterListeners = new ArrayList<>();

    protected void addUppgifterListener(UppgifterListener uppgifterListener) {
        uppgifterListeners.add(uppgifterListener);
    }


    @FXML private AnchorPane anchorHeader;

    @FXML private Button saveButton;

    @FXML private TextField name;
    @FXML private TextField lastname;
    @FXML private TextField epost;
    @FXML private TextField phonenumber;

    @FXML private TextField cvc;
    @FXML private TextField datemonth;
    @FXML private TextField dateyear;
    @FXML private TextField kortnummer1;
    @FXML private TextField kortnummer2;
    @FXML private TextField kortnummer3;
    @FXML private TextField kortnummer4;

    @FXML private TextField adress;
    @FXML private TextField postnummer;
    @FXML private TextField portkod;

    @FXML private AnchorPane areYouSureAboutDat;

    private MainViewController mainViewController;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
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
        mainViewController.withoutVarukorgHeaderUppgifter.addButtonPushedListener(this); // lyssna efter att en knapp i headern trycks på

        fillInDefaults();

        setFocusNextButton(name, lastname);
        setFocusNextButton(lastname, phonenumber);
        setFocusNextButton(phonenumber, epost);
        setFocusNextButton(epost, kortnummer1);
        setFocusNextButton(kortnummer1, kortnummer2);
        setFocusNextButton(kortnummer2, kortnummer3);
        setFocusNextButton(kortnummer3, kortnummer4);
        setFocusNextButton(kortnummer4, datemonth);
        setFocusNextButton(datemonth, dateyear);
        setFocusNextButton(dateyear, cvc);
        setFocusNextButton(cvc, adress);
        setFocusNextButton(adress, postnummer);
        setFocusNextButton(postnummer, portkod);

        kortnummerFocus(kortnummer1, kortnummer2);
        kortnummerFocus(kortnummer2, kortnummer3);
        kortnummerFocus(kortnummer3, kortnummer4);
        kortnummerFocus(kortnummer4, datemonth);

        monthYearFocus(datemonth, dateyear);
        monthYearFocus(dateyear, cvc);

    }




    @FXML
    private void saveAndClose() {
        saveUppgifter();
        mainViewController.uppgifterAnchor.toBack();
    }

    protected void villDuSpara() {
        areYouSureAboutDat.toFront();
    }


    @FXML private void saveAndContinue() {
        saveUppgifter();
        areYouSureAboutDat.toBack();
        continueAfterClick();
    }
    @FXML
    private void dontSaveContinue() {
        areYouSureAboutDat.toBack();
        continueAfterClick();
    }

    protected boolean interruptionTriggered = false;
    private void continueAfterClick() {
        if(mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted.equals("backToHomePage")) {
            mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted = "";
            mainViewController.withoutVarukorgHeaderUppgifter.backToHomePage();
        }

        if(mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted.equals("onSearch")) {
            mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted = "";
            mainViewController.withoutVarukorgHeaderUppgifter.onSearch();
        }

        //FIXA FÖR TIDIGARE KÖP OXÅ
        if(mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted.equals("openTidigareKop")) {
            mainViewController.withoutVarukorgHeaderUppgifter.methodInterrupted = "";
            mainViewController.withoutVarukorgHeaderUppgifter.openTidigareKop();
            //
        }
    }

    private Customer customer = iMatDataHandler.getCustomer();


    @FXML
    private void closeButton() {
        mainViewController.uppgifterAnchor.toBack();
    }
    private CreditCard creditCard = iMatDataHandler.getCreditCard();
    protected void fillInDefaults() {
        if(customer.getFirstName() != null) {
            name.setText(customer.getFirstName());
        }
        if(customer.getLastName() != null) {
            lastname.setText(customer.getLastName());
        }
        if(customer.getEmail() != null) {
            epost.setText(customer.getEmail());
        }
        if(customer.getAddress() != null) {
            adress.setText(customer.getAddress());
        }
        if(customer.getPhoneNumber() != null) {
            phonenumber.setText(customer.getPhoneNumber());
        }
        if(customer.getPostCode() != null) {
            portkod.setText(customer.getPostCode());
        }
        if(customer.getPostAddress() != null) {
            postnummer.setText(customer.getPostAddress());
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
            datemonth.setText(String.valueOf(creditCard.getValidMonth()));
            if(creditCard.getValidMonth() == -1) {
                datemonth.setText("");
            }
        }

        if(String.valueOf(creditCard.getValidYear()) != null) {
            dateyear.setText(String.valueOf(creditCard.getValidYear()));
            if(creditCard.getValidYear() == -1) {
                dateyear.setText("");
            }
        }

        if(String.valueOf(creditCard.getVerificationCode()) != null) {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
            if(creditCard.getVerificationCode() == -1) {
                cvc.setText("");
            }
        }

    }

    private void saveUppgifter() {
        customer.setFirstName(name.getText());
        customer.setEmail(epost.getText());
        customer.setLastName(lastname.getText());
        customer.setAddress(adress.getText());
        customer.setPhoneNumber(phonenumber.getText());
        customer.setPostAddress(postnummer.getText());
        customer.setPostCode(portkod.getText());


        creditCard.setCardNumber(kortnummer1.getText() + " " + kortnummer2.getText() + " " + kortnummer3.getText() + " " + kortnummer4.getText());

        if(!datemonth.getText().isEmpty()) {
            creditCard.setValidMonth(Integer.parseInt(datemonth.getText()));
        } else {
            System.out.println("empty");
            creditCard.setValidMonth(-1);
        }

        if(!dateyear.getText().isEmpty()) {
            creditCard.setValidYear(Integer.parseInt(datemonth.getText()));
        } else {
            creditCard.setValidYear(-1);
        }

        if(!cvc.getText().isEmpty()) {
            creditCard.setVerificationCode(Integer.parseInt(cvc.getText()));
        } else {
            creditCard.setVerificationCode(-1);
        }


        //creditCard.setValidYear(Integer.parseInt(dateyear.getText()));

        for(UppgifterListener uppgifterListener : uppgifterListeners) {
            uppgifterListener.updateUppgifter();
        }

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

    private void setFocusNextButton(TextField field, TextField nextField) {
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    nextField.requestFocus();
                }
            }
        });
    }

}
