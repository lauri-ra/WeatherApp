package fi.tuni.weatherapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class DigiTrafficTest implements IDataSource {

    private String name = "DigiTrafficTest";
    private ArrayList<Variable> variables;

    private static final String URL = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes?endFrom=2022-01-19T09%3A00%3A00Z&endBefore=2022-01-19T14%3A00%3A00Z&xMin=21&yMin=61&xMax=22&yMax=62&taskId=&domain=state-roads";

    public DigiTrafficTest() {
        variables = new ArrayList<>();
        variables.add(new Variable("DigiTraffic1", "Unit1"));
        variables.add(new Variable("DigiTraffic2", "Unit 2"));
    }

    public static ArrayList<String> GetTasks(String response) {

        ArrayList<String> tasks = new ArrayList<>();

        JSONObject obj = new JSONObject(response);
        JSONArray features = obj.getJSONArray("features");

        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            JSONArray task = properties.getJSONArray("tasks");

            System.out.println(task.getString(0));
            tasks.add(task.getString(0));
        }

        return tasks;

    }

    public static void TrafficMessages(String response) {
        // todo
    }

    public static void GetRoadCondition(String response) {
        // todo
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

        // GET Request
        URI url = URI.create(URL);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =  HttpRequest
                .newBuilder()
                .GET()
                .header("Accept-Encoding", "gzip header")
                .uri(url)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ArrayList<String> foo = new ArrayList<>();
            foo = GetTasks(response.body());

        }
        catch (IOException | InterruptedException e) {
            System.out.println("GET request error");
        }

        return data;
    }
}
