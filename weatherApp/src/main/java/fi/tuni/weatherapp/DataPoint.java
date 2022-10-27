package fi.tuni.weatherapp;

import java.time.LocalDateTime;

public class DataPoint {
    private final LocalDateTime date;
    private final double value;

    public LocalDateTime getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public DataPoint(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }
}
