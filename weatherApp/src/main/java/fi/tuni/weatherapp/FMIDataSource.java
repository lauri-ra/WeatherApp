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
import java.util.List;
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
    
    private void QueryData() {
        System.out.println("Stuff1");
        String baseUrl = "https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0";
        String queryType = "&storedquery_id=" + "fmi::observations::weather::simple";
        String parameters = "&parameters=" + "t2m";
        String coordinates = "&bbox=" + "23,61,24,62";
        String timestep = "&timestep=" + "30";
        
        String startTime = "&starttime=" + "2022-10-19T09:00:00Z";
        String endTime = "&endtime=" + "2022-10-19T12:00:00Z";
        
        
        String url =  baseUrl + queryType + parameters + coordinates + timestep + startTime + endTime;
        

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            SAXBuilder builder = new SAXBuilder();
            InputStream stream = new ByteArrayInputStream(
                    response.toString().getBytes("UTF-8"));
            
            
            Document doc = builder.build(stream);
            XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
            xout.output(doc, System.out);
            System.out.println(doc.toString());
            Element rootNode = doc.getRootElement();
            System.out.println(rootNode);
            
            
        } catch (MalformedURLException ex) {
        } catch (IOException | JDOMException ex) {
        }
        
        System.out.println("Stuff2");
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
        QueryData();
        return new ArrayList<>();
    }
    
    
}
