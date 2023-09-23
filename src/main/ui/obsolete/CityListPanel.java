package ui.obsolete;

import model.City;
import model.CityList;

import javax.swing.*;
import java.awt.*;

// panel that displays the list of cities and selected entry's information
public class CityListPanel extends JPanel {

    private CityList cityList;
    private TimeAppGUI timeAppGUI;

    private DefaultListModel<String> labelsAndNames = new DefaultListModel<>();
    private JList<String> cities;
    private JButton button1 = new JButton("Add City");
    private JButton button2 = new JButton("Remove City");

    // EFFECTS: constucts a panel displaying CityList of GUI and gives options for adding, removing, and deleting
    public CityListPanel(TimeAppGUI timeAppGUI) {

        this.timeAppGUI = timeAppGUI;
        this.cityList = this.timeAppGUI.getCityList();
        cities = new JList<>(labelsAndNames);
        initializeCitiesFromCityList();

        JScrollPane listScroller = new JScrollPane(cities);
        listScroller.setPreferredSize(new Dimension(150, 40));
        //panel.add(label1);
        // panel.add(button1);
        //panel.add(button2);

        add(button1);
        add(listScroller);
        add(button2);

    }

    // MODIFIES: this
    // EFFECTS: take this.timeAppGUI.getCityList() and take the name and label from each City in
    //          the cityList's listOfCities and put them into labelsAndNames
    public void initializeCitiesFromCityList() {

        for (City c : this.cityList.getListOfCities()) {
            String label = "";
            if (!c.getLabel().equals("")) {
                label = "<" + c.getLabel() + ">";
            }
            labelsAndNames.addElement(label + " " + c.getCityName());
        }

    }

}
