package ui;

import com.sun.jdi.request.DuplicateRequestException;
import model.City;
import model.CityList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.InputMismatchException;


//@SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // will delete later

// Background map is sourced from Wikipedia
// Link : https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/World_location_map_%28equirectangular_180%29.svg/2560px-World_location_map_%28equirectangular_180%29.svg.png

// All Image Icons are sourced from Icons8 and belong to their respective creators:
// Add: <a target="_blank" href="https://icons8.com/icon/102544/add">Add</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Modify: <a target="_blank" href="https://icons8.com/icon/12082/edit">Edit</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Remove: <a target="_blank" href="https://icons8.com/icon/11997/close">Delete</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Move Up: <a target="_blank" href="https://icons8.com/icon/12956/up">Up</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Move Down: <a target="_blank" href="https://icons8.com/icon/12067/down">Down</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Save: <a target="_blank" href="https://icons8.com/icon/18764/save">Save</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Load: <a target="_blank" href="https://icons8.com/icon/EgTUMY0lUCUE/load-from-file">Load From File</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Clear all: <a target="_blank" href="https://icons8.com/icon/11200/empty-trash">Empty Trash</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
//            <a target="_blank" href="https://icons8.com/icon/102550/remove">Remove</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
// Warning: <a target="_blank" href="https://icons8.com/icon/KarJz0n4bZSj/general-warning-sign">Warning</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>


// DISCLAIMER: Many methods and internal classes are made to comply the CPSC 210 CheckStyle.
// If you want to view the original code without splitting into many different methods or types,
// please check the TimeAppGUI2 class.

// the GUI version of the application (properly edited to adhere to checkstyle rules)
public class RefactoredTimeAppGUI2 extends JFrame implements CityListAppWithSaveLoad {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    private static final String[] HOUR_CHOICES = new String[]{"-12", "-11", "-10", "-09", "-08",
            "-07", "-06", "-05", "-04", "-03", "-02", "-01",
            "+00", "+01", "+02", "+03", "+04", "+05", "+06", "+07",
            "+08", "+09", "+10", "+11", "+12", "+13", "+14"};
    private static final String[] MINUTE_CHOICES = new String[]{"00", "15", "30", "45"};

    private JPanel cityInfoPanel = new JPanel();
    // private final JButton testButton = new JButton("Test");
    // private final JButton addButtonBasic = new JButton("Add city");
    // private final JButton loadButton1 = new JButton("Load File 1");
    //private final JButton saveButton1 = new JButton("Save to File 1");

    private CityIconPanel citiesIconsPanel;
    private JPanel cityPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JPanel mainPanel = new JPanel();

    private JFrame errorWarningFrame = new JFrame();

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private CityList cityList = new CityList();

    private EventLog eventLog = EventLog.getInstance();


    private DefaultListModel<City> cityDefaultListModel = new DefaultListModel<>();
    private JList<City> cities = new JList<>(cityDefaultListModel);
    private JScrollPane citiesScrollable = new JScrollPane(this.cities);
    //private JScrollPane citiesScrollable = new JScrollPane(cities); // cannot get it to work

    private static final String JSON_STORE_PARENT = "./data/";

    //private static final String JSON_STORE_1 = JSON_STORE_PARENT + "myCityList1.json";
    // uncomment this for loadButton1 and saveButton1

    // private static final String JSON_STORE_2 = JSON_STORE_PARENT + "myCityList2.json";
    // private static final String JSON_STORE_3 = JSON_STORE_PARENT + "myCityList3.json";


    private ImageIcon backgroundMap = new ImageIcon(JSON_STORE_PARENT
            + "World_location_map_(equirectangular_180).svg.png");
    private ImageIcon backgroundMapSmaller = new ImageIcon(backgroundMap.getImage().getScaledInstance(
            WIDTH / 2, WIDTH / 4, Image.SCALE_SMOOTH));

    private ImageIcon addIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-add-48.png").getImage().getScaledInstance(WIDTH / 120, WIDTH / 120, Image.SCALE_DEFAULT));

    private ImageIcon removeIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-delete-48.png").getImage().getScaledInstance(WIDTH / 100, WIDTH / 100, Image.SCALE_DEFAULT));
    private ImageIcon downIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-down-48.png").getImage().getScaledInstance(WIDTH / 100, WIDTH / 100, Image.SCALE_DEFAULT));
    private ImageIcon modifyIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-edit-48.png").getImage().getScaledInstance(WIDTH / 100, WIDTH / 100, Image.SCALE_DEFAULT));
    private ImageIcon clearAllIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-remove-48.png").getImage().getScaledInstance(WIDTH / 100, WIDTH / 100, Image.SCALE_DEFAULT));

