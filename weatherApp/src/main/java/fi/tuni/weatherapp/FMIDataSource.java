package fi.tuni.weatherapp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 *
 * @author jussi
 */
public class FMIDataSource implements IDataSource {
    
    private String name = "FMI";
    private HashMap<String, Variable> variableMap;
    private HashMap<String,String> variableCodes;
    private ArrayList<Variable> variables;

    /**
     * Constructor initializes dataSources containers.
     */
    public FMIDataSource() {
        // Set up available variables
        variables = new ArrayList<>();
        variableCodes = new HashMap<>();
        variableMap = new HashMap<>();
        PopulateVariables();
    }
    
    /**
     * Adds all the predetermined variables to this dataSource.
     */
    private void PopulateVariables() {
        variables.add(new Variable("Temperature", "Celsius", "Date", false, "none"));
        variableCodes.put("Temperature","t2m");
        
        variables.add(new Variable("Temperature daily average", "Celsius", "Date", false, "avg"));
        variableCodes.put("Temperature daily average","t2m");
        
        variables.add(new Variable("Temperature daily min", "Celsius", "Date", false, "min"));
        variableCodes.put("Temperature daily min","t2m");
        
        variables.add(new Variable("Temperature daily max", "Celsius", "Date", false, "max"));
        variableCodes.put("Temperature daily max","t2m");
        
        variables.add(new Variable("Wind speed", "m/s", "Date", false, "none"));
        variableCodes.put("Wind speed","ws_10min");
        
        variables.add(new Variable("Wind speed daily avg", "m/s", "Date", false, "avg"));
        variableCodes.put("Wind speed daily avg","ws_10min");
        
        variables.add(new Variable("Wind speed daily min", "m/s", "Date", false, "min"));
        variableCodes.put("Wind speed daily min","ws_10min");
                
        variables.add(new Variable("Wind speed daily max", "m/s", "Date", false, "max"));
        variableCodes.put("Wind speed daily max","ws_10min");
        
        variables.add(new Variable("Cloud amount", "1/8", "Date", false,"none"));
        variableCodes.put("Cloud amount", "n_man");
        
        variables.add(new Variable("Cloud amount daily avg", "1/8", "Date", false,"avg"));
        variableCodes.put("Cloud amount daily avg", "n_man");
        
        variables.add(new Variable("Cloud amount daily min", "1/8", "Date", false,"min"));
        variableCodes.put("Cloud amount daily min", "n_man");
        
        variables.add(new Variable("Cloud amount daily max", "1/8", "Date", false,"max"));
        variableCodes.put("Cloud amount daily max", "n_man");
        
                
        variables.add(new Variable("Temperature forecast", "Celsius", "Time", true, "none"));
        variableCodes.put("Temperature forecast", "temperature");
        
        variables.add(new Variable("Wind speed forecast","m/s","Time",true,"none"));
        variableCodes.put("Wind speed forecast", "windspeedms");
        
        for (Variable variable : variables) {
            variableMap.put(variable.getName(), variable);
        }
    }
    
    
    /**
     * Queries data from FMI based on the parameters.
     * @param variableCode string representing the variable on the FMI query
     * @param isForecast is the variable forecast or not
     * @param coordinates coordinates over which to query, as four edge values
     * @param startDate start date for the query as a string
     * @param endDate end date for the query as a string
     * @return The queried data as an xml document
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException 
     */
    private Document QueryData(String variableCode, boolean isForecast, String coordinates, 
            String startDate, String endDate) throws MalformedURLException, IOException, JDOMException {
        
        String baseUrl = "https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0";
        String queryType;
        String coordinatesStr;
        if (isForecast) {
            queryType = "&storedquery_id=" + "fmi::forecast::harmonie::surface::point::simple";
            coordinatesStr = "&latlon=" +  coordinates;
        }
        else {
            queryType = "&storedquery_id=" + "fmi::observations::weather::simple";
            coordinatesStr = "&bbox=" + coordinates;
        }
        String parameters = "&parameters=" + variableCode;
        String timestep = "&timestep=" + "60";
        
        String startTime = "&starttime=" + startDate;
        String endTime = "&endtime=" + endDate;
        
        
        String url =  baseUrl + queryType + parameters + coordinatesStr + timestep + startTime + endTime;
        

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
            
        SAXBuilder builder = new SAXBuilder();
        InputStream stream = new ByteArrayInputStream(
                response.toString().getBytes("UTF-8"));
            
            
        Document doc = builder.build(stream);
        
        
        return doc;
    }
    
    
    /**
     * Parses the data from an xml document
     * @param doc xml document to be parsed
     * @return LIst of parsed dataPOints
     * @throws IOException 
     */
    private ArrayList ProcessXml(Document doc) throws IOException {
        ArrayList<DataPoint> data = new ArrayList<>();
        
        HashMap<String, ArrayList<Double>> queriedData = new HashMap<>();
        
        Element rootNode = doc.getRootElement();
        List<Element> records = rootNode.getChildren();
        
        for (Element record : records) {
            record = record.getChildren().get(0);
            String time = "";
            Double value = 0.0;
            String valueStr = "";
            for (Element child : record.getChildren()) {
                if ("Time".equals(child.getName())) {
                    time = child.getValue();
                } else if ("ParameterValue".equals(child.getName())) {
                    valueStr = child.getValue();
                }
                
            }
            if (!valueStr.equals("NaN")) {
                if (!queriedData.containsKey(time)) {
                    queriedData.put(time, new ArrayList<>());

                }
                value = Double.valueOf(valueStr);
                queriedData.get(time).add(value);
            }

        }
        
        for (Map.Entry<String, ArrayList<Double>> pair : queriedData.entrySet()) {
            Double sum = 0.0;
            for (Double value : pair.getValue()) {
                sum += value;
            }
            Double avgValue = sum / pair.getValue().size();
            
            String timeString = pair.getKey().substring(0, pair.getKey().length() - 1);
            
            data.add(new DataPoint(timeString, avgValue));
        }
        
        return data;
    }
    
