/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author alex
 */
public class FXMLDocumentController implements Initializable {
    
    private Stage stage;
    private FileChooser fileChooser = new FileChooser();
    
    @FXML
    private TextArea textArea;
    
    private MySAXParser saxParser = new MySAXParser();
    
    
    @FXML
    private void handleAbout(ActionEvent event) {
        this.textArea.setText("A SAX XML parsing example to display XML data within a JavaFX application.\n"
                + "By Alex Gompper");
    }
    
    @FXML
    private void handleQuit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void handleOpen(ActionEvent event) {
        this.textArea.setText("Open");
        this.fileChooser.setTitle("Open XML File");
        File file = this.fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            this.saxParser.parseFile(file);
            this.textArea.setText(this.saxParser.getParsedText());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Houston we have a problem");
            alert.setHeaderText("File Error");
            alert.setContentText("There was an error opening the specified file.");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void start(Stage stage) {
        this.stage = stage;
        this.fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
    }    
    
}
