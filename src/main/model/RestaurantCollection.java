package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents collection of visited restaurants
public class RestaurantCollection implements Writable {
    private String name;
    private LinkedList<Restaurant> collection;

    // EFFECTS: constructs a new list of restaurants
    public RestaurantCollection(String collectionName) {
        this.name = collectionName;
        collection = new LinkedList<>();

    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns an unmodifiable list of restaurants in the collection
    public List<Restaurant> getRestaurantCollection() {
        return Collections.unmodifiableList(collection);
    }

    // MODIFIES: this
    // EFFECTS: adds restaurant to list of restaurants visited
    public void addRestaurant(Restaurant restaurant) {
        EventLog.getInstance().logEvent(new Event("Added Restaurant: " + restaurant));
        this.collection.add(restaurant);
    }

    // EFFECTS: returns the names of all the restaurants visited in the collection
    public ArrayList<String> getRestaurantsVisited() {
        ArrayList<String> restaurantNameList = new ArrayList<>();
        for (Restaurant restaurant : collection) {
            restaurantNameList.add(restaurant.getRestaurantName());
        }
        return restaurantNameList;
    }

    // REQUIRES: non-empty collection
    // EFFECTS: returns specific restaurant from the collection given the restaurant name,
    //          return null if restaurant name not found
    public Restaurant getRestaurant(String restaurantName) {
        for (Restaurant restaurant : collection) {
            if (restaurantName.equals(restaurant.getRestaurantName())) {
                return restaurant;
            }
        }
        return null;
    }

    public boolean isRestaurantInCollection(String restaurantName) {
        for (Restaurant restaurant : collection) {
            if (restaurantName.equals(restaurant.getRestaurantName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns restaurants in this restaurant collection as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();
        Iterator restaurant = this.collection.iterator();

        for (Restaurant r : collection) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

}
