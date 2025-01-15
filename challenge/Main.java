package dev.lpa.challenge;

import java.util.Random;

/*
* Shoe Warehouse Fulfillment center
* Producer = generate orders
* Consumer = process the orders in FIFO order
* Should generate 10 orders and call receiveOrder for each
* two consumers: each needs to process 5 fulfillment orders,
*               calls the fulfillOrder method for each
* */
class Consumer implements Runnable {
    private ShoeWarehouse fulfillOrder;

    public Consumer(ShoeWarehouse shoeWarehouse) {
        this.fulfillOrder = shoeWarehouse;
    }

    @Override
    public void run() {
        int ordersTaken = 0;
        do {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(2000,3500));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            fulfillOrder.fulfillOrder();
            ordersTaken++;
        } while(ordersTaken < 5);
    }
}
class Producer implements Runnable {
    private static int LAST_ID = 1111;
    private ShoeWarehouse makingOrder;

    public Producer(ShoeWarehouse shoeWarehouse) {
        this.makingOrder = shoeWarehouse;
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            Random random = new Random();
            makingOrder.receiveOrder(createOrder(ShoeWarehouse.productList.get(
                    random.nextInt(0, ShoeWarehouse.productList.size())))
            );
            try {
                Thread.sleep(random.nextInt(500,2000));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
    private static Order createOrder(String typeShoe) {
        Random random = new Random();
        return new Order(LAST_ID++, typeShoe, random.nextInt(1,41));
    }
}
public class Main {
    public static void main(String[] args) {
        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();

        Thread producer = new Thread(new Producer(shoeWarehouse));
        Thread consumer1 = new Thread(new Consumer(shoeWarehouse), "consumer 1");
        Thread consumer2 = new Thread(new Consumer(shoeWarehouse), "consumer 2");

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
