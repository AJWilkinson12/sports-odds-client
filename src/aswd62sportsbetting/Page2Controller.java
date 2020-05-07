/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aswd62sportsbetting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


/**
 * 
 * @author ajwilkinson
 */
public class Page2Controller extends Switchable implements Initializable {
    
    @FXML
    private TextField searchTextField; 
    
    @FXML
    private ListView listView;
    
    @FXML
    private Label homeLabel;
    
    @FXML
    private Label timeLabel;
    
    @FXML
    private Label searchLabel;
    
    @FXML
    private Label spreadLabel;
    
    @FXML
    private Label sourceLabel;
    
    @FXML
    private Label gameLabel;
    
    private Stage stage; 
    public Scene page1Scene; 
    public Page1Controller page1Controller;
    private String searchString = "College Football";
    private DataModel dm;
    
    // Creating my lists.
    ObservableList<String> gamesItems;
    ArrayList<Sports> games;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        searchTextField.setText(searchString);
        start(stage);
    }
    
    
    // Starts the searching process for the information being sought  
    public void start(Stage stage){
        
        this.stage = stage;
        dm = new DataModel();
        gamesItems = FXCollections.observableArrayList();
        gameLabel.setText("-");
        homeLabel.setText("-");
        timeLabel.setText("-");
        spreadLabel.setText("-");
        sourceLabel.setText("-");
        
        //Checks to see if the user has selected any other games within the list view
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                if((int) new_val < 0 || (int) new_val > (games.size() - 1)){
                    return; 
                }
                
                Sports s = games.get((int) new_val);
                
                gameLabel.setText(s.game);
                homeLabel.setText(s.hometeam);
                timeLabel.setText(s.time.toString());
                spreadLabel.setText(s.odds);
                sourceLabel.setText(s.site);
                
            }
        
        }); 
        searchTextField.setText(searchString);
        searchString = searchString.toUpperCase();
        loadGames();
    }
    
    
    // Sends a request to fetch the game data from API
    private void loadGames() {
        try {
            dm.load(searchString);
        } catch (Exception ex) {
            Logger.getLogger(Page2Controller.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        games = dm.getSports();
        gamesItems.clear();
        
        for(Sports s : games){
            gamesItems.add(s.game); 
        }
        
        listView.setItems(gamesItems);
        
        if(games.size() > 0){
            listView.getSelectionModel().select(0);
            listView.getFocusModel().focus(0); 
            listView.scrollTo(0); 
        }
        
    }
    
    // Handles the search button
    @FXML
    private void handleSearch(ActionEvent event) {
        if (searchTextField.getText().equals("")) {
            displayErrorAlert("The search field cannot be blank. Enter one or more search words.");
            return;
        }
        
        searchString = null;
        searchString = searchTextField.getText().toUpperCase();
        start(stage);
    }
    
    // Will display error if needed.
    public void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // This is here for just in case.
    private void displayExceptionAlert(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("An Exception Occurred!");
        alert.setContentText("An exception occurred.  View the exception information below by clicking Show Details.");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    // Goes to the 'About' section of the app
    @FXML
    private void goBackToPage1(ActionEvent event) {
        System.out.println("Going to App Info");
        Switchable.switchTo("Page1");
    }
}