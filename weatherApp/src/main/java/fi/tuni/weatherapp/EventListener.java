package fi.tuni.weatherapp;

public interface EventListener {
    void handleCoordinates();
    void handleStartDate();
    void handleEndDate();
    void handleForecast();
    void handleApply();
    void handleReset();
    void handleLeftChart();
    void handleRightChart();
    void handleSaveData();
    void handleLoadData();
    void handleSaveSettings();
    void handleLoadSettings();
}
