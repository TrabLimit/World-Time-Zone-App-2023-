package ui.obsolete;

import model.City;
import model.CityList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


@SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // will delete later


// DISCLAIMER: this was a work-in-progress GUI that was abandoned due to buggy function.
// the Main method is located at the bottom of the code, which has been commented out to prevent it
// from running. If you wish to run the broken code, uncomment the Main method at the bottom.


// the GUI version of the application
public class TimeAppGUI extends JFrame {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private static final String JSON_STORE_PARENT = "./data/";

    private static final String JSON_STORE_1 = JSON_STORE_PARENT + "myCityList1.json";
    private static final String JSON_STORE_2 = JSON_STORE_PARENT + "myCityList2.json";
    private static final String JSON_STORE_3 = JSON_STORE_PARENT + "myCityList3.json";

    /*
    private JsonReader jsonReader1 = new JsonReader(JSON_STORE_1);
    private JsonWriter jsonWriter1 = new JsonWriter(JSON_STORE_1);
    private JsonReader jsonReader2 = new JsonReader(JSON_STORE_2);
    private JsonWriter jsonWriter2 = new JsonWriter(JSON_STORE_2);
    private JsonReader jsonReader3 = new JsonReader(JSON_STORE_3);
    private JsonWriter jsonWriter3 = new JsonWriter(JSON_STORE_3);
    */


    private DateTimeFormatter timeFormatter;

    private CityList cityList = new CityList();

    private DefaultListModel<City> cityDefaultListModel = new DefaultListModel<>();
    private JList<City> cities = new JList<>(cityDefaultListModel);

    private JPanel errorWarningFrame = new JPanel();

    private JFrame loadFrame = new JFrame("Load from...");
    private JFrame saveFrame = new JFrame("Save to...");

    private JFrame fileNotFoundFrame = new JFrame("Error");

    private JPanel cityListManagePanel = new JPanel();
    private JPanel cityManagePanel = new JPanel();
    private JPanel cityActionsPanel = new JPanel();


    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem loadMenuItem = new JMenuItem("Load");
    private JMenuItem saveMenuItem = new JMenuItem("Save");

    /*
    private JPanel lsPanel = new JPanel();
    private JLabel lsLabel = new JLabel();
    private String[] fileOptions = {"myCityList1.json", "myCityList2.json", "myCityList3.json"};
    private JComboBox chooseFile = new JComboBox(fileOptions);
    private JButton loadSaveOkButton = new JButton("Ok");
    */


    private JScrollPane cityListScroller = new JScrollPane(cities);

    private JLabel cityLabel = new JLabel("\n");
    private JLabel cityName = new JLabel("\n");
    private JLabel cityUTC = new JLabel("\n");
    private JLabel cityCoord = new JLabel("\n");
    private JLabel cityCurrentTime = new JLabel("\n");


    private CityListPanel cityListPanel;
    private JButton buttonAdd = new JButton("Add City");
    private JButton buttonRemove = new JButton("Remove");
    private JButton buttonModify = new JButton("Modify");
    private JButton buttonMoveUp = new JButton("Move Up");
    private JButton buttonMoveDown = new JButton("Move Down");
    private JButton buttonSearch = new JButton("Search City");
    private JButton buttonLoad = new JButton("Load CityList");
    private JButton buttonSave = new JButton("Save CityList");

    private JLabel label1 = new JLabel("Here is your list of Cities:");
    private JList<String> testCityListGUI = new JList<>();


    // EFFECTS: Constructs and sets main window for the World Time app
    public TimeAppGUI() {
        super("World Time Application");
        cityListInitialize();

        initMenuBar();
        initCityListManagePanel();

        //this.setJMenuBar(menuBar);
        this.add(cityListManagePanel);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.pack();
        this.setVisible(true);


    }

    // MODIFIES: this
    // EFFECTS: initialize menu bar with load and save option
    private void initMenuBar() {

        setUpFileItemsHotkeyPopupFrame(loadMenuItem, loadFrame, KeyEvent.VK_L, ActionEvent.CTRL_MASK);
        setUpFileItemsHotkeyPopupFrame(saveMenuItem, saveFrame, KeyEvent.VK_S, ActionEvent.CTRL_MASK);



        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

    }

