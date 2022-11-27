package fi.tuni.weatherapp;

public interface EventListener {
    void handleEdited();
    void handleTopApply();
    void handleReset();
    void handleLeftChartApply();
    void handleLeftChartSave();
    void handleLeftChartLoad();
    void handleRightChartApply();
    void handleRightChartSave();
    void handleRightChartLoad();    
    void handleSaveSettings();
    void handleLoadSettings();
}
