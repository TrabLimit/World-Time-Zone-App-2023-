package persistence;

import model.City;
import model.CityList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// IMPORTANT DISCLAIMER OF RECOGNITION:
// Code for JsonWriterTest is sourced/found from JsonWriterTest class in the JsonSerializationDemo from the course's edX
// website for Personal Project Phase 2 module.
// These methods were then modified accordingly to suit the need of this project.
// All related work belong to the teaching team of UBC CPSC 210.
// GitHub: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/test/persistence/JsonWriterTest.java

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            CityList cl = new CityList();
            JsonWriter writer = new JsonWriter("./data/invalid\0namefor:fileName.json");
            // invalid path file name
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass when you catch the expected exception
        }
    }

    @Test
    void testWriterEmptyCityList() {
        try {
            CityList cl = new CityList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCityList.json");
            writer.open();
            assertEquals("./data/testWriterEmptyCityList.json", writer.getDestination());
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCityList.json");
            cl = reader.read();
            assertEquals("./data/testWriterEmptyCityList.json", reader.getSource());

            assertEquals(0, cl.size());
            // pass since the file is found
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterEmptyCityListToNonEmpty() {
        try {
            CityList cl = new CityList();
            cl.add(new City("Whatever", "Wonderland", 1.0, 45.0, 90.0));

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCityList.json");
            writer.open();
            assertEquals("./data/testWriterEmptyCityList.json", writer.getDestination());
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCityList.json");
            cl = reader.read();
            assertEquals("./data/testWriterEmptyCityList.json", reader.getSource());

            assertEquals(1, cl.size());
            assertEquals("Whatever", cl.getListOfCities().get(0).getLabel());
            assertEquals("Wonderland", cl.getListOfCities().get(0).getCityName());
            assertEquals(1.0, cl.getListOfCities().get(0).getOffsetUTC());
            assertEquals(45.0, cl.getListOfCities().get(0).getLatitude());
            assertEquals(90.0, cl.getListOfCities().get(0).getLongitude());
            // pass since the file is found
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterDefaultCityList() {
        try {
            CityList cl = new CityList();
            cl.add(new City("School", "Surrey", -7.0, 49.1913, -122.8490));
            cl.add(new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600));

            JsonWriter writer = new JsonWriter("./data/testWriterDefaultCityList.json");
            writer.open();
            assertEquals("./data/testWriterDefaultCityList.json", writer.getDestination());
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultCityList.json");
            cl = reader.read();
            assertEquals("./data/testWriterDefaultCityList.json", reader.getSource());
            assertEquals(2, cl.size());

            assertEquals("School", cl.getListOfCities().get(0).getLabel());
            assertEquals("Surrey", cl.getListOfCities().get(0).getCityName());
            assertEquals(-7.0, cl.getListOfCities().get(0).getOffsetUTC());
            assertEquals(49.1913, cl.getListOfCities().get(0).getLatitude());
            assertEquals(-122.8490, cl.getListOfCities().get(0).getLongitude());

            assertEquals("Harry Potter", cl.getListOfCities().get(1).getLabel());
            assertEquals("Surrey", cl.getListOfCities().get(1).getCityName());
            assertEquals(0.0, cl.getListOfCities().get(1).getOffsetUTC());
            assertEquals(51.3148, cl.getListOfCities().get(1).getLatitude());
            assertEquals(-0.5600, cl.getListOfCities().get(1).getLongitude());
            // pass since the file is found

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

/*
testCity4 = new City("School", "Surrey", -7.0, 49.1913, -122.8490);
        testCity5 = new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600);
*/
