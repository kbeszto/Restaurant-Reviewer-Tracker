package persistence;


import model.RestaurantCollection;
import model.Restaurant;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads restaurant collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads restaurant collection from file and returns it
    public RestaurantCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestaurantCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses restaurant collection from JSON object and returns it
    private RestaurantCollection parseRestaurantCollection(JSONObject jsonObject) {
        String collectionName = jsonObject.getString("name");
        RestaurantCollection rc = new RestaurantCollection(collectionName);
        addRestaurants(rc, jsonObject);
        return rc;
    }

    // MODIFIES: rc
    // EFFECTS: parses restaurants from JSON object and adds them to restaurant collection
    private void addRestaurants(RestaurantCollection rc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(rc, nextRestaurant);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses restaurant from JSON object and adds it to restaurant
    private void addRestaurant(RestaurantCollection rc, JSONObject jsonObject) {
        String restaurantName = jsonObject.getString("name");
        Restaurant restaurant = new Restaurant(restaurantName);
        rc.addRestaurant(restaurant);

    }

}