    private ImageIcon loadIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-load-from-file-48.png").getImage().getScaledInstance(WIDTH / 120,
            WIDTH / 120, Image.SCALE_DEFAULT));
    private ImageIcon saveIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-save-50.png").getImage().getScaledInstance(WIDTH / 120, WIDTH / 120, Image.SCALE_DEFAULT));
    private ImageIcon upIcon = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-up-48.png").getImage().getScaledInstance(WIDTH / 100, WIDTH / 100, Image.SCALE_DEFAULT));
    // private ImageIcon warningIconMidSize = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
    //         + "icons8-warning-48.png").getImage().getScaledInstance(WIDTH / 50, WIDTH / 50, Image.SCALE_DEFAULT));

    // private final JButton modifyButtonBasic = new JButton("Modify city");
    private JButton addButton = new JButton("Add city", addIcon);
    private JButton clearAllButton = new JButton("Clear All Cities", clearAllIcon);
    private JButton modifyButton = new JButton("Modify city", modifyIcon);
    private JButton moveUpButton = new JButton("Move Up", upIcon);
    private JButton moveDownButton = new JButton("Move Down", downIcon);
    private JButton removeButton = new JButton("Remove city", removeIcon);


    // private JsonReader jsonReader1 = new JsonReader(JSON_STORE_1); // uncomment this for loadButton1
    // private JsonWriter jsonWriter1 = new JsonWriter(JSON_STORE_1); // uncomment this for saveButton1

    /*
    private JsonReader jsonReader2 = new JsonReader(JSON_STORE_2);
    private JsonWriter jsonWriter2 = new JsonWriter(JSON_STORE_2);
    private JsonReader jsonReader3 = new JsonReader(JSON_STORE_3);
    private JsonWriter jsonWriter3 = new JsonWriter(JSON_STORE_3);
 */


    // EFFECTS: runs the main GUI app
    public static void main(String[] args) {
        new RefactoredTimeAppGUI2();
    }


    // MODIFIES: this
    // EFFECTS: Constructs and sets main window for the World Time app with default cities

    public RefactoredTimeAppGUI2() {
        super("World Time Application");
        cityListInitialize();
        initializeCitiesFromCityList();
        initMenuBar();

        errorWarningFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.citiesIconsPanel = new CityIconPanel(this.cityList, backgroundMapSmaller);

        //JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        Dimension mainPanelDimension = new Dimension(WIDTH - 700, HEIGHT / 2 - 50);
        mainPanel.setPreferredSize(mainPanelDimension);


        add(mainPanel);
        Container mainFrameContentPane = this.getContentPane();
        mainFrameContentPane.setLayout(new FlowLayout());


        this.setJMenuBar(menuBar);
        add(cityPanel);
        add(citiesIconsPanel);
        mainPanel.add(cityPanel);
        mainPanel.add(citiesIconsPanel);









           /*
        JPanel mapPanel = new JPanel();
        Dimension mapPanelDimension = new Dimension(WIDTH / 2, HEIGHT / 2);
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        mapPanel.setPreferredSize(mapPanelDimension);
        mapPanel.setMinimumSize(mapPanelDimension);
        mapPanel.setMaximumSize(mapPanelDimension);

        JLabel worldMap = new JLabel(backgroundMapSmaller);
        */


        setUpCityPanel();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.pack();
        this.setVisible(true);
        printLogWhenClosed();


    }


    // EFFECTS: when this is closed, print out all the event logs
    private void printLogWhenClosed() {
        // eventLog = EventLog.getInstance();
        this.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e : window event
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    for (Event next : eventLog) {
                        System.out.println(next.getDescription() + "\n");
                    }
                    System.out.println("\nApp closed successfully.");
                } catch (Exception ee) {
                    System.out.println("An error occured while printing Event Logs.");
                }
            }
        });

    }

    // MODIFIES:  this
    // EFFECTS: sets up CityPanel and its components
    private void setUpCityPanel() {

        initCityPanel();


       /*
       testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame info = new JFrame("Info");
                JPanel panel = new JPanel();
                JLabel label = new JLabel("I have been clicked");
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                info.setSize(100, 100);
                panel.setSize(100, 100);

                panel.add(label);
                info.add(panel);
                info.setVisible(true);
            }
        });
        */

/*

        addButtonBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                City testCity = new City("label", "name", +0, 0, 0);
                cityList.addCity(testCity);

                cityDefaultListModel.removeAllElements();
                initializeCitiesFromCityList();

                // cityDefaultListModel.addElement(testCity);


                citiesIconsPanel.invalidate();
                citiesIconsPanel.revalidate();
                citiesIconsPanel.repaint();

            }
        });
*/

        AddCityFrame addCityCommandFrame = getAddCityFrame();


        ModifyCityFrame modifyCityFrameCommand = getModifyCityFrame();


        addCityPanelButtonsActionListeners(cityInfoPanel, addCityCommandFrame.addCityDialog,
                addCityCommandFrame.addCityFrame,
                addCityCommandFrame.addLabelTextField, addCityCommandFrame.addNameTextField,
                addCityCommandFrame.addHoursBox, addCityCommandFrame.addMinutesBox, addCityCommandFrame.addNorth,
                addCityCommandFrame.addLatDirectionGroup, addCityCommandFrame.addLatitudeDegField,
                addCityCommandFrame.addEast, addCityCommandFrame.addLongDirectionGroup,
                addCityCommandFrame.addLongitudeDegField, modifyCityFrameCommand.modCityFrame,
                modifyCityFrameCommand.modCityDialog, modifyCityFrameCommand.modLabelTextField,
                modifyCityFrameCommand.modNameTextField, modifyCityFrameCommand.modHoursBox,
                modifyCityFrameCommand.modMinutesBox, modifyCityFrameCommand.modNorth,
                modifyCityFrameCommand.modSouth, modifyCityFrameCommand.modLatDirGroup,
                modifyCityFrameCommand.modLatDegField, modifyCityFrameCommand.modEast,
                modifyCityFrameCommand.modWest, modifyCityFrameCommand.modLongDirGroup,
                modifyCityFrameCommand.modLongDegField);
    }

    // MODIFIES: this
    // EFFECTS: adds all necessary buttons, scrollable list of Cities, cityInfoPanel to CityPanel
    private void initCityPanel() {
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        Dimension testPanelDimension = new Dimension(WIDTH / 8, HEIGHT / 2 - HEIGHT / 10);
        cityPanel.setPreferredSize(testPanelDimension);


        Dimension citiesScrollableDimension = new Dimension(WIDTH / 5, HEIGHT / 10);
        citiesScrollable.setMaximumSize(citiesScrollableDimension);
        citiesScrollable.setPreferredSize(citiesScrollableDimension);


        //mainPanel.add(cityPanel);
        cityPanel.add(cityInfoPanel);
        //mainPanel.add(mapPanel);
        // mapPanel.add(worldMap);


        //cityPanel.add(addButtonBasic);
        cityPanel.add(addButton);

        cityPanel.add(citiesScrollable);
        //cityPanel.add(cities);
        // cityPanel.add(testButton);


        cityPanel.add(moveUpButton);
        cityPanel.add(moveDownButton);

        //cityPanel.add(modifyButtonBasic);
        cityPanel.add(modifyButton);


        cityPanel.add(removeButton);

        // cityPanel.add(loadButton1);
        // cityPanel.add(saveButton1);

        cityPanel.add(clearAllButton);


        initCityInfoPanel();
    }

    // EFFECTS: instantiates and returns a Modify City Frame for use in GUI
    private ModifyCityFrame getModifyCityFrame() {
        JFrame modCityFrame = new JFrame("Modify City");
        JDialog modCityDialog = new JDialog(modCityFrame, "Modify City", true);


        Container modifyCityContentPane = getCityContentPane(modCityDialog);

        //subFrameOnMainFrameOff(modCityFrame);
        initCityBuildPrompts(modifyCityContentPane);


        JTextField modLabelTextField = getLabelNameJTextFieldFromContentPane(10, modifyCityContentPane);


        JTextField modNameTextField = getLabelNameJTextFieldFromContentPane(40, modifyCityContentPane);


        JComboBox modHoursBox = getHoursMinutesBoxFromContentPane(HOUR_CHOICES, 150, modifyCityContentPane);


        JComboBox modMinutesBox = getHoursMinutesBoxFromContentPane(MINUTE_CHOICES, 210, modifyCityContentPane);


        JRadioButton modNorth = getCoordDirectionButtonFromContentPane("N", 150, 100, modifyCityContentPane);


        JRadioButton modSouth = getCoordDirectionButtonFromContentPane("S", 200, 100, modifyCityContentPane);


        ButtonGroup modLatDirGroup = getButtonGroup(modNorth, modSouth);


        JFormattedTextField modLatDegField = getCoordJTextFieldFromContentPane(100, modifyCityContentPane);


        JRadioButton modifyEast = getCoordDirectionButtonFromContentPane("E", 150, 130, modifyCityContentPane);


        JRadioButton modWest = getCoordDirectionButtonFromContentPane("W", 200, 130, modifyCityContentPane);


        ButtonGroup modLongDirGroup = getButtonGroup(modifyEast, modWest);


        JFormattedTextField modLongDegField = getCoordJTextFieldFromContentPane(130, modifyCityContentPane);


        JButton modCityOkButton = getOkButtonCityContentPane(modifyCityContentPane);


        addModifyCityOkButtonListener(modCityDialog, modCityFrame, modLabelTextField, modNameTextField,
                modHoursBox, modMinutesBox, modSouth, modLatDegField, modWest, modLongDegField, modCityOkButton);
        ModifyCityFrame modifyCityFrameCommand = new ModifyCityFrame(modCityDialog, modCityFrame,
                modLabelTextField, modNameTextField, modHoursBox, modMinutesBox, modNorth, modSouth,
                modLatDirGroup, modLatDegField, modifyEast, modWest, modLongDirGroup, modLongDegField);
        return modifyCityFrameCommand;
    }

    // private internal class for Modify City Frame
    private class ModifyCityFrame {
        private final JFrame modCityFrame;
        private final JDialog modCityDialog;
        private final JTextField modLabelTextField;
        private final JTextField modNameTextField;
        private final JComboBox modHoursBox;
        private final JComboBox modMinutesBox;
        private final JRadioButton modNorth;
        private final JRadioButton modSouth;
        private final ButtonGroup modLatDirGroup;
        private final JFormattedTextField modLatDegField;
        private final JRadioButton modEast;
        private final JRadioButton modWest;
        private final ButtonGroup modLongDirGroup;
        private final JFormattedTextField modLongDegField;

        // REQUIRES: none of the arguments should be null
        // EFFECTS: constructs the Add City Frame with all the necessary components to fill out info to modify City

        private ModifyCityFrame(JDialog modCityDialog,
                                JFrame modCityFrame, JTextField modLabelTextField, JTextField modNameTextField,
                                JComboBox modHoursBox, JComboBox modMinutesBox, JRadioButton modNorth,
                                JRadioButton modSouth, ButtonGroup modLatDirGroup,
                                JFormattedTextField modLatDegField, JRadioButton modEast, JRadioButton modWest,
                                ButtonGroup modLongDirGroup, JFormattedTextField modifyLongitudeDegField) {
            this.modCityFrame = modCityFrame;
            this.modCityDialog = modCityDialog;
            this.modLabelTextField = modLabelTextField;
            this.modNameTextField = modNameTextField;
            this.modHoursBox = modHoursBox;
            this.modMinutesBox = modMinutesBox;
            this.modNorth = modNorth;
            this.modSouth = modSouth;
            this.modLatDirGroup = modLatDirGroup;
            this.modLatDegField = modLatDegField;
            this.modEast = modEast;
            this.modWest = modWest;
            this.modLongDirGroup = modLongDirGroup;
            this.modLongDegField = modifyLongitudeDegField;
        }
    }

    // EFFECTS: instantiates and returns a Modify City Frame for use in GUI
    private AddCityFrame getAddCityFrame() {
        JFrame addCityFrame = new JFrame("Add City");
        JDialog addCityDialog = new JDialog(addCityFrame, "Add City", true);


        Container addCityContentPane = getCityContentPane(addCityDialog);

      //  subFrameOnMainFrameOff(addCityFrame);


        initCityBuildPrompts(addCityContentPane);


        JTextField addLabelTextField = getLabelNameJTextFieldFromContentPane(10, addCityContentPane);


        JTextField addNameTextField = getLabelNameJTextFieldFromContentPane(40, addCityContentPane);


        JComboBox addHoursBox = getHoursMinutesBoxFromContentPane(HOUR_CHOICES, 150, addCityContentPane);


        JComboBox addMinutesBox = getHoursMinutesBoxFromContentPane(MINUTE_CHOICES, 210, addCityContentPane);


        JRadioButton addNorth = getCoordDirectionButtonFromContentPane("N", 150, 100, addCityContentPane);


        JRadioButton addSouth = getCoordDirectionButtonFromContentPane("S", 200, 100, addCityContentPane);


        ButtonGroup addLatDirGroup = getButtonGroup(addNorth, addSouth);


        JFormattedTextField addLatDegField = getCoordJTextFieldFromContentPane(100, addCityContentPane);


        JRadioButton addEast = getCoordDirectionButtonFromContentPane("E", 150, 130, addCityContentPane);


        JRadioButton addWest = getCoordDirectionButtonFromContentPane("W", 200, 130, addCityContentPane);


        ButtonGroup addLongDirGroup = getButtonGroup(addEast, addWest);


        JFormattedTextField addLongDegField = getCoordJTextFieldFromContentPane(130, addCityContentPane);


        JButton addCityOkButton = getOkButtonCityContentPane(addCityContentPane);

        addAddCityOkButtonListener(addCityDialog, addCityFrame, addLabelTextField, addNameTextField, addHoursBox,
                addMinutesBox, addSouth, addLatDegField, addWest, addLongDegField, addCityOkButton);
        AddCityFrame addCityCommandFrame = new AddCityFrame(addCityDialog, addCityFrame, addLabelTextField,
                addNameTextField, addHoursBox, addMinutesBox, addNorth, addLatDirGroup, addLatDegField, addEast,
                addLongDirGroup, addLongDegField);
        return addCityCommandFrame;
    }


    // REQUIRES: cityFormFrame is not null
    // MODIFIES: cityFormFrame
    // EFFECTS: sets up given cityFormFrame's Content Pane so components can be added
    private Container getCityContentPane(JDialog cityFormFrame) {
        cityFormFrame.setSize(WIDTH / 6, HEIGHT / 4);
        cityFormFrame.setLocationRelativeTo(null);
        Container cityContentPane = cityFormFrame.getContentPane();
        cityContentPane.setLayout(null);
        cityFormFrame.setVisible(false);
        return cityContentPane;
    }


    // private internal class for Add City Frame
    private class AddCityFrame {
        private final JFrame addCityFrame;
        private final JDialog addCityDialog;
        private final JTextField addLabelTextField;
        private final JTextField addNameTextField;
        private final JComboBox addHoursBox;
        private final JComboBox addMinutesBox;
        private final JRadioButton addNorth;
        private final ButtonGroup addLatDirectionGroup;
        private final JFormattedTextField addLatitudeDegField;
        private final JRadioButton addEast;
        private final ButtonGroup addLongDirectionGroup;
        private final JFormattedTextField addLongitudeDegField;


        // REQUIRES: none of the arguments should be null
        // EFFECTS: constructs the Add City Frame with all the necessary components to fill out info for City

        private AddCityFrame(JDialog addCityDialog, JFrame addCityFrame, JTextField addLabelTextField,
                             JTextField addNameTextField,
                             JComboBox addHoursBox, JComboBox addMinutesBox, JRadioButton addNorth,
                             ButtonGroup addLatDirectionGroup, JFormattedTextField addLatitudeDegField,
                             JRadioButton addEast, ButtonGroup addLongDirectionGroup,
                             JFormattedTextField addLongitudeDegField) {
            this.addCityFrame = addCityFrame;
            this.addCityDialog = addCityDialog;
            this.addLabelTextField = addLabelTextField;
            this.addNameTextField = addNameTextField;
            this.addHoursBox = addHoursBox;
            this.addMinutesBox = addMinutesBox;
            this.addNorth = addNorth;
            this.addLatDirectionGroup = addLatDirectionGroup;
            this.addLatitudeDegField = addLatitudeDegField;
            this.addEast = addEast;
            this.addLongDirectionGroup = addLongDirectionGroup;
            this.addLongitudeDegField = addLongitudeDegField;
        }
    }


    // REQUIRES: all arguments must not be null
    // MODIFIES: this
    // EFFECTS: add and organize action listeners for all buttons within cityPanel
    private void addCityPanelButtonsActionListeners(JPanel cityInfoPanel, JDialog addCityDialog,
                                                    JFrame addCityFrame, JTextField addLabelTextField,
                                                    JTextField addNameTextField, JComboBox addHoursBox,
                                                    JComboBox addMinutesBox, JRadioButton addNorth,
                                                    ButtonGroup addLatDirectionGroup,
                                                    JFormattedTextField addLatitudeDegField, JRadioButton addEast,
                                                    ButtonGroup addLongDirectionGroup,
                                                    JFormattedTextField addLongitudeDegField, JFrame modCityFrame,
                                                    JDialog modCityDialog,
                                                    JTextField modLabelTextField, JTextField modNameTextField,
                                                    JComboBox modHoursBox, JComboBox modMinutesBox,
                                                    JRadioButton modNorth, JRadioButton modSouth,
                                                    ButtonGroup modLatDirectionGroup,
                                                    JFormattedTextField modLatDegField, JRadioButton modEast,
                                                    JRadioButton modWest, ButtonGroup modLongDirectionGroup,
                                                    JFormattedTextField modLongDegField) {
        addAddButtonActionListener(addCityDialog, addCityFrame, addLabelTextField, addNameTextField, addHoursBox,
                addMinutesBox, addNorth, addLatDirectionGroup, addLatitudeDegField, addEast, addLongDirectionGroup,
                addLongitudeDegField);


        /*
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCityFrame.setEnabled(true);
                addLabelTextField.setText("");
                addNameTextField.setText(null);
                addHoursBox.setSelectedItem("+00");
                addMinutesBox.setSelectedItem("00");
                addLatDirectionGroup.setSelected(addNorth.getModel(), true);
                addLatitudeDegField.setText("0.0000");
                addLongDirectionGroup.setSelected(addEast.getModel(), true);
                addLongitudeDegField.setText("0.0000");
                addCityFrame.setVisible(true);


            }
        });

         */


        addRemoveButtonActionListener(cityInfoPanel);

        addClearAllButtonActionListener();

        addModifyButtonActionListener(modCityDialog, modCityFrame, modLabelTextField, modNameTextField, modHoursBox,
                modMinutesBox, modNorth, modSouth, modLatDirectionGroup, modLatDegField, modEast, modWest,
                modLongDirectionGroup, modLongDegField);
/*

        modifyButtonBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    City testCity = new City("label", "name", +0, 0, 0);

                    int modifyIndex = cities.getSelectedIndex();

                    cityList.set(modifyIndex, testCity);

                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();
                    cities.setSelectedIndex(modifyIndex);


                    // cityDefaultListModel.addElement(testCity);
                } catch (IndexOutOfBoundsException ee) {
                    //
                } finally {
                    citiesIconsPanel.invalidate();
                    citiesIconsPanel.revalidate();
                    citiesIconsPanel.repaint();
                }
            }
        });
*/

        addMoveUpDownButtonActionListener(moveUpButton, -1);

        addMoveUpDownButtonActionListener(moveDownButton, +1);

/*
        loadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCityListFromFile(jsonReader1);

                citiesIconsPanel.invalidate();
                citiesIconsPanel.revalidate();
                citiesIconsPanel.repaint();

            }
        });


        saveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCityListToFile(jsonWriter1);

            }
        });
*/
    }


    // REQUIRES: all arguments must not be null
    // MODIFIES: this, modifyCityFrame
    // EFFECTS: add an actionlistener to the Modify City Frame's Ok button
    //          closes the frame and checks if the inputs are valid. If so then update the city in CityList
    //          according to the inputs.
    //          If any error is found, then it will produce error message and make no changes to this
    private void addModifyCityOkButtonListener(JDialog modifyCityDialog, JFrame modifyCityFrame,
                                               JTextField modifyLabelTextField,
                                               JTextField modifyNameTextField, JComboBox modifyHoursBox,
                                               JComboBox modifyMinutesBox, JRadioButton modifySouth,
                                               JFormattedTextField modifyLatDegField, JRadioButton modifyWest,
                                               JFormattedTextField modifyLongDegField,
                                               JButton modifyCityOkButton) {
        modifyCityOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkForValidNameInput(modifyNameTextField);

                    int modifyIndex = cities.getSelectedIndex();

                    City changedCity = generateNewCityFromInputs(modifyHoursBox, modifyMinutesBox, modifyLatDegField,
                            modifySouth, modifyLongDegField, modifyWest,modifyLabelTextField, modifyNameTextField);
                   /*
                    double newOffsetHourUTC = Double.parseDouble(modifyHoursBox.getSelectedItem().toString());
                    double newOffsetMinuteUTC = Double.parseDouble(modifyMinutesBox.getSelectedItem().toString()) / 60;
                    double newOffsetOverallUTC;
                    if (newOffsetHourUTC < 0) {
                        newOffsetOverallUTC = newOffsetHourUTC - newOffsetMinuteUTC;
                    } else {
                        newOffsetOverallUTC = newOffsetHourUTC + newOffsetMinuteUTC;
                    }

                    double newLatitude = checkAndSetLatLong(modifyLatDegField, 90, modifySouth);

                    double newLongitude = checkAndSetLatLong(modifyLongDegField, 180, modifyWest);



                    City changedCity = new City(modifyLabelTextField.getText(), modifyNameTextField.getText(),
                            newOffsetOverallUTC, newLatitude, newLongitude);
                            */

                    checkAndReplaceCity(modifyIndex, changedCity);

                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();

                    cities.setSelectedIndex(modifyIndex);


                    reloadGUI();


                } catch (Exception eee) {
                    //
                    JOptionPane.showMessageDialog(errorWarningFrame,
                            "Invalid input, duplicate City, or no actual change detected.",
                            "Warning: modify City", JOptionPane.WARNING_MESSAGE);

                } finally {
                   /*
                   addLabelTextField.setText("");
                    addNameTextField.setText(null);
                    addHoursBox.setSelectedItem("00");
                    addMinutesBox.setSelectedItem("00");
                    addLatDirectionGroup.setSelected(addNorth.getModel(), true);
                    addLatitudeDegField.setText("0.0000");
                    addLongDirectionGroup.setSelected(addEast.getModel(), true);
                    addLongitudeDegField.setText("0.0000");
                    */


                    //frameOpenOrClose(modifyCityFrame, false);
                    modifyCityFrame.setEnabled(false);
                    modifyCityDialog.setVisible(false);


                }
            }
        });
    }

    // REQUIRES: nameTextField is not null
    // EFFECTS: checks if the input in nameTextField is valid
    //          if there is no input, throw NullPointerException since a city must have a name
    private static void checkForValidNameInput(JTextField nameTextField)
            throws NullPointerException {
        if (nameTextField.getText().equals("")) {
            throw new NullPointerException();
        }
    }

    // REQUIRES: modifyIndex must be within index bounds of the this.cityList
    //          i.e. 0 <= modifyIndex <= this.cityList.numCities() - 1
    //          changedCity must not be null
    // MODIFIES: this
    // EFFECTS: checks if changedCity is already in cityList
    //          if so, then throw DuplicateRequestException
    //          otherwise, replace the city with modifyIndex inside cityList with changedCity

    private void checkAndReplaceCity(int modifyIndex, City changedCity)
            throws DuplicateRequestException {
        if (cityList.doesIdenticalCityAlreadyExist(changedCity)) {
            throw new DuplicateRequestException();
        }

        cityList.set(modifyIndex, changedCity);
    }


    // MODIFIES: this
    // EFFECTS: add an actionlistener for clearAllButton so that when clicked, the warning dialog will
    //          be given. If the user chooses yes, the remove all cities in cityList.
    //          If user says either no or closes the dialog, then nothing happens.
    //          NullPointerException is added in case that there is no city selected, then the button will
    //          not do anything
    private void addClearAllButtonActionListener() {
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(errorWarningFrame,
                            "Clear all cities? This cannot be undone.",
                            "Clear All Warning", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cityInfoPanel.removeAll();
                        cityInfoPanel.revalidate();
                        cityInfoPanel.repaint();

                        cityList.clear();

                        cityDefaultListModel.clear();

                        //cities.removeSelectionInterval(0, cityList.size() - 1);
                        cities.clearSelection();

                        enableOrDisableIndividualCityCommands(false);
                    }


                } catch (NullPointerException ee) {
                    //
                } finally {

                    refreshCitiesIconsPanel();
                }


            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add an actionlistener for removeButton so that when clicked, the warning dialog will
    //          be given. If the user chooses yes, the remove the selected city will be deleted from cityList
    //          If user says either no or closes the dialog, then nothing happens.
    //          NullPointerException is added in case that there is no city selected, then the button will
    //          not do anything
    private void addRemoveButtonActionListener(JPanel cityInfoPanel) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(errorWarningFrame,
                            "Remove selected city? This cannot be undone.",
                            "Remove Warning", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cityInfoPanel.removeAll();
                        cityInfoPanel.revalidate();
                        cityInfoPanel.repaint();

                        cityList.remove(cities.getSelectedValue());

                        cityDefaultListModel.removeElement(cities.getSelectedValue());

                        //cities.removeSelectionInterval(0, cityList.size() - 1);
                        cities.clearSelection();

                        enableOrDisableIndividualCityCommands(false);
                    }


                } catch (NullPointerException ee) {
                    //
                } finally {

                    refreshCitiesIconsPanel();
                }


            }
        });
    }


    // MODIFIES: this
    // EFFECTS: revalidate and repaint this.citiesIconsPanel
    //          basically refresh button for our viewable global map
    private void refreshCitiesIconsPanel() {
        citiesIconsPanel.invalidate();
        citiesIconsPanel.revalidate();
        citiesIconsPanel.repaint();
    }

    // REQUIRES: none of the arguments should be null, and cities (JList of cityList) must have a city selected.
    // MODIFIES: this, all the arguments including modifyCityFrame and all the components within modifyCityFrame
    // EFFECTS: adds an action listener so that when modifyButton is clicked, it initializes modifyCityFrame
    //          by filling in the information of the city that has been selected, and then opens the frame
    //          so that users can edit information to modify the selected city
    private void addModifyButtonActionListener(JDialog modifyCityDialog, JFrame modifyCityFrame,
                                               JTextField modifyLabelTextField,
                                               JTextField modifyNameTextField, JComboBox modifyHoursBox,
                                               JComboBox modifyMinutesBox, JRadioButton modifyNorth,
                                               JRadioButton modifySouth, ButtonGroup modifyLatDirectionGroup,
                                               JFormattedTextField modifyLatitudeDegField, JRadioButton modifyEast,
                                               JRadioButton modifyWest, ButtonGroup modifyLongDirectionGroup,
                                               JFormattedTextField modifyLongitudeDegField) {
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modifyCityFrame.setEnabled(true);

                    int modifyIndex = cities.getSelectedIndex();

                    City selectedCity = cities.getSelectedValue();
                    fillInContentPaneWithCityInfo(selectedCity, modifyLabelTextField, modifyNameTextField,
                            modifyHoursBox, modifyMinutesBox, modifyLatDirectionGroup,
                            modifySouth, modifyNorth, modifyLatitudeDegField, modifyLongDirectionGroup,
                            modifyWest, modifyEast, modifyLongitudeDegField);
                    //modifyCityFrame.setVisible(true);
                    modifyCityDialog.setVisible(true);


                    // cityDefaultListModel.addElement(testCity);
                } catch (Exception ee) {
                    //
                } finally {
                    refreshCitiesIconsPanel();
                }
            }
        });
    }


    // REQUIRES: all arguments must not be null
    // MODIFIES: this, addCityFrame
    // EFFECTS: add an actionlistener to the Add City Frame's Ok button
    //          closes the frame and checks if the inputs are valid. If so then create a new City in CityList
    //          based on the inputs.
    //          If any error is found, then it will produce error message and will not add the city.
    private void addAddCityOkButtonListener(JDialog addCityDialog, JFrame addCityFrame, JTextField addLabelTextField,
                                            JTextField addNameTextField, JComboBox addHoursBox,
                                            JComboBox addMinutesBox, JRadioButton addSouth,
                                            JFormattedTextField addLatitudeDegField, JRadioButton addWest,
                                            JFormattedTextField addLongitudeDegField, JButton addCityOkButton) {
        addCityOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkForValidNameInput(addNameTextField);

                    City newCity = generateNewCityFromInputs(addHoursBox, addMinutesBox, addLatitudeDegField,
                            addSouth, addLongitudeDegField, addWest, addLabelTextField, addNameTextField);

                    cityList.add(newCity);

                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();

                    cities.setSelectedIndex(cityList.size() - 1); // select the newly added city


                    reloadGUI();


                } catch (Exception eee) {
                    //
                    JOptionPane.showMessageDialog(errorWarningFrame, "Invalid input or duplicate City detected.",
                            "Warning: add City", JOptionPane.WARNING_MESSAGE);

                } finally {
                   /*
                   addLabelTextField.setText("");
                    addNameTextField.setText(null);
                    addHoursBox.setSelectedItem("+00");
                    addMinutesBox.setSelectedItem("00");
                    addLatDirectionGroup.setSelected(addNorth.getModel(), true);
                    addLatitudeDegField.setText("0.0000");
                    addLongDirectionGroup.setSelected(addEast.getModel(), true);
                    addLongitudeDegField.setText("0.0000");
                    */


                    //frameOpenOrClose(addCityFrame, false);
                    addCityFrame.setEnabled(false);
                    addCityDialog.setVisible(false);
                }
            }
        });
    }

    // REQUIRES: none of the arguments should be null, and cities (JList of cityList) must have a city selected.
    // MODIFIES: this, all the arguments including addCityFrame and all the components within addCityFrame
    // EFFECTS: adds an action listener so that when addButton is clicked, it initializes addCityFrame
    //          by filling in the default form information for a city (except keep name and label textfield empty)
    //          and then opens the frame so that users can edit information to generate a new City to be added
    private void addAddButtonActionListener(JDialog addCityDialog, JFrame addCityFrame,
                                            JTextField addLabelTextField, JTextField addNameTextField,
                                            JComboBox addHoursBox, JComboBox addMinutesBox,
                                            JRadioButton addNorth, ButtonGroup addLatDirectionGroup,
                                            JFormattedTextField addLatitudeDegField, JRadioButton addEast,
                                            ButtonGroup addLongDirectionGroup,
                                            JFormattedTextField addLongitudeDegField) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addCityFrame.setEnabled(true);

                addLabelTextField.setText("");
                addNameTextField.setText(null);
                addHoursBox.setSelectedItem("+00");
                addMinutesBox.setSelectedItem("00");
                addLatDirectionGroup.setSelected(addNorth.getModel(), true);
                addLatitudeDegField.setText("0.0000");
                addLongDirectionGroup.setSelected(addEast.getModel(), true);
                addLongitudeDegField.setText("0.0000");
                //addCityFrame.setVisible(true);

                addCityDialog.setVisible(true);


            }
        });
    }


    // REQUIRES: all arguments must not be null
    // MODIFIES: modifyCityFrame and every argument except selectedCity
    //           (basically all components within modifyCityFrame)
    // EFFECTS: plug in all the information of selectedCity into the given components (i.e. all arguments except
    // selectedCity) of modifyCityFrame
    private void fillInContentPaneWithCityInfo(City selectedCity, JTextField modLabelTextField,
                                               JTextField modNameTextField, JComboBox modHoursBox,
                                               JComboBox modMinutesBox, ButtonGroup modLatDirectionGroup,
                                               JRadioButton modSouth, JRadioButton modNorth,
                                               JFormattedTextField modLatDegField,
                                               ButtonGroup modLongDirectionGroup,
                                               JRadioButton modWest, JRadioButton modEast,
                                               JFormattedTextField modLongDegField) {
        String selectedCityUtcID = selectedCity.fromUtcToZoneId();
        String selectedCityHour = selectedCityUtcID.substring(3, 6);
        String selectedCityMinute = selectedCityUtcID.substring(7, 9);
        String selectedCityLatitude = Double.toString(Math.abs(selectedCity.getLatitude()));
        String selectedCityLongitude = Double.toString(Math.abs(selectedCity.getLongitude()));


        modLabelTextField.setText(selectedCity.getLabel());
        modNameTextField.setText(selectedCity.getCityName());
        modHoursBox.setSelectedItem(selectedCityHour);
        modMinutesBox.setSelectedItem(selectedCityMinute);
        if (selectedCity.getLatitude() < 0) {
            modLatDirectionGroup.setSelected(modSouth.getModel(), true);
        } else {
            modLatDirectionGroup.setSelected(modNorth.getModel(), true);
        }
        modLatDegField.setText(selectedCityLatitude);
        if (selectedCity.getLongitude() < 0) {
            modLongDirectionGroup.setSelected(modWest.getModel(), true);
        } else {
            modLongDirectionGroup.setSelected(modEast.getModel(), true);
        }

        modLongDegField.setText(selectedCityLongitude);
    }

    // MODIFIES: this
    // EFFECTS: revalidates and repaints GUI
    public void reloadGUI() {
        revalidate();
        repaint();
    }

    // REQUIRES: frame is not null
    // MODIFIES: frame
    // EFFECTS: opens/closes the given frame according to isOpen
    public void frameOpenOrClose(JFrame frame, boolean isOpen) {
        frame.setEnabled(isOpen);
        frame.setVisible(isOpen);
    }


    // REQUIRES: all arguments must not be null
    // EFFECTS: returns a new city to add based on the inputs of the given components (passed argument)
    // from the cityContentPane
    private static City generateNewCityFromInputs(JComboBox addHoursBox, JComboBox addMinutesBox,
                                                  JFormattedTextField addLatitudeDegField, JRadioButton addSouth,
                                                  JFormattedTextField addLongitudeDegField, JRadioButton addWest,
                                                  JTextField addLabelTextField, JTextField addNameTextField)
            throws InputMismatchException {
        double newOffsetHourUTC = Double.parseDouble(addHoursBox.getSelectedItem().toString());
        double newOffsetMinuteUTC = Double.parseDouble(addMinutesBox.getSelectedItem().toString()) / 60;
        double newOffsetOverallUTC;
        if (newOffsetHourUTC < 0) {
            newOffsetOverallUTC = newOffsetHourUTC - newOffsetMinuteUTC;
        } else {
            newOffsetOverallUTC = newOffsetHourUTC + newOffsetMinuteUTC;
        }

        double newLatitude = checkAndSetLatLong(addLatitudeDegField, 90, addSouth);

        double newLongitude = checkAndSetLatLong(addLongitudeDegField, 180, addWest);


        City newCity = new City(addLabelTextField.getText(), addNameTextField.getText(),
                newOffsetOverallUTC, newLatitude, newLongitude);
        return newCity;
    }


    // REQUIRES: coordDegTextField and coordDirectionButton must not be null
    // EFFECTS: checks if the given latitude or longitude is valid by checking if the input is between 0 and max
    //          and if so returns it as a double
    //          if invalid, throws InputMismatchException
    private static double checkAndSetLatLong(JFormattedTextField coordDegTextField,
                                             int max, JRadioButton coordDirectionButton)
            throws InputMismatchException {
        double newLatLong = Double.parseDouble(coordDegTextField.getText());

        if (!(newLatLong >= 0 && newLatLong <= max)) {
            throw new InputMismatchException("Degree out of bounds");
        }
        if (coordDirectionButton.isSelected()) {
            newLatLong = newLatLong * -1;
        }
        return newLatLong;
    }

    // REQUIRES: cityContentPane
    // MODIFIES: cityContentPane
    // EFFECTS: create, add, set, and return Ok JButton for the given cityContentPane

    private JButton getOkButtonCityContentPane(Container cityContentPane) {
        JButton okButton = new JButton("Ok");
        okButton.setSize(60, okButton.getFont().getSize() + 20);
        okButton.setLocation(120, 180);
        cityContentPane.add(okButton);
        return okButton;
    }

    // REQUIRES: cityContentPane is not null
    // MODIFIES: cityContentPane
    // EFFECTS: create, set, and return JFormattedTextField for the coordinates value input inside given cityContentPane

    private JFormattedTextField getCoordJTextFieldFromContentPane(int y, Container cityContentPane) {
        JFormattedTextField coordDegField = new JFormattedTextField(new DecimalFormat("#.######"));
        coordDegField.setSize(50, coordDegField.getFont().getSize() + 10);
        coordDegField.setLocation(240, y);
        cityContentPane.add(coordDegField);
        return coordDegField;
    }


    // REQUIRES: button1 and button2 are not null
    // EFFECTS: group button1 and button2 as a button group (and return the group)
    //          so only one can be selected at a time

    private ButtonGroup getButtonGroup(JRadioButton button1, JRadioButton button2) {
        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(button1);
        directionGroup.add(button2);
        return directionGroup;
    }


    // REQUIRES: cityContentPane is not null, direction must be either "N", "S", "E", or "W"
    // MODIFIES: cityContentPane
    // EFFECTS: create, set, and return JRadiobutton with given direction and x y position inside given cityContentPane

    private JRadioButton getCoordDirectionButtonFromContentPane(String direction, int x, int y,
                                                                Container cityContentPane) {
        JRadioButton directionButton = new JRadioButton(direction);
        directionButton.setSize(40, directionButton.getFont().getSize() + 10);
        directionButton.setLocation(x, y);
        cityContentPane.add(directionButton);
        return directionButton;
    }


    // REQUIRES: cityContentPane is not null
    // MODIFIES: cityContentPane
    // EFFECTS: create and return hours or minutes JComboBox with given string[] as options inside cityContentPane

    private JComboBox getHoursMinutesBoxFromContentPane(String[] hourMinuteChoices, int x, Container cityContentPane) {
        JComboBox hoursMinutesBox = new JComboBox<>(hourMinuteChoices);
        hoursMinutesBox.setSize(50, hoursMinutesBox.getFont().getSize() + 10);
        hoursMinutesBox.setLocation(x, 70);
        cityContentPane.add(hoursMinutesBox);
        return hoursMinutesBox;
    }


    // REQUIRES: moveUpDownButton must not be null
    // MODIFIES: this
    // EFFECTS: add an actionlistener to given move up or move down button and allows the selected city in
    // this.cities to be moved within the JList and corresponding CityList by the direction and magnitude of
    // the given indexShift
    // IndexOutofBounds will be caught so that once the city reaches top or bottom, it cannot go any higher/lower
    private void addMoveUpDownButtonActionListener(JButton moveUpDownButton, int indexShift) {
        moveUpDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int beforeIndex = cities.getSelectedIndex();
                    int afterIndex = beforeIndex + indexShift;
                    cityList.swap(beforeIndex, afterIndex);
                    //Collections.swap(cityList.getListOfCities(), beforeIndex, afterIndex);
                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();
                    cities.setSelectedIndex(afterIndex);

                } catch (IndexOutOfBoundsException ee) {
                    //
                }
            }
        });
    }

    // REQUIRES: cityContentPane is not null
    // MODIFIES: cityContentPane
    // EFFECTS: returns and sets up a new JTextfield within the cityContentPane with given y position
    private JTextField getLabelNameJTextFieldFromContentPane(int y, Container cityContentPane) {
        JTextField labelNameTextField = new JTextField();
        //addLabelTextField.setText("");
        labelNameTextField.setSize(150, labelNameTextField.getFont().getSize() + 10);
        labelNameTextField.setLocation(150, y);
        cityContentPane.add(labelNameTextField);
        return labelNameTextField;
    }


    // REQUIRES: cityContentPane is not null
    // MODIFIES: cityContentPane
    // EFFECTS: add city info setting prompts and intialize them
    private void initCityBuildPrompts(Container cityContentPane) {
        JLabel setCityLabelPrompt = new JLabel("Label:");
        initCityPrompt(setCityLabelPrompt, 10, cityContentPane);


        JLabel setCityNamePrompt = new JLabel("Name: *");
        initCityPrompt(setCityNamePrompt, 40, cityContentPane);


        JLabel setCityUtcPrompt = new JLabel("UTC Offset: *");
        initCityPrompt(setCityUtcPrompt, 70, cityContentPane);


        JLabel setCityLatitudePrompt = new JLabel("Latitude: *");
        initCityPrompt(setCityLatitudePrompt, 100, cityContentPane);


        JLabel setCityLongitudePrompt = new JLabel("Longitude: *");
        initCityPrompt(setCityLongitudePrompt, 130, cityContentPane);

        JLabel setMandatoryLabel = new JLabel("* mandatory");
        initCityPrompt(setMandatoryLabel, 160, cityContentPane);
    }


    // REQUIRES: cityContentPrompt!= null, cityContentPane != null
    // MODIFIES: cityContentPrompt, cityContentPane
    // EFFECTS: sets the size and y position of the given cityContentPrompt and adds it on the given cityContentPane
    private void initCityPrompt(JLabel cityContentPrompt, int y, Container cityContentPane) {
        cityContentPrompt.setSize(100, cityContentPrompt.getFont().getSize() + 10);
        cityContentPrompt.setLocation(10, y);
        cityContentPane.add(cityContentPrompt);
    }

    // REQUIRES: removeButton, modifyButton, moveUpButton, moveDownButton must not be null
    // MODIFIES: this, removeButton, modifyButton, moveUpButton, moveDownButton
    // EFFECTS: initialize given CityInfoPanel and toggle availability of city buttons
    private void initCityInfoPanel() {
        Dimension cityInfoPanelDimension = new Dimension(WIDTH / 8, HEIGHT / 10);
        cityInfoPanel.setLayout(new BoxLayout(cityInfoPanel, BoxLayout.Y_AXIS));
        cityInfoPanel.setPreferredSize(cityInfoPanelDimension);
        cityInfoPanel.setMinimumSize(cityInfoPanelDimension);
        cityInfoPanel.setMaximumSize(cityInfoPanelDimension);

        //JLabel noCitySelectedLabel = new JLabel("Please select or add a city in/to a list.");
        JLabel selectedCityLabel = new JLabel("\n");
        JLabel selectedCityName = new JLabel("\n");
        JLabel selectedCityOffsetUTC = new JLabel("\n");
        JLabel selectedCityCoordinates = new JLabel("\n");
        JLabel selectedCityCurrentTime = new JLabel("\n");


        if (this.cities.isSelectionEmpty()) {
            enableOrDisableIndividualCityCommands(false);
            reloadGUI();
        }

        // display selected city info or nothing if no city is selected
        cities.addListSelectionListener(getCitySelectListener(selectedCityLabel, selectedCityName,
                selectedCityOffsetUTC, selectedCityCoordinates, selectedCityCurrentTime));
    }


    // REQUIRES: all arguments must not be null
    // MODIFIES: this
    // EFFECTS: returns a new ListSelectionListener for city. if cities is empty, make sure no city
    //          is selected and therefore cityInfoPanel must be emptied
    private ListSelectionListener getCitySelectListener(JLabel selectedCityLabel,
                                                        JLabel selectedCityName, JLabel selectedCityOffsetUTC,
                                                        JLabel selectedCityCoordinates,
                                                        JLabel selectedCityCurrentTime) {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (cities.isSelectionEmpty()) {
                    cityInfoPanel.removeAll();
                    cityInfoPanel.revalidate();
                    cityInfoPanel.repaint();

                } else {
                    enableOrDisableIndividualCityCommands(true);

                    updateSelectedCityInCityInfoPanel(cityInfoPanel, selectedCityLabel,
                            selectedCityName, selectedCityOffsetUTC, selectedCityCoordinates, selectedCityCurrentTime);


                    //cityInfoPanel.repaint();
                }
            }
        };
    }


    // REQUIRES: cityInfoPanel, selectedCityLabel, selectedCityName, selectedCityOffsetUTC, selectedCityCoordinates,
    //          selectedCityCurrentTime, and this.cities.getSelectedValue() must not be null,
    // MODIFIES: cityInfoPanel, selectedCityLabel, selectedCityName, selectedCityOffsetUTC, selectedCityCoordinates,
    //          and selectedCityCurrentTime
    // EFFECTS: add selectedCityLabel, selectedCityName, selectedCityOffsetUTC, selectedCityCoordinates,
    //           and selectedCityCurrentTime to cityInfoPanel and update the info labels' values with that of the
    //           City that our JList of cities currently has it selected
    private void updateSelectedCityInCityInfoPanel(JPanel cityInfoPanel, JLabel selectedCityLabel,
                                                   JLabel selectedCityName, JLabel selectedCityOffsetUTC,
                                                   JLabel selectedCityCoordinates, JLabel selectedCityCurrentTime) {
        cityInfoPanel.add(selectedCityLabel);
        cityInfoPanel.add(selectedCityName);
        cityInfoPanel.add(selectedCityOffsetUTC);
        cityInfoPanel.add(selectedCityCoordinates);
        cityInfoPanel.add(selectedCityCurrentTime);

        City selected = cities.getSelectedValue();

        selectedCityLabel.setText(selected.getLabel());
        selectedCityName.setText(selected.getCityName());
        selectedCityOffsetUTC.setText(selected.fromUtcToZoneId());
        //selectedCityCoordinates.setText(coordToString(selected));
        selectedCityCoordinates.setText(selected.latToString() + " " + selected.longToString());
        selectedCityCurrentTime.setText(currentTimeToString(selected));

        Timer tickTock = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCityCurrentTime.setText(currentTimeToString(selected));
                cityInfoPanel.revalidate();
                cityInfoPanel.repaint();
            }
        });

        tickTock.start();
    }

    // REQUIRES: none of removeButton, modifyButton, moveUpButton, moveDownButton are null
    // MODIFIES: removeButton, modifyButton, moveUpButton, moveDownButton
    // EFFECTS: enables or disables all 4 individual selected city commands based on given boolean
    private void enableOrDisableIndividualCityCommands(boolean b) {
        removeButton.setEnabled(b);
        modifyButton.setEnabled(b);
        moveUpButton.setEnabled(b);
        moveDownButton.setEnabled(b);


        reloadGUI();
    }


    // MODIFIES: this
    // EFFECTS: take this.timeAppGUI.getCityList() and take each city and put them into labelsAndNames
    public void initializeCitiesFromCityList() {
        /*for (City c : this.cityList.getListOfCities()) {
            cityDefaultListModel.addElement(c);
        }

         */

        for (int i = 0; i < this.cityList.size(); i++) {
            City c = this.cityList.get(i);
            cityDefaultListModel.addElement(c);
        }
        reloadGUI();

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

        // timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    }

    // EFFECTS: getter for this.cityList
    public CityList getCityList() {
        return this.cityList;
    }

    // EFFECTS: getter for this.cities
    public JList<City> getCities() {
        return this.cities;
    }


    // EFFECTS: getter for this.cityDefaultListModel
    public DefaultListModel<City> getCityDefaultListModel() {
        return this.cityDefaultListModel;
    }


    // REQUIRES: city != null
    // EFFECTS: converts the current time of the city to string value
    public String currentTimeToString(City city) {

        ZoneId zone = ZoneId.of(city.fromUtcToZoneId());
        ZonedDateTime nowDateTime = ZonedDateTime.now(zone);


        return this.timeFormatter.format(nowDateTime);
    }

    // REQUIRES: city != null
    // EFFECTS: converts the double coordinate to string value
    public String coordToString(City city) {
       /*
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
*/
        return city.latToString() + " " + city.longToString();

    }

    // REQUIRES: this.menuBar != null
    // MODIFIES: this
    // EFFECTS: creates and initialize menu bar for the app
    private void initMenuBar() {

        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load", loadIcon);
        JMenuItem saveMenuItem = new JMenuItem("Save", saveIcon);
        JFrame loadFrame = new JFrame("Load from...");
        JDialog loadDialog = new JDialog(loadFrame, "Load from...", true);
        loadDialog.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));
        JFrame saveFrame = new JFrame("Save to...");
        JDialog saveDialog = new JDialog(saveFrame, "Save to...", true);
        saveDialog.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));

        JFrame fileNotFoundFrame = new JFrame("Error");

