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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 *
 * @author jussi
 */
public class FMIDataSource implements IDataSource {
    
    private String name = "FMI";
    private HashMap<String, Variable> variableMap;
    private HashMap<String,String> variableCodes;
    private ArrayList<Variable> variables;

    public FMIDataSource() {
        // Set up available variables
        variables = new ArrayList<>();
        variableCodes = new HashMap<>();
        variableMap = new HashMap<>();
        PopulateVariables();
    }
    
    private void PopulateVariables() {
        variables.add(new Variable("Temperature", "Celsius", "Date", false));
        variableCodes.put("Temperature","t2m");
        
        variables.add(new Variable("Temperature forecast", "Celsius", "Time", true));
        variableCodes.put("Temperature forecast", "temperature");
        
        variables.add(new Variable("Wind speed", "m/s", "Date", false));
        variableCodes.put("Wind speed","ws_10min");
        
        variables.add(new Variable("Cloud amount", "1/8", "Date", false));
        variableCodes.put("Cloud amount", "n_man");
        
        variables.add(new Variable("Wind speed forecast","m/s","Time",true));
        variableCodes.put("Wind speed forecast", "windspeedms");
        
        for (Variable variable : variables) {
            variableMap.put(variable.getName(), variable);
        }
    }
    
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
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        xout.output(doc, System.out);
        
        //System.out.println("Stuff2");
        
        return doc;
    }
    
    
    private ArrayList ProcessXml(Document doc) throws IOException {
        ArrayList<DataPoint> data = new ArrayList<>();
        
        HashMap<String, ArrayList<Double>> queriedData = new HashMap<>();
        
        Element rootNode = doc.getRootElement();
        List<Element> records = rootNode.getChildren();
        
        //XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        for (Element record : records) {
            record = record.getChildren().get(0);
            //xout.output(record, System.out);
            String time = "";
            Double value = 0.0;
            String valueStr = "";
            for (Element child : record.getChildren()) {
                //System.out.println(child.getName());
                if ("Time".equals(child.getName())) {
                    //System.out.println(child.getValue());
                    time = child.getValue();
                } else if ("ParameterValue".equals(child.getName())) {
                    //System.out.println(child.getValue());
                    valueStr = child.getValue();
                    //value = Double.valueOf(child.getValue());
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
        /*
        for (DataPoint point : data) {
            System.out.println(point.getX());
            System.out.println(point.getY());
            System.out.println();
        }
        */
        
        
        return data;
    }
    
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
    
    
    private ArrayList<DataPoint> CombineDataSets(
            ArrayList<ArrayList<DataPoint>>  dataList) {
        
        ArrayList<DataPoint> combinedData = new ArrayList<>();
        HashMap<String, ArrayList<Double>> baseDataMap = new HashMap<>();
        
        for (ArrayList<DataPoint> data : dataList) {
            System.out.println("stuff");
            for(DataPoint dataPoint: data) {
                String x = dataPoint.getX();
                double y = dataPoint.getY();
                System.out.println(x + ", " + y);
                
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
    
    @Override
    public String GetName() {
        return name;
    }
    
    @Override
    public ArrayList<String> GetTrafficMessages() {
        return new ArrayList<>();
    }

    @Override
    public List<Variable> GetVariables() {
        return variables;
    }
    
    @Override 
    public Variable GetVariable(String variableName) {
        return variableMap.get(variableName);
    }

    @Override
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        try {
            //System.out.println(startDate); //2022-11-01
            //System.out.println(endDate); //2022-11-06
            String variableCode = variableCodes.get(variable.getName());
            Document doc = QueryData(variableCode, variable.isForecast(), 
                    coordinates, 
                    startDate.toString()+"T00:00:00Z", 
                    endDate.plusDays(1).toString()+"T00:00:00Z");
            ArrayList<DataPoint> data = ProcessXml(doc);
            Collections.sort(data, Comparator.comparing(DataPoint::getX));
            return data;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    } 

    @Override
    public List<DataPoint> GetForecastData(Variable variable, 
            String coordinates, LocalDateTime startDateTime, 
            LocalDateTime endDateTime) {
        //System.out.println(startDateTime); //2022-11-01
        //System.out.println(endDateTime); //2022-11-06
        String startTimeStr = startDateTime.toString().substring(0,19) + "Z";
        String endTimeStr = endDateTime.toString().substring(0,19) + "Z";
        //System.out.println(startTimeStr);
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
