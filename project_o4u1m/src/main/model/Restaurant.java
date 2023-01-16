package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a visited restaurant with the name of the restaurant, a rating, price value, location, and type of cuisine
public class Restaurant implements Writable {
    private String restaurantName;
    private String locationCity; // e.g. Vancouver, Richmond, Burnaby
    private String cuisineType; // e.g. Chinese, Japanese, Italian, Indian
    private int rate; // 1, 2, 3, 4, 5 stars
    private String price; // represented by $, $$, $$$, $$$$

    // MODIFIES: this
    // EFFECTS: constructs a new restaurant with a name, number rating,
    // price value, location, and type of cuisine
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
        this.locationCity = "city";
        this.cuisineType = "cuisine";
        this.rate = 0;
        this.price = "pricing";
    }

    public Restaurant(String name, String city, String type, int rating, String price) {
        this.restaurantName = name;
        this.locationCity = city;
        this.cuisineType = type;
        this.rate = rating;
        this.price = price;

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setLocation(String cityLocation) {
        EventLog.getInstance().logEvent(new Event("Set Location: " + cityLocation));
        this.locationCity = cityLocation;
    }

    public String getLocation() {
        return locationCity;
    }

    public void setCuisineType(String typeOfCuisine) {
        EventLog.getInstance().logEvent(new Event("Set Cuisine Type: " + typeOfCuisine));
        this.cuisineType = typeOfCuisine;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    // MODIFIES: this
    // EFFECTS: sets restaurant rating to one of: 1, 2, 3, 4, 5 (stars)
    public void setRating(int rating) {
        EventLog.getInstance().logEvent(new Event("Set Rating: " + rating));
        if (rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5) {
            this.rate = rating;
        } else {
            rate = 0;
        }
    }

    public int getRating() {
        return rate;
    }

    // MODIFIES: this
    // EFFECTS: sets restaurant price value to one of: $, $$, $$$, $$$$
    public void setPriceValue(String pricing) {
        EventLog.getInstance().logEvent(new Event("Set Pricing: " + pricing));
        if (pricing.equals("$") || pricing.equals("$$") || pricing.equals("$$$") || pricing.equals("$$$$")) {
            this.price = pricing;
        } else {
            this.price = "Not a valid input";
        }
    }

    public String getPriceValue() {
        return price;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", restaurantName);
        json.put("location", locationCity);
        json.put("type", cuisineType);
        json.put("rate", rate);
        json.put("price", price);
        return json;
    }

}
