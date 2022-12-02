package fi.tuni.weatherapp;

public class Variable {
    private String name;
    private String unit;
    private String xType;
    private boolean isForecast;
    private boolean isAvg;
    private boolean isMin;
    private boolean isMax;

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
    
    public boolean isAvg() {
        return isAvg;
    }
    
    public boolean isMin() {
        return isMin;
    }
    
    public boolean isMax() {
        return isMax;
    }
    
    public boolean isMinMax() {
        return isMin || isMax;
    }

    public Variable(String name, String unit, String xType, boolean isForecast, 
            String type) {
        
        this.name = name;
        this.unit = unit;
        this.xType = xType;
        this.isForecast = isForecast;
        
        this.isAvg = type.equals("avg");
        this.isMin = type.equals("min");
        this.isMax = type.equals("max");
    }
    
}
