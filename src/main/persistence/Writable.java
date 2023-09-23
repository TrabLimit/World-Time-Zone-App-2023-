package persistence;

import org.json.JSONObject;

// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for Writable is sourced/found from Writable class in
// the JsonSerializationDemo from the course's edX website for Personal Project
// Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related work belong to the teaching team of UBC CPSC 210.
// GitHub:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/persistence/Writable.java

// represents an interface where we convert all the subclasses that implements this interface as JSON objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

