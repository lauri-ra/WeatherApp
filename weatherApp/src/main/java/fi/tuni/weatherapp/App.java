package fi.tuni.weatherapp;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private ViewPlaceholder view;
    private ModelPlaceholder model;
    private ControllerPlaceholder controller;
     
    
    @Override
    public void init() {
        view = new ViewPlaceholder();
        model = new ModelPlaceholder();
        controller = new ControllerPlaceholder(view, model);
    }
    
    @Override
    public void start(Stage stage) {
        view.SetStage(stage);
        controller.Begin();
        controller.TestController();
    }

    public static void main(String[] args) {
        launch();
    }

}