package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VaraAvlang extends AnchorPane {
    public VaraAvlang() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
        try {
            Node node = fxmlLoader.load();
            this.getChildren().add(node);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
