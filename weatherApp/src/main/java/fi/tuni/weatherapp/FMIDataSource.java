/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.HashMap;
import java.util.HashSet;
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
    private ArrayList<Variable> variables;

    public FMIDataSource() {
        // Set up available variables
        variables = new ArrayList<>();
        PopulateVariables();
    }
    
    private void PopulateVariables() {
        variables.add(new Variable("TestVariable1", "TestUnit1"));
        variables.add(new Variable("TestVariable2", "TestUnit2"));
    }
    
    private Document QueryData() throws MalformedURLException, IOException, JDOMException {
        System.out.println("Stuff1");
        String baseUrl = "https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0";
        String queryType = "&storedquery_id=" + "fmi::observations::weather::simple";
        String parameters = "&parameters=" + "t2m";
        String coordinates = "&bbox=" + "23,61,24,62";
        String timestep = "&timestep=" + "30";
        
        String startTime = "&starttime=" + "2022-10-19T09:00:00Z";
        String endTime = "&endtime=" + "2022-10-19T12:00:00Z";
        
        
        String url =  baseUrl + queryType + parameters + coordinates + timestep + startTime + endTime;
        

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
            
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
        
        System.out.println("Stuff2");
        
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
            System.out.println(pair.getKey());
            for (Double value : pair.getValue()) {
                System.out.println(value);
            }
            System.out.println();
        }
        
        return data;
    }
    
    @Override
    public String GetName() {
        return name;
    }

    @Override
    public List<Variable> GetVariables() {
        return variables;
    }

    @Override
    public List<DataPoint> GetData(Variable variable, String coordinates, 
            LocalDate startDate, LocalDate endDate) {
        try {
            Document doc = QueryData();
             ArrayList<DataPoint> data = ProcessXml(doc);
             return data;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    
}
