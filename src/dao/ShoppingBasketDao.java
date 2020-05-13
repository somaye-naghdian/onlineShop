package dao;

import dto.Customer;
import java.sql.*;

public class ShoppingBasketDao {
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

    public void insertShoppingBasket(Customer customer, int productId, int itemCount) {

        try {
            int customerId = customer.getId();
            Connection connection = getConnection();
            String query = ("insert into shoppingbasket(customer_id,product_id,itemCount) values (?,?,?) ");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
             if (sumBasketItems() > 5) {
                System.out.println(" basket capacity is full");
                return;
            }
           preparedStatement.setInt(1,customerId);
            preparedStatement.setInt(2,productId);
            preparedStatement.setInt(3,itemCount);
            preparedStatement.executeUpdate();
            System.out.println("product insert was successfully ");
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOfBasket() {
        try {
            Connection connection = getConnection();
            String query = "delete  from  shoppingbasket where product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printBasket() {
        try {
            Connection connection = getConnection();
            String query = "SELECT products.name,products.price,products.brand,products.branch, shoppingbasket.itemCount " +
                    "from products JOIN shoppingbasket  on products.product_id=shoppingbasket.product_id " +
                    "join customer  on shoppingbasket.customer_id = customer.customer_id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("----------- your shopping basket ------------");
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                int numberOfProduct = resultSet.getInt("itemCount");
                System.out.println("product name :  " + productName);
                System.out.println("Number of products : " + numberOfProduct);
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void totalPrice() {
        try {
            Connection connection = getConnection();
            String query = "SELECT sh.itemCount , p.price ,sum(sh.itemCount * p.price) as totalPrice" +
                    " FROM shoppingbasket as sh join products as p " +
                    "WHERE p.product_id = sh.product_id ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int totalPrice = resultSet.getInt("totalPrice");
                System.out.println("total price : " + totalPrice);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBasket() {
        try {
            Connection connection = getConnection();
            String query = "delete from  shoppingbasket";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            System.out.println("Shopping Basket is empty");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int sumBasketItems() {
        int sum = 0;
        try {
            Connection connection = getConnection();
            String query = "SELECT sum(itemCount) FROM shoppingbasket ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sum = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
}

