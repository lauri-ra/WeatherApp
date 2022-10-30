package fi.tuni.weatherapp;

public class DataPoint {
    private final String x;
    private final double y;

    public String getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DataPoint(String date, double value) {
        this.x = date;
        this.y = value;
    }
}
