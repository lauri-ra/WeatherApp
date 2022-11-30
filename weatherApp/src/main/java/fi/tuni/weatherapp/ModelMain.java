package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelMain {
    
    private HashMap<String,IDataSource> dataSources = new HashMap<>();
    
    public void AddDataSource(IDataSource dataSource) {
        dataSources.put(dataSource.GetName(), dataSource);
    }
    
    public ArrayList<String> GetMessages(String dataSourceName, LocalDate startDate, LocalDate endDate) {
        return dataSources.get(dataSourceName).GetTrafficMessages(startDate, endDate);
    }
    
    public List<String> GetDataSourceNames(){
        ArrayList<String> dataSourceNames = new ArrayList<>();
        
        for (String dataSourceName : dataSources.keySet()) {
            dataSourceNames.add(dataSourceName);
        }
        
        return dataSourceNames;
    }
    
    public List<Variable> GetVariables(String dataSourceName) {
        
        List<Variable> variables = dataSources.get(dataSourceName).GetVariables();
        return variables;
    }
    
    public Variable GetVariable(String dataSourceName, String variableName) {
        return dataSources.get(dataSourceName).GetVariable(variableName);
    }
    
    public List<DataPoint> GetPastData(String dataSourceName, 
            Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        
        return dataSources.get(dataSourceName).GetData(variable, coordinates, startDate, endDate);
    }
    
    public List<DataPoint> GetForecastData(String dataSourceName, 
            Variable variable, String coordinates, 
            LocalDateTime startDate, LocalDateTime endDate) {
        
        return dataSources.get(dataSourceName).GetForecastData(variable, 
                coordinates, startDate, endDate);
    }
    
}
