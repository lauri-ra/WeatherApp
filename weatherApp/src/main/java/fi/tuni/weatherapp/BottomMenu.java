package fi.tuni.weatherapp;

import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class BottomMenu extends Element {
    private GridPane _innerContainer;
    private GridPane _settingsContainer;
    private ScrollPane _trafficMsgsContainer;
    private ComboBox _leftOption;
    private ComboBox _rightOption;
    private ComboBox _leftChartType;
    private ComboBox _rightChartType;
    private GridPane _leftChartButtonsContainer;
    private Button _leftChartApply;
    private Button _leftChartSave;
    private Button _leftChartLoad;
    private GridPane _rightChartButtonsContainer;
    private Button _rightChartApply;
    private Button _rightChartSave;
    private Button _rightChartLoad;
    private Label _trafficMsgsLabel;
    private Label _trafficMsgCount;
    private Text _trafficMsgs;
    private Button _saveSettings;
    private Button _loadSettings;
    
    private ArrayList<String> errorMessages;
    
    /**
     * Constructor
     */
    public BottomMenu() {
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
     * Sets inner container.
     * @param container 
     */
    public void setInnerContainer(GridPane container) {
        this._innerContainer = container;
    }  
    
    /**
     * Returns container containing data and settings.
     * @return _settingsContainer
     */
    public GridPane getSettingsContainer() {
        return this._settingsContainer;
    }
    
    /**
     * Sets container containing data and settings.
     * @param container
     */
    public void setSettingsContainer(GridPane container) {
        this._settingsContainer = container;
    }
    
    /**
     * Returns scollable container containing traffic messages.
     * @return _trafficMsgsContainer
     */
    public ScrollPane getTrafficMsgsContainer() {
        return this._trafficMsgsContainer;
    }
    
    /**
     * Sets scrollable container containing traffic messages.
     * @param container
     */
    public void setTrafficMsgsContainer(ScrollPane container) {
        this._trafficMsgsContainer = container;
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
     * Returns left chart buttons container.
     * @return _leftChartButtonsContainer
     */
    public GridPane getLeftChartButtonsContainer() {
        return this._leftChartButtonsContainer;
    }
    
    /**
     * Sets left chart buttons container.
     * @param container 
     */
    public void setLeftChartButtonsContainer(GridPane container) {
        this._leftChartButtonsContainer = container;
    }
    /**
     * Returns left chart apply button.
     * @return _leftChartApply
     */
    public Button getLeftChartApplyButton() {
        return this._leftChartApply;
    }
    
    /**
     * Sets left chart apply button.
     * @param button 
     */
    public void setLeftChartApplyButton(Button button) {
        this._leftChartApply = button;
    }   
    
    /**
     * Returns left chart save button.
     * @return _leftChartSave
     */
    public Button getLeftChartSaveButton() {
        return this._leftChartSave;
    }
    
    /**
     * Sets left chart save button.
     * @param button 
     */
    public void setLeftChartSaveButton(Button button) {
        this._leftChartSave = button;
    }
    
    /**
     * Returns left chart load button.
     * @return  _leftChartLoad
     */
    public Button getLeftChartLoadButton() {
        return this._leftChartLoad;
    }
    
    /**
     * Sets left chart load button.
     * @param button 
     */
    public void setLeftChartLoadButton(Button button) {
        this._leftChartLoad = button;
    }
    
    /**
     * Returns right chart buttons container.
     * @return _rightChartButtonsContainer
     */
    public GridPane getRightChartButtonsContainer() {
        return this._rightChartButtonsContainer;
    }
    
    /**
     * Sets right chart buttons container.
     * @param container 
     */
    public void setRightChartButtonsContainer(GridPane container) {
        this._rightChartButtonsContainer = container;
    }

    /**
     * Returns right chart apply button.
     * @return _rightChartApply
     */
    public Button getRightChartApplyButton() {
        return this._rightChartApply;
    }
    
    /**
     * Sets right chart apply button.
     * @param button 
     */
    public void setRightChartApplyButton(Button button) {
        this._rightChartApply = button;
    }   
    
    /**
     * Returns right chart save button.
     * @return _rightChartSave
     */
    public Button getRightChartSaveButton() {
        return this._rightChartSave;
    }
    
    /**
     * Sets right chart save button.
     * @param button 
     */
    public void setRightChartSaveButton(Button button) {
        this._rightChartSave = button;
    }
    
    /**
     * Returns right chart load button.
     * @return  _rightChartLoad
     */
    public Button getRightChartLoadButton() {
        return this._rightChartLoad;
    }
    
    /**
     * Sets right chart load button.
     * @param button 
     */
    public void setRightChartLoadButton(Button button) {
        this._rightChartLoad = button;
    }
    
    /**
     * Returns traffic message count.
     * @return _trafficMsgCount;
     */
    public Label getTrafficMsgCount() {
        return this._trafficMsgCount;
    }
    
    /**
     * Sets traffic message count.
     * @param count
     */
    public void setTrafficMsgCount(Label count) {
        this._trafficMsgCount = count;
    }   
    
    /**
     * Returns traffic messages.
     * @return _trafficMsgs
     */
    public Text getTrafficMsgs() {
        return this._trafficMsgs;
    }
    
    /**
     * Sets traffic messages.
     * @param text 
     */
    public void setTrafficMsgs(Text text) {
        this._trafficMsgs = text;
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
        comboBox.getItems().clear();
        for (String option : options) {
            comboBox.getItems().add(option);
        }
    }
    
    /**
     * Updates traffic messages.
     * @param messages 
     */
    public void updateTrafficMsgs(ArrayList<String> messages) {
        var text = "";
        var count = messages.size();
        
        
        for (var message : messages) {
            text += (message + System.lineSeparator());
        }
        System.out.println("Count:" + count);
        this.getTrafficMsgCount().setText(Integer.toString(count));
        _trafficMsgsLabel.setText("TRAFFIC MESSAGES " + "(" + count  + ")");
        this.getTrafficMsgs().setText(text);
    }
    
    /**
     * Builds combo box.
     * @param options
     * @param promptText
     * @return comboBox
     */
    private ComboBox _buildComboBox() {
        ComboBox comboBox = new ComboBox();
        
        comboBox.setStyle(
            "-fx-background-color: #fff; -fx-border-radius: 5px; "
            + "-fx-border-color: #bfbfbf"
        );
        comboBox.setMinSize(260, 30);
        comboBox.setMaxSize(260, 30);

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
    
    private GridPane _buildButtonsContainer(Button button1, Button button2, 
            Button button3) {
        var container = new GridPane();
        
        container.add(button1,  0, 0, 1, 1);
        container.add(button2,  1, 0, 1, 1);
        container.add(button3,  2, 0, 1, 1);
        
        container.setHgap(10);
        container.setAlignment(Pos.CENTER);
        
        return container;
    }
    
    private ScrollPane _buildTrafficMsgsContainer() {
        var container = new ScrollPane();
        
        container.setMaxHeight(50);
        container.setMinHeight(50);
        container.setMaxWidth(700);
        container.setStyle("-fx-background: transparent; -"
                         + "fx-background-color: transparent;");
        container.setVbarPolicy(ScrollBarPolicy.NEVER);
        
        return container;
    }
    
    /**
     * Builds saveLoadContainer containing data and settings buttons.
     */
    private void _buildSettingsContainer() {
        var container = new GridPane();      
        
        this.setTrafficMsgsContainer(this._buildTrafficMsgsContainer());
        this.setTrafficMsgCount(new Label("0"));
        this.setTrafficMsgs(new Text(""));
        this.getTrafficMsgs().setWrappingWidth(650);
        
        this._trafficMsgsLabel = new Label("TRAFFIC MESSAGES " + "(" 
                + this.getTrafficMsgCount().getText() + ")");
        this._trafficMsgsLabel.setFont(this.getFont());
        
        var trafficMsgs = new ArrayList<String>();
        
        this.updateTrafficMsgs(trafficMsgs);
        this.getTrafficMsgsContainer().setContent(this.getTrafficMsgs());
        
        var settingsLabel = new Label("SETTINGS");
        settingsLabel.setFont(this.getFont());
        
        this.setSaveSettingsButton(this._buildButton("SAVE"));
        this.setLoadSettingsButton(this._buildButton("LOAD"));
        
        // Column | row | column span | row span
        container.add(_trafficMsgsLabel,                 0, 0, 2, 1);
        container.add(this.getTrafficMsgsContainer(),   0, 1, 2, 1);
        container.add(settingsLabel,                    1, 0, 2, 1);
        container.add(getSaveSettingsButton(),          1, 1, 1, 1);
        container.add(getLoadSettingsButton(),          2, 1, 1, 1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(80);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3= new ColumnConstraints();
        col3.setPercentWidth(10);
        col3.setHalignment(HPos.CENTER);
        container.getColumnConstraints().addAll(col1, col2, col3);

        container.setVgap(10);
        container.setPadding(new Insets(10, 20, 10, 20));
        
        container.setStyle("-fx-background-color: #9ED4E0;"
                         + "-fx-background-radius: 5px;");
        
        this.setSettingsContainer(container); 
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
        
        ArrayList<String> charts = new ArrayList();
        charts.add("Line chart");
        charts.add("Bar chart");
        charts.add("Scatter chart");
        
        this.setLeftOptionComboBox(this._buildComboBox());
        this.getLeftOptionComboBox().setDisable(true);
        
        this.setLeftChartTypeComboBox(this._buildComboBox());
        this.populateComboBox(charts, this.getLeftChartTypeComboBox());
        this.getLeftChartTypeComboBox().getSelectionModel().selectFirst();
        this.getLeftChartTypeComboBox().setDisable(true);
        
        this.setRightOptionComboBox(this._buildComboBox());
        this.getRightOptionComboBox().setDisable(true);
        
        this.setRightChartTypeComboBox(this._buildComboBox());
        this.populateComboBox(charts, this.getRightChartTypeComboBox());
        this.getRightChartTypeComboBox().getSelectionModel().selectFirst();
        this.getRightChartTypeComboBox().setDisable(true);
        
        this.setLeftChartApplyButton(this._buildButton("APPLY"));
        this.setLeftChartSaveButton(this._buildButton("SAVE"));
        this.setLeftChartLoadButton(this._buildButton("LOAD"));
        this.setLeftChartButtonsContainer(this._buildButtonsContainer(
                this.getLeftChartApplyButton(), 
                this.getLeftChartSaveButton(), 
                this.getLeftChartLoadButton()));
        this.getLeftChartApplyButton().setDisable(true);
        this.getLeftChartSaveButton().setDisable(true);
        this.getLeftChartLoadButton().setDisable(true);
        this.setRightChartApplyButton(this._buildButton("APPLY"));
        this.setRightChartSaveButton(this._buildButton("SAVE"));
        this.setRightChartLoadButton(this._buildButton("LOAD"));
        this.setRightChartButtonsContainer(this._buildButtonsContainer(
                this.getRightChartApplyButton(), 
                this.getRightChartSaveButton(), 
                this.getRightChartLoadButton()));
        this.getRightChartApplyButton().setDisable(true);
        this.getRightChartSaveButton().setDisable(true);
        this.getRightChartLoadButton().setDisable(true);
        this._buildSettingsContainer();
        
        // Column | row | column span | row span    
        getInnerContainer().add(chart1Label,                            0, 0, 1, 1);
        getInnerContainer().add(getLeftOptionComboBox(),                0, 1, 1, 1);
        getInnerContainer().add(chart1TypeLabel,                        0, 2, 1, 1);
        getInnerContainer().add(getLeftChartTypeComboBox(),             0, 3, 1, 1);
        getInnerContainer().add(this.getLeftChartButtonsContainer(),    0, 4, 1, 1);        
        getInnerContainer().add(chart2Label,                            1, 0, 1, 1);
        getInnerContainer().add(getRightOptionComboBox(),               1, 1, 1, 1);
        getInnerContainer().add(chart2TypeLabel,                        1, 2, 1, 1);
        getInnerContainer().add(getRightChartTypeComboBox(),            1, 3, 1, 1);
        getInnerContainer().add(this.getRightChartButtonsContainer(),   1, 4, 1, 1);
        getInnerContainer().add(this.getSettingsContainer(),            0, 5, 2, 1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setHalignment(HPos.CENTER);
        getInnerContainer().getColumnConstraints().addAll(col1, col2);
        
        getInnerContainer().setMinWidth(1050);
        getInnerContainer().setMaxWidth(1050);
        getInnerContainer().setVgap(10);
        getInnerContainer().setPadding(new Insets(0, 30, 50, 30));
        
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
    
    /**
     * Sets left chart apply button click event.
     */
    private void _setLeftChartApplyButtonClickEvent() {
        this.getLeftChartApplyButton().setOnAction(event -> {
            this.getListener().handleLeftChartApply();
        });        
    }
    
    /**
     * Sets left chart save button click event.
     */
    private void _setLeftChartSaveButtonClickEvent() {
        this.getLeftChartSaveButton().setOnAction(event -> {
            this.getListener().handleLeftChartSave();
        });        
    }

    /**
     * Sets left chart load button click event.
     */
    private void _setLeftChartLoadButtonClickEvent() {
        this.getLeftChartLoadButton().setOnAction(event -> {
            this.getListener().handleLeftChartLoad();
        });        
    }
    
    /**
     * Sets right chart apply button click event.
     */
    private void _setRightChartApplyButtonClickEvent() {
        this.getRightChartApplyButton().setOnAction(event -> {
            this.getListener().handleRightChartApply();
        });        
    }
    
    /**
     * Sets right chart save button click event.
     */
    private void _setRightChartSaveButtonClickEvent() {
        this.getRightChartSaveButton().setOnAction(event -> {
            this.getListener().handleRightChartSave();
        });        
    }

    /**
     * Sets right chart load button click event.
     */
    private void _setRightChartLoadButtonClickEvent() {
        this.getRightChartLoadButton().setOnAction(event -> {
            this.getListener().handleRightChartLoad();
        });        
    }
    
    /**
     * Sets load data button click event.
     */
    private void _setSaveSettingsButtonClickEvent() {
        this.getSaveSettingsButton().setOnAction(event -> {
            this.getListener().handleSaveSettings();
        });        
    }
    
    /**
     * Sets load data button click event.
     */
    private void _setLoadSettingsButtonClickEvent() {
        this.getLoadSettingsButton().setOnAction(event -> {
            this.getListener().handleLoadSettings();
        });        
    }
    
    /**
     * Sets all button events.
     */
    private void _setButtonEvents() {
        this._setButtonHoverEvent(this.getLeftChartApplyButton());
        this._setButtonHoverEvent(this.getLeftChartSaveButton());
        this._setButtonHoverEvent(this.getLeftChartLoadButton());
        this._setButtonHoverEvent(this.getRightChartApplyButton());
        this._setButtonHoverEvent(this.getRightChartSaveButton());
        this._setButtonHoverEvent(this.getRightChartLoadButton());
                                
        this._setButtonHoverEvent(this.getSaveSettingsButton());
        this._setButtonHoverEvent(this.getLoadSettingsButton());
        
        this._setLeftChartApplyButtonClickEvent();
        this._setLeftChartSaveButtonClickEvent();
        this._setLeftChartLoadButtonClickEvent();
        this._setRightChartApplyButtonClickEvent();
        this._setRightChartSaveButtonClickEvent();
        this._setRightChartLoadButtonClickEvent();
        this._setSaveSettingsButtonClickEvent();
        this._setLoadSettingsButtonClickEvent();
    }
}
