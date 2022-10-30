package fi.tuni.weatherapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.URI;
import java.net.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DigiTrafficTest implements IDataSource {

    private String name = "DigiTrafficTest";
    private ArrayList<Variable> variables;

    private static final String baseURL = "https://tie.digitraffic.fi/api";

    public DigiTrafficTest() {
        variables = new ArrayList<>();
        variables.add(new Variable("DigiTraffic1", "Unit1"));
        variables.add(new Variable("DigiTraffic2", "Unit 2"));
    }

    private HashMap<String, Integer> GetTasks() {
        HashMap<String, Integer> tasks = new HashMap<>();

        String url = baseURL + "/maintenance/v1/tracking/routes?endFrom=2022-01-19T09%3A00%3A00Z&endBefore=2022-01-19T14%3A00%3A00Z&xMin=21&yMin=61&xMax=22&yMax=62&taskId=&domain=state-roads";
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray features = obj.getJSONArray("features");

        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            JSONArray taskArray = properties.getJSONArray("tasks");
            String task = taskArray.getString(0);

            if(!tasks.containsKey(task)) {
                tasks.put(task, 1);
            }
            else {
                tasks.put(task, tasks.get(task) + 1);
            }
        }

        return tasks;
    }

    private int GetTrafficMessages() {
        String url = baseURL + "/traffic-message/v1/messages?inactiveHours=0&includeAreaGeometry=false&situationType=TRAFFIC_ANNOUNCEMENT";
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray features = obj.getJSONArray("features");

        return features.length();
    }

    private HashMap<String, ArrayList<String>> GetRoadCondition() {
        String url = baseURL + "/v3/data/road-conditions/21/61/22/62";
        HttpResponse<String> response = GetRequest(url);

        JSONObject obj = new JSONObject(response.body());
        JSONArray weatherData = obj.getJSONArray("weatherData");
        JSONObject latestData = weatherData.getJSONObject(0);
        JSONArray roadConditions = latestData.getJSONArray("roadConditions");

        HashMap<String, ArrayList<String>> data = new HashMap<>();

        // todo get winter slipperiness
        for(int i = 1; i < roadConditions.length(); i++) {
            ArrayList<String> results = new ArrayList<>();

            JSONObject forecast = roadConditions.getJSONObject(i);
            JSONObject forecastCondition = forecast.getJSONObject("forecastConditionReason");
            String hour = forecast.getString("forecastName");

            // Check and add precipitation
            if(forecastCondition.has("precipitationCondition")) {
                results.add(forecastCondition.getString("precipitationCondition"));
            }

            // Check and add overall road condition
            if(forecastCondition.has("roadCondition")) {
                results.add(forecastCondition.getString("roadCondition"));
            }

            // Check and add road slipperiness
            if(forecastCondition.has("winterSlipperiness")) {
                results.add(forecastCondition.getString("winterSlipperiness"));
            }

            data.put(hour, results);

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

        HashMap<String, Integer> taskMap = GetTasks();
        System.out.println(taskMap);

        int messages = GetTrafficMessages();
        System.out.println(messages);

        HashMap<String, ArrayList<String>> condition = GetRoadCondition();
        System.out.println(condition);

        ArrayList<DataPoint> data = new ArrayList<>();
        return data;
    }
}
