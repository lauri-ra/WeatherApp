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
        
        variables.add(new Variable("Temperature forecast", "Celsius", "TIme", true));
        variableCodes.put("Temperature forecast", "temperature");
        
        
        variableCodes.put("Wind speed","ws_10min");
        variableCodes.put("Cloud amount", "n_man");
        variableCodes.put("Cloud amount", "n_man");
        
        variableCodes.put("Wind speed forecast", "windspeedms");
        
        
        //variables.add(new Variable("TestVariable2", "TestUnit2"));
        
        for (Variable variable : variables) {
            variableMap.put(variable.getName(), variable);
        }
    }
    
    private Document QueryData(String variableCode, boolean isForecast, String coordinates, 
            String startDate, String endDate) throws MalformedURLException, IOException, JDOMException {
        
        String baseUrl = "https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0";
        String queryType;
        if (isForecast) {
            queryType = "&storedquery_id=" + "fmi::forecast::harmonie::surface::point::simple";
        }
        else {
            queryType = "&storedquery_id=" + "fmi::observations::weather::simple";
        }
        String parameters = "&parameters=" + variableCode;
        String coordinatesStr = "&bbox=" + coordinates;
        String timestep = "&timestep=" + "60";
        
        String startTime = "&starttime=" + startDate + "T00:00:00Z";
        String endTime = "&endtime=" + endDate + "T00:00:00Z";
        
        
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
        //XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        //xout.output(doc, System.out);
        
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
            for (Element child : record.getChildren()) {
                //System.out.println(child.getName());
                if ("Time".equals(child.getName())) {
                    //System.out.println(child.getValue());
                    time = child.getValue();
                } else if ("ParameterValue".equals(child.getName())) {
                    //System.out.println(child.getValue());
                    value = Double.valueOf(child.getValue());
                }
                
            }
            if (!queriedData.containsKey(time)) {
                queriedData.put(time, new ArrayList<>());
                
            }
            queriedData.get(time).add(value);
            
            //System.out.println();
            //System.out.println("Number of children: " +record.getChildren().size());
            //System.out.println();
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
            System.out.println(startDate); //2022-11-01
            System.out.println(endDate); //2022-11-06
            String variableCode = variableCodes.get(variable.getName());
            Document doc = QueryData(variableCode, variable.isForecast(), 
                    coordinates, 
                    startDate.toString(), endDate.plusDays(1).toString());
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
    
}
