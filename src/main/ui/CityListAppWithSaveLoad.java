package ui;

import persistence.JsonReader;
import persistence.JsonWriter;


// Interface for apps that uses CityList as main field and has ability to save/load CityList from/to a file
public interface CityListAppWithSaveLoad {


    // MODIFIES: jsonWriter
    // EFFECTS: saves the citylist to given jsonWriter
    public void saveCityListToFile(JsonWriter jsonWriter);


    // MODIFIES: this
    // EFFECTS: loads cityList from given jsonReader file
    public void loadCityListFromFile(JsonReader jsonReader);


}
