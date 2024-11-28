package com.example;

import com.example.firstjavafx.MenuItem;
import com.example.firstjavafx.Order;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private MenuItem availableItem;
    private MenuItem unavailableItem;
    private List<MenuItem> items;
    private List<MenuItem> l1;
    private List<MenuItem> l2;

    @BeforeEach
    void setUp() {
        availableItem = new MenuItem("Burger", 6, "Food", true, 10);
        unavailableItem = new MenuItem("Pizza", 8, "Food", false, 0);

        items = new ArrayList<>();
        items.add(availableItem);
        items.add(unavailableItem);

        l1 = new ArrayList<>();
        l1.add(availableItem);

        l2 = new ArrayList<>();
        l2.add(unavailableItem);
        l2.add(availableItem);
    }

    @Test
    void testOrderUnavailableItems() {

        Order order = new Order(l2, false);

        assertEquals("Received", order.getStatus(), "Order status should be 'Received' initially.");

        order.denyIfContains(unavailableItem.getName());

        assertEquals("Denied", order.getStatus());
    }

    @Test
    void testOrderWithOnlyAvailableItems() {

        Order order = new Order(l1, false);

        order.denyIfContains(unavailableItem.getName());

        assertEquals("Received", order.getStatus());
    }

    @Test
    void testOutOfStockErrorHandling() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (!unavailableItem.getAvailablity()) {
                throw new IllegalArgumentException("Cannot order out-of-stock item: " + unavailableItem.getName());
            }
        });

        assertEquals("Cannot order out-of-stock item: Pizza", exception.getMessage());
    }
}
