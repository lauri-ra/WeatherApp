package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.Period;

public class Controller implements EventListener {
    
    private View view;
    private TopMenu topMenu;
    private Graph graph;
    private BottomMenu bottomMenu;
    private ModelMain model;

    public Controller(View view, ModelMain model) {
        this.view = view;
        this.topMenu = view.getTopMenu();
        this.graph = view.getGraph();
        this.bottomMenu = view.getBottomMenu();
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
    
    @Override
    public void handleTopApply() {
        /*
        This function should check that all the choices are valid and if not,
        give the user appropriate error message using updateErrorMsg function in 
        TopMenu class.
        */
        
        // Clears the previous error messages.
        topMenu.updateErrorMsg("");
        
        var coordinates = topMenu.getCoordinatesTextField().getText();
        var startDate = topMenu.getStartDatePicker().getValue();
        var endDate = topMenu.getEndDatePicker().getValue();
        
        if (coordinates.isEmpty()) {
            topMenu.updateErrorMsg("Coordinates can not be empty!");
        }
        /*
        Check here if the coordinates are valid. If not, give the user an
        error message.
        */
        
        if (endDate != null) {
            if (startDate.isAfter(endDate)) {
                topMenu.updateErrorMsg("Start date cannot be after end date!");
            }
            
            Period period = Period.between(startDate, endDate);
            /*
            Time between the two dates could be max. 7 days, as the graphs
            are quite narrow...
            */
            if (period.getDays() > 7) {
                topMenu.updateErrorMsg("The time between start date and end date "
                        + "cannot be more than 7 days!");
            }
        }
        
        /*
        If all the choices are valid, allow the user to use the
        combo boxes in the bottom menu.
        */
        bottomMenu.getLeftOptionComboBox().setDisable(false);
        bottomMenu.getLeftChartTypeComboBox().setDisable(false);
        bottomMenu.getRightOptionComboBox().setDisable(false);
        bottomMenu.getRightChartTypeComboBox().setDisable(false);
    }

    @Override
    public void handleReset() {
        topMenu.getCoordinatesTextField().clear();
        topMenu.getStartDateContainer().setDisable(false);
        topMenu.getStartDatePicker().setDisable(false);
        topMenu.getStartDatePicker().setValue(LocalDate.now());
        topMenu.getEndDateContainer().setDisable(false);
        topMenu.getEndDatePicker().setDisable(false);
        topMenu.getEndDatePicker().setValue(null);
        topMenu.getForecastComboBox().setValue(null);
        topMenu.getForecastComboBox().setDisable(false);
        topMenu.getAverageRadioButton().setSelected(false);
        topMenu.getAverageRadioButton().setDisable(false);
        topMenu.getMinMaxRadioButton().setDisable(false);
        topMenu.getMinMaxRadioButton().setSelected(false);
        topMenu.updateErrorMsg("");
        bottomMenu.getLeftOptionComboBox().setDisable(true);
        bottomMenu.getLeftChartTypeComboBox().setDisable(true);
        bottomMenu.getRightOptionComboBox().setDisable(true);
        bottomMenu.getRightChartTypeComboBox().setDisable(true);
    }    

     @Override
    public void handleLeftChartApply() {
        var coordinates = topMenu.getCoordinatesTextField().getText();
        var startDate = topMenu.getStartDatePicker().getValue();
        var endDate = topMenu.getEndDatePicker().getValue();
        var average = topMenu.getAverageRadioButton().isSelected();
        var minmax = topMenu.getMinMaxRadioButton().isSelected();
        var value = bottomMenu.getLeftOptionComboBox().getValue();
        var chartType = bottomMenu.getLeftChartTypeComboBox().getValue();
        
        /* 
        Update the chart and traffic messages according to the selections using 
        updateChart function in Graph class. -> view.getGraph().updateChart(...). 
        Data should be given to the function in the form of 
        ObservableList<XYChart.Series<String, Double>>
        
        Such lists can be created with
        
        ObservableList<XYChart.Series<String, Double>> data = 
        FXCollections.observableArrayList();
        
        And data can be added to it as shown below.
        
        Series<String, Double> values = new Series<>();
        values.getData().add(new XYChart.Data("2022-10-25", 8));
        values.getData().add(new XYChart.Data("2022-10-26", 4));
        values.getData().add(new XYChart.Data("2022-10-27", 3));
        values.getData().add(new XYChart.Data("2022-10-28", 0));
        values.getData().add(new XYChart.Data("2022-10-29", -2));
        
        data.add(values);
        
        NOTE! Because both charts provide traffic messages to the same window,
        and the charts might have different dates (due to the load
        option), this function should check x values for both charts in
        order to show all relevant traffic messages. The values can be checked,
        for example, as shown below...

        for (var data : values.getData()) {
            var x = data.getXValue()...
            }
        }
        */
        
        System.out.println("Left chart apply handled!");        
    }

    @Override
    public void handleLeftChartSave() {
        /*
        Save the data in the left chart.
        */
        System.out.println("Left chart save handled!");        
    }

    @Override
    public void handleLeftChartLoad() {
        /*
        Load previously saved left chart and update the graph
        and traffic messages accordingly.
        */
        System.out.println("Left chart load handled!");
    }

    @Override
    public void handleRightChartApply() {
        var coordinates = topMenu.getCoordinatesTextField().getText();
        var startDate = topMenu.getStartDatePicker().getValue();
        var endDate = topMenu.getEndDatePicker().getValue();
        var average = topMenu.getAverageRadioButton().isSelected();
        var minmax = topMenu.getMinMaxRadioButton().isSelected();
        var value = bottomMenu.getLeftOptionComboBox().getValue();
        var chartType = bottomMenu.getLeftChartTypeComboBox().getValue();
        
        /* 
        Update the chart and traffic messages according to the selections using 
        updateChart function in Graph class. -> view.getGraph().updateChart(...). 
        Data should be given to the function in the form of 
        ObservableList<XYChart.Series<String, Double>>
        
        Such lists can be created with
        
        ObservableList<XYChart.Series<String, Double>> data = 
        FXCollections.observableArrayList();
        
        And data can be added to it as shown below.
        
        Series<String, Double> values = new Series<>();
        values.getData().add(new XYChart.Data("2022-10-25", 8));
        values.getData().add(new XYChart.Data("2022-10-26", 4));
        values.getData().add(new XYChart.Data("2022-10-27", 3));
        values.getData().add(new XYChart.Data("2022-10-28", 0));
        values.getData().add(new XYChart.Data("2022-10-29", -2));
        
        data.add(values);
        
        NOTE! Because both charts provide traffic messages to the same window,
        and the charts might have different dates (due to the load
        option), this function should check x values for both charts in
        order to show all relevant traffic messages. The values can be checked,
        for example, as shown below...

        for (var data : values.getData()) {
            var x = data.getXValue()...
            }
        }
        */
        System.out.println("Right chart apply handled!");
    }

    @Override
    public void handleRightChartSave() {
        /*
        Save the data in the right chart.
        */
        System.out.println("Right chart save handled!");
    }

    @Override
    public void handleRightChartLoad() {
        /*
        Load previously saved right chart and update the graph
        and traffic messages accordingly.
        */
        System.out.println("Right chart load handled!");
    }
    
    @Override
    public void handleSaveSettings() {
        var coordinates = topMenu.getCoordinatesTextField().getText();
        var startDate = topMenu.getStartDatePicker().getValue();
        var endDate = topMenu.getEndDatePicker().getValue();
        var forecast = topMenu.getForecastComboBox().getValue();
        var leftOption = bottomMenu.getLeftOptionComboBox().getValue();
        var leftChartType = bottomMenu.getLeftChartTypeComboBox().getValue();
        var rightOption = bottomMenu.getRightOptionComboBox().getValue();
        var rightChartType = bottomMenu.getRightChartTypeComboBox().getValue();
        
        /*
        Save the information in the model.
        */
        
        System.out.println("Save settings handled!");
    }

    @Override
    public void handleLoadSettings() {
        /*
        Load previously saved settings and update both graphs
        and traffic messages accordingly.
        */
        System.out.println("Load settings handled!");
    }
}
