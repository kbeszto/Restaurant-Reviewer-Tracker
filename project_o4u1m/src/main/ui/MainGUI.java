package ui;

import javax.swing.*;
import java.awt.*;


public class MainGUI {
    public static void main(String[] args) {
        RestaurantTrackerGUI app = new RestaurantTrackerGUI();
        app.setTitle("Restaurant Tracker");
        app.setVisible(true);
        app.setSize(800, 300);
        app.setLocation(300, 200);
        app.setBackground(Color.ORANGE);;
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
