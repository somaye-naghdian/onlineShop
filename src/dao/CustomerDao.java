package dao;

import dto.Customer;
import java.sql.*;

public class CustomerDao {

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

    public void insertCustomer(Customer customer) {
        try {
            Connection connection = getConnection();
            String query = "insert into customer(name,family,phone,email,address,username,pass) values (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getFamily());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, String.valueOf(customer.getAddress()));
            preparedStatement.setString(6, customer.getUserName());
            preparedStatement.setString(7, customer.getPassword());

            preparedStatement.executeUpdate();
            System.out.println("successfully insert");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean searchDuplicateUserName(String username) {
        try {
            Connection connection = getConnection();
            String query = "select username from customer where username = '" + username + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Customer passwordValidation(String username, String password) {
            try {
                Connection connection = getConnection();
                String query = "select name,family,phone,email,address,username,pass" +
                        " from customer where username = '" + username + "'and pass = '" + password + "'";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery(query);
                while (resultSet.next()) {

                    String name = resultSet.getString("name");
                    String family = resultSet.getString("family");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    Customer customer = new Customer(name, family, username, password, phone, email, address);
                    return customer;
                }
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
}
