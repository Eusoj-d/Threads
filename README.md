# Shoe Warehouse Fulfillment center
For this project I use threads to generate orders and read them, you will find two classes: Consumer and Producer, each of them implementing the Runnable Interface. 
There is also a Order Record, which is used to generate the orders with an Id, type of shoe and quantity.

**Producer**
- Generate orders.
- Should generate 10 orders and call receiveOrder method for each.
- Will wait if there is more than 10 orders generated and not readed.
  
**Consumer**
- Process the orders in FIFO order.
- Two consumers: each needs to process 5 fulfillment orders.
- Will wait if there order list is empty.
