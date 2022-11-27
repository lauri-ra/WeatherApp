package fi.tuni.weatherapp;

public class Variable {
    private String name;
    private String unit;
    private String xType;
    private boolean isForecast;

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
    
    public String getXType(){
        return xType;
    }
    
    public boolean isForecast() {
        return isForecast;
    }

    public Variable(String name, String unit, String xType, boolean isForecast) {
        this.name = name;
        this.unit = unit;
        this.xType = xType;
        this.isForecast = isForecast;
    }
}
