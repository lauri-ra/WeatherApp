package fi.tuni.weatherapp;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ViewPlaceholder {
    private Stage stage;
    private String message = "PlaceHolder";
    
    public void SetStage(Stage stage) {
        this.stage = stage;
    }
    
    public void OpenView() {
        Refresh();
    }
    
    public void UpdateMessage(String message) {
        this.message = message;
        Refresh();
    }
    
    
    private void Refresh(){
        var label = new Label(message);
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
