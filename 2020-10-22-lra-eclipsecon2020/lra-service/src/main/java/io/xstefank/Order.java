package io.xstefank;

public class Order {

    public int quantity;
    public String product;

    @Override
    public String toString() {
        return "Order{" +
            "quantity=" + quantity +
            ", product='" + product + '\'' +
            '}';
    }
}
