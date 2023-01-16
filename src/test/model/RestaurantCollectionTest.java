package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for RestaurantCollection
public class RestaurantCollectionTest {
    private Restaurant testRestaurant1;
    private Restaurant testRestaurant2;
    private RestaurantCollection testRestaurantCollection;

    @BeforeEach
    void runBefore() {
        testRestaurant1 = new Restaurant("Pacific Poke");
        testRestaurant2 = new Restaurant("McDonald's");
        testRestaurantCollection = new RestaurantCollection("Visited");
    }

    @Test
    void testAddRestaurant() {
        testRestaurantCollection.addRestaurant(testRestaurant1);
        testRestaurantCollection.addRestaurant(testRestaurant2);
    }

    @Test
    void testGetRestaurant(){
        testRestaurantCollection.addRestaurant(testRestaurant1);
        testRestaurantCollection.addRestaurant(testRestaurant2);

        assertEquals(testRestaurant1, testRestaurantCollection.getRestaurant("Pacific Poke"));
        assertEquals(testRestaurant2, testRestaurantCollection.getRestaurant("McDonald's"));
        assertEquals(null, testRestaurantCollection.getRestaurant("Police Station"));

    }

    @Test
    void testGetListOfRestaurantsVisited() {
        testRestaurantCollection.addRestaurant(testRestaurant1);

        assertEquals(1, testRestaurantCollection.getRestaurantsVisited().size());
        assertEquals(testRestaurant1, testRestaurantCollection.getRestaurant("Pacific Poke"));

    }

    @Test
    void testGetName() {
        assertEquals("Visited", testRestaurantCollection.getName());
    }

    @Test
    void testGetRestaurantCollection() {
        testRestaurantCollection.addRestaurant(testRestaurant1);
        testRestaurantCollection.addRestaurant(testRestaurant2);
        assertTrue(testRestaurantCollection.isRestaurantInCollection(testRestaurant1.getRestaurantName()));
        assertEquals(2, testRestaurantCollection.getRestaurantCollection().size());
    }

    @Test
    void testRestaurantNotInCollection() {
        testRestaurantCollection.addRestaurant(testRestaurant1);
        assertFalse(testRestaurantCollection.isRestaurantInCollection(testRestaurant2.getRestaurantName()));
        assertEquals(1, testRestaurantCollection.getRestaurantCollection().size());
    }

}
