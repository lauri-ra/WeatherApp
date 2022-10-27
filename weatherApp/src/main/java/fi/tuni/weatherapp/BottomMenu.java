package fi.tuni.weatherapp;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class BottomMenu extends Element {
    private GridPane _innerContainer;
    private GridPane _saveLoadContainer;
    private ComboBox _leftOption;
    private ComboBox _rightOption;
    private ComboBox _leftChartType;
    private ComboBox _rightChartType;
    private Button _saveData;
    private Button _loadData;
    private Button _saveSettings;
    private Button _loadSettings;
    
    private ArrayList<String> errorMessages;
    
    /**
     * Constructor
     */
    public BottomMenu() {
        this.setInnerContainer(new GridPane());
        this._buildInnerContainer();
        this._setButtonHoverEvents();
    }
    
    /**
     * Returns inner container containing all individual nodes.
     * @return _innerContainer
     */
    public GridPane getInnerContainer() {
        return this._innerContainer;
    }
    
    /**
     * Sets innerContainer.
     * @param container 
     */
    public void setInnerContainer(GridPane container) {
        this._innerContainer = container;
    }
    
    /**
     * Returns container containing data and settings.
     * @return 
     */
    public GridPane getSaveLoadContainer() {
        return this._saveLoadContainer;
    }
    
    /**
     * Sets container containing data and settings.
     * @param container
     */
    public void setSaveLoadContainer(GridPane container) {
        this._saveLoadContainer = container;
    }
    
    /**
     * Returns left option combo box.
     * @return _leftOption
     */
    public ComboBox getLeftOptionComboBox() {
        return this._leftOption;
    }
    
    /**
     * Sets left option combo box.
     * @param comboBox 
     */
    public void setLeftOptionComboBox(ComboBox comboBox) {
        this._leftOption = comboBox;
    }
    
    /**
     * Populates left option combo box with data.
     * @param options 
     */
    public void populateLeftOptionComboBox(ArrayList<String> options) {
        for (var option : options) {
            this.getLeftOptionComboBox().getItems().add(option);
        }
    }
    
    /**
     * Returns left chart type combo box.
     * @return _leftChartType;
     */
    public ComboBox getLeftChartTypeComboBox() {
        return this._leftChartType;
    }
    
    /**
     * Sets left chart type combo box.
     * @param comboBox 
     */
    public void setLeftChartTypeComboBox(ComboBox comboBox) {
        this._leftChartType = comboBox;
    }
    
    /**
     * Populates left chart type combo box with data.
     * @param options 
     */
    public void populateLeftChartTypeComboBox(ArrayList<String> options) {
        for (var option : options) {
            this.getLeftChartTypeComboBox().getItems().add(option);
        }
    }
    
    /**
     * Returns right option combo box.
     * @return  _rightOption
     */
    public ComboBox getRightOptionComboBox() {
        return this._rightOption;
    }
    
    /**
     * Sets right option combo box.
     * @param comboBox 
     */
    public void setRightOptionComboBox(ComboBox comboBox) {
        this._rightOption = comboBox;
    }
    
    /**
     * Populates right option combo box with data.
     * @param options
     */
    public void populateRightOptionComboBox(ArrayList<String> options) {
        for (var option : options) {
            this.getRightOptionComboBox().getItems().add(option);
        }
    }

    /**
     * Returns right chart type combo box.
     * @return _rightChartType;
     */
    public ComboBox getRightChartTypeComboBox() {
        return this._rightChartType;
    }
    
    /**
     * Sets right chart type combo box.
     * @param comboBox 
     */
    public void setRightChartTypeComboBox(ComboBox comboBox) {
        this._rightChartType = comboBox;
    }
    
    /**
     * Populates right chart type combo box with data.
     * @param options 
     */
    public void populateRightChartTypeComboBox(ArrayList<String> options) {
        for (var option : options) {
            this.getRightChartTypeComboBox().getItems().add(option);
        }
    }
    
    /**
     * Returns save data button.
     * @return _saveData
     */
    public Button getSaveDataButton() {
        return this._saveData;
    }
    
    /**
     * Sets save data button.
     * @param button 
     */
    public void setSaveDataButton(Button button) {
        this._saveData = button;
    }
    
    /**
     * Returns load data button.
     * @return  _loadData
     */
    public Button getLoadDataButton() {
        return this._loadData;
    }
    
    /**
     * Sets load data button.
     * @param button 
     */
    public void setLoadDataButton(Button button) {
        this._loadData = button;
    }
    
    /**
     * Returns save settings button.
     * @return _saveSettings
     */
    public Button getSaveSettingsButton() {
        return this._saveSettings;
    }
    
    /**
     * Sets save settings button.
     * @param button 
     */
    public void setSaveSettingsButton(Button button) {
        this._saveSettings = button;
    }
    
    /**
     * Returns load settings button.
     * @return _loadSettings
     */
    public Button getLoadSettingsButton() {
        return this._loadSettings;
    }
    
    /**
     * Sets load settings button.
     * @param button 
     */
    public void setLoadSettingsButton(Button button) {
        this._loadSettings = button;
    }
    
    /**
     * Returns predefined font.
     * @return font
     */
    public Font getFont() {
        Font font = Font.font("Liberation Sans", FontWeight.BOLD, 
                FontPosture.REGULAR, 16);
        return font;
    }

    /**
     * Deactivates combo box.
     * @param comboBox 
     */
    public void deactivateComboBox(ComboBox comboBox) {
        comboBox.setEditable(false);
    }
    
    /**
     * Activates combo box.
     * @param comboBox 
     */
    public void activateComboBox(ComboBox comboBox) {
        comboBox.setEditable(true);
    }
    
    /**
     * Changes color of button to default.
     * @param button
     */
    public void setButtonDefaultStyle(Button button) {
        button.setStyle("-fx-text-fill: #fff; "
                      + "-fx-background-color: #44A0B5; "
                      + "-fx-background-radius: 5px;");
    }
    
    /**
     * Changes color of button upon hovering.
     * @param button 
     */
    public void setButtonHoverStyle(Button button) {
        button.setStyle("-fx-text-fill: #fff; "
                     + "-fx-background-color: #65BBCF; "
                     + "-fx-background-radius: 5px;");        
    }
    
    /**
     * Builds combo box.
     * @param options
     * @param promptText
     * @return comboBox
     */
    private ComboBox _buildComboBox(ArrayList<String> options, String... promptText) {
        ComboBox comboBox = new ComboBox();
        
        comboBox.setStyle(
            "-fx-background-color: #fff; -fx-border-radius: 5px; "
            + "-fx-border-color: #bfbfbf"
        );
        comboBox.setMinSize(200, 30);
        comboBox.setMaxSize(200, 30);
        
        if (promptText.length > 0) {
            comboBox.setPromptText(promptText[0]);   
        }
        
        for (String option : options) {
            comboBox.getItems().add(option);
        }
        return comboBox;
    }
    
    /**
     * Builds button.
     * @param text
     * @return button
     */
    private Button _buildButton(String text) {
        Button button = new Button(text);
        button.setFont(this.getFont());
        setButtonDefaultStyle(button);
        button.setMinSize(80, 35);
        button.setMaxSize(80, 35);
        return button;
    }
    
    /**
     * Builds saveLoadContainer containing data and settings buttons.
     */
    private void _buildSaveLoadContainer() {
        var container = new GridPane();
        
        var dataLabel = new Label("DATA");
        dataLabel.setFont(this.getFont());
        
        var settingsLabel = new Label("SETTINGS");
        settingsLabel.setFont(this.getFont());
        
        this.setSaveDataButton(this._buildButton("SAVE"));
        this.setLoadDataButton(this._buildButton("LOAD"));
        this.setSaveSettingsButton(this._buildButton("SAVE"));
        this.setLoadSettingsButton(this._buildButton("LOAD"));
        
        // Column | row | column span | row span
        container.add(dataLabel,                  0, 0, 2, 1);
        container.add(settingsLabel,              2, 0, 2, 1);
        container.add(getSaveDataButton(),        0, 1, 1, 1);
        container.add(getLoadDataButton(),        1, 1, 1, 1);
        container.add(getSaveSettingsButton(),    2, 1, 1, 1);
        container.add(getLoadSettingsButton(),    3, 1, 1, 1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        container.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        container.setVgap(10);
        container.setHgap(20);
        container.setPadding(new Insets(10, 20, 10, 20));
        
        container.setStyle("-fx-background-color: #9ED4E0;"
                         + "-fx-background-radius: 5px;");
        
        this.setSaveLoadContainer(container); 
    }
    
    /**
     * Builds contents of inner container.
     */
    private void _buildInnerContainer() {
        var chart1Label = new Label("CHART 1");
        chart1Label.setFont(this.getFont());
        
        var chart1TypeLabel = new Label("CHART 1 TYPE");
        chart1TypeLabel.setFont(this.getFont());
        
        var chart2Label = new Label("CHART 2");
        chart2Label.setFont(this.getFont());
        
        var chart2TypeLabel = new Label("CHART 2 TYPE");
        chart2TypeLabel.setFont(this.getFont());
        
        // Code below is for demonstration purpose only.
        ArrayList<String> options = new ArrayList();
        options.add("Temperature");
        options.add("Predicted temperature");
        options.add("Wind");
        options.add("Predicted wind");
        options.add("Cloudiness");
        options.add("Precipitation");
        options.add("Winter slipperiness");
        options.add("Overall condition");
        
        ArrayList<String> charts = new ArrayList();
        charts.add("Line chart");
        charts.add("Bar chart");
        charts.add("Scatter chart");
        // Code above is for demonstration purpose only.
        
        this.setLeftOptionComboBox(this._buildComboBox(options));
        this.setLeftChartTypeComboBox(this._buildComboBox(charts, "Line chart"));
        this.setRightOptionComboBox(this._buildComboBox(options));
        this.setRightChartTypeComboBox(this._buildComboBox(charts, "Line chart"));
        this._buildSaveLoadContainer();
        
        // Column | row | column span | row span
        getInnerContainer().add(chart1Label,                    0, 0, 1, 1);
        getInnerContainer().add(getLeftOptionComboBox(),        0, 1, 1, 1);
        getInnerContainer().add(chart1TypeLabel,                0, 2, 1, 1);
        getInnerContainer().add(getLeftChartTypeComboBox(),     0, 3, 1, 1);
        getInnerContainer().add(chart2Label,                    1, 0, 1, 1);
        getInnerContainer().add(getRightOptionComboBox(),       1, 1, 1, 1);
        getInnerContainer().add(chart2TypeLabel,                1, 2, 1, 1);
        getInnerContainer().add(getRightChartTypeComboBox(),    1, 3, 1, 1);
        getInnerContainer().add(getSaveLoadContainer(),         2, 0, 1, 4);
        
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        getInnerContainer().getColumnConstraints().addAll(col1,col2,col3);
        
        getInnerContainer().setMinWidth(1000);
        getInnerContainer().setMaxWidth(1000);
        getInnerContainer().setHgap(20);
        getInnerContainer().setVgap(10);
        getInnerContainer().setPadding(new Insets(10, 50, 50, 50));
        
        this.getNodes().add(getInnerContainer());
    }
    
    private void _setButtonHoverEvents() {
        this.getSaveDataButton().setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(this.getSaveDataButton());
        });
        this.getSaveDataButton().setOnMouseExited(event -> {
            this.setButtonDefaultStyle(this.getSaveDataButton());
        });
        this.getLoadDataButton().setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(this.getLoadDataButton());
        });
        this.getLoadDataButton().setOnMouseExited(event -> {
            this.setButtonDefaultStyle(this.getLoadDataButton());
        });
        this.getSaveSettingsButton().setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(this.getSaveSettingsButton());
        });
        this.getSaveSettingsButton().setOnMouseExited(event -> {
            this.setButtonDefaultStyle(this.getSaveSettingsButton());
        });
        
    }
}
