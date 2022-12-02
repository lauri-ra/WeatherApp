package fi.tuni.weatherapp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

public class DigiTrafficSource implements IDataSource {

    private String name = "DigiTrafficSource";
    private ArrayList<Variable> variables;
    private HashMap<String, Variable> variableMap;

    private static final String baseURL = "https://tie.digitraffic.fi/api";

    public DigiTrafficSource() {
        variables = new ArrayList<>();
        variableMap = new HashMap<>(); 
        variables.add(new Variable("Overall condition", "Hour", "Condition", true, "none"));
        variables.add(new Variable("Task types", "Amount", "Task", false, "none"));
        variables.add(new Variable("Task averages", "Amount", "Task", false, "avg"));
        variables.add(new Variable("Precipitation", "Hour", "Condition", true, "none"));
        variables.add(new Variable("Winter slipperiness", "Hour", "Condition", true, "none"));
        variables.add(new Variable("Forecast", "Hour", "Condition", true, "none"));
        
        for(Variable variable : variables) {
            variableMap.put(variable.getName(), variable);
        }

    }

    // This function gets road maintenance tasks
    private ArrayList<DataPoint> GetTasks(boolean average, String coordinates, LocalDate startDate, LocalDate endDate) {
        HashMap<String, Double> tasks = new HashMap<>();

        // Parse given coordinates for the url
        coordinates = coordinates.replaceAll("[^0-9]", "");
        String xMin = "&xMin=" + coordinates.substring(0, 2);
        String yMin = "&yMin=" + coordinates.substring(2, 4);
        String xMax = "&xMax=" + coordinates.substring(4, 6);
        String yMax = "&yMax=" + coordinates.substring(6, 8);
        String place = xMin + yMin + xMax + yMax;

        // If there is no end date, timeline will be only the start date
        if(endDate == null) {
            endDate = startDate;
        }

        // loop through each day, api only allows fetching data on 24h intervals
        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;

        for(int x = 0; x < days; x++) {
            // Parse the url together and fetch data for the 24 hour period
            String start = "endFrom=" + startDate.toString() +"T00%3A00%3A00Z";
            String end = "&endBefore=" + startDate.toString() + "T24%3A00%3A00Z";
            String time = start + end;

            String url = baseURL + "/maintenance/v1/tracking/routes?" + time + place + "&taskId=&domain=state-roads";

            HttpResponse<String> response = GetRequest(url);
            JSONObject obj = new JSONObject(response.body());
            JSONArray features = obj.getJSONArray("features");

            // Loop through the json and save/update each individual task
            for(int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                JSONArray taskArray = properties.getJSONArray("tasks");
                String task = taskArray.getString(0);

                if(!tasks.containsKey(task)) {
                    tasks.put(task, 1.00);
                }
                else {
                    tasks.put(task, tasks.get(task) + 1.00);
                }
            }
            startDate = startDate.plusDays(1);
        }

        // Save data to an array as DataPoint types.
        ArrayList<DataPoint> data = new ArrayList<>();

        if(!average) {
            tasks.forEach((key, value) -> {
                data.add(new DataPoint(key, value));
            });
        }
        else {
            tasks.forEach((key, value) -> {
                data.add(new DataPoint(key, value / days));
            });
        }

        return data;

    }

    // Function gets all traffic messages for the given timeline.
    @Override
    public ArrayList<String> GetTrafficMessages(LocalDate startDate, LocalDate endDate) {
        ArrayList<String> data = new ArrayList<>();

        if(endDate == null) {
            endDate = startDate;
        }

        // Parse the url with the given time period & fetch data.
        long hours = ChronoUnit.DAYS.between(startDate, endDate) * 24;
        String time = String.valueOf(hours);

        String url = baseURL + "/traffic-message/v1/messages?inactiveHours=" + time + "&includeAreaGeometry=false&situationType=TRAFFIC_ANNOUNCEMENT";
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray features = obj.getJSONArray("features");

        // Loop through the json response and save fetched messages
        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i).getJSONObject("properties");
            JSONArray announcements = feature.getJSONArray("announcements");
            JSONObject message = announcements.getJSONObject(0);
            String title = message.get("title").toString();

