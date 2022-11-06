package fi.tuni.weatherapp;

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
        
        //model.AddDataSource(new TestDataSource());
        model.AddDataSource(new FMIDataSource());
        model.AddDataSource(new DigiTrafficTest());
        
        /* Create an ArrayList<String> of the combo box choices and add the 
        data to the combo boxes. When this has been implemented, delete the extra 
        code from BottomMenu that populated the combo boxes for demonstration.
        
        view.getBottomMenu().populateComboBox(ArrayList<String>, view.getBottomMenu()
                .getLeftOptionComboBox());
        view.getBottomMenu().populateComboBox(ArrayList<String>, view.getBottomMenu()
                .getRightOptionComboBox());
        */
        
        controller = new Controller(view, model);
    }
    
    @Override
    public void start(Stage stage) {
        for (var element : view.getElements()) {
            element.setListener(controller);
        }
        view.setStage(stage);

        controller.Begin();
        controller.TestController();
    }

    public static void main(String[] args) {
        launch();
    }

}