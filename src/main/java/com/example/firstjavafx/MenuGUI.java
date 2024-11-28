package com.example.firstjavafx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuGUI extends JFrame {
    //JFrame is the main container for my graphical interface
    private Admin admin;

    public MenuGUI(Admin admin) {
        this.admin = admin;
        setTitle("Canteen Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] columnNames = {"Name", "Price", "Category", "Availability", "Quantity"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable menuTable = new JTable(tableModel);
        //Using JTable for creating a table to display the entire menu


        for (MenuItem item : admin.getMenu()) {
            tableModel.addRow(new Object[]{ //Populating the table with values
                item.getName(),
                item.getPrice(),
                item.getCategory(),
                item.getAvailablity() ? "Yes" : "No",
                item.getQuantity()
            });
        }


        JScrollPane scrollPane = new JScrollPane(menuTable);
        JButton viewOrdersButton = new JButton("View Pending Orders");
        viewOrdersButton.addActionListener(e -> {dispose();
            new PendingOrdersGUI(admin);
        });


        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(viewOrdersButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}

