package fi.tuni.weatherapp;

import java.time.LocalDate;

public class ControllerPlaceholder {
    
    private ViewPlaceholder view;
    private ModelMain model;

    public ControllerPlaceholder(ViewPlaceholder view, ModelMain model) {
        this.view = view;
        this.model = model;
    }
    
    public void Begin(){
        view.OpenView();
    }
    
    public void TestController(){        
        
        String newMessage = "";
        for (String dataSourceName: model.GetDataSourceNames() ) {
            newMessage  += dataSourceName + ": \n";
            for (Variable variable : model.GetVariables(dataSourceName)) {
                newMessage += variable.getName() + ", " + variable.getUnit() + "\n";
            }
        }
        for (DataPoint dataPoint : model.GetVariableData("FMI", 
                new Variable("a","b"), "coordinates", 
                LocalDate.MAX, LocalDate.MAX)) {
            newMessage += dataPoint.getDate() + ", " + dataPoint.getValue() + "\n";
        }
        
        view.UpdateMessage(newMessage);
    }

}
