package dto;

import java.util.List;

public class ShoppingBasket {
    private Customer customer;
    private int itemCount;
    private double totalPrice;
    private int capacity;
    private List<Products> productList;

    public ShoppingBasket(Customer customer, int itemCount,  int capacity) {
        this.customer = customer;
        this.itemCount = itemCount;
        capacity = 5;
    }

    public ShoppingBasket() {
        capacity = 5;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }
}
