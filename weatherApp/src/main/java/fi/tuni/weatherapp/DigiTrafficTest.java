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
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DigiTrafficTest implements IDataSource {

    private String name = "DigiTrafficTest";
    private ArrayList<Variable> variables;
    private HashMap<String, Variable> variableMap;

    private static final String baseURL = "https://tie.digitraffic.fi/api";

    public DigiTrafficTest() {
        variables = new ArrayList<>();
        variableMap = new HashMap<>(); 
        variables.add(new Variable("Overall condition", "Hour", "Condition", true));
        variables.add(new Variable("Task types", "Amount", "Task", false));
        variables.add(new Variable("Task averages", "Amount", "Task", false));
        variables.add(new Variable("Precipitation", "Hour", "Condition", true));
        variables.add(new Variable("Winter slipperiness", "Hour", "Condition", true));
        variables.add(new Variable("Forecast", "Hour", "Condition", true));
        
        for(Variable variable : variables) {
            variableMap.put(variable.getName(), variable);
        }

    }

    private ArrayList<DataPoint> GetTasks(boolean average, String coordinates, LocalDate startDate, LocalDate endDate) {
        HashMap<String, Double> tasks = new HashMap<>();

        coordinates = coordinates.replaceAll("[^0-9]", "");
        String xMin = "&xMin=" + coordinates.substring(0, 2);
        String yMin = "&yMin=" + coordinates.substring(2, 4);
        String xMax = "&xMax=" + coordinates.substring(4, 6);
        String yMax = "&yMax=" + coordinates.substring(6, 8);
        String place = xMin + yMin + xMax + yMax;

        if(endDate == null) {
            endDate = startDate;
        }

        // loop through each day, api only allows fetching data on 24h intervals
        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;

        for(int x = 0; x < days; x++) {
            String start = "endFrom=" + startDate.toString() +"T00%3A00%3A00Z";
            String end = "&endBefore=" + startDate.toString() + "T24%3A00%3A00Z";
            String time = start + end;

            String url = baseURL + "/maintenance/v1/tracking/routes?" + time + place + "&taskId=&domain=state-roads";
            System.out.println(url);

            HttpResponse<String> response = GetRequest(url);
            JSONObject obj = new JSONObject(response.body());
            JSONArray features = obj.getJSONArray("features");

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
    
    @Override
    public ArrayList<String> GetTrafficMessages() {
        ArrayList<String> data = new ArrayList<>();

        String url = baseURL + "/traffic-message/v1/messages?inactiveHours=0&includeAreaGeometry=false&situationType=TRAFFIC_ANNOUNCEMENT";
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray features = obj.getJSONArray("features");

        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i).getJSONObject("properties");
            JSONArray announcements = feature.getJSONArray("announcements");
            JSONObject message = announcements.getJSONObject(0);
            String title = message.get("title").toString();

            data.add(title);
        }

        return data;
    }

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