/*
        JFrame fileNotFoundErrorFrame = new JFrame("Error");
        Container fileNotFoundErrorPane = fileNotFoundErrorFrame.getContentPane();
        fileNotFoundErrorPane.setLayout(new BoxLayout(fileNotFoundErrorPane, BoxLayout.Y_AXIS));
        JLabel fileNotFoundErrorLabel = new JLabel("Sorry. File not found.");
        JButton fileNotFoundErrorOkButton = new JButton("Ok");

        fileNotFoundErrorPane.add(fileNotFoundErrorLabel);
        fileNotFoundErrorPane.add(fileNotFoundErrorOkButton);



        fileNotFoundErrorFrame.setSize(WIDTH / 10, HEIGHT / 10);
        fileNotFoundErrorOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileNotFoundErrorFrame.setVisible(false);
            }
        });*/


        /*
        initLoadFrame(loadFrame);
        initSaveFrame(saveFrame);


        setUpFileItemsHotkeyPopupFrame(loadMenuItem, loadFrame, KeyEvent.VK_L, ActionEvent.CTRL_MASK);
        setUpFileItemsHotkeyPopupFrame(saveMenuItem, saveFrame, KeyEvent.VK_S, ActionEvent.CTRL_MASK);

         */

        initLoadFrame(loadFrame, loadDialog);
        initSaveFrame(saveFrame, saveDialog);


        setUpFileItemsHotkeyPopupFrame(loadMenuItem, loadFrame, loadDialog, KeyEvent.VK_L, ActionEvent.CTRL_MASK);
        setUpFileItemsHotkeyPopupFrame(saveMenuItem, saveFrame, saveDialog, KeyEvent.VK_S, ActionEvent.CTRL_MASK);



        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);


    }


    // MODIFIES: this
    // EFFECTS: loads cityList from given jsonReader file
    @Override
    public void loadCityListFromFile(JsonReader jsonReader) {
        try {
            CityList loadedList = jsonReader.read();
            // this.cityList = loadedList;

            this.cityList.clear();
            for (int i = 0; i < loadedList.size(); i++) {
                City c = loadedList.get(i);
                this.cityList.add(c);
            }

            this.cityDefaultListModel.removeAllElements();
            initializeCitiesFromCityList();

            cityPanel.revalidate();
            cityPanel.repaint();


        } catch (IOException e) {
            // nothing yet
            JOptionPane.showMessageDialog(errorWarningFrame,
                    "File not found.",
                    "Load error",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            enableOrDisableIndividualCityCommands(false);
            reloadGUI();
        }
    }


    // MODIFIES: jsonWriter
    // EFFECTS: saves the citylist to given jsonWriter
    @Override
    public void saveCityListToFile(JsonWriter jsonWriter) {
        try {
            jsonWriter.open();
            jsonWriter.write(this.cityList);
            jsonWriter.close();

        } catch (FileNotFoundException e) {
            // nothing yet
            JOptionPane.showMessageDialog(errorWarningFrame,
                    "File not found.",
                    "Save error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this, item, frame, dialog
    // EFFECTS: set up the menu items with given shortcuts and frames
    private void setUpFileItemsHotkeyPopupFrame(JMenuItem item, JFrame frame, JDialog dialog,  int ke, int ae) {
        item.setMnemonic(ke);
        item.setAccelerator(KeyStroke.getKeyStroke(
                ke, ae));
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(true);

                dialog.setVisible(true);
                // setEnabled(false);
            }
        });
    }


    // MODIFIES: frame, dialog
    // EFFECTS: set up the load frame
    private void initLoadFrame(JFrame frame, JDialog dialog) {
        loadSaveFrameSetUp(frame, dialog);


    }

    // MODIFIES: frame, dialog
    // EFFECTS: set up the save frame
    private void initSaveFrame(JFrame frame, JDialog dialog) {
        loadSaveFrameSetUp(frame, dialog);

    }


    // REQUIRES: prompt must be either "Load from..." or  "Save to..."
    // MODIFIES: frame, dialog
    // EFFECTS: set up load or save frame and confirm load/save action
    //         if isCommandLoad is true then we load
    //         otherwise we save

    private void loadSaveFrameSetUp(JFrame frame, JDialog dialog)  {

        // frame.setSize(WIDTH / 8, HEIGHT / 10);
        //  frame.setLocationRelativeTo(null);

        JPanel lsPanel = new JPanel();
        // frame.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));
        //dialog.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));

        //Container loadSavedialogContainer = dialog.getContentPane();

        // frame.setLocationRelativeTo(null);
        dialog.setLocationRelativeTo(null);
        //loadSavedialogContainer.setLayout(null);

        JLabel lsLabel = new JLabel(dialog.getTitle());
        //lsLabel.setText(prompt);
        String[] fileOptions = {"myCityList1.json", "myCityList2.json", "myCityList3.json"};
        JComboBox chooseFile = new JComboBox(fileOptions);
        chooseFile.setSelectedIndex(0);
        JButton loadSaveOkButton = new JButton("Ok");



        //subFrameOnMainFrameOff(frame);


        lsPanel.add(lsLabel);
        lsPanel.add(chooseFile);
        lsPanel.add(loadSaveOkButton);
        dialog.add(lsPanel);
        //frame.add(lsPanel);

        // loadSavedialogContainer.add(lsLabel);
        //loadSavedialogContainer.add(chooseFile);
        // loadSavedialogContainer.add(loadSaveOkButton);



        loadSaveOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog.getTitle().equals("Load from...")) {
                    loadCityListFromFile(new JsonReader(JSON_STORE_PARENT + chooseFile.getSelectedItem()));

                } else if (dialog.getTitle().equals("Save to...")) {
                    saveCityListToFile(new JsonWriter(JSON_STORE_PARENT + chooseFile.getSelectedItem()));

                }
                frame.setEnabled(false);
                dialog.setVisible(false);


            }
        });
    }

    // REQUIRES: frame and chooseFile are not null, prompt must be either "Load from..." or  "Save to..."
    // MODIFIES: this, frame
    // MODIFIES: adds the ActionListener for loadSaveOkbutton, so that whether the save or load action will take place
    // once the OK button is pressed, calls for either loadCityListFromFile or saveCityListTo file depending on decision
    private void addLoadSaveOkButtonActionListener(JFrame frame, String prompt,
                                                   JComboBox chooseFile, JButton loadSaveOkButton) {
        loadSaveOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (prompt.equals("Load from...")) {
                    loadCityListFromFile(new JsonReader(JSON_STORE_PARENT + chooseFile.getSelectedItem()));

                } else if (prompt.equals("Save to...")) {
                    saveCityListToFile(new JsonWriter(JSON_STORE_PARENT + chooseFile.getSelectedItem()));

                }


                frameOpenOrClose(frame, false);
            }
        });
    }


}