    // MODIFIES: this, item, frame
    // EFFECTS: set up the menu items with given shortcuts and frames
    private void setUpFileItemsHotkeyPopupFrame(JMenuItem item, JFrame frame, int ke, int ae) {
        item.setMnemonic(ke);
        item.setAccelerator(KeyStroke.getKeyStroke(
                ke, ae));
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initLoadFrame();
                initSaveFrame();
                frame.setVisible(true);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: set up the load frame
    public void initLoadFrame() {
        loadSaveFrameSetUp(true);


    }

    // MODIFIES: this
    // EFFECTS: set up the save frame
    public void initSaveFrame() {
        loadSaveFrameSetUp(false);

    }


    // EFFECTS: set up load or save frame and confirm load/save action
    //         if isCommandLoad is true then we load
    //         otherwise we save

    private void loadSaveFrameSetUp(boolean isCommandLoad) {
        String prompt = "Save to...";
        JFrame frame = saveFrame;
        JsonReader reader;
        JsonWriter writer;
        if (isCommandLoad) {
            frame = loadFrame;
            prompt = "Load from...";
        }
        frame.setSize(WIDTH / 8, HEIGHT / 10);
        //lsLabel.setText(prompt);
        JPanel lsPanel = new JPanel();
        JLabel lsLabel = new JLabel(prompt);
        String[] fileOptions = {"myCityList1.json", "myCityList2.json", "myCityList3.json"};
        JComboBox chooseFile = new JComboBox(fileOptions);
        chooseFile.setSelectedIndex(0);
        JButton loadSaveOkButton = new JButton("Ok");
        lsPanel.setPreferredSize(new Dimension(WIDTH / 10, HEIGHT / 10));


        lsPanel.add(lsLabel);
        lsPanel.add(chooseFile);
        lsPanel.add(loadSaveOkButton);
        frame.add(lsPanel);

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSaveOkButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isCommandLoad) {
                            loadCityListFromFile(new JsonReader(JSON_STORE_PARENT + chooseFile.getSelectedItem()));
                            loadFrame.setVisible(false);
                        } else {
                            saveFrame.setVisible(false);
                        }

                    }
                });
            }
        });


    }

    // EFFECTS: returns the
    private void convertFileNametoReader(String filename) {

    }

    // MODIFIES: this
    // EFFECTS: loads citylist from given jsonReader file
    private void loadCityListFromFile(JsonReader jsonReader) {
        try {
            CityList loadedList = jsonReader.read();

            updateCityListGUI(loadedList);
            this.revalidate();


        } catch (IOException e) {

            JLabel fileNotFoundLabel = new JLabel("Sorry. File not found.");
            JButton fileNotFoundOkButton = new JButton("Ok");

            fileNotFoundFrame.add(fileNotFoundLabel);
            fileNotFoundFrame.add(fileNotFoundOkButton);
            fileNotFoundFrame.setVisible(true);
            fileNotFoundFrame.setSize(WIDTH / 10, HEIGHT / 10);

            fileNotFoundOkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileNotFoundFrame.setVisible(false);
                }
            });




        }
    }

    // MODIFIES: this
    // EFFECTS: update the list of cities in the GUI anytime change is made
    private void updateCityListGUI(CityList updatedList) {
        DefaultListModel<City> updatedCityDefaultListModel = new DefaultListModel<>();

        for (City c : updatedList.getListOfCities()) {
            updatedCityDefaultListModel.addElement(c);
        }

        JList<City> updatedCities = new JList<>(updatedCityDefaultListModel);
        JScrollPane updatedCityListScroller = new JScrollPane(updatedCities);

        this.cityList = updatedList;
        this.cityDefaultListModel = updatedCityDefaultListModel;
        this.cities = updatedCities;
        this.cityListScroller = updatedCityListScroller;


    }


    // MODIFIES: this
    // EFFECTS: adds 3 test cities to cityList for demonstration/test purposes and sets the dateFormatter to default
    public void cityListInitialize() {
        City testCity1 = new City("Home", "Vancouver", -7.0, 49.2827, -123.1207);
        City testCity2 = new City("School", "Montreal", -4.0, 45.5019, -73.5674);
        City testCity3 = new City("Family", "Seoul", +9.0, 37.5519, 126.9918);

        this.cityList.add(testCity1);
        this.cityList.add(testCity2);
        this.cityList.add(testCity3);

        timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    }

    // EFFECTS: getter for this.cityList
    public CityList getCityList() {
        return this.cityList;
    }

    // sets up our cityListManagePanel
    // EFFECTS: sets our cityListManagePanel as displaying CityList of GUI and gives options for adding, removing,
    //          modifying, (and maybe searching) cities
    public void initCityListManagePanel() {
        this.cityListManagePanel.setLayout(new FlowLayout());
        initializeCitiesFromCityList();
        setCityManagePanel();

        //JScrollPane listScroller = new JScrollPane(cities);
        cityListScroller.setPreferredSize(new Dimension(WIDTH / 6, HEIGHT / 8));

        this.cityListManagePanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 7));

        this.cityListManagePanel.add(buttonAdd);
        this.cityListManagePanel.add(cities);
        this.cityListManagePanel.add(cityManagePanel);



    }


    // EFFECTS: set the panel for managing the selected city
    //          if selected a city in the list, it will show the information of the city and option to either delete or
    //          modify the city
    private void setCityManagePanel() {

        this.cityManagePanel.setLayout(new BoxLayout(cityManagePanel, BoxLayout.Y_AXIS));

        obtainSelectedCityInfo();
        this.cityManagePanel.setPreferredSize(new Dimension(WIDTH / 6, HEIGHT / 8));
    }

    // EFFECTS: show the information of the city selected, and option to either delete or modify the city
    private void obtainSelectedCityInfo() {
        this.cities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                cityManagePanel.add(cityLabel);
                cityManagePanel.add(cityName);
                cityManagePanel.add(cityUTC);
                cityManagePanel.add(cityCoord);
                cityManagePanel.add(cityCurrentTime);
                cityManagePanel.add(cityActionsPanel);


                City selectedCity = cities.getSelectedValue();
                cityLabel.setText(selectedCity.getLabel());
                cityName.setText(selectedCity.getCityName());
                cityUTC.setText(selectedCity.fromUtcToZoneId());
                cityCoord.setText(coordToString(selectedCity));
                cityCurrentTime.setText(currentTimeToString(selectedCity));
                setUpCityActionsPanel(selectedCity);

            }
        });
    }

    // EFFECTS: display panel to remove, modify, shift up/down the selected city
    private void setUpCityActionsPanel(City city) {
        this.cityActionsPanel.setMaximumSize(new Dimension(WIDTH / 9, HEIGHT / 20));
        cityActionsPanel.setLayout(new GridLayout(2, 2));
        cityActionsPanel.add(buttonRemove);
        cityActionsPanel.add(buttonModify);
        cityActionsPanel.add(buttonMoveUp);
        cityActionsPanel.add(buttonMoveDown);

        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cityList.remove(city);
                cityDefaultListModel.removeElement(city);
                revalidate();
                repaint();
            }
        });

    }


    // EFFECTS: converts the current time of the city to string value
    private String currentTimeToString(City city) {
        ZoneId zone = ZoneId.of(city.fromUtcToZoneId());
        ZonedDateTime nowDateTime = ZonedDateTime.now(zone);

        return this.timeFormatter.format(nowDateTime);
    }


    // EFFECTS: converts the double coordinate to string value
    private String coordToString(City city) {
        String latString;
        String longString;
        if (city.getLatitude() < 0) {
            latString = "S " + (city.getLatitude() * -1);
        } else {
            latString = "N " + city.getLatitude();
        }

        if (city.getLongitude() < 0) {
            longString = "W " + (city.getLongitude() * -1);
        } else {
            longString = "E " + city.getLongitude();
        }

        return latString + " " + longString;
    }


    // MODIFIES: this
    // EFFECTS: take this.timeAppGUI.getCityList() and take each city and put them into labelsAndNames
    public void initializeCitiesFromCityList() {

        for (City c : this.cityList.getListOfCities()) {
            cityDefaultListModel.addElement(c);
        }

    }



    // uncomment main and run at your own risk

   /*
    // EFFECTS: runs the main GUI app
    public static void main(String[] args) {
        new TimeAppGUI();
    }

*/

}
