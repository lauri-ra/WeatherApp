package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelMain {
    
    private HashMap<String,IDataSource> dataSources = new HashMap<>();
    
    public void AddDataSource(IDataSource dataSource) {
        dataSources.put(dataSource.GetName(), dataSource);
    }
    
    public ArrayList<String> GetMessages(String dataSourceName) {
        return dataSources.get(dataSourceName).GetTrafficMessages();
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
    
    public List<DataPoint> GetVariableData(String dataSourceName, 
            Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        
        return dataSources.get(dataSourceName).GetData(variable, coordinates, startDate, endDate);
    }
}
