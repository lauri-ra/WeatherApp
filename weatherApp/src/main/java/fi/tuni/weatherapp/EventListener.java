package fi.tuni.weatherapp;

import java.time.LocalDate;

public interface EventListener {
    void handleCoordinates(Object... args);
    void handleStartDate(Object... args);
    void handleEndDate(Object... args);
    void handleForecast(Object... args);
    void handleApply(String coordinates, LocalDate startDate, 
            LocalDate endDate, Object forecast);
    void handleClear(Object... args);
    void handleLeftChart(Object... args);
    void handleRightChart(Object... args);
    void handleSaveData(Object... args);
    void handleLoadData(Object... args);
    void handleSaveSettings(Object... args);
    void handleLoadSettings(Object... args);
}
