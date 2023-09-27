package ui;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

import model.City;
import model.CityList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.time.*;


// World Time Zone Application
public class TimeApp implements CityListAppWithSaveLoad {
    private Scanner input;
    private CityList cityList;
    private DateTimeFormatter timeFormatter;
    private static final String JSON_STORE_PARENT = "./data/";

    private static final String JSON_STORE_1 = JSON_STORE_PARENT + "myCityList1.json";
    private static final String JSON_STORE_2 = JSON_STORE_PARENT + "myCityList2.json";
    private static final String JSON_STORE_3 = JSON_STORE_PARENT + "myCityList3.json";

    private JsonReader jsonReader1;
    private JsonWriter jsonWriter1;
    private JsonReader jsonReader2;
    private JsonWriter jsonWriter2;
    private JsonReader jsonReader3;
    private JsonWriter jsonWriter3;


    // FOR TESTING PURPOSE ONLY
    // MODIFIES: this
    // EFFECTS: adds 3 test cities to cityList for demonstration/test purposes
    public void testDummy() {
        City testCity1 = new City("Home", "Vancouver", -7.0, 49.2827, -123.1207);
        City testCity2 = new City("School", "Montreal", -4.0, 45.5019, -73.5674);
        City testCity3 = new City("Family", "Seoul", +9.0, 37.5519, 126.9918);

        this.cityList.add(testCity1);
        this.cityList.add(testCity2);
        this.cityList.add(testCity3);

    }
    // END OF TEST
    // DO NOT DELETE OR CHANGE BELOW

    // IMPORTANT DISCLAIMER OF RECOGNITION:
    // 1. Codes for TimeApp(), runTimeApp(), displayOptionsMenu(), processCommand(String command), init() are
    // sourced/found from the TellerApp class from the course's edX website for Personal Project Phase 1 module.
    // 2. Codes for saveCityListWhere(), saveCityListToFile(jsonWriter), loadCityListWhichFile(),
    // and loadCityListFromFile(jsonReader) are sourced/found from the WorkRoomApp class in JsonSerializationDemo
    // from the course's edX website for Personal Project Phase 2 module.
    // All of these methods were then modified accordingly to suit the need of this project.
    // All related works belong to the teaching team of UBC CPSC 210.
    // GitHub:
    // 1.  https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/e7b9393073a9c312696114b14567173ea3247ed6/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // 2.  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/d31979d8a993d63c3a8c13c8add7f9d1753777b6/src/main/ui/WorkRoomApp.java



    // EFFECTS: runs the world time zone application
    public TimeApp() {
        System.out.println("\nWelcome to Bart's Time Zone App!");
        //init();
        runTimeApp();

    }


