package persistence;

import model.Restaurant;
import model.RestaurantCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestaurantCollection rc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRestaurantCollection() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyRestaurantCollection.json");
        try {
            RestaurantCollection rc = reader.read();
            assertEquals("My restaurant collection", rc.getName());
            assertEquals(0, rc.getRestaurantCollection().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRestaurantCollection() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralRestaurantCollection.json");
        try {
            RestaurantCollection rc = reader.read();
            assertEquals("My restaurant collection", rc.getName());
            List<Restaurant> restaurant = rc.getRestaurantCollection();
            assertEquals(2, restaurant.size());
            checkRestaurant("restaurant1", restaurant.get(0));
            checkRestaurant("restaurant2", restaurant.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
