package ui;

import model.Restaurant;
import model.RestaurantCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Restaurant tracker application
public class RestaurantTracker {
    private static final String SELECT_VISITED_COMMAND = "v";

    private static final String JSON_STORE = "./data/restaurant.json";
    private Scanner input;
    private Restaurant restaurant;
    private RestaurantCollection collection;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs restaurant tracker application
    public RestaurantTracker() throws FileNotFoundException {
        input = new Scanner(System.in);
        collection = new RestaurantCollection("Restaurants Visited");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRestaurantTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runRestaurantTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you! See you soon!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals(SELECT_VISITED_COMMAND)) {
            addRestaurantVisited();
        } else if (command.equals("p")) {
            printRestaurantVisited(collection);
        } else if (command.equals("s")) {
            saveRestaurantCollection();
        } else if (command.equals("l")) {
            loadRestaurantCollection();
        } else if (command.equals("sel")) {
            selectRestaurant();
        } else if (command.equals("pp")) {
            printByPrice();
        } else if (command.equals("pl")) {
            printByLocation();
        } else {
            System.out.println("Selection invalid...");
        }
    }

    private void processRestaurantCommand(Restaurant  restaurant) {
        String str = input.next();

        if (str.equals("l")) {
            visitedLocation(restaurant);
        } else if (str.equals("c")) {
            visitedCuisineType(restaurant);
        } else if (str.equals("p")) {
            visitedPrice(restaurant);
        } else if (str.equals("r")) {
            visitedRating(restaurant);
        } else if (str.equals("h")) {
            System.out.println("Heading back to main menu...");
        } else {
            System.out.println("Sorry, I do not understand your command. Please try again.");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes restaurant collection
    public void init() {
        collection = new RestaurantCollection("Restaurants Visited");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> add restaurant to visited");
        System.out.println("\ts -> save restaurant collection to file");
        System.out.println("\tl -> load restaurant collection from file");
        System.out.println("\tsel -> select restaurant from collection to edit");
        System.out.println("\tp -> print restaurants");
        System.out.println("\tpp -> filter restaurant by price");
        System.out.println("\tpl -> filter restaurant by location");
        System.out.println("\tq -> to quit program");

    }

    // EFFECTS: displays menu of options to user after they have selected add
    private void displayRestaurantMenu(Restaurant restaurant) {
        System.out.println("\nSelect from:");
        System.out.println("\tl <- edit location");
        System.out.println("\tc <- edit cuisine type");
        System.out.println("\tp <- edit price value");
        System.out.println("\tr <- edit rating");
        System.out.println("\th -> go home to main menu");
        processRestaurantCommand(restaurant);
    }


    // MODIFIES: this
    // EFFECTS: adds new restaurant visited to the collection
    private void addRestaurantVisited() {
        System.out.println("Please enter the name of the restaurant you visited:");
        String restaurantName = input.next();

        System.out.println("Enter in the city " + restaurantName + " is located in");
        String city = input.next();
        System.out.println(restaurantName + " location has been set to " + city);

        System.out.println("Specify what type of cuisine " + restaurantName + "'s is:");
        String cui = input.next();
        System.out.println(restaurantName + "'s cuisine type has been set to " + cui);

        System.out.println("Set " + restaurantName + " price value as one of: $, $$, $$$, or $$$$");
        String prc = input.next();
        System.out.println(restaurantName + " has been set to " + prc);

        System.out.println("Rate " + restaurantName + " from 1 to 5 stars");
        String rate = input.next();
        int rateInt = Integer.parseInt(rate);
        System.out.println(restaurantName + " has been set to " + rate + " stars");

        Restaurant r = new Restaurant(restaurantName, city, cui, rateInt, prc);
        collection.addRestaurant(r);
    }


    private void selectRestaurant() {
        System.out.println("Indicate which restaurant you wish to select: [restaurant name]");
        String restaurantName = input.next();
        if (collection.isRestaurantInCollection(restaurantName)) {
            System.out.println("You have selected: " + restaurantName);
            this.restaurant = collection.getRestaurant(restaurantName);
            displayRestaurantMenu(restaurant);
        }
    }


    // EFFECTS: prints list of restaurants by price
    private void printByPrice() {
        System.out.println("Enter in the price point you wish to filter the restaurants by:");
        String prc = input.next();

        List<String> restaurantByPrice = new ArrayList<>();

        for (Restaurant restaurant : collection.getRestaurantCollection()) {
            if (restaurant.getPriceValue().equals(prc)) {
                restaurantByPrice.add(restaurant.getRestaurantName());
            }
        }
        System.out.println(restaurantByPrice);
    }

    // EFFECTS: prints list of restaurants by location
    private void printByLocation() {
        System.out.println("Enter in the location you wish to filter the restaurants by:");
        String loc = input.next();

        List<String> restaurantByLocation = new ArrayList<>();

        for (Restaurant restaurant : collection.getRestaurantCollection()) {
            if (restaurant.getLocation().equals(loc)) {
                restaurantByLocation.add(restaurant.getRestaurantName());
            }
        }
        System.out.println(restaurantByLocation);
    }


    // EFFECTS: prints collection of restaurants
    private void printRestaurantVisited(RestaurantCollection restaurantVisitedCollection) {
        System.out.println("Restaurants: " + collection.getRestaurantsVisited());
    }

    // EFFECTS: adds rating from 1 to 5 to restaurant with user input
    private void visitedRating(Restaurant restaurantVisited) {
        System.out.println("rate " + restaurantVisited.getRestaurantName() + " from 1 to 5 stars");
        int rating = input.nextInt();

        restaurantVisited.setRating(rating);
        System.out.println(restaurantVisited.getRestaurantName() + " has been set to " + rating + " stars");
        displayRestaurantMenu(restaurantVisited);
    }

    // EFFECTS: adds price value to restaurant with user input
    private void visitedPrice(Restaurant restaurantVisited) {
        System.out.println("set " + restaurantVisited.getRestaurantName()
                + " price value as one of: $, $$, $$$, or $$$$");
        String pricing = input.next();

        restaurantVisited.setPriceValue(pricing);
        System.out.println(restaurantVisited.getRestaurantName() + " has been set to " + pricing);
        displayRestaurantMenu(restaurantVisited);
    }

    // EFFECTS: adds cuisine type of restaurant with user input
    private void visitedCuisineType(Restaurant restaurantVisited) {
        System.out.println("Specify the type of cuisine " + restaurantVisited.getRestaurantName());
        String type = input.next();

        restaurantVisited.setCuisineType(type);
        System.out.println(restaurantVisited.getRestaurantName() + "'s cuisine type has been set to " + type);
        displayRestaurantMenu(restaurantVisited);
    }

    // EFFECTS: adds location (city) of restaurant with user input
    private void visitedLocation(Restaurant restaurantVisited) {
        System.out.println("Enter in the city " +  restaurantVisited.getRestaurantName() + " is located in");
        String city = input.next();

        restaurantVisited.setLocation(city);
        System.out.println(restaurantVisited.getRestaurantName() + " location has been set to " + city);
        displayRestaurantMenu(restaurantVisited);
    }

    // citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: saves the restaurant collection to file
    private void saveRestaurantCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved " + collection.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads restaurant from file
    private void loadRestaurantCollection() {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded " + collection.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

