package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Cost.*;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AmazonUnitTest {
    private Amazon amazon;
    private ShoppingCart fakeCart;
    private List<PriceRule> fakeRules;

    @BeforeEach
    void setUp() {
        // Sets up the mocked version of the dependencies.
        fakeCart = mock(ShoppingCart.class);
        fakeRules = List.of(mock(RegularCost.class), mock(DeliveryPrice.class), mock(ExtraCostForElectronics.class));
        amazon = new Amazon(fakeCart, fakeRules);
    }

    @Test
    @DisplayName("specification-based")
    void testAddToCart() {
        // This unit test confirms that the mocked cart added the item to itself.
        Item item = new Item(ItemType.ELECTRONIC, "Phone", 1, 10.0);
        // Adds the item to the cart.
        amazon.addToCart(item);
        // Verifies that the cart added the item.
        verify(fakeCart).add(item);
    }

    @Test
    @DisplayName("specification-based")
    void testAddToCartWithEmpty() {
        // This test method is to ensure that when calculate() gets called, it handles the empty cart correctly.
        when(fakeCart.getItems()).thenReturn(List.of());
        // Verifies that the empty cart correctly returns 0.0.
        assertEquals(0.0, amazon.calculate());
    }

    @Test
    @DisplayName("specification-based")
    void testCalculatePrices() {
        // Creates the list of fake item.
        List<Item> fakeItems = List.of(new Item(ItemType.ELECTRONIC, "Tablet", 1, 10.0));
        when(fakeCart.getItems()).thenReturn(fakeItems);
        // Mocks the behavior of the price rules.
        for (PriceRule rule : fakeRules) {
            when(rule.priceToAggregate(any())).thenReturn(10.0);
        }
        assertEquals(30.0, amazon.calculate());
        // Verifies that all price rules were called.
        for (PriceRule rule : fakeRules) {
            verify(rule).priceToAggregate(any());
        }
    }

    @Test
    @DisplayName("structural-based")
    void testCalculateWithNoPriceRules() {
        // This unit test confirms that an empty price rule results in 0 being returned.
        amazon = new Amazon(fakeCart, List.of());  // No price rules

        // Create the fakeItems and stubs them to the getItem method.
        List<Item> fakeItems = List.of(new Item(ItemType.ELECTRONIC, "Tablet", 1, 10.0));
        when(fakeCart.getItems()).thenReturn(fakeItems);

        // Asserts that the returned value is 0 when an empty price rule is given.
        assertEquals(0.0, amazon.calculate());
    }
}
