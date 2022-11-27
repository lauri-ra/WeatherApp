package fi.tuni.weatherapp;

public class Variable {
    private String name;
    private String unit;
    private String xType;

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
    
    public String getXType(){
        return xType;
    }

    public Variable(String name, String unit, String xType) {
        this.name = name;
        this.unit = unit;
        this.xType = xType;
    }
}
