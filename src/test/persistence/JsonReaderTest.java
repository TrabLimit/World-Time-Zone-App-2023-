package persistence;

import model.City;
import model.CityList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for JsonReaderTest is sourced/found from JsonReaderTest class in
// the JsonSerializationDemo from the course's edX website for Personal
// Project Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related work belong to the teaching team of UBC CPSC 210.
// GitHub: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/test/persistence/JsonReaderTest.java

public class JsonReaderTest extends JsonTest{


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CityList cl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCityList() {
        JsonReader reader = new JsonReader("./data/TestReaderEmptyCityList.json");
        try {
            CityList cl = reader.read();
            assertEquals("./data/TestReaderEmptyCityList.json", reader.getSource());
            assertEquals(0, cl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderDefaultCityList() {
        JsonReader reader = new JsonReader("./data/TestReaderDefaultCityList.json");
        try {
            CityList cl = reader.read();
            assertEquals("./data/TestReaderDefaultCityList.json", reader.getSource());
            List<City> cities = cl.getListOfCities();
            assertEquals(3, cities.size());
            checkCity("Home", "Vancouver", -7.0, 49.2827, -123.1207, cities.get(0));
            checkCity("School", "Montreal", -4.0, 45.5019, -73.5674, cities.get(1));
            checkCity("Family", "Seoul", +9.0, 37.5519, 126.9918, cities.get(2));
            // pass since there should be no issue with locating/reading from the file
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    /*
    testCity1 = new City("Home", "Vancouver", -7.0, 49.2827, -123.1207);
    testCity2 = new City("School", "Montreal", -4.0, 45.5019, -73.5674);
    testCity3 = new City("Family", "Seoul", +9.0, 37.5519, 126.9918);
    */
}
