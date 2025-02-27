package org.example;

import org.example.Barnes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BarnesAndNobleTest {
    private BarnesAndNoble barnesAndNoble;
    private BookDatabase bookDatabase;
    private BuyBookProcess buyBookProcess;

    @BeforeEach
    // Resets the database each time to ensure no reuse of the same database.
    void setUp() {
        bookDatabase = mock(BookDatabase.class);
        buyBookProcess = mock(BuyBookProcess.class);
        barnesAndNoble = new BarnesAndNoble(bookDatabase, buyBookProcess);
    }

    @Test
    @DisplayName("specification-based")
    // The reasoning behind this test case was what happens when a null input is given, I believe that the
    // method should handle it by simply returning null to inform the user that nothing can happen.
    void testGetPriceForCartNullOrder() {
        // When null is given, the method should return null.
        assertNull(barnesAndNoble.getPriceForCart(null));
    }

    @Test
    @DisplayName("specification-based")
    // The reasoning behind this test case was what happens when there is enough stock for the person to purchase.
    // I believe that the order should go through and that the results should reflect the order happening.
    void testGetPriceForCartCorrectTotalPrice() {
        // Fakes the two books and stubs the database to return these values.
        Book book1 = new Book("12345", 10, 5);
        Book book2 = new Book("54321", 30, 7);
        when(bookDatabase.findByISBN("12345")).thenReturn(book1);
        when(bookDatabase.findByISBN("54321")).thenReturn(book2);

        // Creates the 2 orders
        Map<String, Integer> order = new HashMap<>();
        order.put("12345", 3);
        order.put("54321", 5);

        // Get the purchase summary which will be used to verify if the methods were called correctly.
        PurchaseSummary summary = barnesAndNoble.getPriceForCart(order);

        // Verify that the totalPrice is 180. (5 * 30) + (3 * 10)
        assertEquals(180, summary.getTotalPrice());

        // Verifies that the method buyBook was actually called.
        verify(buyBookProcess).buyBook(book1, 3);
        verify(buyBookProcess).buyBook(book2, 5);
    }

    @Test
    @DisplayName("specification-based")
    // The reasoning behind this test case was what happens if there is not enough of the book in stock,
    // I believe that the method should handle it by allowing the purchase to happen but only to what books are available.
    void testGetPriceForCartWithNotEnoughInventory() {
        // Creates the fake book with only two available.
        Book book = new Book("12345", 15, 2);
        when(bookDatabase.findByISBN("12345")).thenReturn(book);

        // Place an order for ten, but only 2 in stock.
        Map<String, Integer> order = new HashMap<>();
        order.put("12345", 10);

        PurchaseSummary summary = barnesAndNoble.getPriceForCart(order);

        // Should only be 2 books bought for a total of 30
        assertEquals(30, summary.getTotalPrice());

        // Checks to see that it is unavailable for the remaining 8 books.
        assertEquals(1, summary.getUnavailable().size());
        assertEquals(8, summary.getUnavailable().get(book));

        // Verifies that buyBook was called with just the available stock.
        verify(buyBookProcess).buyBook(book, 2);
    }

    // My specification-based tests already covered every scenario in BarnesAndNoble
    // However, the Book.java class was not fully covered. In particular, the equals() method was
    // not covered and this meant that I needed to write test cases on it to ensure 100% branch coverage.

    @Test
    @DisplayName("structural-based")
    void testBookSameObject() {
        // Tests the Book.java file equals method. This simply check whether a book object equals the same book object.
        Book book = new Book("12345", 10, 2);
        assertTrue(book.equals(book));
    }

    @Test
    @DisplayName("structural-based")
    void testBookEqualsDifferentClass() {
        // Tests the Book.java file equals method. This simply check whether a book object does not equal a string object.
        Book book = new Book("12345", 20, 5);
        String differentObject = "Not a Book";
        assertFalse(book.equals(differentObject));
    }

    @Test
    @DisplayName("structural-based")
    void testBookEqualsNullObject() {
        // Tests the Book.java file equals method. This simply check whether a book object does not equal a null object.
        Book book = new Book("12345", 20, 5);
        assertFalse(book.equals(null));
    }
}
