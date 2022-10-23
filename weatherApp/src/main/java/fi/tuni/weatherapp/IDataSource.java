package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.util.List;

public interface IDataSource {
        
    public String GetName();
    
    public List<Variable> GetVariables();
    
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate);
}
