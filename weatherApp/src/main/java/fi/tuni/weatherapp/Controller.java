package fi.tuni.weatherapp;

import java.time.LocalDate;

public class Controller {
    
    private View view;
    private ModelMain model;

    public Controller(View view, ModelMain model) {
        this.view = view;
        this.model = model;
    }
    
    public void Begin(){
        view.render();
    }
    
    public void TestController(){
        
        String newMessage = "";
        for (String dataSourceName: model.GetDataSourceNames() ) {
            newMessage  += dataSourceName + ": \n";
            for (Variable variable : model.GetVariables(dataSourceName)) {
                newMessage += variable.getName() + ", " + variable.getUnit() + "\n";
            }
        }
        for (DataPoint dataPoint : model.GetVariableData("DigiTrafficTest",
                new Variable("a","b"), "coordinates", 
                LocalDate.MAX, LocalDate.MAX)) {
            newMessage += dataPoint.getX() + ", " + dataPoint.getY() + "\n";
        }
        System.out.println(newMessage);
        //view.UpdateMessage(newMessage);
    }

}
