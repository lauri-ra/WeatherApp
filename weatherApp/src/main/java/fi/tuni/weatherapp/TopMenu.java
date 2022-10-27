package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TopMenu extends Element {
    private GridPane _innerContainer;
    private GridPane _choiceContainer;
    private TextField _coordinates;
    private TilePane _startDate;
    private TilePane _endDate;
    private ComboBox _forecast;
    private Button _apply;
    private Button _clear;
    
    private ArrayList<String> errorMessages;
    
    /**
     * Constructor
     */
    public TopMenu() {
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
     * Returns container containing end date and forecast.
     * @return _choiceContainer;
     */
    public GridPane getChoiceContainer() {
        return this._choiceContainer;
    }
    
    /**
     * Sets choiceContainer.
     * @param container 
     */
    public void setChoiceContainer(GridPane container) {
        this._choiceContainer = container;
    }
    
    /**
     * Returns coordinate text field.
     * @return _coordinates 
     */
    public TextField getCoordinatesTextField() {
        return this._coordinates;
    }
    
    /**
     * Sets coordinates text field.
     * @param textField
     */
    public void setCoordinatesTextField(TextField textField) {
        this._coordinates = textField;
    }
    
    /**
     * Returns start date tile pane.
     * @return _startDate
     */
    public TilePane getStartDateTilePane() {
        return this._startDate;
    }
    
    /**
     * Sets start date tile pane.
     * @param tilePane
     */
    public void setStartDateTilePane(TilePane tilePane) {
        this._startDate = tilePane;
    }
    
    /**
     * Returns end date tile pane.
     * @return  _endDate
     */
    public TilePane getEndDateTilePane() {
        return this._endDate;
    }
    
    /**
     * Sets end date tile pane.
     * @param tilePane
     */
    public void setEndDateTilePane(TilePane tilePane) {
        this._endDate = tilePane;
    }
    
    /**
     * Returns forecast combo box.
     * @return  _forecast
     */
    public ComboBox getForecastComboBox() {
        return this._forecast;
    }
    
    /**
     * Sets forecast combo box.
     * @param comboBox 
     */
    public void setForecastComboBox(ComboBox comboBox) {
        this._forecast = comboBox;
    }
    
    /**
     * Returns apply button.
     * @return _apply
     */
    public Button getApplyButton() {
        return this._apply;
    }
    
    /**
     * Sets apply button.
     * @param button 
     */
    public void setApplyButton(Button button) {
        this._apply = button;
    }
    
    /**
     * Returns clear button.
     * @return  _clear
     */
    public Button getClearButton() {
        return this._clear;
    }
    
    /**
     * Sets clear button.
     * @param button 
     */
    public void setClearButton(Button button) {
        this._clear = button;
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
     * Deactivates text field.
     * @param textField 
     */
    public void deactivateTextField(TextField textField) {
        textField.setEditable(false);
    }
    
    /**
     * Activates text field.
     * @param textField 
     */
    public void activateTextField(TextField textField) {
        textField.setEditable(true);
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
     * Builds text field.
     * @return textField
     */
    private TextField _buildTextField() {
        TextField textField = new TextField();
        textField.setMinSize(170, 30);
        textField.setMaxSize(170, 30);
        return textField;
    }
    
    /**
     * Builds date picker.
     * @return 
     */
    private TilePane _buildDatePicker() {
        TilePane tilePane = new TilePane();
        tilePane.setMinSize(170, 30);
        tilePane.setMaxSize(170, 30);
        
        LocalDate date = LocalDate.now();
        DatePicker datePicker = new DatePicker(date);
        tilePane.getChildren().add(datePicker);
        
        return tilePane;
    }
    
    /**
     * Builds combo box.
     * @param options
     * @param promptText
     * @return comboBox
     */
    private ComboBox _buildComboBox(ArrayList<String> options, String promptText) {
        ComboBox comboBox = new ComboBox();
        
        comboBox.setStyle(
            "-fx-background-color: #fff; "
          + "-fx-border-radius: 5px; "
          + "-fx-border-color: #bfbfbf"
        );
        comboBox.setMinSize(130, 30);
        comboBox.setMaxSize(130, 30);
        comboBox.setPromptText(promptText);
        
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
     * Builds choiceContainer containing end date and forecast.
     */
    private void _buildChoiceContainer() {
        var container = new GridPane();
        
        var endDateLabel = new Label("END DATE");
        endDateLabel.setFont(this.getFont());
        
        var orLabel = new Label("OR");
        orLabel.setFont(this.getFont());
        orLabel.setStyle("-fx-text-fill: #44A0B5");
        
        var forecastLabel = new Label("FORECAST");
        forecastLabel.setFont(this.getFont());
        
        // ArrayList created below is just for demonstration. This info would
        // normally come from elsewhere (controller?).
        ArrayList<String> options = new ArrayList();
        options.add("2 hours");
        options.add("4 hours");
        options.add("6 hours");
        options.add("12 hours");
        
        this.setEndDateTilePane(this._buildDatePicker());
        this.setForecastComboBox(this._buildComboBox(options, "Time"));
        
        // Column | row | column span | row span
        container.add(endDateLabel,             0, 0, 1, 1);
        container.add(forecastLabel,            2, 0, 1, 1);
        container.add(getEndDateTilePane(),     0, 1, 1, 1);
        container.add(orLabel,                  1, 1, 1, 1);
        container.add(getForecastComboBox(),    2, 1, 1, 1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints();
        container.getColumnConstraints().addAll(col1, col2, col3);
        
        container.setVgap(10);
        container.setHgap(20);
        container.setPadding(new Insets(10, 20, 10, 20));
        
        container.setStyle("-fx-background-color: #9ED4E0;"
                         + "-fx-background-radius: 5px;");

        this.setChoiceContainer(container);        
    }
    
    /**
     * Builds contents of inner container.
     */
    private void _buildInnerContainer() {
        var titleLabel = new Label("WeatherApp");
        titleLabel.setFont(Font.font("Liberation Sans", FontWeight.BOLD, 
                FontPosture.REGULAR, 30));
        titleLabel.setPadding(new Insets(0, 0, 20, 0));
        
        var coordinatesLabel = new Label("COORDINATES");
        coordinatesLabel.setFont(this.getFont());
        
        var startDateLabel = new Label("START DATE");
        startDateLabel.setFont(this.getFont());
        
        this.setCoordinatesTextField(this._buildTextField());
        this.setStartDateTilePane(this._buildDatePicker());
        this._buildChoiceContainer();
        this.setApplyButton(this._buildButton("APPLY"));
        this.setClearButton(this._buildButton("CLEAR"));
        
        // Column | row | column span | row span
        getInnerContainer().add(titleLabel,                 0, 0, 4, 1);
        getInnerContainer().add(coordinatesLabel,           0, 1, 1, 1);
        getInnerContainer().add(startDateLabel,             1, 1, 1, 1);
        getInnerContainer().add(getChoiceContainer(),       2, 1, 1, 2);
        getInnerContainer().add(getApplyButton(),           3, 1, 1, 1);
        getInnerContainer().add(getCoordinatesTextField(),  0, 2, 1, 1);
        getInnerContainer().add(getStartDateTilePane(),     1, 2, 1, 1);
        getInnerContainer().add(getClearButton(),           3, 2, 1, 1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        getInnerContainer().getColumnConstraints().addAll(col1, col2, col3, col4);
        
        getInnerContainer().setHalignment(getApplyButton(), HPos.RIGHT);
        getInnerContainer().setHalignment(getClearButton(), HPos.RIGHT);
        
        getInnerContainer().setMinWidth(1000);
        getInnerContainer().setMaxWidth(1000);
        getInnerContainer().setHgap(20);
        getInnerContainer().setPadding(new Insets(50, 50, 10, 50));
        
        this.getNodes().add(getInnerContainer());
    }
    
    private void _setButtonHoverEvents() {
        this.getApplyButton().setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(this.getApplyButton());
        });
        this.getApplyButton().setOnMouseExited(event -> {
            this.setButtonDefaultStyle(this.getApplyButton());
        });
        this.getClearButton().setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(this.getClearButton());
        });
        this.getClearButton().setOnMouseExited(event -> {
            this.setButtonDefaultStyle(this.getClearButton());
        });
        
    }
}
