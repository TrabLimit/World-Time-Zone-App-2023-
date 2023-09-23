package persistence;

import model.City;
import model.CityList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import model.Event;
import model.EventLog;
import org.json.*;


// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for JsonReader is sourced/found from the JsonReader Class in JsonSerializationDemo from the course's edX website
// for Personal Project Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related works belong to the teaching team of UBC CPSC 210.
// GitHub:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/persistence/JsonReader.java

// Represents a reader that reads CityList and City from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CityList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loading " + this.source + "..."));
        return parseCityList(jsonObject);
    }

    public String getSource() {
        return this.source;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses citylist from JSON object and returns it
    private CityList parseCityList(JSONObject jsonObject) {
        CityList cl = new CityList();
        addCities(cl, jsonObject);
        return cl;
    }

    // MODIFIES: cl
    // EFFECTS: parses cities from JSON object and adds them to cl
    private void addCities(CityList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("List of Cities");
        for (Object json : jsonArray) {
            JSONObject nextCity = (JSONObject) json;
            addCity(cl, nextCity);
        }
    }

    // MODIFIES: cl
    // EFFECTS: parses city from JSON object and adds it to cl
    private void addCity(CityList cl, JSONObject jsonObject) {
        String label = jsonObject.getString("Label");
        String name = jsonObject.getString("City Name");
        double utcZone = jsonObject.getDouble("UTC Zone Offset");
        double latitude = jsonObject.getDouble("Latitude");
        double longitude = jsonObject.getDouble("Longitude");


        City city = new City(label, name, utcZone, latitude, longitude);
        cl.add(city);
        // do not be confused with addCity from JsonReader class
        // this one is from City class
    }
}

