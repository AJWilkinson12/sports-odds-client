/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ajwilkinson
 */
public class Aswd62SportsBetting extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Page2.fxml"));
        Parent root = loader.load();
        Page2Controller controller = loader.getController();
        
        Scene scene = new Scene(root);
        
        Switchable.scene = scene; 
        Switchable.switchTo("Page2");
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
       
        controller.start(stage); 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
