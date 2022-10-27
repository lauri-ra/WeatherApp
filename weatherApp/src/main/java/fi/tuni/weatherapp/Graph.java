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

public class Graph extends Element {
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
    
    enum ChartType {
        LINE,
        BAR,
        SCATTER
    }
    
    enum Side {
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
     * Builds line chart.
     * @param label
     * @param x
     * @param y
     * @return chart
     */
    public LineChart buildLineChart(String label, CategoryAxis x, NumberAxis y) {
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
    public BarChart buildBarChart(String label, CategoryAxis x, NumberAxis y) {
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
    public ScatterChart buildScatterChart(String label, CategoryAxis x, NumberAxis y) {
        ScatterChart<String, Number> chart = new ScatterChart<>(x, y);
        chart.setTitle(label);
        this.setChartStyle(chart);
        
        return chart;
    }
    
    /**
     * Builds chart.
     * @param type
     * @param label
     * @param lowerBound
     * @param upperBound
     * @param tickUnit
     * @param data 
     */
    private void _buildChart(Side side, ChartType type, String label, 
            double lowerBound, double upperBound, double tickUnit, 
            ObservableList<XYChart.Series<String, Integer>> data) {
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis(lowerBound, upperBound, tickUnit);
        x.setLabel("Date");

        
        switch (type) {
            case BAR:
                BarChart barChart = this.buildBarChart(label, x, y);
                barChart.setData(data);
                
                if (side == Side.LEFT) {
                    this.setLeftChart(barChart);
                }
                else {
                    this.setRightChart(barChart);
                }
                break;
            case SCATTER:
                ScatterChart scatterChart = this.buildScatterChart(label, x, y);
                scatterChart.setData(data);
                if (side == Side.LEFT) {
                    this.setLeftChart(scatterChart);
                }
                else {
                    this.setRightChart(scatterChart);
                }
                break;
            default:
                LineChart lineChart = this.buildLineChart(label, x, y);
                lineChart.setData(data);
                if (side == Side.LEFT) {
                    this.setLeftChart(lineChart);
                }
                else {
                    this.setRightChart(lineChart);
                }
                break;
        }
    }
    
    /**
     * Initializes charts for first time use.
     */
    private void _initCharts() {
        // Code below is for demonstration purpose only.
        ObservableList<XYChart.Series<String, Integer>> data = 
                FXCollections.observableArrayList();
        
        Series<String, Integer> values1 = new Series<>();
        values1.getData().add(new XYChart.Data("2022-10-25", 8));
        values1.getData().add(new XYChart.Data("2022-10-26", 4));
        values1.getData().add(new XYChart.Data("2022-10-27", 3));
        values1.getData().add(new XYChart.Data("2022-10-28", 0));
        values1.getData().add(new XYChart.Data("2022-10-29", -2));
        
        data.add(values1);
        // Code above is for demonstration purpose only.
        
        this._buildChart(Side.LEFT, ChartType.LINE, "Temperature", 
        -15, 15, 1, data);
        
        // Code below is for demonstration purpose only.
        data.clear();
        
        Series<String, Integer> values2 = new Series<>();
        values2.getData().add(new XYChart.Data("2022-10-25", 0));
        values2.getData().add(new XYChart.Data("2022-10-26", 1));
        values2.getData().add(new XYChart.Data("2022-10-27", 3));
        values2.getData().add(new XYChart.Data("2022-10-28", 2));
        values2.getData().add(new XYChart.Data("2022-10-29", 0));
        
        data.add(values2);
        // Code above is for demonstration purpose only.
        
        this._buildChart(Side.RIGHT, ChartType.BAR, "Precipitation", 
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
