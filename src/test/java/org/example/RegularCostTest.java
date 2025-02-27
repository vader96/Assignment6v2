package org.example;

import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.RegularCost;
import org.example.Amazon.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularCostTest {
    private final RegularCost regularCost = new RegularCost();

    @Test
    @DisplayName("specification-based")
    void testCalculateTotalItemCost() {
        // This test method is used to simply verify that the regular cost correctly calculates the price.
        // This is the list of items to be used.
        List<Item> cart = List.of(
                new Item(ItemType.OTHER, "Notebook", 2, 15.0),
                new Item(ItemType.OTHER, "Pen", 1, 5.0)
        );
        // Asserts that the price calculation is accurate
        assertEquals(35, regularCost.priceToAggregate(cart));
    }

    @Test
    @DisplayName("specification-based")
    void testEmptyCart() {
        // An empty cart should have no price in it.
        List<Item> cart = List.of();
        assertEquals(0.0, regularCost.priceToAggregate(cart));
    }
}
