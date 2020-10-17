package io.xstefank;

public class Order {

    private String product;
    private int quantity;

    public Order() {
    }

    public Order(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
            "product='" + product + '\'' +
            ", quantity=" + quantity +
            '}';
    }
}