    // MODIFIES: this
    // EFFECTS: processes user input command (option to save offered before quitting)
    private void runTimeApp() {
        boolean keepRunning = true;
        String command = null;

        init();

        while (keepRunning) {
            displayOptionsMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Before you quit, would you like to save your current list to a file?");
        System.out.println("Press \"Y\" to save. Press any other key to skip.");
        String saveResponse = input.next();
        saveResponse = saveResponse.toLowerCase();
        if (saveResponse.equals("y")) {
            saveCityListWhere();
        }

        System.out.println("______________________________________________________________________");
        System.out.println("\nThank you for using our app! Make sure you leave us a review!");
    }

    // EFFECTS: displays options menu to user
    private void displayOptionsMenu() {
        System.out.println("______________________________________________________________________");
        System.out.println("\nPlease select from the following options:");
        System.out.println("\tPress \"L\" to load your saved list of cities from a file.");
        System.out.println("\tPress \"S\" to save your current list of cities to a file.\n");
        System.out.println("\tPress \"C\" to add new city.");
        System.out.println("\tPress \"D\" to display all cities added.");
        System.out.println("\tPress \"M\" to modify a city.");
        System.out.println("\tPress \"X\" to delete a city.");
        System.out.println("\tPress \"F\" to find cities.\n");
        System.out.println("\tPress \"W\" to view World Times for cities.");
        System.out.println("\tPress \"T\" to change format of your date/time.\n");
        System.out.println("\tPress \"Q\" to quit.");
    }


    // MODIFIES: this
    // EFFECTS: processes the given command by user
    private void processCommand(String command) {

        if (command.equals("l")) {
            loadCityListWhichFile();
        } else if (command.equals("s")) {
            saveCityListWhere();
        } else if (command.equals("c")) {
            createCity();
        } else if (command.equals("d")) {
            displayCities();
        } else if (command.equals("m")) {
            modifyCity();
        } else if (command.equals("x")) {
            deleteCity();
        } else if (command.equals("f")) {
            findCity();
        } else if (command.equals("w")) {
            displayWorldTime();
        } else if (command.equals("t")) {
            selectTimeFormat();
        } else {
            System.out.println("\nInvalid command! Please try the valid option.");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes CityList, timeFormatter, Scanner, and Json files for save/load
    private void init() {
        cityList = new CityList();
        timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        // placing test dummy here - remove later
        this.testDummy();

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        jsonReader1 = new JsonReader(JSON_STORE_1);
        jsonWriter1 = new JsonWriter(JSON_STORE_1);

        jsonReader2 = new JsonReader(JSON_STORE_2);
        jsonWriter2 = new JsonWriter(JSON_STORE_2);

        jsonReader3 = new JsonReader(JSON_STORE_3);
        jsonWriter3 = new JsonWriter(JSON_STORE_3);


    }

    // REQUIRES: nextResponse must be either 1, 2, or 3
    // EFFECTS: saves the current citylist to one of 3 file choices
    private void saveCityListWhere() {
        System.out.println("Which Save file# do you wish to save your list on?");
        System.out.println("WARNING: Your previous save will be overwritten. \n");

        System.out.println("Save to... \n");

        System.out.println("\tFile #1 \t\t\t\t-> Press 1");
        System.out.println("\tFile #2 \t\t\t\t-> Press 2");
        System.out.println("\tFile #3 \t\t\t\t-> Press 3");

        int nextResponse = input.nextInt();

        if (nextResponse == 1) {
            saveCityListToFile(jsonWriter1);
        } else if (nextResponse == 2) {
            saveCityListToFile(jsonWriter2);
        } else if (nextResponse == 3) {
            saveCityListToFile(jsonWriter3);
        }

    }

    // MODIFIES: jsonWriter
    // EFFECTS: saves the citylist to given jsonWriter
    @Override
    public void saveCityListToFile(JsonWriter jsonWriter) {
        try {
            jsonWriter.open();
            jsonWriter.write(cityList);
            jsonWriter.close();
            System.out.println("Successfully saved current list of cities to " + jsonWriter.getDestination() + "!");
        } catch (FileNotFoundException e) {
            System.out.println("Sorry. We are unable to write to file " + jsonWriter.getDestination() + ".");
        }
    }


    // REQUIRES: nextResponse must be either 1, 2, or 3
    // MODIFIES: this
    // EFFECTS: loads the citylist from one of 3 file choices
    private void loadCityListWhichFile() {
        System.out.println("From which Save file# do you wish to load your list of cities?");

        System.out.println("Load from... \n");

        System.out.println("\tFile #1 \t\t\t\t-> Press 1");
        System.out.println("\tFile #2 \t\t\t\t-> Press 2");
        System.out.println("\tFile #3 \t\t\t\t-> Press 3");

        int nextResponse = input.nextInt();

        if (nextResponse == 1) {
            loadCityListFromFile(jsonReader1);
        } else if (nextResponse == 2) {
            loadCityListFromFile(jsonReader2);
        } else if (nextResponse == 3) {
            loadCityListFromFile(jsonReader3);
        }

    }


    // MODIFIES: this
    // EFFECTS: loads citylist from given jsonReader file
    @Override
    public void loadCityListFromFile(JsonReader jsonReader) {
        try {
            cityList = jsonReader.read();
            System.out.println("Successfully loaded the list of cities" + " from " + jsonReader.getSource() + "!");
        } catch (IOException e) {
            System.out.println("Sorry. We are unable to read from file " + jsonReader.getSource() + ".");
        }
    }

    // REQUIRES: name's length must be greater than 0
    //          -12.0 <= zoneUTC <= 12.0, zoneUTC must be 1 decimal point, with value either 0 or 5 for the decimal.
    //          -90 =< latitude =< 90, -180 =< longitude =< 180
    // MODIFIES: this
    // EFFECTS: create a new city, set its values, and add it to citylist
    private void createCity() {
        City city = new City("", "null", 0, 0, 0);
        System.out.println(
                "\nDo you wish to add a label to your city?  Press \"Y\" to add label. Press any other key to skip.");
        String response = input.next();
        response = response.toLowerCase();
        if (response.equals("y")) {
            System.out.println("\nEnter your city's label:");
            // String label = input.next();
            city.setLabel(input.next());
        }
        System.out.println("\nEnter your city's name:");
        //String name = input.next();
        city.setCityName(input.next());

        System.out.println(
                "\nEnter your city's UTC zone (e.g. For +05:00, put \"+5.\" For -10:30, put \"-10.5.\"):");
        //double utc = input.nextDouble();
        city.setOffsetUTC(input.nextDouble());

        System.out.println("\nEnter your city's latitude (for N, put +, and for S, put - before your degree):");
        //double lat = input.nextDouble();
        city.setLatitude(input.nextDouble());

        System.out.println("\nEnter your city's longitude (for E, put +, and for W, put - before your degree):");
        //double longi = input.nextDouble();
        city.setLongitude(input.nextDouble());

        if (!cityList.add(city)) {
            System.out.println("\nSorry. That city has already been added. Please try again.");
        } else {
            System.out.println("\nYour city has been successfully created and added!");
        }
    }


    // EFFECTS: print all cities (show names, labels, time zone, and coordinates) in the list
    private void displayCities() {
        System.out.println("\nHere are your cities:\n");
        List<City> cities = this.cityList.getListOfCities();

        if (cities.size() > 0) {
            for (City c : cities) {
                String label = c.getLabel();
                if (label.equals("")) {
                    label = "* no label *";
                }
                System.out.println("City #" + (cities.indexOf(c) + 1) + ": " + c.getCityName() + " (" + label + ") ");
                System.out.println("Time Zone: " + c.fromUtcToZoneId());
                System.out.println("Coordinates: " + c.latToString() + ", " + c.longToString() + "\n");
            }
        } else {
            System.out.println("(You have no cities in the list. You can add a city from the main menu.)");
        }
    }


    // REQUIRES: 1 <= input.nextInt() <= cityList.size()
    // MODIFIES: this
    // EFFECTS: select a city and modify its values
    private void modifyCity() {
        if (this.cityList.size() > 0) {
            System.out.println("\nEnter the City # of the city you wish to modify:");
            int selection = input.nextInt();
            int index = selection - 1;
            City selectedCity = this.cityList.get(index);

            // originally had this.cityList.getCityByIndex(selection);
            // but since we didn't use it that much, we removed the method

            City changedCity = new City("", "null", 0, 0, 0);
            // blank slate where we will put the changes in

            System.out.println("\nModifying City #" + selection + ": " + selectedCity.getCityName() + "...");

            labelUpdate(selectedCity, changedCity);
            nameUpdate(selectedCity, changedCity);
            zoneUtcUpdate(selectedCity, changedCity);
            coordUpdate(selectedCity, changedCity);

            if ((cityList.doesIdenticalCityAlreadyExist(changedCity))
                // && (changedCity.getLabel().equals(selectedCity.getLabel()))
            ) {
                System.out.println("\nSorry. That city already exists. Cannot overwrite.");
            } else {
                System.out.println("\nCity #" + selection + " has been successfully updated!");
                cityList.set(index, changedCity);
            }
        } else {
            System.out.println("(You have no cities in the list. You can add a city from the main menu.)");
        }


    }

    // REQUIRES: newLabel's length should be greater than 0
    // MODIFIES: changedCity
    // EFFECTS: set the label of changedCity to newLabel if responseLabel is Y,
    //          otherwise set its label to that of selectedCity
    private void labelUpdate(City selectedCity, City changedCity) {
        System.out.println("\nModify label? Press \"Y\" to confirm, or press any other key to skip:");
        String responseLabel = input.next();
        responseLabel = responseLabel.toLowerCase();

        if (responseLabel.equals("y")) {
            System.out.println("\nEnter your city's new label:");
            String newLabel = input.next();
            changedCity.setLabel(newLabel); // set new label
        } else {
            changedCity.setLabel(selectedCity.getLabel());
        }
    }

    // REQUIRES: newName's length should be greater than 0
    // MODIFIES: changedCity
    // EFFECTS: set the cityName of changedCity to newName if responseName is Y,
    //          otherwise set its cityName to that of selectedCity
    private void nameUpdate(City selectedCity, City changedCity) {
        System.out.println("\nModify name? Press \"Y\" to confirm, or press any other key to skip:");
        String responseName = input.next();
        responseName = responseName.toLowerCase();

        if (responseName.equals("y")) {
            System.out.println("\nEnter your city's new name:");
            String newName = input.next();
            changedCity.setCityName(newName);  // set new city name
        } else {
            changedCity.setCityName(selectedCity.getCityName());
        }
    }

    // REQUIRES: -12.0 <= newUTC <= 12.0, newUTC must be 1 decimal point, with value either 0 or 5 for the decimal.
    // MODIFIES: changedCity
    // EFFECTS: set the offsetUTC of changedCity to newUTC if responseUTC is Y,
    //          otherwise set its offsetUTC to that of selectedCity
    private void zoneUtcUpdate(City selectedCity, City changedCity) {
        System.out.println("\nModify UTC Zone? Press \"Y\" to confirm, or press any other key to skip:");
        String responseUTC = input.next();
        responseUTC = responseUTC.toLowerCase();

        if (responseUTC.equals("y")) {
            System.out.println(
                    "\nEnter your city's new UTC zone (e.g. For +05:00, put \"+5.\" For -10:30, put \"-10.5.\"):");
            double newUTC = input.nextDouble();
            changedCity.setOffsetUTC(newUTC);
        } else {
            changedCity.setOffsetUTC(selectedCity.getOffsetUTC());
        }
    }

    // REQUIRES: -90 =< newLat =< 90, -180 =< newLongi =< 180
    // MODIFIES: changedCity
    // EFFECTS: set latitude and longitude of changedCity to newLat and newLongi respectively if responseCoord is Y,
    //          otherwise set its latitude and longitude to those of selectedCity
    private void coordUpdate(City selectedCity, City changedCity) {
        System.out.println("\nModify coordinates? Press \"Y\" to confirm, or press any other key to skip:");
        String responseCoord = input.next();
        responseCoord = responseCoord.toLowerCase();

        if (responseCoord.equals("y")) {
            System.out.println("\nEnter your city's new latitude (+ for N, - for S):");
            double newLat = input.nextDouble();
            changedCity.setLatitude(newLat);
            System.out.println("\nEnter your city's new longitude (+ for E, - for W):");
            double newLongi = input.nextDouble();
            changedCity.setLongitude(newLongi);
            //changedCity.setCoordinates(newLat, newLongi);
        } else {
            //changedCity.setCoordinates(selectedCity.getLatitude(), selectedCity.getLongitude());
            changedCity.setLatitude(selectedCity.getLatitude());
            changedCity.setLongitude(selectedCity.getLongitude());
        }
    }


    // REQUIRES: 1 <= selection <= cityList.size()
    // MODIFIES: this
    // EFFECTS: select a city and delete it from the list
    private void deleteCity() {
        if (this.cityList.size() > 0) {
            System.out.println("\nEnter the City # of the city you wish to delete:");
            int selection = input.nextInt();
            int index = selection - 1;
            City selectedCity = cityList.get(index);

            System.out.println("\nDelete City #" + selection + ": " + selectedCity.getCityName() + "?");
            System.out.println("WARNING: This action CANNOT be undone!\n");
            System.out.println("Press \"Y\" to confirm deletion. Press any other key to cancel.");
            String responseDelete = input.next();
            responseDelete = responseDelete.toLowerCase();
            if (responseDelete.equals("y")) {
                System.out.println("\nCity #" + selection + ": " + selectedCity.getCityName() + " has been deleted.");
                cityList.remove(index);
            }
        } else {
            System.out.println("(You have no cities in the list. You can add a city from the main menu.)");
        }

    }

    // REQUIRES: nextResponse must be 1,2,3, or 4
    // EFFECTS: find a city in the list by label, name, utc, or coordinates range and print search results
    private void findCity() {
        if (this.cityList.size() > 0) {
            System.out.println("\nFind city by:");
            System.out.println("\t1. Label \t\t\t\t-> Press 1");
            System.out.println("\t2. Name \t\t\t\t-> Press 2");
            System.out.println("\t3. UTC \t\t\t-\t\t-> Press 3");
            System.out.println("\t4. Coordinates Range \t-> Press 4");

            int nextResponse = input.nextInt();

            if (nextResponse == 1) {
                labelSearch();
            } else if (nextResponse == 2) {
                cityNameSearch();
            } else if (nextResponse == 3) {
                zoneUtcSearch();
            } else if (nextResponse == 4) {
                coordRangeSearch();
            }
            System.out.println("End of Search.");
        } else {
            System.out.println("(You have no cities in the list. You can add a city from the main menu.)");
        }
    }


    // REQUIRES: ans1's length must be greater than 0
    // EFFECTS: initiates search by label (ans1) and print out the label search results
    private void labelSearch() {
        System.out.println("\nPlease enter label:");
        String ans1 = input.next();
        List<City> searchResults1 = cityList.searchCityByLabel(ans1);
        System.out.println("\nHere are the search results: \n");
        //if (searchResults1.size() > 0) {
        for (City c : searchResults1) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            System.out.println("Result #" + (searchResults1.indexOf(c) + 1) + ": ");
            System.out.println(c.getCityName() + " (" + label + ") ");
            System.out.println("Time Zone: " + c.fromUtcToZoneId());
            System.out.println("Coordinates: " + c.getLatitude() + ", " + c.getLongitude() + "\n");
        }
//        } else {
//            System.out.println("No search results.");
//        }
    }

    // REQUIRES: ans2's length must be greater than 0
    // EFFECTS: initiates search by city name (ans2) and print out the city name search results
    private void cityNameSearch() {
        System.out.println("\nPlease enter city name:");
        String ans2 = input.next();
        List<City> searchResults2 = cityList.searchByName(ans2);
        System.out.println("\nHere are the search results: \n");
        //if (searchResults2.size() > 0) {
        for (City c : searchResults2) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            System.out.println("Result #" + (searchResults2.indexOf(c) + 1) + ": ");
            System.out.println(c.getCityName() + " (" + label + ") ");
            System.out.println("Time Zone: " + c.fromUtcToZoneId());
            System.out.println("Coordinates: " + c.getLatitude() + ", " + c.getLongitude() + "\n");
        }
//        } else {
//            System.out.println("No search results.");
//        }
    }


    // REQUIRES: -12.0 <= ans3 <= 12.0, ans3 must be 1 decimal point, with value either 0 or 5 for the decimal.
    // EFFECTS: initiates search by UTC zone (ans3) and print out the label search results
    private void zoneUtcSearch() {
        System.out.println("\nPlease enter UTC zone (e.g. For +05:00, put \"+5.\" For -10:30, put \"-10.5.\"):");
        double ans3 = input.nextDouble();
        List<City> searchResults3 = cityList.searchByUTC(ans3);
        System.out.println("\nHere are the search results: \n");
        //  if (searchResults3.size() > 0) {
        for (City c : searchResults3) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            System.out.println("Result #" + (searchResults3.indexOf(c) + 1) + ": ");
            System.out.println(c.getCityName() + " (" + label + ") ");
            System.out.println("Time Zone: " + c.fromUtcToZoneId());
            System.out.println("Coordinates: " + c.getLatitude() + ", " + c.getLongitude() + "\n");
        }
//        } else {
//            System.out.println("No search results.");
//        }
    }

