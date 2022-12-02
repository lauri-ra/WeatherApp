package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelMain {
    
    private HashMap<String,IDataSource> dataSources = new HashMap<>();
    
    
    /**
     * Adds new dataSource to the model.
     * @param dataSource The IDataSource to be added
     */
    public void AddDataSource(IDataSource dataSource) {
        dataSources.put(dataSource.GetName(), dataSource);
    }
    
    /**
     * Function for getting messages between two dates form a given dataSource.
     * @param dataSourceName Name of the dataSource to be used
     * @param startDate start date for query
     * @param endDate end date for query
     * @return List of message strings
     */
    public ArrayList<String> GetMessages(String dataSourceName, LocalDate startDate, LocalDate endDate) {
        return dataSources.get(dataSourceName).GetTrafficMessages(startDate, endDate);
    }
    
    /**
     * Get the names of al dataSources in the model.
     * @return List of the names of dataSources as Strings
     */
    public List<String> GetDataSourceNames(){
        ArrayList<String> dataSourceNames = new ArrayList<>();
        
        for (String dataSourceName : dataSources.keySet()) {
            dataSourceNames.add(dataSourceName);
        }
        
        return dataSourceNames;
    }
    
    /**
     * Get list of variables available from a given dataSource.
     * @param dataSourceName The Name of the dataSource to be queried
     * @return List of variables
     */
    public List<Variable> GetVariables(String dataSourceName) {
        
        List<Variable> variables = dataSources.get(dataSourceName).GetVariables();
        return variables;
    }
    
    /**
     * Gets a particular variable from a particular dataSource.
     * @param dataSourceName name of the relevant dataSOurce
     * @param variableName name of the wanted variable
     * @return variable with the wanted name
     */
    public Variable GetVariable(String dataSourceName, String variableName) {
        return dataSources.get(dataSourceName).GetVariable(variableName);
    }
    
    /**
     * Returns measured data of a variable between two dates in given area.
     * @param dataSourceName Name of the dataSource to be queried
     * @param variable The variable of which data is queried
     * @param coordinates the coordinates over to be covered by the data
     * @param startDate start date of the query
     * @param endDate end date of the query
     * @return List of dataPoints representing the queried data
     */
    public List<DataPoint> GetPastData(String dataSourceName, 
            Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        
        return dataSources.get(dataSourceName).GetData(variable, coordinates, startDate, endDate);
    }
    
    /**
     * Returns forecast data of variable between two dates in given area.
     * @param dataSourceName Name of the dataSource to be queried
     * @param variable The variable of which data is queried
     * @param coordinates the coordinates over to be covered by the data
     * @param startDate start date of the query
     * @param endDate end date of the query
     * @return List of dataPoints representing the queried data
     */
    public List<DataPoint> GetForecastData(String dataSourceName, 
            Variable variable, String coordinates, 
            LocalDateTime startDate, LocalDateTime endDate) {
        
        return dataSources.get(dataSourceName).GetForecastData(variable, 
                coordinates, startDate, endDate);
    }
    
}