    /**
     * Combines hourly data to wanted type of daily data
     * @param rawData base data to be reduced
     * @param type which type of reduction to do (avg/min/max)
     * @return List of the reduced data
     */
    private ArrayList ReduceData(ArrayList<DataPoint> rawData, String type) {
        ArrayList<DataPoint> data = new ArrayList<>();
        HashMap<String, ArrayList<Double>> groupedData = new HashMap<>();
        
        
        for(DataPoint dataPoint: rawData) {
            String x = dataPoint.getX();
            x = x.substring(0,10);
            double y = dataPoint.getY();
                
            if (!groupedData.containsKey(x)) {
                groupedData.put(x, new ArrayList<>());
            }
            groupedData.get(x).add(y);
        }
        
        if (type.equals("avg")) {
            for (Map.Entry<String, ArrayList<Double>> pair : groupedData.entrySet()) {
                Double sum = 0.0;
                for (Double value : pair.getValue()) {
                    sum += value;
                }
                Double avgValue = sum / pair.getValue().size();

                data.add(new DataPoint(pair.getKey(), avgValue));
            }
        }
        else if (type.equals("min")) {
            for (Map.Entry<String, ArrayList<Double>> pair : groupedData.entrySet()) {
                Double min = pair.getValue().get(0);
                for (Double value : pair.getValue()) {
                    if (value < min) {
                        min = value;
                    }
                }

                data.add(new DataPoint(pair.getKey(), min));
            }
        }
        
        else if (type.equals("max")) {
            for (Map.Entry<String, ArrayList<Double>> pair : groupedData.entrySet()) {
                Double max = pair.getValue().get(0);
                for (Double value : pair.getValue()) {
                    if (value > max) {
                        max = value;
                    }
                }

                data.add(new DataPoint(pair.getKey(), max));
            }
        }
        
        return data;
        
    }
    
