package ui;

import model.City;
import model.CityList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;


// Panel for the city map and the city icons to be shown
public class CityIconPanel extends JPanel {

    // public static final int WIDTH = 1920;
    //  public static final int HEIGHT = 1080;

    private CityList cityList;

   /*
   private static final String JSON_STORE_PARENT = "./data/";

    private BufferedImage img;

    private ImageIcon backgroundMapSmaller = new ImageIcon(new ImageIcon(JSON_STORE_PARENT
            + "World_location_map_(equirectangular_180).svg.png").getImage().getScaledInstance(
            WIDTH / 2, WIDTH / 4, Image.SCALE_SMOOTH));

            */

    private ImageIcon backgroundMap;
    private int backgroundWidth;
    private int backgroundHeight;


    // REQUIRES: both cityList and imageIcon must be valid and not null
    // EFFECTS: Constructs the new icon display panel with given citylist and map
    public CityIconPanel(CityList cityList, ImageIcon imageIcon) {
        super();
        this.cityList = cityList;
        this.backgroundMap = imageIcon;
        this.backgroundWidth = this.backgroundMap.getIconWidth();
        this.backgroundHeight = this.backgroundMap.getIconHeight();


        Dimension cityIConDimension = new Dimension(backgroundWidth, backgroundHeight);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(cityIConDimension);
        this.setMinimumSize(cityIConDimension);
        this.setMaximumSize(cityIConDimension);

    }


    //MODIFIES: this
    //EFFECTS: draw and fill a circle representing each city in citylist
    @Override
    public void paintComponent(Graphics g) {


        super.paintComponent(g);


        g.clearRect(0, 0, backgroundWidth * 2, backgroundHeight * 2);

        g.drawImage(this.backgroundMap.getImage(), 0, 0, this);
        // shift the BG map to get the vertical correctly


        g.setColor(Color.BLUE);

        for (City c : this.cityList.getListOfCities()) {
            g.fillOval(longToX(c), latToY(c), backgroundHeight / 50, backgroundHeight / 50);

            int stringLengthInGraphic = (int) Math.round(c.getCityName().length() * 6);

            if (longToX(c) + 10 + stringLengthInGraphic >= backgroundWidth) {
                g.drawString(c.getCityName(), longToX(c) - stringLengthInGraphic, latToY(c) + 10);
            } else {
                g.drawString(c.getCityName(), longToX(c) + 10, latToY(c) + 10);
                // g.fillRect(0, 0, WIDTH / 100, WIDTH / 100);
            }
        }


    }

    // EFFECTS: return the x position value for the given city C's icon based on longitutde
    public int longToX(City c) {
        return (int) (backgroundWidth / 2 + (c.getLongitude() / 180 * backgroundWidth / 2)
                - 1); // <--- shift the value so that we can align it properly
    }


    // EFFECTS: return the y position value for the given city C's icon based on latitiude
    public int latToY(City c) {
        return (int) (backgroundHeight / 2 - (c.getLatitude() / 90 * backgroundHeight / 2)
                - 3); // <--- shift the value so that we can align it properly
    }

}
