package fi.tuni.weatherapp;

import java.time.LocalDate;

public class DataPoint {
    private final LocalDate date;
    private final double value;

    public LocalDate getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public DataPoint(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }
}
