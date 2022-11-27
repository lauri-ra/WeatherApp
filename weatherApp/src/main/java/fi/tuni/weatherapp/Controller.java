package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class Controller implements EventListener {
    
    private View view;
    private TopMenu topMenu;
    private Graph graph;
    private BottomMenu bottomMenu;
    private ModelMain model;
    private String messageSourceName = "";

    public Controller(View view, ModelMain model, String messageSourceName) {
        this.view = view;
        this.topMenu = view.getTopMenu();
        this.graph = view.getGraph();
        this.bottomMenu = view.getBottomMenu();
        this.model = model;
        this.messageSourceName = messageSourceName;
    }
    
    public void Begin(){
        view.render();
    }

    public void UpdateTrafficMessages() {
        ArrayList<String> data = this.model.GetMessages(messageSourceName);
        view.getBottomMenu().updateTrafficMsgs(data);
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
            return;
        }
        /*
        Check here if the coordinates are valid. If not, give the user an
        error message.
        */
        
        if (endDate != null) {
            if (startDate.isAfter(endDate)) {
                topMenu.updateErrorMsg("Start date cannot be after end date!");
                return;
            }
            
            Period period = Period.between(startDate, endDate);
            /*
            Time between the two dates could be max. 7 days, as the graphs
            are quite narrow...
            */
            if (period.getDays() > 7) {
                topMenu.updateErrorMsg("The time between start date and end date "
                        + "cannot be more than 7 days!");
                return;
            }
        }
        if (endDate == null && topMenu.getForecastComboBox().getValue() == null) {
            topMenu.updateErrorMsg("Need to select endDate or forecast length.");
            return;
        }

        /*
        If all the choices are valid, allow the user to use the
        combo boxes in the bottom menu.
        */
        bottomMenu.getLeftOptionComboBox().setDisable(false);
        bottomMenu.getLeftChartTypeComboBox().setDisable(false);
        bottomMenu.getLeftChartApplyButton().setDisable(false);
        bottomMenu.getLeftChartSaveButton().setDisable(false);
        bottomMenu.getLeftChartLoadButton().setDisable(false);
        bottomMenu.getRightOptionComboBox().setDisable(false);
        bottomMenu.getRightChartTypeComboBox().setDisable(false);
        bottomMenu.getRightChartApplyButton().setDisable(false);
        bottomMenu.getRightChartSaveButton().setDisable(false);
        bottomMenu.getRightChartLoadButton().setDisable(false);
        
        UpdateTrafficMessages();
        
        if (topMenu.getForecastComboBox().getValue() != null) {
            UpdateAvailableVariables(true);
        }
        else {
            UpdateAvailableVariables(false);
        }
        
        
    }
    
    private void UpdateAvailableVariables(boolean isForecast) {
        ArrayList<String> options = new ArrayList<>();
        for (String dataSourceName: model.GetDataSourceNames()) {
            List<Variable> dataSourceVariables = model.GetVariables(dataSourceName);
            
            for (Variable variable : dataSourceVariables) {
                if (variable.isForecast() == isForecast) {
                    options.add(dataSourceName + ": " + variable.getName());
                }
            }
        }
        
        view.getBottomMenu().populateComboBox(options, view.getBottomMenu()
                .getLeftOptionComboBox());
        view.getBottomMenu().populateComboBox(options, view.getBottomMenu()
                .getRightOptionComboBox());
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
        bottomMenu.getLeftChartApplyButton().setDisable(true);
        bottomMenu.getLeftChartSaveButton().setDisable(true);
        bottomMenu.getLeftChartLoadButton().setDisable(true);        
        bottomMenu.getRightOptionComboBox().setDisable(true);
        bottomMenu.getRightChartTypeComboBox().setDisable(true);
        bottomMenu.getRightChartApplyButton().setDisable(true);
        bottomMenu.getRightChartSaveButton().setDisable(true);
        bottomMenu.getRightChartLoadButton().setDisable(true);
    }    
    
    private void UpdateChart(String side) {
        String coordinates = topMenu.getCoordinatesTextField().getText();
        var startDate = topMenu.getStartDatePicker().getValue();
        var endDate = topMenu.getEndDatePicker().getValue();
        boolean average = topMenu.getAverageRadioButton().isSelected();
        boolean minmax = topMenu.getMinMaxRadioButton().isSelected();
        String value;
        String chartType;
        if ("left".equals(side)) {
            value = bottomMenu.getLeftOptionComboBox().getValue().toString();
            chartType = bottomMenu.getLeftChartTypeComboBox().getValue().toString();
        }
        else {
            value = bottomMenu.getRightOptionComboBox().getValue().toString();
            chartType = bottomMenu.getRightChartTypeComboBox().getValue().toString();
        }
        String[] parts = value.split(":");
        String dataSourceName = parts[0];
        String variableName = parts[1].substring(1);
        
        Variable variable = this.model.GetVariable(dataSourceName, variableName);
        
        List<DataPoint> rawData;
        if (topMenu.getForecastComboBox().getValue() != null) {
            String forecastStr = topMenu.getForecastComboBox().getValue().toString();
            int forecastLength = Integer.parseInt(forecastStr.substring(0,1));
            rawData = model.GetForecastData(dataSourceName,
                variable,
                coordinates,
                LocalDateTime.now(), LocalDateTime.now().plusHours(forecastLength));
        }

        else {
            rawData = model.GetPastData(dataSourceName,
                variable,
                coordinates,
                startDate, endDate);
        }
        
        //System.out.println(data);
        
        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
                
        Series<String, Double> values = new Series<>();
        
        if (rawData.size() == 0) {
            values.getData().add(new XYChart.Data("no data available", 1));
        }
        else {
            for (DataPoint dataPoint: rawData) {
                String x = dataPoint.getX();
                double y = dataPoint.getY();
                if (x.length() > 14) {
                    x = x.substring(0,13);
                }
                values.getData().add(new XYChart.Data(x, y));
            }
        }
        
        data.add(values);
        
        DataPoint max;
        int maxValue = 10; // Default for cases with no data
        
        if (rawData.size() != 0) {
            max = Collections.max(rawData, Comparator.comparing(DataPoint::getY));
            maxValue = (int) Math.ceil(max.getY()) + 2;
        }

        
        
        if ("left".equals(side)) {
            view.getGraph().updateChart(Graph.Side.LEFT, chartType, 
                variable.getName(), variable.getXType(), variable.getUnit(), 
                0, maxValue, maxValue/10, data);
        }
        else {
            view.getGraph().updateChart(Graph.Side.RIGHT, chartType, 
                variable.getName(), variable.getXType(), variable.getUnit(), 
                0, maxValue, maxValue/10, data);
        }

    }
    
    @Override
    public void handleLeftChartApply() {
        
        UpdateChart("left");
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
        UpdateChart("right");
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
