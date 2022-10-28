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
    void handleLeftOption();
    void handleLeftChartType();
    void handleRightOption();
    void handleRightChartType();
    void handleSaveData();
    void handleLoadData();
    void handleSaveSettings();
    void handleLoadSettings();
}
