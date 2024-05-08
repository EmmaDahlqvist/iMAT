package imat;


import java.awt.*;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class iMatApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("imat/resources/iMat");
        
        Parent root = FXMLLoader.load(getClass().getResource("imat_app.fxml"), bundle);

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Scene scene = new Scene(root, 1440, 1024);
//        stage.setMaximized(true);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
        System.out.println("hej");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
