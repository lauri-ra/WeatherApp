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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public final class TopMenu extends Element {
    private GridPane _innerContainer;
    private GridPane _choiceContainer;
    private TextField _coordinates;
    private HBox _startDate;
    private DatePicker _startDatePicker;
    private HBox _endDate;
    private DatePicker _endDatePicker;
    private ComboBox _forecast;
    private Button _apply;
    private Button _reset;
    
    private ArrayList<String> errorMessages;
    
    /**
     * Constructor
     */
    public TopMenu() {
        this.setInnerContainer(new GridPane());
        this._buildInnerContainer();
        this._setButtonEvents();
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
    public HBox getStartDateContainer() {
        return this._startDate;
    }
    
    /**
     * Sets start date tile pane.
     * @param container
     */
    public void setStartDateContainer(HBox container) {
        this._startDate = container;
    }
    
    /**
     * Returns start date picker.
     * @return _startDatePicker
     */
    public DatePicker getStartDatePicker() {
        return this._startDatePicker;
    }
    
    /**
     * Sets start date picker.
     * @param datePicker 
     */
    public void setStartDatePicker(DatePicker datePicker) {
        this._startDatePicker = datePicker;
    }
    
    /**
     * Returns end date tile pane.
     * @return  _endDate
     */
    public HBox getEndDateContainer() {
        return this._endDate;
    }
    
    /**
     * Sets end date tile pane.
     * @param container
     */
    public void setEndDateContainer(HBox container) {
        this._endDate = container;
    }
    
    /**
     * Returns end date picker.
     * @return _endDatePicker
     */
    public DatePicker getEndDatePicker() {
        return this._endDatePicker;
    }
    
    /**
     * Sets end date picker.
     * @param datePicker 
     */
    public void setEndDatePicker(DatePicker datePicker) {
        this._endDatePicker = datePicker;
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
     * Returns reset button.
     * @return  _reset
     */
    public Button getResetButton() {
        return this._reset;
    }
    
    /**
     * Sets reset button.
     * @param button 
     */
    public void setResetButton(Button button) {
        this._reset = button;
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
     * Populates combo box with data.
     * @param options
     * @param comboBox 
     */
    public void populateComboBox(ArrayList<String> options, ComboBox comboBox) {
        for (String option : options) {
            comboBox.getItems().add(option);
        }
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
     * @return container
     */
    private HBox _buildDatePickerContainer() {
        HBox container = new HBox();
        container.setMinSize(170, 30);
        container.setMaxSize(170, 30);
        
        return container;
    }
    
    /**
     * Builds date picker.
     * @return datePicker
     */
    private DatePicker _buildDatePicker() {
        LocalDate date = LocalDate.now();
        DatePicker datePicker = new DatePicker(date);
        datePicker.setMinSize(170, 30);
        datePicker.setMaxSize(170, 30);
        
        return datePicker;
    }
    
    /**
     * Builds combo box.
     * @param options
     * @param promptText
     * @return comboBox
     */
    private ComboBox _buildComboBox(String promptText) {
        ComboBox comboBox = new ComboBox();
        
        comboBox.setStyle(
            "-fx-background-color: #fff; "
          + "-fx-border-radius: 5px; "
          + "-fx-border-color: #bfbfbf"
        );
        comboBox.setMinSize(130, 30);
        comboBox.setMaxSize(130, 30);
        comboBox.setPromptText(promptText);
        
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
        
        // Code below is for demonstration purpose only.
        ArrayList<String> options = new ArrayList();
        options.add("2 hours");
        options.add("4 hours");
        options.add("6 hours");
        options.add("12 hours");
        // Code above is for demonstration purpose only.
        
        this.setEndDateContainer(this._buildDatePickerContainer());
        this.setEndDatePicker(this._buildDatePicker());
        this.getEndDateContainer().getChildren().add(this.getEndDatePicker());
        this.setForecastComboBox(this._buildComboBox("Time"));
        // Code below is for demonstration purpose only.
        this.populateComboBox(options, this.getForecastComboBox());
        // Code above is for demonstration purpose only.
        
        // Column | row | column span | row span
        container.add(endDateLabel,             0, 0, 1, 1);
        container.add(forecastLabel,            2, 0, 1, 1);
        container.add(getEndDateContainer(),    0, 1, 1, 1);
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
        this.setStartDateContainer(this._buildDatePickerContainer());
        this.setStartDatePicker(this._buildDatePicker());
        this.getStartDateContainer().getChildren().add(this.getStartDatePicker());
        this._buildChoiceContainer();
        this.setApplyButton(this._buildButton("APPLY"));
        this.setResetButton(this._buildButton("RESET"));
        
        // Column | row | column span | row span
        getInnerContainer().add(titleLabel,                 0, 0, 4, 1);
        getInnerContainer().add(coordinatesLabel,           0, 1, 1, 1);
        getInnerContainer().add(startDateLabel,             1, 1, 1, 1);
        getInnerContainer().add(getChoiceContainer(),       2, 1, 1, 2);
        getInnerContainer().add(getApplyButton(),           3, 1, 1, 1);
        getInnerContainer().add(getCoordinatesTextField(),  0, 2, 1, 1);
        getInnerContainer().add(getStartDateContainer(),     1, 2, 1, 1);
        getInnerContainer().add(getResetButton(),           3, 2, 1, 1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        getInnerContainer().getColumnConstraints().addAll(col1, col2, col3, col4);
        
        getInnerContainer().setHalignment(getApplyButton(), HPos.RIGHT);
        getInnerContainer().setHalignment(getResetButton(), HPos.RIGHT);
        
        getInnerContainer().setMinWidth(1000);
        getInnerContainer().setMaxWidth(1000);
        getInnerContainer().setHgap(20);
        getInnerContainer().setPadding(new Insets(50, 50, 10, 50));
        
        this.getNodes().add(getInnerContainer());
    }
    
    /**
     * Sets button hover event.
     * @param button 
     */
    private void _setButtonHoverEvent(Button button) {
        button.setOnMouseEntered(event -> {               
            this.setButtonHoverStyle(button);
        });
        button.setOnMouseExited(event -> {
            this.setButtonDefaultStyle(button);
        });        
    }
    
    private void _setForecastClickEvent(ComboBox comboBox) {
        comboBox.setOnMouseClicked(event -> {
            this.getEndDatePicker().setDisable(true);
        });        
    }
    
     private void _setEndDateClickEvent(DatePicker datePicker) {
        datePicker.setOnMouseClicked(event -> {
            this.getForecastComboBox().setDisable(true);
        });   
         datePicker.getEditor().setOnMouseClicked(event -> {
            this.getForecastComboBox().setDisable(true);
        });
    }   
 
    /**
     * Sets apply button click event.
     * @param button 
     */
    private void _setApplyButtonClickEvent(Button button) {
        button.setOnAction(event -> {
            this.getListener().handleApply();
        });
    }
    
    /**
     * Sets reset button click event.
     * @param button 
     */
    private void _setResetButtonClickEvent(Button button) {
        button.setOnAction(event -> {
            this.getListener().handleReset();
        });
    }
    
    /**
     * Sets all button events.
     */
    private void _setButtonEvents() {
        this._setButtonHoverEvent(this.getApplyButton());
        this._setButtonHoverEvent(this.getResetButton());
        
        this._setApplyButtonClickEvent(this.getApplyButton());
        this._setResetButtonClickEvent(this.getResetButton());
        this._setForecastClickEvent(this.getForecastComboBox());
        this._setEndDateClickEvent(this.getEndDatePicker());
    }
}
