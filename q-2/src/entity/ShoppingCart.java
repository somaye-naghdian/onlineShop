package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingCart {

    public ShoppingCart() {
        capacity = 5;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private int itemCount;
    private double totalPrice;
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany
    List<Products> productList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return 5;
    }

    public void setCapacity() {
        this.capacity = 5;
    }

    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return itemCount == that.itemCount &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                capacity == that.capacity &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(productList, that.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCount, totalPrice, capacity, productList);
    }

    @Override
    public String toString() {
        return "ShoppingBasket{" +
                "customer=" + customer +
                ", itemCount=" + itemCount +
                ", totalPrice=" + totalPrice +
                ", capacity=" + capacity +
                ", productList=" + productList +
                '}';
    }


}
