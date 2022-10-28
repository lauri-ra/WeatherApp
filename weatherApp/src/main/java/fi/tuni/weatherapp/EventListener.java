package fi.tuni.weatherapp;

public interface EventListener {
    void handleCoordinates(Object... args);
    void handleStartDate(Object... args);
    void handleEndDate(Object... args);
    void handleForecast(Object... args);
    void handleApply(Object... args);
    void handleClear(Object... args);
    void handleLeftChart(Object... args);
    void handleRightChart(Object... args);
    void handleSaveData(Object... args);
    void handleLoadData(Object... args);
    void handleSaveSettings(Object... args);
    void handleLoadSettings(Object... args);
}