    /**
     * Gets available data for a given coordinate and parameters
     * @param variableCode string representing the variable on the FMI query
     * @param isForecast is the variable forecast or not
     * @param coordinates coordinates of a point
     * @param startTimeStr start date for the query as a string
     * @param endTimeStr end date for the query as a string
     * @return List of data at the queried point
     */
    private ArrayList<DataPoint> GetCoordinatePointData(String variableCode, boolean isForecast, 
            String coordinates, String startTimeStr, 
            String endTimeStr) {
        Document doc;
        ArrayList<DataPoint> data = new ArrayList<>();
        try { 
            doc = QueryData(variableCode, isForecast, 
                    coordinates,
                    startTimeStr, endTimeStr);
            
            data = ProcessXml(doc);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        return data;
    }
    
    /**
     * Combines multiple data sets into single data set
     * @param dataList List dataLists to be combined
     * @return List of combined data
     */
    private ArrayList<DataPoint> CombineDataSets(
            ArrayList<ArrayList<DataPoint>>  dataList) {
        
        ArrayList<DataPoint> combinedData = new ArrayList<>();
        HashMap<String, ArrayList<Double>> baseDataMap = new HashMap<>();
        
        for (ArrayList<DataPoint> data : dataList) {
            for(DataPoint dataPoint: data) {
                String x = dataPoint.getX();
                double y = dataPoint.getY();
                
                if (!baseDataMap.containsKey(x)) {
                    baseDataMap.put(x, new ArrayList<>());
                }
                baseDataMap.get(x).add(y);
            }
        }
        
        for (Map.Entry<String, ArrayList<Double>> pair : baseDataMap.entrySet()) {
            Double sum = 0.0;
            for (Double value : pair.getValue()) {
                sum += value;
            }
            Double avgValue = sum / pair.getValue().size();
            
            String timeString = pair.getKey().substring(0, pair.getKey().length() - 1);
            
            combinedData.add(new DataPoint(timeString, avgValue));
        }
        
        
        
        return combinedData; 
    }
    
    /**
     * Gets name of the dataSource.
     * @return The name of the dataSource
     */
    @Override
    public String GetName() {
        return name;
    }
    
    /**
     * not implemented for this dataSource.
     * @param startDate start date for the query
     * @param endDate end date for the query
     * @return Nothing
     */
    @Override
    public ArrayList<String> GetTrafficMessages(LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>();
    }

    /**
     * Get variables this dataSource has.
     * @return List of variables
     */
    @Override
    public List<Variable> GetVariables() {
        return variables;
    }
    
    /**
     * Get a single variable based on name.
     * @param variableName The name of the wanted variable
     * @return variable with the given name
     */
    @Override 
    public Variable GetVariable(String variableName) {
        return variableMap.get(variableName);
    }

    /**
     * Get measured data of a variable between two dates in given area.
     * @param variable The variable to be queried
     * @param coordinates coordinates of the area to be queried
     * @param startDate start date for the query
     * @param endDate end date for the query
     * @return List of dataPoints representing the queried data
     */
    @Override
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        try {
            String variableCode = variableCodes.get(variable.getName());
            
            int days = (int) DAYS.between(startDate,endDate);
            
            ArrayList<DataPoint> data;
            
            if(days > 6) {
                ArrayList<ArrayList<DataPoint>> dataList = new ArrayList<>();
                
                LocalDate tmpStart = startDate;
                LocalDate tmpEnd = startDate.plusDays(6);
                
                while (tmpStart.isBefore(endDate)) {
                    Document doc = QueryData(variableCode, variable.isForecast(), 
                    coordinates, 
                    tmpStart.toString()+"T00:00:00Z", 
                    tmpEnd.plusDays(1).toString()+"T00:00:00Z");

                    dataList.add(ProcessXml(doc));
                    
                    tmpStart = tmpEnd;
                    if(tmpStart.plusDays(6).isAfter(endDate)) {
                        tmpEnd = endDate;
                    }
                    else {
                        tmpEnd = tmpStart.plusDays(6);
                    }
                }

                data = CombineDataSets(dataList);
                
            }
            else {
               Document doc = QueryData(variableCode, variable.isForecast(), 
                    coordinates, 
                    startDate.toString()+"T00:00:00Z", 
                    endDate.plusDays(1).toString()+"T00:00:00Z");
                data = ProcessXml(doc); 
            }
            
            if (variable.isAvg()) {
                data = ReduceData(data, "avg");
            }
            else if (variable.isMin()) {
                data = ReduceData(data, "min");
            }
            else if (variable.isMax()) {
                data = ReduceData(data, "max");
            }
            Collections.sort(data, Comparator.comparing(DataPoint::getX));
            return data;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    } 

    /**
     * Get forecast data of a variable between two dates in given area.
     * @param variable The variable to be queried
     * @param coordinates coordinates of the area to be queried
     * @param startDateTime start dateTime for the query
     * @param endDateTime end dateTime for the query
     * @return List of dataPoints representing the queried data
     */
    @Override
    public List<DataPoint> GetForecastData(Variable variable, 
            String coordinates, LocalDateTime startDateTime, 
            LocalDateTime endDateTime) {
        String startTimeStr = startDateTime.toString().substring(0,19) + "Z";
        String endTimeStr = endDateTime.toString().substring(0,19) + "Z";
        String variableCode = variableCodes.get(variable.getName());
        String lat1 = coordinates.substring(3,5);
        String lat2 = coordinates.substring(9,11);
        String lon1 = coordinates.substring(0,2);
        String lon2 = coordinates.substring(6,8);

        String coordinates1 = lat1 + "," + lon1;
        String coordinates2 = lat1 + "," + lon2;
        String coordinates3 = lat2 + "," + lon1;
        String coordinates4 = lat2 + "," + lon2;
        
        ArrayList<ArrayList<DataPoint>> dataList = new ArrayList<>();
        dataList.add(GetCoordinatePointData(variableCode,
                variable.isForecast(), coordinates1,
                startTimeStr, endTimeStr));
        dataList.add(GetCoordinatePointData(variableCode,
                variable.isForecast(), coordinates1,
                startTimeStr, endTimeStr));
        dataList.add(GetCoordinatePointData(variableCode,
                variable.isForecast(), coordinates1,
                startTimeStr, endTimeStr));
        dataList.add(GetCoordinatePointData(variableCode,
                variable.isForecast(), coordinates1,
                startTimeStr, endTimeStr));

        ArrayList<DataPoint> data = CombineDataSets(dataList);
        Collections.sort(data, Comparator.comparing(DataPoint::getX));
        return data;
        
    }
    
    
}
