package fi.tuni.weatherapp;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private View view;
    private ModelMain model;
    private Controller controller;
    
    @Override
    public void init() {
        view = new View();
        model = new ModelMain();
        
        model.AddDataSource(new FMIDataSource());
        model.AddDataSource(new DigiTrafficTest());
        
        controller = new Controller(view, model, "DigiTrafficTest");
    }
    
    @Override
    public void start(Stage stage) {
        for (var element : view.getElements()) {
            element.setListener(controller);
        }
        view.setStage(stage);

        controller.Begin();
    }

    public static void main(String[] args) {
        launch();
    }

}