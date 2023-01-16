package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private Restaurant testRestaurant1;
    private Restaurant testRestaurant2;
    private Restaurant testRestaurant3;

    @BeforeEach
    void runBefore() {
        testRestaurant1 = new Restaurant("Pacific Poke");
        testRestaurant2 = new Restaurant("McDonald's");
        testRestaurant3 = new Restaurant("Aji Sai", "Vancouver", "Japanese", 5, "$$");
    }

    @Test
    void testRestaurantConstructor() {
        assertEquals("Pacific Poke", testRestaurant1.getRestaurantName());
        assertEquals(0, testRestaurant2.getRating());

    }

    @Test
    void testRestaurantConstructor2() {
        assertEquals("Aji Sai", testRestaurant3.getRestaurantName());
        assertEquals("Vancouver", testRestaurant3.getLocation());
        assertEquals("Japanese", testRestaurant3.getCuisineType());
        assertEquals(5, testRestaurant3.getRating());
        assertEquals("$$", testRestaurant3.getPriceValue());

    }

    @Test
    void testGetRestaurantName() {
        assertEquals(testRestaurant1.getRestaurantName(), "Pacific Poke");
        assertEquals(testRestaurant2.getRestaurantName(), "McDonald's");

    }
    @Test
    void testGetRestaurantLocation() {
        testRestaurant1.setLocation("Vancouver");
        assertEquals("Vancouver", testRestaurant1.getLocation());
    }

    @Test
    void testGetRestaurantCuisineType() {
        testRestaurant2.setCuisineType("Italian");
        assertEquals("Italian", testRestaurant2.getCuisineType());

    }

    @Test
    void testSetRating1() {
        testRestaurant1.setRating(5);
        testRestaurant2.setRating(1);
        assertEquals(testRestaurant1.getRating(), 5);
        assertEquals(testRestaurant2.getRating(), 1);

    }

    @Test
    void testSetRating2() {
        testRestaurant1.setRating(2);
        testRestaurant2.setRating(6);
        assertEquals(testRestaurant1.getRating(), 2);
        assertEquals(testRestaurant2.getRating(), 0);
    }

    @Test
    void testSetRating3() {
        testRestaurant1.setRating(10);
        testRestaurant2.setRating(3);
        assertEquals(testRestaurant1.getRating(), 0);
        assertEquals(testRestaurant2.getRating(), 3);
    }

    @Test
    void testSetRating4() {
        testRestaurant1.setRating(4);
        assertEquals(testRestaurant1.getRating(), 4);
    }

    @Test
    void testSetRating5() {
        testRestaurant1.setRating(5);
        assertEquals(testRestaurant1.getRating(), 5);
    }

    @Test
    void testSetPriceValue$() {
        testRestaurant1.setPriceValue("$");
        testRestaurant2.setPriceValue("$$$$$$$");
        assertEquals(testRestaurant1.getPriceValue(), "$");
        assertEquals(testRestaurant2.getPriceValue(), "Not a valid input");

    }

    @Test
    void testSetPriceValue$$() {
        testRestaurant1.setPriceValue("$$");
        assertEquals(testRestaurant1.getPriceValue(), "$$");
    }

    @Test
    void testSetPriceValue$$$() {
        testRestaurant1.setPriceValue("CHEAP");
        testRestaurant2.setPriceValue("$$$");
        assertEquals(testRestaurant1.getPriceValue(), "Not a valid input");
        assertEquals(testRestaurant2.getPriceValue(), "$$$");
    }

    @Test
    void testSetPriceValue$$$$() {
        testRestaurant1.setPriceValue("$$$$");
        testRestaurant2.setPriceValue("$$$$");
        assertEquals(testRestaurant1.getPriceValue(), "$$$$");
        assertEquals(testRestaurant2.getPriceValue(), "$$$$");
    }


}