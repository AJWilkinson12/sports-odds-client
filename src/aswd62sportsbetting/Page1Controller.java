/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author ajwilkinson
 */
public class Page1Controller extends Switchable implements Initializable {
    
    private Stage stage; 
    private Scene page1Scene;
    private Scene page2Scene; 
    private Page2Controller page2Controller; 
    
    @FXML
    private Label label;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    @FXML
    private void goToPage2(ActionEvent event) {
       System.out.println("Going to App");
       Switchable.switchTo("Page2");
    }
}