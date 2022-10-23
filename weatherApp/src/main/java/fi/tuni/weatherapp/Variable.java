package fi.tuni.weatherapp;

public class Variable {
    private String name;
    private String unit;

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public Variable(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }
}
