package com.example.firstjavafx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.PriorityQueue;

public class PendingOrdersGUI extends JFrame { //extending JFrame which is the main container
    //for my graphical Interface
    private Admin admin;

    public PendingOrdersGUI(Admin admin) {
        this.admin = admin;
        setTitle("Pending Orders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] columnNames = {"Order ID", "Items", "VIP", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable ordersTable = new JTable(tableModel);
        //Using JTable to display Pending Orders


        PriorityQueue<Order> orders = admin.getOrders();
        for (Order order : orders) {
            StringBuilder itemsList = new StringBuilder();
            for (MenuItem item : order.getItems()) {
                itemsList.append(item.getName()).append(", ");
            }
            tableModel.addRow(new Object[]{ //Filling in the table with data
                order.getId(),
                itemsList.toString(),
                order.getisVIP() ? "Yes" : "No",
                order.getStatus()
            });
        }

        JScrollPane scrollPane = new JScrollPane(ordersTable);

        JButton viewMenuButton = new JButton("View Menu"); //Button to alternate between screens
        viewMenuButton.addActionListener(e -> {dispose();
            new MenuGUI(admin);
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(viewMenuButton, BorderLayout.SOUTH);
        setVisible(true);
    }
}

