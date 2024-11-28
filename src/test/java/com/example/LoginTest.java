package com.example;

import com.example.firstjavafx.Customer;
import com.example.firstjavafx.InvalidLoginException;
import com.example.firstjavafx.UserManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private static HashMap<String, Customer> customers;
    @BeforeAll
    static void setup() {
        customers = new HashMap<>();
        customers.put("d", new Customer("Dhruv", true, "d", "d"));
        UserManager.saveCustomers(customers);
    }


    @Test
    void testWrongEmail() {
        String email = "invalid@example.com";
        String password = "d";
        InvalidLoginException exception = assertThrows(InvalidLoginException.class, () -> {
            if (!customers.containsKey(email)) {
                throw new InvalidLoginException("Invalid email");
            }
        });
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void testWrongPassword() {
        String email = "d";
        String password = "wrongPassword";
        InvalidLoginException exception = assertThrows(InvalidLoginException.class, () -> {
            Customer customer = customers.get(email);
            if (!customer.getPassword().equals(password)) {
                throw new InvalidLoginException("Invalid password");
            }
        });
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testRightLogin() {
        String email = "d";
        String password = "d";
        //using assertDoesNotThrow() to check for entirely correct login
        assertDoesNotThrow(() -> {
            Customer customer = customers.get(email);
            if (!customer.getPassword().equals(password)) {
                throw new InvalidLoginException("Invalid password");
            }
        });
    }
}
