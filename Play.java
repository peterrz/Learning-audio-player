/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package play;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Peter
 */
public class Play extends Application {
     @Override
    public void start(Stage primaryStage) throws Exception {
      
        Parent rooot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(rooot);
        primaryStage.setTitle("Learning player");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      launch(args);
       }
    
}
