package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDataSource implements IDataSource {
    
    private String name = "TestDataSource";
    private ArrayList<Variable> variables;

    public TestDataSource() {
        // Set up available variables
        variables = new ArrayList<>();
        variables.add(new Variable("TestVariable1", "TestUnit1"));
        variables.add(new Variable("TestVariable2", "TestUnit2"));
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
        
        //Dummy data for interface testing
        ArrayList<DataPoint> data = new ArrayList<>();
        data.add(new DataPoint("2016-08-16", 15));
        
        return data;
    }
    
}
