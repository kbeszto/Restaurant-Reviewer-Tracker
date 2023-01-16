package persistence;

import model.Restaurant;
import model.RestaurantCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRestaurant(String name, Restaurant restaurant) {
        assertEquals(name, restaurant.getRestaurantName());

    }
}
