package dev.lpa.challenge;

import java.util.LinkedList;
import java.util.List;

/*
* Should maintain a product list as a public static field
* Maintain a private list of orders
* Method receive Order: get call by producer; should poll or loop
*       indefinitely, checking the size of the list, but it should call
*       wait if the list has reached some maximum capacity
* Method fulfillOrder: get call by consumer; check if the list is empty,
*       if not, wait until an order is added
* */
public class ShoeWarehouse {
    public static List<String> productList = List.of("Nike",
            "Adidas", "Converse", "Michelene", "Tommy",
            "Crocks", "Jordan");

    private List<Order> listOrder = new LinkedList<>();

    public List<Order> getOrder () {
        return listOrder;
    }
    public ShoeWarehouse() {
    }

    public synchronized void receiveOrder(Order order) {
        listOrder.add(order);
        while(listOrder.size() >= 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notifyAll();
        System.out.println("Incoming order: " + order);
    }

    public synchronized Order fulfillOrder() {
        while(listOrder.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order order = listOrder.remove(0);
        System.out.println(Thread.currentThread().getName() + " reading order: " + order);
        notifyAll();
        return order;
    }
}
