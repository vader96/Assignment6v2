package org.example;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryPriceTest {
    private final DeliveryPrice deliveryPrice = new DeliveryPrice();

    @Test
    @DisplayName("specification-based")
    void testZeroItems() {
        // When no items are in the cart, the delivery price should be 0, which this test case tests.
        List<Item> emptyCart = List.of();
        assertEquals(0.0, deliveryPrice.priceToAggregate(emptyCart));
    }

    @Test
    @DisplayName("structural-based")
    void testFourToTenItems() {
        // This test case is to test when there are 4-10 items in the cart.
        List<Item> items = List.of(
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0)
        );
        assertEquals(12.5, deliveryPrice.priceToAggregate(items));
    }

    @Test
    @DisplayName("structural-based")
    void testMoreThanTenItems() {
        // Create a list of eleven books, and tests to see that the max delivery price is returned.
        List<Item> items = List.of(
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0),
                new Item(ItemType.OTHER, "Book", 4, 5.0)
        );
        assertEquals(20.0, deliveryPrice.priceToAggregate(items));
    }
}
