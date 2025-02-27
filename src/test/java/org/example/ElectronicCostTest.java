package org.example;

import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectronicCostTest {
    private final ExtraCostForElectronics extraCostForElectronics = new ExtraCostForElectronics();

    @Test
    @DisplayName("specification-based")
    void testNoElectronicItem() {
        // This test method is used to simply verify that the electronic cost is correctly used to calculate the price.
        // If there is no electronic device in the cart, there should be no additional charge added.
        List<Item> cart = List.of(new Item(ItemType.OTHER, "Book", 1, 10.0));
        assertEquals(0.0, extraCostForElectronics.priceToAggregate(cart));
    }

    @Test
    @DisplayName("specification-based")
    void testWithElectronicItem() {
        // If there is an electronic device in the cart, there should be an additional electronic charge.
        List<Item> cart = List.of(
                new Item(ItemType.OTHER, "Book", 1, 10.0),
                new Item(ItemType.ELECTRONIC, "Laptop", 1, 20.0)
        );
        // Since there is an electronic in this cart, there should be an additional 7.5 added to the total.
        assertEquals(7.50, extraCostForElectronics.priceToAggregate(cart));
    }
}
