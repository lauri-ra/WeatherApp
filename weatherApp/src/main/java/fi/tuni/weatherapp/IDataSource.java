package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IDataSource {
        
    /**
     * Gets name of the dataSource
     * @return The name of the dataSource
     */
    public String GetName();
    
    /**
     * Returns traffic messages over wanted period of time
     * @param startDate start date for the query
     * @param endDate end date for the query
     * @return List of traffic message Strings
     */
    public ArrayList<String> GetTrafficMessages(LocalDate startDate, LocalDate endDate);
    
    /**
     * Get variables this dataSource has
     * @return List of variables
     */
    public List<Variable> GetVariables();
    
    /**
     * Get a single variable based on name
     * @param variableName The name of the wanted variable
     * @return variable with the given name
     */
    public Variable GetVariable(String variableName);
    
    /**
     * Get measured data of a variable between two dates in given area
     * @param variable The variable to be queried
     * @param coordinates coordinates of the area to be queried
     * @param startDate start date for the query
     * @param endDate end date for the query
     * @return List of dataPoints representing the queried data
     */
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate);
    
    /**
     * Get forecast data of a variable between two dates in given area
     * @param variable The variable to be queried
     * @param coordinates coordinates of the area to be queried
     * @param startDateTime start dateTime for the query
     * @param endDateTime end dateTime for the query
     * @return List of dataPoints representing the queried data
     */
    public List<DataPoint> GetForecastData(Variable variable, String coordinates, 
            LocalDateTime startDateTime, LocalDateTime endDateTime);
}
