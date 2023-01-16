package persistence;

import model.RestaurantCollection;
import model.Restaurant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RestaurantCollection rc = new RestaurantCollection("My restaurant collection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRestaurantCollection() {
        try {
            RestaurantCollection rc = new RestaurantCollection("My restaurant collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestaurantCollection.json");
            writer.open();
            writer.write(rc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRestaurantCollection.json");
            rc = reader.read();
            assertEquals("My restaurant collection", rc.getName());
            assertEquals(0, rc.getRestaurantsVisited().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRestaurantCollection() {
        try {
            RestaurantCollection rc = new RestaurantCollection("My restaurant collection");
            rc.addRestaurant(new Restaurant("restaurant1"));
            rc.addRestaurant(new Restaurant("restaurant2"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRestaurantCollection.json");
            writer.open();
            writer.write(rc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRestaurantCollection.json");
            rc = reader.read();
            assertEquals("My restaurant collection", rc.getName());
            List<Restaurant> restaurants = rc.getRestaurantCollection();
            assertEquals(2, restaurants.size());
            checkRestaurant("restaurant1", restaurants.get(0));
            checkRestaurant("restaurant2", restaurants.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
