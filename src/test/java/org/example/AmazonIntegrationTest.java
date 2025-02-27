package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Cost.*;
import org.example.Amazon.Database;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCartAdaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {
    private Amazon amazon;
    private Database database;
    private ShoppingCartAdaptor shoppingCart;

    @BeforeEach
    void setUp() {
        // Sets up the new Database, ShoppingCart, PriceRule and Amazon instance to see whether they all work together.
        database = new Database();
        database.resetDatabase();
        shoppingCart = new ShoppingCartAdaptor(database);
        List<PriceRule> rules = List.of(new RegularCost(), new DeliveryPrice(), new ExtraCostForElectronics());
        amazon = new Amazon(shoppingCart, rules);
    }

    @Test
    @DisplayName("specification-based")
    void testCartStoresAndRetrieveItems() {
        // This test case is to see whether the cart store the items and doesn't mess up the order.
        // Adds the items to the shopping cart.
        Item item1 = new Item(ItemType.OTHER, "Notebook", 1, 5.0);
        Item item2 = new Item(ItemType.ELECTRONIC, "Headphone", 1, 10.0);
        shoppingCart.add(item1);
        shoppingCart.add(item2);

        // Retrieves the items and puts them into a list.
        List<Item> retrievedItems = shoppingCart.getItems();

        // Should be a size of two and checks to see that the order in which they were inserted hasn't changed.
        assertEquals(2, retrievedItems.size());
        assertEquals("Notebook", retrievedItems.get(0).getName());
        assertEquals("Headphone", retrievedItems.get(1).getName());
    }

    @Test
    @DisplayName("specification-based")
    void testAmazonCalculate() {
        // This test case is to see whether Amazon can integrate with real data and whether everything works smoothly.
        // Adds the items to the shoppingCart
        shoppingCart.add(new Item(ItemType.OTHER, "Notebook", 1, 20.0));
        shoppingCart.add(new Item(ItemType.ELECTRONIC, "Phone", 1, 40.0));

        // Calculates the total Cost accounting for everything.
        double expectedTotal = (1 * 20.0) + (1 * 40.0) + 5 + 7.50;

        // Asserts that the calculated value is accurate to what is expected.
        assertEquals(expectedTotal, amazon.calculate());
    }

    @Test
    @DisplayName("specification-based")
    void testDatabaseClearsShoppingCart() {
        // Test case to help see whether the real database and shopping cart can work together smoothly.
        // Adds the items into the shopping cart.
        shoppingCart.add(new Item(ItemType.OTHER, "Notebook", 1, 20.0));
        shoppingCart.add(new Item(ItemType.ELECTRONIC, "Phone", 1, 40.0));

        // Resets the database, which means that everything should be deleted now from the shopping cart.
        database.resetDatabase();

        // Asserts that the shopping cart doesn't actually have items in it still.
        assertEquals(0, shoppingCart.getItems().size());
    }

    @Test
    @DisplayName("specification-based")
    void testNumberOfItemsInCart() {
        // Verifies that the shopping cart counts the updates correctly and returns the correct number of items.
        // Adds the items to the shopping cart.
        shoppingCart.add(new Item(ItemType.OTHER, "Notebook", 1, 10.0));
        shoppingCart.add(new Item(ItemType.OTHER, "Pen", 2, 2.0));
        // There should be two unique items in the cart.
        assertEquals(2, shoppingCart.numberOfItems());
    }

    @Test
    @DisplayName("specification-based")
    void testAddItem() {
        // Ensures that the items can be added to the cart
        Item item = new Item(ItemType.OTHER, "Notebook", 2, 10.0);
        shoppingCart.add(item);
        // Verifies that the item was added to the cart.
        assertEquals(1, shoppingCart.getItems().size());
    }

    @Test
    @DisplayName("structural-based")
    void testAmazonCalculateWithNoElectronicItems() {
        // This test method is to cover the scenario where there are no electronic devices, this means
        // that there should be no additional charge for an electronic.
        // Adds only one non-electronic item
        shoppingCart.add(new Item(ItemType.OTHER, "Book", 1, 15.0));

        // Expected cost: Regular price + Delivery price (no extra charge for electronics)
        double expectedTotal = 15 + 5.0 + 0.0;

        // Ensure the total price matches expected cost
        assertEquals(expectedTotal, amazon.calculate());
    }
}
