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
        
        // view.UpdateMessage(newMessage);
    }

    @Override
    public void handleCoordinates() {
        
    }

    @Override
    public void handleStartDate() {
        
    }

    @Override
    public void handleEndDate() {        
        
    }

    @Override
    public void handleForecast() {
        
    }
    
    @Override
    public void handleApply() {
        var coordinates = view.getTopMenu().getCoordinatesTextField().getText();
        var startDate = view.getTopMenu().getStartDatePicker().getValue();
        var endDate = view.getTopMenu().getEndDatePicker().getValue();
        var forecast = view.getTopMenu().getForecastComboBox().getValue();
        
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
    public void handleReset() {
        view.getTopMenu().getCoordinatesTextField().clear();
        view.getTopMenu().getStartDatePicker().setValue(LocalDate.now());
        view.getTopMenu().getEndDatePicker().setValue(LocalDate.now());
        view.getTopMenu().getForecastComboBox().setValue(null);
        view.getTopMenu().getForecastComboBox().setDisable(false);
        view.getTopMenu().getEndDateContainer().setDisable(false);
        view.getTopMenu().getEndDatePicker().setDisable(false);        
    }    

    @Override
    public void handleLeftChart() {
        
    }

    @Override
    public void handleRightChart() {
        
    }

    @Override
    public void handleSaveData() {
        System.out.println("Save data handled!");
    }

    @Override
    public void handleLoadData() {
        System.out.println("Load data handled!");
    }

    @Override
    public void handleSaveSettings() {
        System.out.println("Save settings handled!");
    }

    @Override
    public void handleLoadSettings() {
        System.out.println("Load settings handled!");
    }
}
