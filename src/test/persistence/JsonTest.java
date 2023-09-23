package persistence;

import model.City;

import static org.junit.jupiter.api.Assertions.*;

// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for JsonTest is sourced/found from JsonTest class in
// the JsonSerializationDemo from the course's edX website for Personal Project
// Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related work belong to the teaching team of UBC CPSC 210.
// GitHub: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/test/persistence/JsonTest.java

public class JsonTest {
    protected void checkCity(String label, String name, double zoneUTC, double lat, double longi, City city) {
        assertEquals(label, city.getLabel());
        assertEquals(name, city.getCityName());
        assertEquals(zoneUTC, city.getOffsetUTC());
        assertEquals(lat, city.getLatitude());
        assertEquals(longi, city.getLongitude());
    }
}
