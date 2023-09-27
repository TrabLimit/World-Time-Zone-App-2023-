package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// List of Cities (made of City class objects)
public class CityList implements Writable {

    public static final int MAX_ARRAY_SIZE = 10;

    private ArrayList<City> listOfCities; // might change to some other list in the future

    // EFFECTS: create an empty list of cities
    public CityList() {
        listOfCities = new ArrayList<>();
    }

    // EFFECTS: returns true if CityList has the given city, or has a city that is identical to the given city
    // public boolean isCityIncluded(City city) {
    //    return (this.listOfCities.contains(city) || this.doesIdenticalCityAlreadyExist(city));
    // }

    // EFFECTS: returns true if CityList has a city that is identical in terms of city information to the given city,
    //          false if none found.
    public boolean doesIdenticalCityAlreadyExist(City city) {
        for (City c : this.listOfCities) {
            if (c.isSameCity(city)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return the number of cities in CityList
    public int size() {
        return this.listOfCities.size();
    }

    // REQUIRES: this.size() < MAX_ARRAY_SIZE
    // MODIFIES: this
    // EFFECTS: add a given city to the listOfCities and return true, if it's already been added, then just return false
    public boolean add(City city) {
        if (!(this.doesIdenticalCityAlreadyExist(city))) {
            this.listOfCities.add(city);
            EventLog.getInstance().logEvent(new Event(city + " has been added."));
            return true;
        }
        return false;

    }


    // MODIFIES: this
    // EFFECTS: remove the given city to the listOfCities if it's present on the list
    //          shift everything to the right to left in listofCities
    public void remove(City city) {
        if (this.doesIdenticalCityAlreadyExist(city)) {  // originally had isCityIncluded(city)
            this.listOfCities.remove(city);
            EventLog.getInstance().logEvent(new Event(city + " has been removed."));
        }
    }

    // MODIFIES: this
    // EFFECTS: remove city with given index i to the listOfCities if it's present on the list
    //          shift everything to the right to left in listofCities
    // OVERLOAD
    public void remove(int i) {
        if (0 <= i && i < this.listOfCities.size()) {
            this.listOfCities.remove(i);
           // EventLog.getInstance().logEvent(new Event(this.get(i) + " has been removed."));
        }
    }


    public ArrayList<City> getListOfCities() {
        return this.listOfCities;
    }



    //REQUIRES: 0 <=  i < listOfCities.size()
    //EFFECTS: retrieve a city object in the list of cities by the array index number
    public City get(int i) {
        return this.listOfCities.get(i);
    }


    // MODIFIES: this
    // EFFECTS: clears all cities in CityLIst
    public void clear() {
        this.listOfCities.clear();
        EventLog.getInstance().logEvent(new Event("All cities have been cleared."));
    }


    // REQUIRES: City c must be valid and not null
    // EFFECTS: returns true if the given city C is  in CityList, false if not found
    public boolean contains(City c) {
        return this.listOfCities.contains(c);
    }


    // REQUIRES: City c must be valid and not null
    // EFFECTS: returns the index of the city c inside CityList , or -1 if not found
    public int indexOf(City c) {
        return this.listOfCities.indexOf(c);
    }



    // REQUIRES: 0 =< beforeIndex, afterIndex < listOfCities.size()
    //          otherwise we will have IndexOutOfBoundsException (which needs to be caught)
    // MODIFIES: this
    // EFFECTS: swap the city in beforeIndex to that of afterIndex and rearrange cityList
    public void swap(int beforeIndex, int afterIndex) {
       /*
       if (beforeIndex >= 0 && beforeIndex < listOfCities.size()
                && afterIndex >= 0 && afterIndex < listOfCities.size()) {
            if (beforeIndex != afterIndex) {
                Collections.swap(this.listOfCities, beforeIndex, afterIndex);
            }
        }
        */
        Collections.swap(this.listOfCities, beforeIndex, afterIndex);
        EventLog.getInstance().logEvent(new Event("A city of index " + beforeIndex
                + " has been shifted to index " + afterIndex + "."));
    }


    // REQUIRES: c must not be valid
    // MODIFIES: this
    // EFFECTS: set and overwrite the given index in the listOfCities with a given city c
    public void set(int i, City c) {
        if (i >= 0 && i < listOfCities.size() && c != null) {
            this.listOfCities.set(i, c);
            EventLog.getInstance().logEvent(new Event("A city of index " + i + " has been set to " + c + "."));
        }
    }


    //REQUIRES: label's length must be greater than 0
    //EFFECTS: returns a list of cities that have label being searched or empty if no city found
    //          for searching label, upper/lowercase difference is ignored
    public ArrayList<City> searchCityByLabel(String label) {
        ArrayList<City> searchResults = new ArrayList<>();
        for (City c : this.listOfCities) {
            if (c.getLabel().equalsIgnoreCase(label)) {
                searchResults.add(c);
            }
        }
        return searchResults;
    }

    //REQUIRES: name's length must be greater than 0
    //EFFECTS: returns a list of cities that have name being searched or empty if no city found
    //          for searching name, upper/lowercase difference is ignored
    public ArrayList<City> searchByName(String name) {
        ArrayList<City> searchResults = new ArrayList<>();
        for (City c : this.listOfCities) {
            if (c.getCityName().equalsIgnoreCase(name)) {
                searchResults.add(c);
            }
        }
        return searchResults;
    }

    //REQUIRES: -12.0 <= utc <= 12.0, utc must be 1 decimal point, with value either 0 or 5 for the decimal.
    //EFFECTS: returns a list of cities that have UTC zone being searched or empty if no city found
    public ArrayList<City> searchByUTC(double utc) {
        ArrayList<City> searchResults = new ArrayList<>();
        for (City c : this.listOfCities) {
            if (c.getOffsetUTC() == utc) {
                searchResults.add(c);
            }
        }
        return searchResults;
    }

    //REQUIRES: -90 =< south =< north =< 90, -180 =< west =< east =< 180
    //EFFECTS: returns a list of cities that are found within the location range or empty if no city found
    public ArrayList<City> searchByLocationRange(double south, double north, double west, double east) {
        ArrayList<City> searchResults = new ArrayList<>();
        for (City c : this.listOfCities) {
            if ((south <= c.getLatitude()) && (c.getLatitude() <= north)
                    && (west <= c.getLongitude()) && (c.getLongitude() <= east)) {
                searchResults.add(c);
            }
        }
        return searchResults;
    }

    // IMPORTANT DISCLAIMER OF RECOGNITION
    // Code for toJson() and citiesToJson() are sourced/found from WorkRoom class in the JsonSerializationDemo
    // from the course's edX website for Personal Project Phase 2 module.
    // These methods were then modified accordingly to suit the need of this project.
    // All related works belong to the teaching team of UBC CPSC 210.
    // GitHub: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/model/WorkRoom.java

    @Override
    // EFFECTS: returns CityList as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("List of Cities", citiesToJson());
        return json;
    }


    // EFFECTS: returns cities in this listofCities as a JSON array
    private JSONArray citiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (City c : listOfCities) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


}
