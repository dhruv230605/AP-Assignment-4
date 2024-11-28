package com.example.firstjavafx;

import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getisVIP() && !o2.getisVIP()) return -1;
        else if (!o1.getisVIP() && o2.getisVIP()) return 1;
        return 0;
    }
}
