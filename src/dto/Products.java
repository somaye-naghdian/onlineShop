package dto;

public class Products {
    private int productId;
    private String branch;
    private String name;
    private double price;
    private  int stock;
    private String brand;

    public Products(int productId,String branch,String name, double price, int stock, String brand) {
        this.productId = productId;
        this.branch=branch;
        this.name=name;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
    }

    public Products() {
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", branch='" + branch + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", brand='" + brand + '\'' +
                '}';
    }
}
