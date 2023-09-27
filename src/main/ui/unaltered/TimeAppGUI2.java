package ui.unaltered;

import com.sun.jdi.request.DuplicateRequestException;
import model.City;
import model.CityList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.CityIconPanel;
import ui.CityListAppWithSaveLoad;

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

import static java.lang.Math.abs;


@SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // will delete later

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

// the GUI version (unedited version) of the application
public class TimeAppGUI2 extends JFrame implements CityListAppWithSaveLoad {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;


    private JFrame errorWarningFrame = new JFrame();

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private CityList cityList = new CityList();

    private EventLog eventLog = EventLog.getInstance();

    private DefaultListModel<City> cityDefaultListModel = new DefaultListModel<>();
    private JList<City> cities = new JList<>(cityDefaultListModel);
    private JScrollPane citiesScrollable = new JScrollPane(cities); // cannot get it to work

    private static final String JSON_STORE_PARENT = "./data/";

    private static final String JSON_STORE_1 = JSON_STORE_PARENT + "myCityList1.json";
    private static final String JSON_STORE_2 = JSON_STORE_PARENT + "myCityList2.json";
    private static final String JSON_STORE_3 = JSON_STORE_PARENT + "myCityList3.json";


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

    private ImageIcon warningIconMidSize = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "icons8-warning-48.png").getImage().getScaledInstance(WIDTH / 50, WIDTH / 50, Image.SCALE_DEFAULT));

    private final JButton modifyButtonBasic = new JButton("Modify city");
    private final JButton removeButton = new JButton("Remove city", removeIcon);
    private final JButton moveDownButton = new JButton("Move Down", downIcon);
    private final JButton modifyButton = new JButton("Modify city", modifyIcon);
    private final JButton moveUpButton = new JButton("Move Up", upIcon);

    private JsonReader jsonReader1 = new JsonReader(JSON_STORE_1);
    private JsonWriter jsonWriter1 = new JsonWriter(JSON_STORE_1);
    private JsonReader jsonReader2 = new JsonReader(JSON_STORE_2);
    private JsonWriter jsonWriter2 = new JsonWriter(JSON_STORE_2);
    private JsonReader jsonReader3 = new JsonReader(JSON_STORE_3);
    private JsonWriter jsonWriter3 = new JsonWriter(JSON_STORE_3);


    // EFFECTS: runs the main GUI app
    public static void main(String[] args) {
        new TimeAppGUI2();
    }


    // MODIFIES: this
    // EFFECTS: Constructs and sets main window for the World Time app with default cities

    public TimeAppGUI2() {
        super("World Time Application");
        cityListInitialize();
        initializeCitiesFromCityList();

        errorWarningFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JButton testbutton = new JButton("Test");

        JButton addButtonBasic = new JButton("Add city");
        JButton addButton = new JButton("Add city", addIcon);
        JButton loadButton1 = new JButton("Load File 1");
        JButton saveButton1 = new JButton("Save to File 1");
        JButton clearAllButton = new JButton("Clear All Cities", clearAllIcon);

        JLabel noCitySelectedLabel = new JLabel("Please select or add a city in/to a list.");
        JLabel selectedCityLabel = new JLabel("\n");
        JLabel selectedCityName = new JLabel("\n");
        JLabel selectedCityOffsetUTC = new JLabel("\n");
        JLabel selectedCityCoordinates = new JLabel("\n");
        JLabel selectedCityCurrentTime = new JLabel("\n");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        Dimension mainPanelDimension = new Dimension(WIDTH - 700, HEIGHT / 2 - 50);
        mainPanel.setPreferredSize(mainPanelDimension);


        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        Dimension testPanelDimension = new Dimension(WIDTH / 8, HEIGHT / 2 - HEIGHT / 10);
        cityPanel.setPreferredSize(testPanelDimension);


        JPanel cityInfoPanel = new JPanel();
        Dimension cityInfoPanelDimension = new Dimension(WIDTH / 8, HEIGHT / 10);
        cityInfoPanel.setLayout(new BoxLayout(cityInfoPanel, BoxLayout.Y_AXIS));
        cityInfoPanel.setPreferredSize(cityInfoPanelDimension);
        cityInfoPanel.setMinimumSize(cityInfoPanelDimension);
        cityInfoPanel.setMaximumSize(cityInfoPanelDimension);

        Dimension citiesScrollableDimension = new Dimension(WIDTH / 5, HEIGHT / 10);
        citiesScrollable.setMaximumSize(citiesScrollableDimension);
        citiesScrollable.setPreferredSize(citiesScrollableDimension);


        JFrame loadFrame = new JFrame("Load from...");
        JDialog loadDialog = new JDialog(loadFrame, "Load from...", true);
        loadDialog.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));
       // Container loadDialogContainer = loadDialog.getContentPane();


        JFrame saveFrame = new JFrame("Save to...");

        JDialog saveDialog = new JDialog(saveFrame, "Save to...", true);
        saveDialog.setSize(new Dimension(WIDTH / 8, HEIGHT / 10));
       // Container saveDialogContainer = saveDialog.getContentPane();

        JFrame fileNotFoundFrame = new JFrame("Error");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load", loadIcon);
        JMenuItem saveMenuItem = new JMenuItem("Save", saveIcon);

        JFrame fileNotFoundErrorFrame = new JFrame("Error");
        JPanel fileNotFoundErrorPanel = new JPanel();
        JLabel fileNotFoundErrorLabel = new JLabel("Sorry. File not found.");
        JButton fileNotFoundErrorOkButton = new JButton("Ok");

        fileNotFoundErrorFrame.add(fileNotFoundErrorPanel);
        fileNotFoundErrorPanel.add(fileNotFoundErrorLabel);
        fileNotFoundErrorPanel.add(fileNotFoundErrorOkButton);


        fileNotFoundErrorPanel.setLayout(new BoxLayout(fileNotFoundErrorPanel, BoxLayout.Y_AXIS));
        fileNotFoundErrorFrame.setSize(WIDTH / 10, HEIGHT / 10);
        fileNotFoundErrorOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileNotFoundErrorFrame.setVisible(false);
            }
        });


        initLoadFrame(loadFrame, loadDialog);
        initSaveFrame(saveFrame, saveDialog);


        setUpFileItemsHotkeyPopupFrame(loadMenuItem, loadFrame, loadDialog, KeyEvent.VK_L, ActionEvent.CTRL_MASK);
        setUpFileItemsHotkeyPopupFrame(saveMenuItem, saveFrame, saveDialog, KeyEvent.VK_S, ActionEvent.CTRL_MASK);


        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);

        /*
        JPanel mapPanel = new JPanel();
        Dimension mapPanelDimension = new Dimension(WIDTH / 2, HEIGHT / 2);
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        mapPanel.setPreferredSize(mapPanelDimension);
        mapPanel.setMinimumSize(mapPanelDimension);
        mapPanel.setMaximumSize(mapPanelDimension);

        JLabel worldMap = new JLabel(backgroundMapSmaller);
        */

        CityIconPanel citiesIconsPanel = new CityIconPanel(this.cityList, backgroundMapSmaller);


        add(mainPanel);
        Container mainFrameContentPane = this.getContentPane();
        mainFrameContentPane.setLayout(new FlowLayout());

        //add(cityPanel);

        mainPanel.add(cityPanel);
        cityPanel.add(cityInfoPanel);
        //mainPanel.add(mapPanel);
        // mapPanel.add(worldMap);

       // add(citiesIconsPanel);
        mainPanel.add(citiesIconsPanel);


        //cityPanel.add(addButtonBasic);
        cityPanel.add(addButton);

        cityPanel.add(citiesScrollable);
        //cityPanel.add(cities);
        // cityPanel.add(testbutton);


        cityPanel.add(moveUpButton);
        cityPanel.add(moveDownButton);

        //cityPanel.add(modifyButtonBasic);
        cityPanel.add(modifyButton);


        cityPanel.add(removeButton);

        // cityPanel.add(loadButton1);
        // cityPanel.add(saveButton1);

        cityPanel.add(clearAllButton);


        if (this.cities.isSelectionEmpty()) {
            removeButton.setEnabled(false);
            modifyButton.setEnabled(false);
            moveUpButton.setEnabled(false);
            moveDownButton.setEnabled(false);
        }

        // display selected city info or nothing if no city is selected
        cities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (cities.isSelectionEmpty()) {
                    cityInfoPanel.removeAll();
                    cityInfoPanel.revalidate();
                    cityInfoPanel.repaint();

                } else {
                    removeButton.setEnabled(true);
                    modifyButton.setEnabled(true);
                    moveUpButton.setEnabled(true);
                    moveDownButton.setEnabled(true);

                    cityInfoPanel.add(selectedCityLabel);
                    cityInfoPanel.add(selectedCityName);
                    cityInfoPanel.add(selectedCityOffsetUTC);
                    cityInfoPanel.add(selectedCityCoordinates);
                    cityInfoPanel.add(selectedCityCurrentTime);

                    City selected = cities.getSelectedValue();

                    selectedCityLabel.setText(selected.getLabel());
                    selectedCityName.setText(selected.getCityName());
                    selectedCityOffsetUTC.setText(selected.fromUtcToZoneId());
                    selectedCityCoordinates.setText(coordToString(selected));
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


                    //cityInfoPanel.repaint();
                }
            }
        });


        testbutton.addActionListener(new ActionListener() {
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


        addButtonBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                City testCity = new City("label", "name", +0, 0, 0);
                cityList.add(testCity);

                cityDefaultListModel.removeAllElements();
                initializeCitiesFromCityList();

                // cityDefaultListModel.addElement(testCity);


                citiesIconsPanel.invalidate();
                citiesIconsPanel.revalidate();
                citiesIconsPanel.repaint();

            }
        });

        JFrame addCityFrame = new JFrame("Add City");
        JDialog addCityDialog = new JDialog(addCityFrame, "Add City", true);


        addCityFrame.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        addCityDialog.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        addCityFrame.setLocationRelativeTo(null);
        addCityDialog.setLocationRelativeTo(null);
        Container addCityContentPane = addCityDialog.getContentPane();
        addCityContentPane.setLayout(null);

        //subFrameOnMainFrameOff(addCityFrame);



        JFrame modifyCityFrame = new JFrame("Modify City");
        JDialog modifyCityDialog = new JDialog(modifyCityFrame, "Modify City", true);


        modifyCityFrame.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        modifyCityDialog.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        modifyCityFrame.setLocationRelativeTo(null);
        modifyCityDialog.setLocationRelativeTo(null);
        Container modifyCityContentPane = modifyCityDialog.getContentPane();
        modifyCityContentPane.setLayout(null);

       // subFrameOnMainFrameOff(modifyCityFrame);


        /*JFrame modifyCityFrame = new JFrame("Modify City");
        JDialog modifyCityDialog = new JDialog(modifyCityFrame, "Modify City", true);


        modifyCityFrame.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        modifyCityDialog.setSize(new Dimension(WIDTH / 6, HEIGHT / 4));
        modifyCityFrame.setLocationRelativeTo(null);
        modifyCityDialog.setLocationRelativeTo(null);
        Container modifyCityContentPane = modifyCityDialog.getContentPane();
        modifyCityContentPane.setLayout(null);

        subFrameOnMainFrameOff(modifyCityFrame);*/


        JLabel addCityLabelPrompt = new JLabel("Label:");
        addCityLabelPrompt.setSize(100, addCityLabelPrompt.getFont().getSize() + 10);
        addCityLabelPrompt.setLocation(10, 10);
        addCityContentPane.add(addCityLabelPrompt);

        JLabel modifyCityLabelPrompt = new JLabel("Label:");
        modifyCityLabelPrompt.setSize(100, modifyCityLabelPrompt.getFont().getSize() + 10);
        modifyCityLabelPrompt.setLocation(10, 10);
        modifyCityContentPane.add(modifyCityLabelPrompt);


        JLabel addCityNamePrompt = new JLabel("Name: *");
        addCityNamePrompt.setSize(100, addCityNamePrompt.getFont().getSize() + 10);
        addCityNamePrompt.setLocation(10, 40);
        addCityContentPane.add(addCityNamePrompt);

        JLabel modifyCityNamePrompt = new JLabel("Name: *");
        modifyCityNamePrompt.setSize(100, modifyCityNamePrompt.getFont().getSize() + 10);
        modifyCityNamePrompt.setLocation(10, 40);
        modifyCityContentPane.add(modifyCityNamePrompt);


        JLabel addCityUtcPrompt = new JLabel("UTC Offset: *");
        addCityUtcPrompt.setSize(100, addCityUtcPrompt.getFont().getSize() + 10);
        addCityUtcPrompt.setLocation(10, 70);
        addCityContentPane.add(addCityUtcPrompt);


        JLabel modifyCityUtcPrompt = new JLabel("UTC Offset: *");
        modifyCityUtcPrompt.setSize(100, modifyCityUtcPrompt.getFont().getSize() + 10);
        modifyCityUtcPrompt.setLocation(10, 70);
        modifyCityContentPane.add(modifyCityUtcPrompt);


        JLabel addCityLatitudePrompt = new JLabel("Latitude: *");
        addCityLatitudePrompt.setSize(100, addCityLatitudePrompt.getFont().getSize() + 10);
        addCityLatitudePrompt.setLocation(10, 100);
        addCityContentPane.add(addCityLatitudePrompt);


        JLabel modifyCityLatitudePrompt = new JLabel("Latitude: *");
        modifyCityLatitudePrompt.setSize(100, modifyCityLatitudePrompt.getFont().getSize() + 10);
        modifyCityLatitudePrompt.setLocation(10, 100);
        modifyCityContentPane.add(modifyCityLatitudePrompt);


        JLabel addCityLongitudePrompt = new JLabel("Longitude: *");
        addCityLongitudePrompt.setSize(100, addCityLongitudePrompt.getFont().getSize() + 10);
        addCityLongitudePrompt.setLocation(10, 130);
        addCityContentPane.add(addCityLongitudePrompt);

        JLabel modifyCityLongitudePrompt = new JLabel("Longitude: *");
        modifyCityLongitudePrompt.setSize(100, modifyCityLongitudePrompt.getFont().getSize() + 10);
        modifyCityLongitudePrompt.setLocation(10, 130);
        modifyCityContentPane.add(modifyCityLongitudePrompt);


        JLabel addMandatoryLabel = new JLabel("* mandatory");
        addMandatoryLabel.setSize(100, addMandatoryLabel.getFont().getSize() + 10);
        addMandatoryLabel.setLocation(10, 160);
        addCityContentPane.add(addMandatoryLabel);

        JLabel modifyMandatoryLabel = new JLabel("* mandatory");
        modifyMandatoryLabel.setSize(100, modifyMandatoryLabel.getFont().getSize() + 10);
        modifyMandatoryLabel.setLocation(10, 160);
        modifyCityContentPane.add(modifyMandatoryLabel);


        JTextField addLabelTextField = new JTextField();
        //addLabelTextField.setText("");
        addLabelTextField.setSize(150, addLabelTextField.getFont().getSize() + 10);
        addLabelTextField.setLocation(150, 10);
        addCityContentPane.add(addLabelTextField);


        JTextField modifyLabelTextField = new JTextField();
        modifyLabelTextField.setSize(150, modifyLabelTextField.getFont().getSize() + 10);
        modifyLabelTextField.setLocation(150, 10);
        modifyCityContentPane.add(modifyLabelTextField);


        JTextField addNameTextField = new JTextField();
        //addNameTextField.setText(null);
        addNameTextField.setSize(150, addNameTextField.getFont().getSize() + 10);
        addNameTextField.setLocation(150, 40);
        addCityContentPane.add(addNameTextField);

        JTextField modifyNameTextField = new JTextField();
        modifyNameTextField.setSize(150, modifyNameTextField.getFont().getSize() + 10);
        modifyNameTextField.setLocation(150, 40);
        modifyCityContentPane.add(modifyNameTextField);


        String[] hourChoices = {"-12", "-11", "-10", "-09", "-08", "-07", "-06", "-05", "-04", "-03", "-02", "-01",
                "+00", "+01", "+02", "+03", "+04", "+05", "+06", "+07",
                "+08", "+09", "+10", "+11", "+12", "+13", "+14"};

        String[] minuteChoices = {"00", "15", "30", "45"};


        JComboBox addHoursBox = new JComboBox<>(hourChoices);
        //addHoursBox.setSelectedItem("+00");
        addHoursBox.setSize(50, addHoursBox.getFont().getSize() + 10);
        addHoursBox.setLocation(150, 70);
        addCityContentPane.add(addHoursBox);


        JComboBox modifyHoursBox = new JComboBox<>(hourChoices);
        modifyHoursBox.setSize(50, modifyHoursBox.getFont().getSize() + 10);
        modifyHoursBox.setLocation(150, 70);
        modifyCityContentPane.add(modifyHoursBox);


        JComboBox addMinutesBox = new JComboBox<>(minuteChoices);
        //addMinutesBox.setSelectedItem("00");
        addMinutesBox.setSize(50, addMinutesBox.getFont().getSize() + 10);
        addMinutesBox.setLocation(210, 70);
        addCityContentPane.add(addMinutesBox);

        JComboBox modifyMinutesBox = new JComboBox<>(minuteChoices);
        modifyMinutesBox.setSize(50, modifyMinutesBox.getFont().getSize() + 10);
        modifyMinutesBox.setLocation(210, 70);
        modifyCityContentPane.add(modifyMinutesBox);


        JRadioButton addNorth = new JRadioButton("N");
        addNorth.setSize(40, addNorth.getFont().getSize() + 10);
        addNorth.setLocation(150, 100);
        //addNorth.setSelected(true);
        addCityContentPane.add(addNorth);

        JRadioButton modifyNorth = new JRadioButton("N");
        modifyNorth.setSize(40, modifyNorth.getFont().getSize() + 10);
        modifyNorth.setLocation(150, 100);
        modifyCityContentPane.add(modifyNorth);


        JRadioButton addSouth = new JRadioButton("S");
        addSouth.setSize(40, addSouth.getFont().getSize() + 10);
        addSouth.setLocation(200, 100);
        //addSouth.setSelected(false);
        addCityContentPane.add(addSouth);

        JRadioButton modifySouth = new JRadioButton("S");
        modifySouth.setSize(40, modifySouth.getFont().getSize() + 10);
        modifySouth.setLocation(200, 100);
        modifyCityContentPane.add(modifySouth);


        ButtonGroup addLatDirectionGroup = new ButtonGroup();
        addLatDirectionGroup.add(addNorth);
        addLatDirectionGroup.add(addSouth);


        ButtonGroup modifyLatDirectionGroup = new ButtonGroup();
        modifyLatDirectionGroup.add(modifyNorth);
        modifyLatDirectionGroup.add(modifySouth);


        JFormattedTextField addLatitudeDegField = new JFormattedTextField(new DecimalFormat("#.######"));
        //addLatitudeDegField.setText("0.0000");
        addLatitudeDegField.setSize(50, addLatitudeDegField.getFont().getSize() + 10);
        addLatitudeDegField.setLocation(240, 100);
        addCityContentPane.add(addLatitudeDegField);


        JFormattedTextField modifyLatitudeDegField = new JFormattedTextField(new DecimalFormat("#.######"));
        modifyLatitudeDegField.setSize(50, modifyLatitudeDegField.getFont().getSize() + 10);
        modifyLatitudeDegField.setLocation(240, 100);
        modifyCityContentPane.add(modifyLatitudeDegField);


        JRadioButton addEast = new JRadioButton("E");
        addEast.setSize(40, addEast.getFont().getSize() + 10);
        addEast.setLocation(150, 130);
        //addEast.setSelected(true);
        addCityContentPane.add(addEast);


        JRadioButton modifyEast = new JRadioButton("E");
        modifyEast.setSize(40, modifyEast.getFont().getSize() + 10);
        modifyEast.setLocation(150, 130);
        modifyCityContentPane.add(modifyEast);


        JRadioButton addWest = new JRadioButton("W");
        addWest.setSize(40, addWest.getFont().getSize() + 10);
        addWest.setLocation(200, 130);
        //addWest.setSelected(false);
        addCityContentPane.add(addWest);

        JRadioButton modifyWest = new JRadioButton("W");
        modifyWest.setSize(40, modifyWest.getFont().getSize() + 10);
        modifyWest.setLocation(200, 130);
        modifyCityContentPane.add(modifyWest);


        ButtonGroup addLongDirectionGroup = new ButtonGroup();
        addLongDirectionGroup.add(addEast);
        addLongDirectionGroup.add(addWest);


        ButtonGroup modifyLongDirectionGroup = new ButtonGroup();
        modifyLongDirectionGroup.add(modifyEast);
        modifyLongDirectionGroup.add(modifyWest);


        JFormattedTextField addLongitudeDegField = new JFormattedTextField(new DecimalFormat("#.######"));
        //addLongitudeDegField.setText("0.0000");
        addLongitudeDegField.setSize(50, addLongitudeDegField.getFont().getSize() + 10);
        addLongitudeDegField.setLocation(240, 130);
        addCityContentPane.add(addLongitudeDegField);


        JFormattedTextField modifyLongitudeDegField = new JFormattedTextField(new DecimalFormat("#.######"));
        //addLongitudeDegField.setText("0.0000");
        modifyLongitudeDegField.setSize(50, modifyLongitudeDegField.getFont().getSize() + 10);
        modifyLongitudeDegField.setLocation(240, 130);
        modifyCityContentPane.add(modifyLongitudeDegField);


        JButton addCityOkButton = new JButton("Ok");
        addCityOkButton.setSize(60, addCityOkButton.getFont().getSize() + 20);
        addCityOkButton.setLocation(120, 180);
        addCityContentPane.add(addCityOkButton);




        addCityOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (addNameTextField.getText().equals("")) {
                        throw new NullPointerException();
                    }

                    double newOffsetHourUTC = Double.parseDouble(addHoursBox.getSelectedItem().toString());
                    double newOffsetMinuteUTC = Double.parseDouble(addMinutesBox.getSelectedItem().toString()) / 60;
                    double newOffsetOverallUTC;
                    if (newOffsetHourUTC < 0) {
                        newOffsetOverallUTC = newOffsetHourUTC - newOffsetMinuteUTC;
                    } else {
                        newOffsetOverallUTC = newOffsetHourUTC + newOffsetMinuteUTC;
                    }

                    double newLatitude = Double.parseDouble(addLatitudeDegField.getText());

                    if (!(newLatitude >= 0 && newLatitude <= 90)) {
                        throw new InputMismatchException("Degree out of bounds");
                    }
                    if (addSouth.isSelected()) {
                        newLatitude = newLatitude * -1;
                    }

                    double newLongitude = Double.parseDouble(addLongitudeDegField.getText());

                    if (!(newLongitude >= 0 && newLongitude <= 180)) {
                        throw new InputMismatchException("Degree out of bounds");
                    }
                    if (addWest.isSelected()) {
                        newLongitude = newLongitude * -1;
                    }


                    City newCity = new City(addLabelTextField.getText(), addNameTextField.getText(),
                            newOffsetOverallUTC, newLatitude, newLongitude);

                    cityList.add(newCity);

                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();



                    cities.setSelectedIndex(cityList.size() - 1);  // select the newly added city


                    revalidate();
                    repaint();




                } catch (Exception eee) {
                    //

                    JOptionPane.showMessageDialog(errorWarningFrame,
                            "Invalid input or duplicate City detected.",
                            "Warning: add City",
                            JOptionPane.WARNING_MESSAGE);

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

                    addCityFrame.setEnabled(false);
                    //addCityFrame.setVisible(false);
                    addCityDialog.setVisible(false);


                }
            }
        });


        JButton modifyCityOkButton = new JButton("Ok");
        modifyCityOkButton.setSize(60, modifyCityOkButton.getFont().getSize() + 20);
        modifyCityOkButton.setLocation(120, 180);
        modifyCityContentPane.add(modifyCityOkButton);




        modifyCityOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (modifyNameTextField.getText().equals("")) {
                        throw new NullPointerException();
                    }

                    double newOffsetHourUTC = Double.parseDouble(modifyHoursBox.getSelectedItem().toString());
                    double newOffsetMinuteUTC = Double.parseDouble(modifyMinutesBox.getSelectedItem().toString()) / 60;
                    double newOffsetOverallUTC;
                    if (newOffsetHourUTC < 0) {
                        newOffsetOverallUTC = newOffsetHourUTC - newOffsetMinuteUTC;
                    } else {
                        newOffsetOverallUTC = newOffsetHourUTC + newOffsetMinuteUTC;
                    }

                    double newLatitude = Double.parseDouble(modifyLatitudeDegField.getText());

                    if (!(newLatitude >= 0 && newLatitude <= 90)) {
                        throw new InputMismatchException("Degree out of bounds");
                    }
                    if (modifySouth.isSelected()) {
                        newLatitude = newLatitude * -1;
                    }

                    double newLongitude = Double.parseDouble(modifyLongitudeDegField.getText());

                    if (!(newLongitude >= 0 && newLongitude <= 180)) {
                        throw new InputMismatchException("Degree out of bounds");
                    }
                    if (modifyWest.isSelected()) {
                        newLongitude = newLongitude * -1;
                    }

                    int modifyIndex = cities.getSelectedIndex();

                    City changedCity = new City(modifyLabelTextField.getText(), modifyNameTextField.getText(),
                            newOffsetOverallUTC, newLatitude, newLongitude);

                    if (cityList.doesIdenticalCityAlreadyExist(changedCity)) {
                        throw new DuplicateRequestException();
                    }

                    cityList.set(modifyIndex, changedCity);

                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();

                    cities.setSelectedIndex(modifyIndex);


                    revalidate();
                    repaint();


                } catch (Exception eee) {
                    //
                    JOptionPane.showMessageDialog(errorWarningFrame,
                            "Invalid input, duplicate City, or no actual change detected.",
                            "Warning: modify City",
                            JOptionPane.WARNING_MESSAGE);


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


                    modifyCityFrame.setEnabled(false);
                   // modifyCityFrame.setVisible(false);
                    modifyCityDialog.setVisible(false);

                }
            }
        });


        addCityFrame.setVisible(false);
        modifyCityFrame.setVisible(false);
        addCityDialog.setVisible(false);
        modifyCityDialog.setVisible(false);



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

                        removeButton.setEnabled(false);
                        modifyButton.setEnabled(false);
                        moveUpButton.setEnabled(false);
                        moveDownButton.setEnabled(false);
                    }

                } catch (NullPointerException ee) {
                    //
                } finally {

                    citiesIconsPanel.invalidate();
                    citiesIconsPanel.revalidate();
                    citiesIconsPanel.repaint();

                }


            }
        });

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

                        removeButton.setEnabled(false);
                        modifyButton.setEnabled(false);
                        moveUpButton.setEnabled(false);
                        moveDownButton.setEnabled(false);
                    }


                } catch (NullPointerException ee) {
                    //
                } finally {


                    citiesIconsPanel.invalidate();
                    citiesIconsPanel.revalidate();
                    citiesIconsPanel.repaint();
                }


            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    modifyCityFrame.setEnabled(true);

                    int modifyIndex = cities.getSelectedIndex();
                    City selectedCity = cities.getSelectedValue();
                    String selectedCityUtcID = selectedCity.fromUtcToZoneId();
                    String selectedCityHour = selectedCityUtcID.substring(3, 6);
                    String selectedCityMinute = selectedCityUtcID.substring(7, 9);
                    String selectedCityLatitude = Double.toString(Math.abs(selectedCity.getLatitude()));
                    String selectedCityLongitude = Double.toString(Math.abs(selectedCity.getLongitude()));


                    modifyLabelTextField.setText(selectedCity.getLabel());
                    modifyNameTextField.setText(selectedCity.getCityName());
                    modifyHoursBox.setSelectedItem(selectedCityHour);
                    modifyMinutesBox.setSelectedItem(selectedCityMinute);
                    if (selectedCity.getLatitude() < 0) {
                        modifyLatDirectionGroup.setSelected(modifySouth.getModel(), true);
                    } else {
                        modifyLatDirectionGroup.setSelected(modifyNorth.getModel(), true);
                    }
                    modifyLatitudeDegField.setText(selectedCityLatitude);
                    if (selectedCity.getLongitude() < 0) {
                        modifyLongDirectionGroup.setSelected(modifyWest.getModel(), true);
                    } else {
                        modifyLongDirectionGroup.setSelected(modifyEast.getModel(), true);
                    }

                    modifyLongitudeDegField.setText(selectedCityLongitude);
                    modifyCityDialog.setVisible(true);


                    // cityDefaultListModel.addElement(testCity);
                } catch (Exception ee) {
                    //
                } finally {
                    citiesIconsPanel.invalidate();
                    citiesIconsPanel.revalidate();
                    citiesIconsPanel.repaint();
                }
            }
        });

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

        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int beforeIndex = cities.getSelectedIndex();
                    int afterIndex = cities.getSelectedIndex() - 1;
                    //Collections.swap(cityList.getListOfCities(), beforeIndex, afterIndex);
                    cityList.swap(beforeIndex, afterIndex);
                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();
                    cities.setSelectedIndex(afterIndex);

                } catch (IndexOutOfBoundsException ee) {
                    //
                }
            }
        });

        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int beforeIndex = cities.getSelectedIndex();
                    int afterIndex = cities.getSelectedIndex() + 1;
                    //Collections.swap(cityList.getListOfCities(), beforeIndex, afterIndex);
                    cityList.swap(beforeIndex, afterIndex);
                    cityDefaultListModel.removeAllElements();
                    initializeCitiesFromCityList();
                    cities.setSelectedIndex(afterIndex);

                } catch (IndexOutOfBoundsException ee) {
                    //
                }
            }
        });

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


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.pack();
        this.setVisible(true);

        printLogWhenClosed();


    }

    // EFFECTS: when this is closed, print out all the event logs
    private void printLogWhenClosed() {
        // EventLog eventLog = EventLog.getInstance();
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
                } catch (Exception ee) {
                    System.out.println("An error occured while printing Event Logs.");
                } finally {
                    System.out.println("\nApp closed successfully.");
                }
            }
        });

    }


    // REQUIRES: none of removeButton, modifyButton, moveUpButton, moveDownButton are null
    // MODIFIES: removeButton, modifyButton, moveUpButton, moveDownButton
    // EFFECTS: enables or disables all 4 individual selected city commands based on given boolean
    public void enableOrDisableIndividualCityCommands(boolean b) {
        removeButton.setEnabled(b);
        modifyButton.setEnabled(b);
        moveUpButton.setEnabled(b);
        moveDownButton.setEnabled(b);


        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: loads citylist from given jsonReader file
    public void loadCityListFromFile(JsonReader jsonReader) {
        try {
            CityList loadedList = jsonReader.read();
            // this.cityList = loadedlist;

            this.cityList.clear();
            for (City c : loadedList.getListOfCities()) {
                this.cityList.add(c);
            }

            this.cityDefaultListModel.removeAllElements();
            initializeCitiesFromCityList();


        } catch (IOException e) {
            // nothing yet
            JOptionPane.showMessageDialog(errorWarningFrame,
                    "File not found.",
                    "Load error",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            revalidate();
            repaint();
            enableOrDisableIndividualCityCommands(false);
           // removeButton.setEnabled(false);
           // modifyButton.setEnabled(false);
           // moveUpButton.setEnabled(false);
            //  moveDownButton.setEnabled(false);
        }
    }


    // MODIFIES: jsonWriter
    // EFFECTS: saves the citylist to given jsonWriter
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


    // MODIFIES: this
    // EFFECTS: take this.timeAppGUI.getCityList() and take each city and put them into labelsAndNames
    private void initializeCitiesFromCityList() {
        for (City c : this.cityList.getListOfCities()) {
            cityDefaultListModel.addElement(c);
        }
        revalidate();
        repaint();

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

        //timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    }

    // EFFECTS: getter for this.cityList
    public CityList getCityList() {
        return this.cityList;
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

    // MODIFIES: this, item, frame
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


    // MODIFIES: this
    // EFFECTS: set up the load frame
    private void initLoadFrame(JFrame frame, JDialog dialog) {
        loadSaveFrameSetUp(frame, dialog);


    }

    // MODIFIES: this
    // EFFECTS: set up the save frame
    private void initSaveFrame(JFrame frame, JDialog dialog) {
        loadSaveFrameSetUp(frame, dialog);

    }


    // EFFECTS: set up load or save frame and confirm load/save action
    //         if isCommandLoad is true then we load
    //         otherwise we save

    private void loadSaveFrameSetUp(JFrame frame, JDialog dialog) {

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

    // REQUIRES : frame is not null
    // MODIFIES: this
    // EFFECTS: while this frame is enabled, the main frame of the GUI will be disabled but visible
    private void subFrameOnMainFrameOff(JFrame subframe) {
        subframe.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is activated.
             *
             * @param e : WindowAdapter
             */
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                setEnabled(false);
            }

            /**
             * Invoked when a window is de-activated.
             *
             * @param e : WindowAdapter
             */
            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                setVisible(true);
                setEnabled(true);
            }
        });
    }
}
