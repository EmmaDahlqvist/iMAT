
package imat;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class MainViewController implements Initializable {

    @FXML
    Label pathLabel;
    @FXML
    private AnchorPane anchorHeader;



    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);
        anchorHeader.getChildren().add(new HeaderController());

        for(Product product : iMatDataHandler.getProducts()) {
            System.out.println(product);

        }

    }
}