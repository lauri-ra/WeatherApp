package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IDataSource {
        
    public String GetName();
    
    public ArrayList<String> GetTrafficMessages();
    
    public List<Variable> GetVariables();
    
    public Variable GetVariable(String variableName);
    
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate);
    
    public List<DataPoint> GetForecastData(Variable variable, String coordinates, 
            LocalDateTime startDateTime, LocalDateTime endDateTime);
}
