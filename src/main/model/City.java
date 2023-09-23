package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.*;
import java.util.Objects;

import static java.lang.Math.abs;

// City with label, name, UTC timezone, and coordinates
public class City implements Writable {
    private String label;
    private String cityName;
    private double offsetUTC;
    private double latitude;   // N degree = +, S degree = -
    private double longitude;  // E degree = +, W degree = -


    // REQUIRES: name's length must be greater than 0
    //          -12.0 <= zoneUTC <= 12.0, zoneUTC must be 1 decimal point, with value either 0 or 5 for the decimal.
    //          -90 =< lat =< 90, -180 =< longi =< 180
    // EFFECTS: Create a city with given label, name, zoneUTC, lat(itude), and longi(tude)
    public City(String label, String name, double zoneUTC, double lat, double longi) {
        this.label = label;
        this.cityName = name;
        this.offsetUTC = zoneUTC;
        this.latitude = lat;
        this.longitude = longi;
    }

    public String getLabel() {
        return this.label;
    }

    public String getCityName() {
        return this.cityName;
    }

    public double getOffsetUTC() {
        return this.offsetUTC;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }


    // MODIFIES: this
    // EFFECTS: sets this.label to given label
    public void setLabel(String label) {
        this.label = label;
    }


    // REQUIRES: name's length must be greater than 0
    // MODIFIES: this
    // EFFECTS: sets this.cityName to given name
    public void setCityName(String name) {
        this.cityName = name;
        EventLog.getInstance().logEvent(new Event("City name set to: " + this.cityName + "."));
    }

    // REQUIRES: -12.0 <= zoneUTC <= 12.0, zoneUTC must be 1 decimal point, with value either 0 or 5 for the decimal.
    // MODIFIES: this
    // EFFECTS: sets this.offsetUTC to given UTC Timezone
    public void setOffsetUTC(double zoneUTC) {
        this.offsetUTC = zoneUTC;
        EventLog.getInstance().logEvent(new Event("City UTC zone set to: " + this.fromUtcToZoneId() + "."));
    }

//    // REQUIRES: -90 =< lat =< 90, -180 =< longi =< 180
//    // MODIFIES: this
//    // EFFECTS: sets this.latitude to given lat, and this.longitude to given longi
//    public void setCoordinates(double lat, double longi) {
//        this.latitude = lat;
//        this.longitude = longi;
//    }

    // REQUIRES: -90 =< lat =< 90
    // MODIFIES: this
    // EFFECTS: sets this.latitude to given lat
    public void setLatitude(double lat) {
        this.latitude = lat;
        EventLog.getInstance().logEvent(new Event("City latitude set to: " + this.latToString() + "."));
    }


    // EFFECTS: converts the city's latitude to string value
    public String latToString() {
        String latString;
        if (this.latitude < 0) {
            latString = "S " + (Math.abs(this.latitude));
        } else {
            latString = "N " + Math.abs(this.latitude);
        }
        return latString;
    }

    // REQUIRES: -180 =< longi =< 180
    // MODIFIES: this
    // EFFECTS: sets this.longitude to given longi
    public void setLongitude(double longi) {
        this.longitude = longi;
        EventLog.getInstance().logEvent(new Event("City Longitude set to: " + this.longToString() + "."));
    }



    // EFFECTS: converts the city's longitude to string value
    public String longToString() {
        String longString;

        if (this.longitude < 0) {
            longString = "W " + (Math.abs(this.longitude));
        } else {
            longString = "E " + (Math.abs(this.longitude));
        }

        return longString;
    }




    // EFFECTS: returns true if City has the same name, UTC zone, and coordinates as the given City c
    public boolean isSameCity(City c) {
        return ((c.getLabel().equals(this.getLabel())) // checking labels may be optional in the future
                && (c.getCityName().equals(this.getCityName()))
                && (c.getOffsetUTC() == this.getOffsetUTC())
                && (c.getLatitude() == this.getLatitude())
                && (c.getLongitude() == this.getLongitude()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }

        City city = (City) o;

        if (Double.compare(city.offsetUTC, offsetUTC) != 0) {
            return false;
        }
        if (Double.compare(city.latitude, latitude) != 0) {
            return false;
        }
        if (Double.compare(city.longitude, longitude) != 0) {
            return false;
        }
        if (!Objects.equals(label, city.label)) {
            return false;
        }
        return Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    // EFFECTS: returns the zoneId string value of this.offsetUTC
    public String fromUtcToZoneId() {
        double offsetUtcAbsVal = abs(this.offsetUTC);
        int hour = (int) offsetUtcAbsVal;
        int min = (int) ((offsetUtcAbsVal - hour) * 60);

        String plusmin;
        if (this.offsetUTC < 0) {
            plusmin = "-";
        } else {
            plusmin = "+";
        }
        String hourString = String.format("%02d", hour);
        String minString = String.format("%02d", min);

        return "UTC" + plusmin + hourString + ":" + minString;
    }

    // IMPORTANT DISCLAIMER OF RECOGNITION
    // Code for toJson() is sourced/found from the Thingy class in JsonSerializationDemo
    // from the course's edX website for Personal Project Phase 2 module.
    // These methods were then modified accordingly to suit the need of this project.
    // All related works belong to the teaching team of UBC CPSC 210.
    // GitHub: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/model/Thingy.java
    @Override
    // EFFECTS: returns City as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Label", this.label);
        json.put("City Name", this.cityName);
        json.put("UTC Zone Offset", this.offsetUTC);
        json.put("Latitude", this.latitude);
        json.put("Longitude", this.longitude);

        return json;
    }

    // EFFECTS: This is for GUI purposes for JList
    //          Displays City's label and name  when used for DefaultModelList
    @Override
    public String toString() {
        /*
        return "City{" +
                "label='" + label + '\'' +
                ", cityName='" + cityName + '\'' +
                ", offsetUTC=" + offsetUTC +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
        */
        String label = "";
        if (!this.label.equals("")) {
            label = "<" + this.label + "> ";
        }
        return label + this.cityName;
    }
}
