package ui;

import model.Event;
import model.EventLog;
import model.Restaurant;
import model.RestaurantCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RestaurantTrackerGUI extends JFrame implements ActionListener {

    private JPanel textPanel;
    private JPanel buttonPanel;
    private JPanel gridPanel;

    private JButton addRestaurantButton;
    private JButton saveRestaurantButton;
    private JButton loadRestaurantButton;
    private JButton printRestaurantsButton;
    private JButton printRestaurantsByPriceButton;
    private JButton printRestaurantsByLocationButton;
    private JButton quitRestaurantButton;
    private JButton clearAllButton;

    private JTextField addRestaurantText;
    private JTextField addLocationText;
    private JTextField addCuisineTypeText;
    private JTextField addPriceText;
    private JTextField addRatingText;

    private JComboBox addPriceCombo;
    private JComboBox addRatingCombo;

    private JPopupMenu addRestaurantPopUpMenu;

    private ImageIcon addRestaurantIcon;
    private ImageIcon saveRestaurantIcon;
    private ImageIcon loadRestaurantIcon;
    private ImageIcon printerIcon;
    private ImageIcon printPriceIcon;
    private ImageIcon printLocationIcon;
    private ImageIcon clearScreenIcon;
    private ImageIcon quitIcon;

    private JList<String> restaurantJList;
    private DefaultListModel<String> restaurantModel;
    private JList<String> restaurantByPriceJList;
    private JList<String> restaurantByCityJList;

    private static final String JSON_STORE = "./data/restaurant.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private RestaurantCollection collection;

    // Constructs restaurant tracker GUI
    public RestaurantTrackerGUI() {
        initFields();
        initGraphics();

        setUpLabels();
        setUpAddButton();
        setUpSaveButton();
        setUpLoadButton();
        setUpPrintButtons();
        setUpClearButton();
        setUpQuitButton();
//        setUpComboBox();

        setActionCommands();
        addActionListener();

    }

    // EFFECTS: initializes fields
    private void initFields() {
        collection = new RestaurantCollection("restaurants");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        restaurantModel = new DefaultListModel<>();
        restaurantJList = new JList<>(restaurantModel);
        restaurantJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    // EFFECTS: initializes graphics
    private void initGraphics() {
        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.setBackground(Color.ORANGE);
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.ORANGE);
        gridPanel = new JPanel(new GridLayout(2, 5));
        add(new JScrollPane(restaurantJList));

        addRestaurantPopUpMenu = new JPopupMenu("Restaurant Added");
    }

    // EFFECTS: loads images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        addRestaurantIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "addRestaurantIcon.png");
        saveRestaurantIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "saveIcon.png");
        loadRestaurantIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "loadIcon.png");
        printerIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "printIcon.png");
        printPriceIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "printByPriceIcon.png");
        printLocationIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "printByCityIcon.png");
        clearScreenIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "deleteIcon.png");
        quitIcon = new ImageIcon(System.getProperty("user.dir") + sep + "icons" + sep
                + "quitIcon.png");
    }

    // MODIFIES: this
    // EFFECTS: instantiates and add labels and text fields in JFrame window
    private void setUpLabels() {
        JLabel addRestaurantLabel = new JLabel("Restaurant:");
        addRestaurantText = new JTextField(8);

        JLabel addLocationLabel = new JLabel("City:");
        addLocationText = new JTextField(8);

        JLabel addCuisineLabel = new JLabel("Cuisine:");
        addCuisineTypeText = new JTextField(8);

        JLabel addPriceLabel = new JLabel("Price($-$$$$):");
        addPriceText = new JTextField(4);

        JLabel addRatingLabel = new JLabel("Rate(1-5):");
        addRatingText = new JTextField(4);

        textPanel.add(addRestaurantLabel);
        textPanel.add(addRestaurantText);
        textPanel.add(addLocationLabel);
        textPanel.add(addLocationText);
        textPanel.add(addCuisineLabel);
        textPanel.add(addCuisineTypeText);
        textPanel.add(addPriceLabel);
        textPanel.add(addPriceText);
        textPanel.add(addRatingLabel);
        textPanel.add(addRatingText);

        gridPanel.add(textPanel);
        gridPanel.add(buttonPanel);
        add(gridPanel, BorderLayout.SOUTH);

    }

    private void setUpComboBox() {
        JLabel addPriceLabel = new JLabel("Price:");
        String[] priceString = {"", "$", "$$", "$$$", "$$$$"};
        JComboBox priceList = new JComboBox(priceString);
        priceList.addActionListener(this);
        textPanel.add(addPriceLabel);
        textPanel.add(priceList, BorderLayout.EAST);
        priceList.setBackground(Color.WHITE);

        JLabel addRatingLabel = new JLabel("Rate:");
        String[] rateString = {"", "1", "2", "3", "4", "5"};
        JComboBox rateList = new JComboBox(rateString);
        rateList.addActionListener(this);
        textPanel.add(addRatingLabel);
        textPanel.add(rateList, BorderLayout.EAST);
        rateList.setBackground(Color.WHITE);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons in JFrame window
    private void setUpAddButton() {
        loadImages();
        addRestaurantButton = new JButton(addRestaurantIcon);
        addRestaurantButton.setText("Add restaurant");
        addRestaurantButton.setHorizontalTextPosition(JButton.RIGHT);

        buttonPanel.add(addRestaurantButton);
    }


    // MODIFIES: this
    // EFFECTS: sets up buttons in JFrame window
    private void setUpSaveButton() {
        loadImages();
        saveRestaurantButton = new JButton(saveRestaurantIcon);
        saveRestaurantButton.setText("Save");
        saveRestaurantButton.setActionCommand("save");

        buttonPanel.add(saveRestaurantButton);

    }

    // MODIFIES: this
    // EFFECTS: sets up buttons in JFrame window
    private void setUpLoadButton() {
        loadImages();
        loadRestaurantButton = new JButton(loadRestaurantIcon);
        loadRestaurantButton.setText("Load");
        loadRestaurantButton.setActionCommand("load");

        buttonPanel.add(loadRestaurantButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up print buttons in JFrame window
    private void setUpPrintButtons() {
        loadImages();
        printRestaurantsButton = new JButton(printerIcon);
        printRestaurantsButton.setText("Print");
        printRestaurantsButton.setActionCommand("print");

        printRestaurantsByPriceButton = new JButton(printPriceIcon);
        printRestaurantsByPriceButton.setText("Print by price");
        printRestaurantsByPriceButton.setActionCommand("print by price");

        printRestaurantsByLocationButton = new JButton(printLocationIcon);
        printRestaurantsByLocationButton.setText("Print by city");
        printRestaurantsByLocationButton.setActionCommand("print by location");

        buttonPanel.add(printRestaurantsButton);
        buttonPanel.add(printRestaurantsByPriceButton);
        buttonPanel.add(printRestaurantsByLocationButton);

    }

    // MODIFIES: this
    // EFFECTS: sets up clear button in JFrame window
    private void setUpClearButton() {
        clearAllButton = new JButton(clearScreenIcon);
        clearAllButton.setActionCommand("clear");

        buttonPanel.add(clearAllButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up quit button in JFrame window
    private void setUpQuitButton() {
        quitRestaurantButton = new JButton(quitIcon);
        quitRestaurantButton.setText("Quit");
        quitRestaurantButton.setActionCommand("quit");

        buttonPanel.add(quitRestaurantButton);

    }

    // MODIFIES: this
    // EFFECTS:  sets action commands to buttons
    private void setActionCommands() {
        addRestaurantButton.setActionCommand("add restaurant");
        saveRestaurantButton.setActionCommand("save");
        loadRestaurantButton.setActionCommand("load");
        clearAllButton.setActionCommand("clear");
        printRestaurantsButton.setActionCommand("print");
        printRestaurantsByPriceButton.setActionCommand("print by price");
        printRestaurantsByLocationButton.setActionCommand("print by location");
        quitRestaurantButton.setActionCommand("quit");
    }

    // EFFECTS: adds action listener to button
    private void addActionListener() {
        addRestaurantButton.addActionListener(this);
        saveRestaurantButton.addActionListener(this);
        loadRestaurantButton.addActionListener(this);
        clearAllButton.addActionListener(this);
        printRestaurantsButton.addActionListener(this);
        printRestaurantsButton.addActionListener(this);
        printRestaurantsButton.addActionListener(this);
        quitRestaurantButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "add restaurant":
                addRestaurantAction();

                break;
            case "save":
                saveAction();

                break;
            case "load":
                loadAction();

                break;
            case "clear":
                clearAllAction();

                break;
            case "quit":
                quitAction();
        }
    }

    public void printActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "print":
                printAction();

                break;
            case "print by price":
                printPriceAction();

                break;
            case "print by location":
                printLocationAction();
        }

    }

    // MODIFIES: this
    // EFFECTS: sets action to add restaurant button
    public void addRestaurantAction() {
        Restaurant r = new Restaurant(addRestaurantText.getText());
        restaurantModel.addElement(addRestaurantText.getText() + " has been added to your collection!");
        restaurantModel.addElement("");
        restaurantModel.addElement("Remember to click save!");
        r.setLocation(addLocationText.getText());
        r.setCuisineType(addCuisineTypeText.getText());
        r.setPriceValue(addPriceText.getText());
        r.setRating(Integer.parseInt(addRatingText.getText()));

        collection.addRestaurant(r);
        Toolkit.getDefaultToolkit().beep();
    }


    // MODIFIES: this
    // EFFECTS: sets action to print restaurant button
    public void printAction() {

        try {
            restaurantModel.removeAllElements();
            collection = jsonReader.read();
            for (Restaurant r : collection.getRestaurantCollection()) {
                restaurantModel.addElement(r.getRestaurantName());

            }
            Toolkit.getDefaultToolkit().beep();
        } catch (IOException e) {
            System.out.println("Unable to print from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets action to print restaurant by price button
    public void printPriceAction() {
        try {
            collection = jsonReader.read();
            JList<Restaurant> listRestaurant = new JList<>();
            for (Restaurant r : collection.getRestaurantCollection()) {
                if (r.getPriceValue().equals(addPriceText.getText())) {
                    listRestaurant.add(addRestaurantText);
                    restaurantModel.addElement(addRestaurantText.getText());
                }

            }
            Toolkit.getDefaultToolkit().beep();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        Toolkit.getDefaultToolkit().beep();
    }

    // MODIFIES: this
    // EFFECTS: sets action to print restaurant by location button
    public void printLocationAction() {
        Toolkit.getDefaultToolkit().beep();

    }

    public void clearAllAction() {
        restaurantModel.removeAllElements();
        Toolkit.getDefaultToolkit().beep();
    }

    public void quitAction() {

        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
        }
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: sets action to save restaurant button
    public void saveAction() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.collection);
            this.jsonWriter.close();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this,
                    "Saved " + this.collection.getName() + " to ./data/restaurant.json");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: sets action to load restaurant button
    public void loadAction() {
        try {
            restaurantModel.removeAllElements();
            collection = jsonReader.read();
            for (Restaurant r : collection.getRestaurantCollection()) {
                restaurantModel.addElement(r.getRestaurantName());

                r.setLocation(addLocationText.getText());
                restaurantModel.addElement(addLocationText.getText());

                r.setCuisineType(addCuisineTypeText.getText());
                restaurantModel.addElement(addCuisineTypeText.getText());

                r.setPriceValue(addPriceText.getText());
                restaurantModel.addElement(addPriceText.getText());

                restaurantModel.addElement(addRatingText.getText());

            }
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this,
                    "Loaded " + this.collection.getName() + " from ./data/restaurant.json");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
