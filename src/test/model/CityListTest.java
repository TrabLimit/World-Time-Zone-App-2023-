package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CityListTest {
    private City testCity1;
    private City testCity2;
    private City testCity3;
    private City testCity4;
    private City testCity5;
    private City testCity6;
    private City testCity7;

    private CityList testCityList;

    @BeforeEach
    void runBefore() {
        testCity1 = new City("Home", "Vancouver", -7.0, 49.2827, -123.1207);
        testCity2 = new City("School", "Montreal", -4.0, 45.5019, -73.5674);
        testCity3 = new City("Family", "Seoul", +9.0, 37.5519, 126.9918);
        testCity4 = new City("School", "Surrey", -7.0, 49.1913, -122.8490);
        testCity5 = new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600);
        testCity6 = new City("", "Seattle", -7.0, 47.6062, -122.3321);

        testCity7 = new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600);
        // same city as testCity5

        testCityList = new CityList();
    }

    @Test
    void testRemoveWithIndex(){
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);

        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.remove(-1); // invalid

        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.remove(3); // invalid
        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.remove(2);
        assertEquals(2, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertFalse(testCityList.contains(testCity3));

        testCityList.remove(0);
        assertEquals(1, testCityList.size());
        assertFalse(testCityList.contains(testCity1));
        assertEquals(testCity2, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertFalse(testCityList.contains(testCity3));




    }

    @Test
    void testConstructor() {
        assertEquals(0, testCityList.size());
        // assertEquals(0, testCityList.getListOfCities().size());
    }

    @Test
    void testIndexOf() {
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);

        assertEquals(0, testCityList.indexOf(testCity1));
        assertEquals(1, testCityList.indexOf(testCity2));
        assertEquals(-1, testCityList.indexOf(testCity4)); // not found
    }

    @Test
    void testSet(){
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);

        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.set(-1, testCity4); // invalid index
        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.set(3, testCity4); // invalid index
        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.set(0, testCity4);
        assertEquals(3, testCityList.size());
        assertFalse(testCityList.contains(testCity1));
        assertTrue(testCityList.contains(testCity4));
        assertEquals(testCity4, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertTrue(testCityList.contains(testCity3));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.set(2, testCity6);
        assertEquals(3, testCityList.size());
        assertFalse(testCityList.contains(testCity1));
        assertTrue(testCityList.contains(testCity4));
        assertEquals(testCity4, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertFalse(testCityList.contains(testCity3));
        assertTrue(testCityList.contains(testCity6));
        assertEquals(testCity6, testCityList.get(2));


    }

    @Test
    void testSwap(){
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);

        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        /*
        testCityList.swap(-1, 0); // invalid index

        assertEquals(testCity1, testCityList.get(0));
        assertEquals(testCity2, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.swap(0, 4); // invalid index

        assertEquals(testCity1, testCityList.get(0));
        assertEquals(testCity2, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        */

        testCityList.swap(1, 1); // self swap so no change

        assertEquals(testCity1, testCityList.get(0));
        assertEquals(testCity2, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.swap(0, 1);

        assertEquals(testCity2, testCityList.get(0));
        assertEquals(testCity1, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.swap(2, 0);

        assertEquals(testCity3, testCityList.get(0));
        assertEquals(testCity1, testCityList.get(1));
        assertEquals(testCity2, testCityList.get(2));



    }


    @Test
    void testClear(){
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);

        assertEquals(3, testCityList.size());
        assertTrue(testCityList.contains(testCity1));
        assertEquals(testCity1, testCityList.get(0));
        assertTrue(testCityList.contains(testCity2));
        assertEquals(testCity2, testCityList.get(1));
        assertEquals(testCity3, testCityList.get(2));

        testCityList.clear();
        assertEquals(0, testCityList.size());
        assertFalse(testCityList.contains(testCity1));
        assertFalse(testCityList.contains(testCity2));
        assertFalse(testCityList.contains(testCity3));

    }

    @Test
    void testContains() {

        assertFalse(testCityList.contains(testCity1));

        testCityList.add(testCity1);
        assertTrue(testCityList.contains(testCity1));

        assertFalse(testCityList.contains(testCity5));

        testCityList.add(testCity5);
        assertTrue(testCityList.contains(testCity1));
        assertTrue(testCityList.contains(testCity5));
        assertTrue(testCityList.contains(testCity7));
        // because testCity7 has the exact same city info as testCity5

        testCityList.add(testCity3);
        assertTrue(testCityList.contains(testCity3));
        assertFalse(testCityList.contains(testCity2));
        assertFalse(testCityList.contains(testCity4));

        testCity7.setCityName("Wonderland"); // now testCity7 is different from testCity5
        assertFalse(testCityList.contains(testCity7));

        testCity7.setCityName("Surrey"); // revert it back
        assertTrue(testCityList.contains(testCity7));

        testCity7.setOffsetUTC(+11); // now testCity7 is different from testCity5
        assertFalse(testCityList.contains(testCity7));

        testCity7.setOffsetUTC(+0.0); // revert it back
        assertTrue(testCityList.contains(testCity7));


    }




    @Test
    void testDoesIdenticalCityAlreadyExist() {

        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity1));

        testCityList.add(testCity1);
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity1));

        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity5));

        testCityList.add(testCity5);
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity1));
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity5));
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity7));
        // because testCity7 has the exact same city info as testCity5

        testCityList.add(testCity3);
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity3));
        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity2));
        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity4));

        testCity7.setCityName("Wonderland"); // now testCity7 is different from testCity5
        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity7));

        testCity7.setCityName("Surrey"); // revert it back
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity7));

        testCity7.setOffsetUTC(+11); // now testCity7 is different from testCity5
        assertFalse(testCityList.doesIdenticalCityAlreadyExist(testCity7));

        testCity7.setOffsetUTC(+0.0); // revert it back
        assertTrue(testCityList.doesIdenticalCityAlreadyExist(testCity7));


    }


    @Test
    void testAddRemoveCityToList() {

        assertEquals(0, testCityList.size());
        // assertEquals(0, testCityList.getListOfCities().size());

        assertTrue(testCityList.add(testCity1));

        assertEquals(1, testCityList.size());
        // assertEquals(1,testCityList.getListOfCities().size());
        assertTrue(testCityList.contains(testCity1));

        testCityList.remove(testCity1);
        assertEquals(0, testCityList.size());
        // assertEquals(0, testCityList.getListOfCities().size());
        assertFalse(testCityList.contains(testCity1));

        testCityList.remove(testCity3);
        assertEquals(0, testCityList.size());
        // assertEquals(0, testCityList.getListOfCities().size());


        assertTrue(testCityList.add(testCity2));
        assertFalse(testCityList.add(testCity2));
        assertFalse(testCityList.add(testCity2));
        assertEquals(1, testCityList.size());
        // assertEquals(1,testCityList.getListOfCities().size());
        assertTrue(testCityList.contains(testCity2));


        assertTrue(testCityList.add(testCity1));
        assertFalse(testCityList.add(testCity2));
        assertTrue(testCityList.add(testCity3));
        assertEquals(3, testCityList.size());
        // assertEquals(3,testCityList.getListOfCities().size());
        assertEquals("Vancouver", testCityList.get(1).getCityName());
        assertEquals(9, testCityList.get(2).getOffsetUTC());
        assertEquals(-73.5674, testCityList.get(0).getLongitude());

        testCityList.remove(testCity2);
        testCityList.remove(testCity6);
        testCityList.remove(testCity4);
        assertEquals(2, testCityList.size());
        // assertEquals(2,testCityList.getListOfCities().size());
        assertEquals("Seoul", testCityList.get(1).getCityName());
        assertEquals(9, testCityList.get(1).getOffsetUTC());
        assertEquals(-123.1207, testCityList.get(0).getLongitude());
        assertEquals("Home", testCityList.get(0).getLabel());


        assertTrue(testCityList.add(testCity5));
        assertFalse(testCityList.add(testCity7)); // testCity7 is literally the same as testCity5
        testCityList.remove(testCity4);
        assertEquals(3, testCityList.size());
        // assertEquals(3,testCityList.getListOfCities().size());
        assertEquals("Harry Potter", testCityList.get(2).getLabel());
        assertEquals("Surrey", testCityList.get(2).getCityName());
        assertEquals(0, testCityList.get(2).getOffsetUTC());
        assertEquals(51.3148, testCityList.get(2).getLatitude());

        testCity7.setCityName("Wonderland"); // now testCity7 is different from testCity5
        assertTrue(testCityList.add(testCity7)); // so it can be added now
        assertEquals(4, testCityList.size());
        // assertEquals(4,testCityList.getListOfCities().size());
        assertEquals(testCity7, testCityList.get(3));

        testCityList.remove(testCity7);
        testCityList.remove(testCity7);
        assertEquals(3, testCityList.size());
        // assertEquals(3,testCityList.getListOfCities().size());
        assertEquals(testCity5, testCityList.get(2));


    }

    @Test
    void testSearchCityByLabel() {
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);
        testCityList.add(testCity4);
        testCityList.add(testCity5);
        testCityList.add(testCity6);
        testCityList.add(testCity7); // will not add since it's identical to testCity5

        ArrayList<City> testList = testCityList.getListOfCities();


        assertEquals(1, testCityList.searchCityByLabel("Home").size());
        assertEquals(testCity1, testCityList.searchCityByLabel("Home").get(0));

        assertEquals(2, testCityList.searchCityByLabel("School").size());
        assertEquals(testCity2, testCityList.searchCityByLabel("School").get(0));
        assertEquals(testCity4, testCityList.searchCityByLabel("School").get(1));

        // check to make sure testCity7 is not involved
        assertEquals(1, testCityList.searchCityByLabel("Harry Potter").size());
        assertEquals(testCity5, testCityList.searchCityByLabel("Harry Potter").get(0));

        testList.get(0).setLabel("School");
        assertEquals(3, testCityList.searchCityByLabel("School").size());
        assertEquals(testCity1, testCityList.searchCityByLabel("School").get(0));
        assertEquals(testCity2, testCityList.searchCityByLabel("School").get(1));
        assertEquals(testCity4, testCityList.searchCityByLabel("School").get(2));


        testList.get(1).setLabel("Home");
        testList.get(3).setLabel("Friend");
        assertEquals(1, testCityList.searchCityByLabel("School").size());
        assertEquals(testCity1, testCityList.searchCityByLabel("School").get(0));

    }

    @Test
    void testSearchCityByName() {
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);
        testCityList.add(testCity4);
        testCityList.add(testCity5);
        testCityList.add(testCity6);
        testCityList.add(testCity7); // will not add since it's identical to testCity5

        ArrayList<City> testList = testCityList.getListOfCities();

        assertEquals(1, testCityList.searchByName("Seoul").size());
        assertEquals(testCity3, testCityList.searchByName("Seoul").get(0));

        // check to make sure testCity7 is not involved, but both 4 and 5 are involved
        assertEquals(2, testCityList.searchByName("Surrey").size());
        assertEquals(testCity4, testCityList.searchByName("Surrey").get(0));
        assertEquals(testCity5, testCityList.searchByName("Surrey").get(1));

        // change testCity6's name to SURREY and search for surrey
        // for search, lowercase/uppercase difference doesn't matter
        testList.get(5).setCityName("SURREY");
        assertEquals(3, testCityList.searchByName("surrey").size());
        assertEquals(testCity4, testCityList.searchByName("surrey").get(0));
        assertEquals(testCity5, testCityList.searchByName("surrey").get(1));
        assertEquals(testCity6, testCityList.searchByName("surrey").get(2));
        assertTrue(testCityList.searchByName("surrey").size()
                == testCityList.searchByName("SURREY").size());


        // testCity4 is no longer Surrey
        testList.get(3).setCityName("Sydney");
        assertEquals(2, testCityList.searchByName("Surrey").size());
        assertEquals(testCity5, testCityList.searchByName("Surrey").get(0));


        // change testCity4's name to sEOuL
        // for search, lowercase/uppercase difference doesn't matter
        testList.get(3).setCityName("sEOuL");
        assertEquals(2, testCityList.searchByName("seoUl").size());
        assertEquals(testCity3, testCityList.searchByName("seoUl").get(0));
        assertEquals(testCity4, testCityList.searchByName("seoUl").get(1));
        assertEquals(2, testCityList.searchByName("sEOuL").size());
        assertTrue(testCityList.searchByName("sEOUL").size()
                == testCityList.searchByName("seoUl").size());
    }


    @Test
    void testSearchCityByUTC() {
        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);
        testCityList.add(testCity4);
        testCityList.add(testCity5);
        testCityList.add(testCity6);
        testCityList.add(testCity7); // will not add since it's identical to testCity5

        ArrayList<City> testList = testCityList.getListOfCities();

        // check to make sure testCity7 is not involved
        assertEquals(1, testCityList.searchByUTC(0).size());
        assertEquals(testCity5, testCityList.searchByUTC(0).get(0));

        assertEquals(1, testCityList.searchByUTC(-4.0).size());
        assertEquals(testCity2, testCityList.searchByUTC(-4.0).get(0));

        assertEquals(3, testCityList.searchByUTC(-7.0).size());
        assertEquals(testCity1, testCityList.searchByUTC(-7.0).get(0));
        assertEquals(testCity4, testCityList.searchByUTC(-7.0).get(1));
        assertEquals(testCity6, testCityList.searchByUTC(-7.0).get(2));

        testList.get(0).setOffsetUTC(-4.0);
        assertEquals(2, testCityList.searchByUTC(-7.0).size());
        assertEquals(2, testCityList.searchByUTC(-4.0).size());
        assertEquals(testCity4, testCityList.searchByUTC(-7.0).get(0));
        assertEquals(testCity6, testCityList.searchByUTC(-7.0).get(1));
        assertEquals(testCity1, testCityList.searchByUTC(-4.0).get(0));
        assertEquals(testCity2, testCityList.searchByUTC(-4.0).get(1));

    }

    @Test
    void testSearchCityByLocationRange() {

        testCityList.add(testCity1);
        testCityList.add(testCity2);
        testCityList.add(testCity3);
        testCityList.add(testCity4);
        testCityList.add(testCity5);
        testCityList.add(testCity6);
        testCityList.add(testCity7); // will not add since it's identical to testCity5

        //ArrayList<City> testList = testCityList.getListOfCities();

        assertEquals(0, testCityList.searchByLocationRange(0, 0, 0, 0).size());

        assertEquals(0,
                testCityList.searchByLocationRange(50.2827, 51.2827,
                        -123.1207, -123.1207).size());

        assertEquals(0,
                testCityList.searchByLocationRange(49.2827, 49.2827,
                        -126.1207, -125.1207).size());


        assertEquals(1,
                testCityList.searchByLocationRange(49.2827, 49.2827,
                        -123.1207, -123.1207).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(49.2827, 49.2827,
                        -123.1207, -123.1207).get(0));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1914, 49.2827,
                        -123.1207, -122.8491).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(49.1914, 49.2827,
                        -123.1207, -122.8491).get(0));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1914, 49.2827,
                        -123.1207, -122.8490).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(49.1914, 49.2827,
                        -123.1207, -122.8490).get(0));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1207, -122.8491).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1207, -122.8491).get(0));


        assertEquals(2,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1207, -122.8490).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1207, -122.8490).get(0));
        assertEquals(testCity4,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1207, -122.8490).get(1));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1913, 49.2826,
                        -123.1206, -122.8490).size());
        assertEquals(testCity4,
                testCityList.searchByLocationRange(49.1913, 49.2826,
                        -123.1206, -122.8490).get(0));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1206, -122.8490).size());
        assertEquals(testCity4,
                testCityList.searchByLocationRange(49.1913, 49.2827,
                        -123.1206, -122.8490).get(0));


        assertEquals(1,
                testCityList.searchByLocationRange(49.1913, 49.2826,
                        -123.1207, -122.8490).size());
        assertEquals(testCity4,
                testCityList.searchByLocationRange(49.1913, 49.2826,
                        -123.1207, -122.8490).get(0));


        // check to make sure testCity7 is not involved
        assertEquals(1,
                testCityList.searchByLocationRange(50, 52,
                        -1, 1).size());
        assertEquals(testCity5,
                testCityList.searchByLocationRange(50, 52,
                        -1, 1).get(0));


        assertEquals(3,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).size());
        assertEquals(testCity1,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(0));
        assertEquals(testCity4,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(1));
        assertEquals(testCity6,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(2));


        testCity1.setLongitude(0.0);
        assertEquals(2,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).size());
        assertEquals(testCity4,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(0));
        assertEquals(testCity6,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(1));


        testCity3.setLatitude(48.0);
        testCity3.setLongitude(-123.0);

        testCity5.setLatitude(49.0);
        testCity5.setLongitude(-122.5);


        assertEquals(4,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).size());
        assertEquals(testCity3,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(0));
        assertEquals(testCity4,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(1));
        assertEquals(testCity5,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(2));
        assertEquals(testCity6,
                testCityList.searchByLocationRange(47.6062, 49.2827,
                        -123.1207, -122.3321).get(3));


    }


    @Test
    void testEmptyCityListToJson() {
        JSONObject testObject = testCityList.toJson();
        assertTrue(testObject.getJSONArray("List of Cities").isEmpty());
        assertEquals(0, testObject.getJSONArray("List of Cities").length());
    }

    @Test
    void testOneInCityListToJSon() {
        testCityList.add(testCity6);
        JSONObject testObject = testCityList.toJson();
        JSONArray testArray = testObject.getJSONArray("List of Cities");

        assertFalse(testArray.isEmpty());
        assertEquals(1, testArray.length());
        assertEquals("", testArray.getJSONObject(0).getString("Label"));
        assertEquals("Seattle", testArray.getJSONObject(0).getString("City Name"));
        assertEquals(-7.0, testArray.getJSONObject(0).getDouble("UTC Zone Offset"));
        assertEquals(47.6062, testArray.getJSONObject(0).getDouble("Latitude"));
        assertEquals(-122.3321, testArray.getJSONObject(0).getDouble("Longitude"));
    }


    @Test
    void testMultipleCitiesInCityListToJSon() {
        testCityList.add(testCity5);
        testCityList.add(testCity6);
        testCityList.add(testCity4);

        testCityList.add(testCity3);
        testCityList.remove(testCity3);

        JSONObject testObject = testCityList.toJson();
        JSONArray testArray = testObject.getJSONArray("List of Cities");
        assertFalse(testArray.isEmpty());
        assertEquals(3, testArray.length());

        assertEquals("Harry Potter", testArray.getJSONObject(0).getString("Label"));
        assertEquals("Surrey", testArray.getJSONObject(0).getString("City Name"));
        assertEquals(0.0, testArray.getJSONObject(0).getDouble("UTC Zone Offset"));
        assertEquals(51.3148, testArray.getJSONObject(0).getDouble("Latitude"));
        assertEquals(-0.5600, testArray.getJSONObject(0).getDouble("Longitude"));

        assertEquals("", testArray.getJSONObject(1).getString("Label"));
        assertEquals("Seattle", testArray.getJSONObject(1).getString("City Name"));
        assertEquals(-7.0, testArray.getJSONObject(1).getDouble("UTC Zone Offset"));
        assertEquals(47.6062, testArray.getJSONObject(1).getDouble("Latitude"));
        assertEquals(-122.3321, testArray.getJSONObject(1).getDouble("Longitude"));

        assertEquals("School", testArray.getJSONObject(2).getString("Label"));
        assertEquals("Surrey", testArray.getJSONObject(2).getString("City Name"));
        assertEquals(-7.0, testArray.getJSONObject(2).getDouble("UTC Zone Offset"));
        assertEquals(49.1913, testArray.getJSONObject(2).getDouble("Latitude"));
        assertEquals(-122.8490, testArray.getJSONObject(2).getDouble("Longitude"));

    }



/*

        testCity6 = new City("", "Seattle", -7.0, 47.6062, -122.3321);

testCity4 = new City("School", "Surrey", -7.0, 49.1913, -122.8490);
        testCity5 = new City("Harry Potter", "Surrey", 0.0, 51.3148, -0.5600);

assertEquals("Home", testObject1.getString("Label"));
        assertEquals("Vancouver", testObject1.getString("City Name"));
        assertEquals(-7.0, testObject1.getDouble("UTC Zone Offset"));
        assertEquals(49.2827, testObject1.getDouble("Latitude"));
        assertEquals(-123.1207, testObject1.getDouble("Longitude"));
 */

}
