package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CompleteController extends AnchorPane{

    private MainViewController mainViewController;


    private String adress;
    private String time;
    private String day;
    private String name;

    @FXML private AnchorPane anchorHeader;

    @FXML private Label nameLabel;
    @FXML private Label dayLabel;
    @FXML private Label timeLabel;
    @FXML private Label adressLabel;


    public CompleteController(MainViewController mainViewController, String name, String day, String time, String adress) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("complete.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.name = name;
        this.day = day;
        this.time = time;
        this.adress = adress;

        this.mainViewController = mainViewController;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

//        this.anchorHeader.getChildren().add(mainViewController.mainHeader);
        this.mainViewController.anchorHeader.toFront();
        this.mainViewController.varukorgPopupAnchor.toFront();

        initialize();
    }

    @FXML
    protected void backToHomePage(){
        mainViewController.backToHomePage();
    }

    private void initialize() {
        nameLabel.setText("Tack för din beställning " + name + "!");
        dayLabel.setText(day);
        timeLabel.setText(time);
        adressLabel.setText(adress);
    }
}
