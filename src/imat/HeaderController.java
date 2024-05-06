package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class HeaderController extends AnchorPane {

    @FXML
    private AnchorPane tidigareKop;
    @FXML
    private AnchorPane dinaUppgifter;
    @FXML
    private AnchorPane varuKorgen;
    @FXML
    private ImageView logo;

    public HeaderController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