    // REQUIRES: -90 =< ans4s =< ans4n =< 90, -180 =< ans4w =< ans4e =< 180
    // EFFECTS: initiates search by coordinate range (ans4n, ans4s, ans4e, ans4w)
    //          and print out the coordinate search results
    private void coordRangeSearch() {
        System.out.println("\nPlease enter NORTH (upper) degree boundary (+ for N, - for S):");
        double ans4n = input.nextDouble();
        System.out.println("\nPlease enter SOUTH (lower) degree boundary (+ for N, - for S):");
        double ans4s = input.nextDouble();
        System.out.println("\nPlease enter EAST (right) degree boundary (+ for E, - for W):");
        double ans4e = input.nextDouble();
        System.out.println("\nPlease enter WEST (left) degree boundary (+ for E, - for W):");
        double ans4w = input.nextDouble();

        List<City> searchResults4 = cityList.searchByLocationRange(ans4s, ans4n, ans4w, ans4e);
        System.out.println("\nHere are the search results: \n");
        //if (searchResults4.size() > 0) {
        for (City c : searchResults4) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            System.out.println("Result #" + (searchResults4.indexOf(c) + 1) + ": ");
            System.out.println(c.getCityName() + " (" + label + ") ");
            System.out.println("Time Zone: " + c.fromUtcToZoneId());
            System.out.println("Coordinates: " + c.getLatitude() + ", " + c.getLongitude() + "\n");
        }
//        } else {
//            System.out.println("No search results.");
//        }
    }

    // EFFECTS: show all the cities' name and time and prompt for set and display custom time option
    private void displayWorldTime() {
        //List<City> cities = this.cityList.getListOfCities();
        if (this.cityList.size() > 0) {
            displayCurrentTime();

            System.out.println("\nWould you like to set a different time and date to display?\n");
            System.out.println("Press \"Y\" to confirm, or press any other key to skip:");
            String responseSetTime = input.next();
            responseSetTime = responseSetTime.toLowerCase();

            if (responseSetTime.equals("y")) {
                setAndDisplayCustomTime();
            }
        } else {
            System.out.println("(You have no cities in the list. You can add a city from the main menu.)");
        }


    }


    // EFFECTS: prints all the cities' name and current local time and date
    private void displayCurrentTime() {
        System.out.println("\nHere are your cities' current local time and date:\n");
        List<City> cities = this.cityList.getListOfCities();


        for (City c : cities) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            ZoneId zone = ZoneId.of(c.fromUtcToZoneId());
            ZonedDateTime nowDateTime = ZonedDateTime.now(zone);

            System.out.println("City #" + (cities.indexOf(c) + 1) + ": " + c.getCityName() + " (" + label + ") ");
            System.out.println("Current Time: " + timeFormatter.format(nowDateTime) + "\n");
        }


    }


    // EFFECTS: calls this.inputCustomTime() to get the time input and reference UTC
    //          and prints all the cities' name and custom local time and date based on the time input and ref UTC
    private void setAndDisplayCustomTime() {
        List<City> cities = this.cityList.getListOfCities();

        ZonedDateTime customTime = this.inputCustomTime();
        //ZonedDateTime localTimeRef = customTime.withZoneSameLocal(refZone);

        System.out.println("\nHere are your cities' time and date to your setting:\n");

        for (City c : cities) {
            String label = c.getLabel();
            if (label.equals("")) {
                label = "* no label *";
            }
            ZoneId zone = ZoneId.of(c.fromUtcToZoneId());
            ZonedDateTime adjustedDateTime = customTime.withZoneSameInstant(zone);

            System.out.println("City #" + (cities.indexOf(c) + 1) + ": " + c.getCityName() + " (" + label + ") ");
            System.out.println("Custom Time: " + timeFormatter.format(adjustedDateTime) + "\n");
        }


    }

    // REQUIRES: year, month, day, hour, min must be valid date and time values
    //           and 0 =< index =< cityList.getListOfCities().size() - 1
    // EFFECTS: sets and returns custom time based on time inputs and selected city's UTC zone of reference
    private ZonedDateTime inputCustomTime() {
        //List<City> cities = this.cityList.getListOfCities();

        System.out.println("\nPlease enter your year YYYY:");
        int year = input.nextInt();
        System.out.println("\nPlease enter your month MM:");
        int month = input.nextInt();
        System.out.println("\nPlease enter your day DD:");
        int day = input.nextInt();
        System.out.println("\nPlease enter your hour HH (in 24-hour: e.g. 10PM -> 22):");
        int hour = input.nextInt();
        System.out.println("\nPlease enter your minute mm:");
        int min = input.nextInt();

        System.out.println("\nPlease enter the City # of the city for reference:");
        int index = input.nextInt() - 1;
        City refCity = this.cityList.get(index);
        ZoneId refZone = ZoneId.of(refCity.fromUtcToZoneId());

        return ZonedDateTime.of(year, month, day, hour, min, 0, 0, refZone);


    }

    // REQUIRES: input must be either 1, 2, 3, 4, or 5 (could add more options)
    // MODIFIES: this
    // EFFECTS: prints option for time and date format and calls setTimeFormatter to change format
    //          according to input
    private void selectTimeFormat() {
        System.out.println("Select the following Time/Date format:\n");
        System.out.println("\t1. Default (i.e. Apr 23, 2018 2:35:40 p.m.) \t\t\t\t\t-> Press 1");
        System.out.println("\t2. Long (i.e. Tuesday, April 23, 2018 2:35:40 p.m. UTC-04:00) \t-> Press 2");
        System.out.println("\t3. 24h (i.e. 2018-04-23 14:35:40) \t\t\t\t\t\t\t\t-> Press 3");
        System.out.println("\t4. AM/PM (i.e. 2018-04-23 2:35:40 p.m.) \t\t\t\t\t\t-> Press 4");
        System.out.println("\t5. UTC (i.e. 23.04.2018 14:35:40 UTC-04:00) \t\t\t\t\t-> Press 5");
        System.out.println("\nMore options coming soon!");

        setTimeFormatter(input.nextInt());
        System.out.println("\nTime format has been changed. Go back to main menu and select \"W\" to view the dates.");

    }


    // REQUIRES: input must be one of 1, 2, 3, 4, or 5 (could add more options)
    // MODIFIES: this
    // EFFECTS: Set TimeFormatter to the desired day and time pattern input
    public void setTimeFormatter(int input) {
        if (input == 1) {
            this.timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        } else if (input == 2) {
            this.timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
        } else if (input == 3) {
            this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        } else if (input == 4) {
            this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        } else if (input == 5) {
            this.timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss z");
        }
        // more to add in the future
    }


}
