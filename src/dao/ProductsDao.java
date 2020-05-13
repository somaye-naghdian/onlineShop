package dao;

import dto.Products;
import java.sql.*;

public class ProductsDao {

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshop"
                    , "root", null);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertProduct(Products product) {
        try {
            Connection connection = getConnection();
            String query = "insert into products (product_id,branch,price,stock,brand) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getBranch());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setString(5, product.getBrand());
            preparedStatement.executeUpdate();
            System.out.println("successfully insert");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showProductsList() {
        try {
            Connection connection = getConnection();
            String query = "select * from products";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("______products list_____");
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String branch = resultSet.getString("branch");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                String brand = resultSet.getString("brand");
                System.out.println("product id :" + id + "\n" + "branch : " + branch + "\n" +
                        "product name :" + name + "\n" + "price :" + price + "\n" + "Brand :" + brand + "\n" + "stock :" + stock);
                System.out.println("_______________________");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Products searchProduct(int id) {
        try {
            Connection connection = getConnection();
            String query = "select  * from  products where  product_id ='" + id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            Products product = new Products();
            while (resultSet.next()) {
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setBrand(resultSet.getString("brand"));
                product.setBranch(resultSet.getString("branch"));
                product.setStock(resultSet.getInt("stock"));
            }
            preparedStatement.close();
            connection.close();
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateProductsStock() {
        try {
            Connection connection = getConnection();
            String query = "INSERT into products (stock) " +
                    "SELECT (products.stock-sh.itemCount) FROM products" +
                    " join shoppingbasket as sh WHERE products.product_id = sh.product_id ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            System.out.println("products stock updated");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
