package persistence;

import model.City;
import model.CityList;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.*;

// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for JsonWriter is sourced/found from JsonWriter class in JsonSerializationDemo from the course's edX website
// for Personal Project Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related works belong to the teaching team of UBC CPSC 210.
// GitHub:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/persistence/JsonWriter.java


// Represents a writer that writes JSON representation of CityList to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    public String getDestination() {
        return this.destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of citylist to file
    public void write(CityList cl) {
        JSONObject json = cl.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saving to " + this.destination + "..."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