            data.add(title);
        }

        return data;
    }

    // Helper function to get the road conditions for given coordinates. Only returns the response data.
    private JSONArray GetRoadCondition(String coordinates) {
        coordinates = coordinates.replaceAll("[^0-9]", "");
        String place = coordinates.substring(0, 2) + "/"
                + coordinates.substring(2, 4) + "/"
                + coordinates.substring(4, 6) + "/"
                + coordinates.substring(6, 8);


        String url = baseURL + "/v3/data/road-conditions/" + place;
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray weatherData = obj.getJSONArray("weatherData");
        JSONObject latestData = weatherData.getJSONObject(0);

        return latestData.getJSONArray("roadConditions");
    }

    // Function that gets specific type of road condition data
    private ArrayList<DataPoint> GetTypeCondition(String type, String coordinates) {
        JSONArray conditions = GetRoadCondition(coordinates);

        ArrayList<DataPoint> data = new ArrayList<>();

        for(int i = 1; i < conditions.length(); i++) {
            JSONObject forecast = conditions.getJSONObject(i);
            JSONObject forecastCondition = forecast.getJSONObject("forecastConditionReason");
            String hour = forecast.getString("forecastName").replace("h", "");

            if(forecast.has(type)) {
                String condition = forecast.getString(type);
                data.add(new DataPoint(condition, Double.parseDouble(hour)));
            }
            else if(forecastCondition.has(type)) {
                String condition = forecastCondition.getString(type);
                data.add(new DataPoint(condition, Double.parseDouble(hour)));
            }
        }

        return data;
    }

    // Function gets road condtion data for the given timeline
    private ArrayList<DataPoint> GetTimeCondition(String coordinates, LocalDateTime start, LocalDateTime end) {
        String hour = String.valueOf(ChronoUnit.HOURS.between(start, end));

        JSONArray conditions = GetRoadCondition(coordinates);

        ArrayList<DataPoint> data = new ArrayList<>();

        for(int i = 1; i < conditions.length(); i++) {
            JSONObject forecast = conditions.getJSONObject(i);
            String forecastTime = forecast.getString("forecastName").replace("h", "");

            if(forecastTime.equals(hour)) {
                // Get overall road condition
                if(forecast.has("overallRoadCondition")) {
                    String condition = forecast.getString("overallRoadCondition");
                    data.add(new DataPoint(condition, Double.parseDouble(hour)));
                }

                JSONObject forecastCondition = forecast.getJSONObject("forecastConditionReason");
                // Check and add precipitation
                if(forecastCondition.has("precipitationCondition")) {
                    String condition = forecastCondition.getString("precipitationCondition");
                    data.add(new DataPoint(condition, Double.parseDouble(hour)));
                }
                // Check and add road slipperiness
                if(forecastCondition.has("winterSlipperiness")) {
                    String condition = forecastCondition.getString("winterSlipperiness");
                    data.add(new DataPoint(condition, Double.parseDouble(hour)));
                }
            }
        }
        return data;
    }

    // Function for handling GET requests
    private HttpResponse<String> GetRequest(String URL) {
        URI url = URI.create(URL);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =  HttpRequest
                .newBuilder()
                .GET()
                .header("Accept-Encoding", "gzip header")
                .uri(url)
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            System.out.println("GET request error");
            return null;
        }
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
    public List<DataPoint> GetData(Variable variable, String coordinates, LocalDate startDate, LocalDate endDate) {
        ArrayList<DataPoint> data = new ArrayList<>();

        if (variable.getName() == "Task types") {
            data = GetTasks(false, coordinates, startDate, endDate);
        }

        if( variable.getName() == "Task averages") {
            data = GetTasks(true, coordinates, startDate, endDate);
        }

        return data;
    }
    
    @Override
    public Variable GetVariable(String variableName) {
        return variableMap.get(variableName);
    }

    @Override
    public List<DataPoint> GetForecastData(Variable variable, 
            String coordinates, LocalDateTime startDateTime, 
            LocalDateTime endDateTime) {

        ArrayList<DataPoint> data = new ArrayList<>();

        if(variable.getName() == "Precipitation") {
            data = GetTypeCondition("precipitationCondition", coordinates);
        }

        if(variable.getName() == "Overall condition") {
            data = GetTypeCondition("overallRoadCondition", coordinates);
        }

        if(variable.getName() == "Winter slipperiness") {
            data = GetTypeCondition("winterSlipperiness", coordinates);
        }

        if(variable.getName() == "Forecast") {
            data = GetTimeCondition(coordinates, startDateTime, endDateTime);
        }

        return data;
    }
}
