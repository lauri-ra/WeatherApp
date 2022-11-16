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
import javafx.scene.layout.GridPane;

public final class Graph extends Element {
    private GridPane _innerContainer;
    private XYChart _leftChart;
    private XYChart _rightChart;
    public static final String LINE = "Line chart";
    public static final String BAR = "Bar chart";
    public static final String SCATTER = "Scatter chart";
    
    /**
     * Constructor
     */
    public Graph() {
        this.setInnerContainer(new GridPane());
        this._initCharts();
        this._buildInnerContainer();
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
     * Sets left chart.
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
     * Sets right chart.
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
     * @param xLegend
     * @param yLegend
     * @param lowerBound
     * @param upperBound
     * @param tickUnit
     * @param data 
     */
    public void updateChart(Side side, String type, String label, 
            String xLegend, String yLegend, double lowerBound, 
            double upperBound, double tickUnit, 
            ObservableList<XYChart.Series<String, Double>> data) {
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis(lowerBound, upperBound, tickUnit);
        x.setLabel(xLegend);
        y.setLabel(yLegend);

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
            if (this.getLeftChart() != null) {
                getInnerContainer().getChildren().remove(this.getLeftChart());
            }
            this.setLeftChart(chart);
            getInnerContainer().add(chart,  0, 0, 1, 1);
        }
        else {
            if (this.getRightChart() != null) {
                getInnerContainer().getChildren().remove(this.getRightChart());
            }
            this.setRightChart(chart);
            getInnerContainer().add(chart,  1, 0, 1, 1);
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
        
        this.updateChart(Side.LEFT, LINE, "Chart 1", "x-values", "y-values", 
        -5, 5, 1, data);

        this.updateChart(Side.RIGHT, BAR, "Chart 2", "x-values", "y-values", 
                0, 5, 1, data);
    }
    
    /**
     * Builds contents of inner container.
     */
    private void _buildInnerContainer() {
        // Column | row | column span | row span
        getInnerContainer().setMinWidth(1050);
        getInnerContainer().setMaxWidth(1050);
        getInnerContainer().setVgap(15);
        getInnerContainer().setPadding(new Insets(0, 20, 20, 20));
        
        this.getNodes().add(getInnerContainer());        
    }
}
