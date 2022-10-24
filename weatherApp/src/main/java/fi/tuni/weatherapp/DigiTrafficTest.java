package fi.tuni.weatherapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.*;
import org.json.JSONArray;

public class DigiTrafficTest implements IDataSource {

    private String name = "DigiTrafficTest";
    private ArrayList<Variable> variables;

    public DigiTrafficTest() {
        variables = new ArrayList<>();
        variables.add(new Variable("DigiTraffic1", "Unit1"));
        variables.add(new Variable("DigiTraffic2", "Unit 2"));
    }

    public static void parse(String response) {
        System.out.println(response);
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
        //Dummy data for interface testing
        ArrayList<DataPoint> data = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes?endFrom=2022-01-19T09%3A00%3A00Z&endBefore=2022-01-19T14%3A00%3A00Z&xMin=21&yMin=61&xMax=22&yMax=62&taskId=&domain=state-roads");

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .setHeader("Accept-Encoding", "gzip header")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(DigiTrafficTest::parse)
                .join();

        return data;
    }
}
