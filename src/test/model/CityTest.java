package model;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class CityTest {
    private City testCity1;
    private City testCity2;
    private City testCity3;
    private City testCity4;
    private City testCity5;
    private City testCity6;
    private City testCity7;


    @BeforeEach
    void runBefore() {
        testCity1 = new City("Home", "Vancouver", -7.0, 49.2827, -123.1207);
        testCity2 = new City("School", "Montreal", -4.0, 45.5019, -73.5674);
        testCity3 = new City("Family", "Seoul", +9.0, 37.5519, 126.9918);
        testCity4 = new City("School", "Surrey", -7.0, 49.1913, -122.8490);
        testCity5 = new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600);
        testCity6 = new City("", "Seattle", -7.0, 47.6062, -122.3321);
        testCity7 = new City("", "Wonderland", 1.0, 0.0, 0.0);

    }

    @Test
    void testConstructor() {
        assertEquals("Home", testCity1.getLabel());
        assertEquals("Vancouver", testCity1.getCityName());
        assertEquals(-7.0, testCity1.getOffsetUTC());
        assertEquals(49.2827, testCity1.getLatitude());
        assertEquals(-123.1207, testCity1.getLongitude());

        assertEquals("", testCity7.getLabel());
        assertEquals("Wonderland", testCity7.getCityName());
        assertEquals(1.0, testCity7.getOffsetUTC());
        assertEquals(0.0, testCity7.getLatitude());
        assertEquals(0.0, testCity7.getLongitude());
    }


    @Test
    void testSetLabel() {
        testCity7.setLabel("Harry Potter");

        assertEquals("Harry Potter", testCity7.getLabel());
        assertEquals("Wonderland", testCity7.getCityName());
        assertEquals(1.0, testCity7.getOffsetUTC());
        assertEquals(0.0, testCity7.getLatitude());
        assertEquals(0.0, testCity7.getLongitude());

        testCity7.setLabel("Dudley");
        testCity7.setLabel("Hedwig");

        assertEquals("Hedwig", testCity7.getLabel());
    }

    @Test
    void testSetCityName(){
        testCity7.setCityName("Emerald City");

        assertEquals("", testCity7.getLabel());
        assertEquals("Emerald City", testCity7.getCityName());
        assertEquals(1.0, testCity7.getOffsetUTC());
        assertEquals(0.0, testCity7.getLatitude());
        assertEquals(0.0, testCity7.getLongitude());

        testCity7.setCityName("Gotham");
        testCity7.setCityName("Metropolis");

        assertEquals("Metropolis", testCity7.getCityName());
    }

    @Test
    void testSetOffsetUTC(){
        testCity7.setOffsetUTC(6.0);

        assertEquals("", testCity7.getLabel());
        assertEquals("Wonderland", testCity7.getCityName());
        assertEquals(6.0, testCity7.getOffsetUTC());
        assertEquals(0.0, testCity7.getLatitude());
        assertEquals(0.0, testCity7.getLongitude());

        testCity7.setOffsetUTC(11.0);
        testCity7.setOffsetUTC(-9.5);
        assertEquals(-9.5, testCity7.getOffsetUTC());
    }


    @Test
    void testSetLatitudeAndLongitude(){
        testCity7.setLatitude(6.0);

        assertEquals("", testCity7.getLabel());
        assertEquals("Wonderland", testCity7.getCityName());
        assertEquals(1.0, testCity7.getOffsetUTC());
        assertEquals(6.0, testCity7.getLatitude());
        assertEquals(0.0, testCity7.getLongitude());

        testCity7.setLatitude(0);
        assertEquals(0, testCity7.getLatitude());

        testCity7.setLatitude(1.3);
        testCity7.setLatitude(-80.1111);
        assertEquals(-80.1111, testCity7.getLatitude());


        testCity7.setLongitude(-114.3);
        assertEquals("", testCity7.getLabel());
        assertEquals("Wonderland", testCity7.getCityName());
        assertEquals(1.0, testCity7.getOffsetUTC());
        assertEquals(-80.1111, testCity7.getLatitude());
        assertEquals(-114.3, testCity7.getLongitude());

        testCity7.setLongitude(-1.63);
        testCity7.setLongitude(150.2394);
        assertEquals(150.2394, testCity7.getLongitude());

        testCity7.setLongitude(0);
        assertEquals(0, testCity7.getLongitude());

    }


    @Test
    void testIsSameCity() {

        testCity7.setLabel("Harry Potter");
        testCity7.setCityName("Surrey");
        testCity7.setOffsetUTC(0.0);
        testCity7.setLatitude(51.3148);
        testCity7.setLongitude(-0.5600);  // same city as testCity5

        assertTrue(testCity5.isSameCity(testCity5)); // always equal with itself
        assertTrue(testCity5.isSameCity(testCity7)); // test case for testCity7 which has same info as testCity5
        assertFalse(testCity5.isSameCity(testCity1));

        testCity7.setLabel("Dursleys"); // changed label, should be different city now (for the time being)
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setLabel("Hary Potter"); // revert back... spelling mistake! oops
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setLabel("Harry Potter"); // revert back
        assertTrue(testCity5.isSameCity(testCity7));

        testCity7.setCityName("London");
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setCityName("Surrey"); // revert back
        assertTrue(testCity5.isSameCity(testCity7));

        testCity7.setOffsetUTC(+1.0);
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setOffsetUTC(+0.0); // revert back
        assertTrue(testCity5.isSameCity(testCity7));

        testCity7.setLatitude(71.2);
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setLatitude(0);
        testCity7.setLongitude(-0);
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setLatitude(51.3148);
        testCity7.setLongitude(-0.5600); // revert back
        assertTrue(testCity5.isSameCity(testCity7));


        testCity7.setLongitude(10.0); // just longitude change
        assertFalse(testCity5.isSameCity(testCity7));

        testCity7.setLongitude(-0.5600); // revert back
        assertTrue(testCity5.isSameCity(testCity7));


        testCity7.setLabel("Peter Parker");
        assertFalse(testCity7.isSameCity(testCity5));

        testCity7.setCityName("New York");
        assertFalse(testCity7.isSameCity(testCity5));

        testCity7.setOffsetUTC(-4.0);
        assertFalse(testCity7.isSameCity(testCity5));

        testCity7.setLatitude(40.7128);
        assertFalse(testCity7.isSameCity(testCity5));

        testCity7.setLongitude(-37.6173);
        assertFalse(testCity7.isSameCity(testCity5));

    }

    @Test
    void testEquals() {

        testCity7.setLabel("Harry Potter");
        testCity7.setCityName("Surrey");
        testCity7.setOffsetUTC(0.0);
        testCity7.setLatitude(51.3148);
        testCity7.setLongitude(-0.5600);  // same city as testCity5

        assertTrue(testCity5.equals(testCity5)); // always equal with itself
        assertTrue(testCity5.equals(testCity7)); // test case for testCity7 which has same info as testCity5
        assertFalse(testCity5.equals(testCity1));

        assertEquals(0, testCity5.hashCode()); // just checking to make sure hashCode is not affecting our equals

        assertFalse(testCity5.equals("testCity5")); // just checking if trying to compare different type, it doesn't work

        testCity7.setLabel("Dursleys"); // changed label, should be different city now (for the time being)
        assertFalse(testCity5.equals(testCity7));

        testCity7.setLabel("Hary Potter"); // revert back... spelling mistake! oops
        assertFalse(testCity5.equals(testCity7));

        testCity7.setLabel("Harry Potter"); // revert back
        assertTrue(testCity5.equals(testCity7));

        testCity7.setCityName("London");
        assertFalse(testCity5.equals(testCity7));

        testCity7.setCityName("Surrey"); // revert back
        assertTrue(testCity5.equals(testCity7));

        testCity7.setOffsetUTC(+1.0);
        assertFalse(testCity5.equals(testCity7));

        testCity7.setOffsetUTC(+0.0); // revert back
        assertTrue(testCity5.equals(testCity7));

        testCity7.setLatitude(71.2);
        assertFalse(testCity5.equals(testCity7));

        testCity7.setLatitude(0);
        testCity7.setLongitude(-0);
        assertFalse(testCity5.equals(testCity7));

        testCity7.setLatitude(51.3148);
        testCity7.setLongitude(-0.5600); // revert back
        assertTrue(testCity5.equals(testCity7));


        testCity7.setLongitude(10.0); // just longitude change
        assertFalse(testCity5.equals(testCity7));

        testCity7.setLongitude(-0.5600); // revert back
        assertTrue(testCity5.equals(testCity7));


        testCity7.setLabel("Peter Parker");
        assertFalse(testCity7.equals(testCity5));

        testCity7.setCityName("New York");
        assertFalse(testCity7.equals(testCity5));

        testCity7.setOffsetUTC(-4.0);
        assertFalse(testCity7.equals(testCity5));

        testCity7.setLatitude(40.7128);
        assertFalse(testCity7.equals(testCity5));

        testCity7.setLongitude(-37.6173);
        assertFalse(testCity7.equals(testCity5));

    }

    @Test
    void testFromUtcToZoneId(){
        assertEquals("UTC+00:00", testCity5.fromUtcToZoneId());
        assertEquals("UTC-04:00", testCity2.fromUtcToZoneId());
        assertEquals("UTC+09:00", testCity3.fromUtcToZoneId());

        testCity7.setOffsetUTC(+11.5);
        assertEquals("UTC+11:30", testCity7.fromUtcToZoneId());

        testCity7.setOffsetUTC(-0.25);
        assertEquals("UTC-00:15", testCity7.fromUtcToZoneId());

        testCity7.setOffsetUTC(+0.333334); // need to put 4 at the last digit otherwise truncates at 19
        assertEquals("UTC+00:20", testCity7.fromUtcToZoneId());


        testCity7.setOffsetUTC(-10.75);
        assertEquals("UTC-10:45", testCity7.fromUtcToZoneId());
    }

    @Test
    void testToJson(){
        JSONObject testObject1 = testCity1.toJson();

        assertEquals("Home", testObject1.getString("Label"));
        assertEquals("Vancouver", testObject1.getString("City Name"));
        assertEquals(-7.0, testObject1.getDouble("UTC Zone Offset"));
        assertEquals(49.2827, testObject1.getDouble("Latitude"));
        assertEquals(-123.1207, testObject1.getDouble("Longitude"));
    }

    @Test
    void testToString(){

        assertEquals("Seattle", testCity6.toString());
        assertEquals("<Harry Potter> Surrey", testCity5.toString());
    }


    @Test
    void testCoordToString(){
        City testCity0 = new City("Evita", "Buenos Aires", -3.0, -34.613, -58.377);

        assertEquals ("N 37.5519", testCity3.latToString());
        assertEquals ("E 126.9918", testCity3.longToString());

        assertEquals ("S 34.613", testCity0.latToString());
        assertEquals ("W 58.377", testCity0.longToString());

    }



}
