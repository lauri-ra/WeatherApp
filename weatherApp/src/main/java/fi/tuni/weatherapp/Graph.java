package fi.tuni.weatherapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.GridPane;

public final class Graph extends Element {
    private GridPane _innerContainer;
    private XYChart _leftChart;
    private XYChart _rightChart;
    
    /**
     * Constructor
     */
    public Graph() {
        this.setInnerContainer(new GridPane());
        this._initCharts();
        this._buildInnerContainer();
    }
    
    public enum ChartType {
        LINE,
        BAR,
        SCATTER
    }
    
    public enum Side {
        LEFT,
        RIGHT
    }
    
    /**
     * Returns inner container containing both graphs.
     * @return _innerContainer
     */
    public GridPane getInnerContainer() {
        return this._innerContainer;
    }
    
    /**
     * Sets inner container.
     * @param container 
     */
    public void setInnerContainer(GridPane container) {
        this._innerContainer = container;
    }
    
    /**
     * Returns left chart.
     * @return _leftChart
     */
    public XYChart getLeftChart() {
        return this._leftChart;
    }
    
    /**
     * Sets left chart (line chart).
     * @param chart 
     */
    public void setLeftChart(XYChart chart) {
        this._leftChart = chart;
    }
    
    /**
     * Returns right chart.
     * @return 
     */
    public XYChart getRightChart() {
        return this._rightChart;
    }
    
    /**
     * Sets right chart (bar chart).
     * @param chart 
     */
    public void setRightChart(XYChart chart) {
        this._rightChart = chart;
    }
    
    /**
     * Sets chart style.
     * @param chart 
     */
    public void setChartStyle(XYChart chart) {
        chart.setStyle("-fx-font-family: Liberation Sans;");
        chart.setLegendVisible(false);        
    }
    
    /**
     * Builds chart.
     * @param side
     * @param type
     * @param label
     * @param lowerBound
     * @param upperBound
     * @param tickUnit
     * @param data 
     */
    public void buildChart(Side side, ChartType type, String label, 
            double lowerBound, double upperBound, double tickUnit, 
            ObservableList<XYChart.Series<String, Double>> data) {
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis(lowerBound, upperBound, tickUnit);
        x.setLabel("Date");

        XYChart chart;
        
        switch (type) {
            case BAR:
                chart = this._buildBarChart(label, x, y);
                break;
            case SCATTER:
                chart = this._buildScatterChart(label, x, y);
                break;
            default:
                chart = this._buildLineChart(label, x, y);
                break;
        }
        
        chart.setData(data);
        
        if (side == Side.LEFT) {
            this.setLeftChart(chart);
        }
        else {
            this.setRightChart(chart);
        }
    }
    
    /**
     * Builds line chart.
     * @param label
     * @param x
     * @param y
     * @return chart
     */
    private LineChart _buildLineChart(String label, CategoryAxis x, NumberAxis y) {
        LineChart<String, Number> chart = new LineChart<>(x, y);
        chart.setTitle(label);
        this.setChartStyle(chart);
        
        return chart;
    }
    
    /**
     * Builds bar chart.
     * @param label
     * @param x
     * @param y
     * @return chart
     */
    private BarChart _buildBarChart(String label, CategoryAxis x, NumberAxis y) {
        BarChart<String, Number> chart = new BarChart<>(x, y);
        chart.setTitle(label);
        this.setChartStyle(chart);
        
        return chart;
    }
    
    /**
     * Builds scatter chart.
     * @param label
     * @param x
     * @param y
     * @return chart
     */
    private ScatterChart _buildScatterChart(String label, CategoryAxis x, NumberAxis y) {
        ScatterChart<String, Number> chart = new ScatterChart<>(x, y);
        chart.setTitle(label);
        this.setChartStyle(chart);
        
        return chart;
    }
    
    /**
     * Initializes charts for first time use.
     */
    private void _initCharts() {
        // Code below is for demonstration purpose only.
        ObservableList<XYChart.Series<String, Double>> data = 
                FXCollections.observableArrayList();
        
        Series<String, Double> values1 = new Series<>();
        values1.getData().add(new XYChart.Data("2022-10-25", 8));
        values1.getData().add(new XYChart.Data("2022-10-26", 4));
        values1.getData().add(new XYChart.Data("2022-10-27", 3));
        values1.getData().add(new XYChart.Data("2022-10-28", 0));
        values1.getData().add(new XYChart.Data("2022-10-29", -2));
        
        data.add(values1);
        // Code above is for demonstration purpose only.
        
        this.buildChart(Side.LEFT, ChartType.LINE, "Temperature", 
        -15, 15, 1, data);
        
        // Code below is for demonstration purpose only.
        data.clear();
        
        Series<String, Double> values2 = new Series<>();
        values2.getData().add(new XYChart.Data("2022-10-25", 0));
        values2.getData().add(new XYChart.Data("2022-10-26", 1));
        values2.getData().add(new XYChart.Data("2022-10-27", 3));
        values2.getData().add(new XYChart.Data("2022-10-28", 2));
        values2.getData().add(new XYChart.Data("2022-10-29", 0));
        
        data.add(values2);
        // Code above is for demonstration purpose only.
        
        this.buildChart(Side.RIGHT, ChartType.BAR, "Precipitation", 
                0, 15, 1, data);
    }
    
    /**
     * Builds contents of inner container.
     */
    private void _buildInnerContainer() {
        // Column | row | column span | row span
        getInnerContainer().add(getLeftChart(),     0, 0, 1, 1);
        getInnerContainer().add(getRightChart(),    1, 0, 1, 1);
        
        getInnerContainer().setMinWidth(1000);
        getInnerContainer().setMaxWidth(1000);
        getInnerContainer().setVgap(15);
        getInnerContainer().setPadding(new Insets(20, 50, 20, 50));
        
        this.getNodes().add(getInnerContainer());        
    }
}
