package fi.tuni.weatherapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller implements EventListener {
    
    private View view;
    private TopMenu topMenu;
    private Graph graph;
    private BottomMenu bottomMenu;
    private ModelMain model;
    private String messageSourceName = "";
    private boolean bottomMenuDisabled = false;
    private String rightChartType;
    private String leftChartType;
    private Variable leftVariable;
    private Variable rightVariable;
    private List<DataPoint> leftData;
    private List<DataPoint> rightData;


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

    public void UpdateTrafficMessages(LocalDate startDate, LocalDate endDate) {
        ArrayList<String> data = this.model.GetMessages(messageSourceName, startDate, endDate);
        view.getBottomMenu().updateTrafficMsgs(data);
    }
    
    /**
     * Enables entire bottom menu.
     */
    public void enableBottomMenu() {
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
        this.bottomMenuDisabled = false;
    }
    
    /**
     * Disables entire bottom menu upon user editing top menu fields.
     */
    @Override
    public void handleEdited() {
        if (!this.bottomMenuDisabled) {
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
            this.bottomMenuDisabled = true;
        }     
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
        if(topMenu.getAverageRadioButton().isSelected()) {
            TaskController(Graph.BAR, "Task averages", coordinates, startDate, endDate);
        }
        
        if(endDate == null && !topMenu.getAverageRadioButton().isSelected()) {
            var forecast = topMenu.getForecastComboBox().getValue().toString();
            ForecastController(forecast, coordinates);
        }
        */
        this.enableBottomMenu();
        UpdateTrafficMessages(startDate, endDate);
        
        if (topMenu.getForecastComboBox().getValue() != null) {
            UpdateAvailableVariables(true);
        }
        else {
            UpdateAvailableVariables(false);
        }
        
        
    }
    
    private void UpdateAvailableVariables(boolean isForecast) {
        ArrayList<String> options = new ArrayList<>();
        boolean isAvg = topMenu.getAverageRadioButton().isSelected();

        boolean isMinMax = topMenu.getMinMaxRadioButton().isSelected();
        for (String dataSourceName: model.GetDataSourceNames()) {
            List<Variable> dataSourceVariables = model.GetVariables(dataSourceName);
            
            for (Variable variable : dataSourceVariables) {
                
                if (variable.isForecast() == isForecast && 
                        variable.isAvg() == isAvg && 
                        variable.isMinMax() == isMinMax) {
                    
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
    
    private void UpdateChart(String side, String variableName, String xType, String unit,
            String chartType, List<DataPoint> rawData) {
        
        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
                
        Series<String, Double> values = new Series<>();
        
        if (rawData.isEmpty()) {
            values.getData().add(new XYChart.Data("No data available", 1));
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
        DataPoint min;
        int minValue = 0;
        
        if (!rawData.isEmpty()) {
            max = Collections.max(rawData, Comparator.comparing(DataPoint::getY));
            maxValue = (int) Math.ceil(max.getY());
            
            min = Collections.min(rawData, Comparator.comparing(DataPoint::getY));
            minValue = (int) Math.floor(min.getY());
        }
        
        int stepSize;
        if ((maxValue - minValue) < 10) {
            stepSize = 1;
        }
        else {
            stepSize = (maxValue-minValue)/10;
        }
        
        int yMin = minValue - 2*stepSize;
        if (minValue > 0 && yMin < 0) {
            yMin = 0;
        } 
        int yMax = maxValue + 2*stepSize;
        
        if ("left".equals(side)) {
            graph.updateChart(Graph.Side.LEFT, chartType, 
                variableName, xType, unit, 
                yMin, yMax, stepSize, data);
        }
        else {
            graph.updateChart(Graph.Side.RIGHT, chartType, 
                variableName, xType, unit, 
                yMin, yMax, stepSize, data);
        }
    }
    
    private void ApplyChart(String side) {
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
            int forecastLength = Integer.parseInt(forecastStr.split(" ")[0]);
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
        
        
        if ("left".equals(side)) {
            leftChartType = chartType;
            leftVariable = variable;
            leftData = rawData;
        }
        else {
            rightChartType = chartType;
            rightVariable = variable;
            rightData = rawData;
        }
        
        UpdateChart(side, variable.getName(), variable.getXType(),variable.getUnit(),
            chartType, rawData);
        
    }
    
    private void saveChartData(String side) {
        String fileName;
        String chartType;
        Variable variable;
        List<DataPoint> data;
        if (side.equals("left")) {
            fileName = "leftChartData.json";
            chartType = leftChartType;
            variable = leftVariable;
            data = leftData;
            
        } else {
            fileName = "rightChartData.json";
            chartType = rightChartType;
            variable = rightVariable;
            data = rightData;
        }
        System.out.println("Stuff: Save");
        
        
        JSONObject mainJson = new JSONObject();
        JSONObject dataJson = new JSONObject();
        try {
            if (data != null) {
                for (DataPoint dataPoint: data) {
                    dataJson.put(dataPoint.getX(), dataPoint.getY());
                }
            }

            mainJson.put("variableName", variable.getName());
            mainJson.put("xType", variable.getXType());
            mainJson.put("yType", variable.getUnit());
            mainJson.put("dataPoints", dataJson);
            mainJson.put("chartType", chartType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.write(mainJson.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void LoadChartData(String side){
        String fileName;
        if (side.equals("left")) {
            fileName = "leftChartData.json";
        } else {
            fileName = "rightChartData.json";
        }
        System.out.println("Stuff: Load");
        
        try {
            String text = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(text);

            //System.out.println("variableName: " + jsonObj.getString("variableName"));
            String variableName = jsonObj.getString("variableName");
            
            //System.out.println("ChartType: " + jsonObj.getString("chartType"));
            String chartType = jsonObj.getString("chartType");
            
            //System.out.println("xType: " + jsonObj.getString("xType"));
            String xType = jsonObj.getString("xType");
            
            //System.out.println("yType: " + jsonObj.getString("yType"));
            String yType = jsonObj.getString("yType");
            
            JSONObject dataJson = jsonObj.getJSONObject("dataPoints");
            ArrayList<DataPoint> data = new ArrayList<>();
            
            for (var tmpTest : dataJson.names()) {
                String xValue = (String) tmpTest;
                double yValue = dataJson.getBigDecimal(xValue).doubleValue();

                data.add(new DataPoint(xValue, yValue));
            }
            
            Collections.sort(data, Comparator.comparing(DataPoint::getX));
            
            UpdateChart(side, variableName, xType, yType, 
                    chartType, data);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void handleLeftChartApply() {
        
        ApplyChart("left");
        System.out.println("Left chart apply handled!");        
    }

    @Override
    public void handleLeftChartSave() {
        /*
        Save the data in the left chart.
        */
        saveChartData("left");
        System.out.println("Left chart save handled!");        
    }

    @Override
    public void handleLeftChartLoad() {
        /*
        Load previously saved left chart and update the graph
        and traffic messages accordingly.
        */
        LoadChartData("left");
        System.out.println("Left chart load handled!");
    }

    @Override
    public void handleRightChartApply() {
        ApplyChart("right");
        System.out.println("Right chart apply handled!");
    }

    @Override
    public void handleRightChartSave() {
        /*
        Save the data in the right chart.
        */
        saveChartData("right");
        System.out.println("Right chart save handled!");
    }

    @Override
    public void handleRightChartLoad() {
        /*
        Load previously saved right chart and update the graph
        and traffic messages accordingly.
        */
        LoadChartData("right");
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
