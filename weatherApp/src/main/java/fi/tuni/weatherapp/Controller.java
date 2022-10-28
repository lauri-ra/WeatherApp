package fi.tuni.weatherapp;

import java.time.LocalDate;

public class Controller implements EventListener {
    
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
            newMessage += dataPoint.getDate() + ", " + dataPoint.getValue() + "\n";
        }
        
        //view.UpdateMessage(newMessage);
    }

    @Override
    public void handleCoordinates(Object... args) {
        
    }

    @Override
    public void handleStartDate(Object... args) {
        
    }

    @Override
    public void handleEndDate(Object... args) {        
        
    }

    @Override
    public void handleForecast(Object... args) {
        
    }
    
    @Override
    public void handleApply(String coordinates, LocalDate startDate, 
            LocalDate endDate, Object forecast) {
        // Testing different things...
        if (forecast == null) {
            System.out.println("Forecast was not selected!");
        }
        else {
            System.out.println("Forecast was selected!");
        }
        
        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date!");
        }
        
        if (view.getBottomMenu().getLeftOptionComboBox().getValue() == null
                && view.getBottomMenu().getRightOptionComboBox().getValue() == null) {
            System.out.println("Choose at least one chart!");
        }
    }

    @Override
    public void handleClear(Object... args) {
        
    }    

    @Override
    public void handleLeftChart(Object... args) {
        
    }

    @Override
    public void handleRightChart(Object... args) {
        
    }

    @Override
    public void handleSaveData(Object... args) {
        System.out.println("Save data..." + args[0]);
    }

    @Override
    public void handleLoadData(Object... args) {
        System.out.println("Load data..." + args[0]);
    }

    @Override
    public void handleSaveSettings(Object... args) {
        System.out.println("Save settings..." + args[0]);
    }

    @Override
    public void handleLoadSettings(Object... args) {
        System.out.println("Load settings..." + args[0]);
    }
}
