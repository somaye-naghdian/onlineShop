package dao;

import entity.Customer;
import entity.OperationLog;
import service.OperationsType;

import java.sql.*;
import java.util.ArrayList;

public class OperationLogDao {
    ConnectionDao connectionDao = new ConnectionDao();

    public void insertOperationLog(OperationLog operationLog, Customer customer) {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        Time time = new Time(millis);

        try {
            Connection connection = connectionDao.getConnection();
            String query = "insert into operationlog(operation,operation_time ,operation_date,customer_username)" +
                    "values (?, ?,?,(SELECT username FROM customer WHERE customer.username=?))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(operationLog.getOperation()));
            preparedStatement.setTime(2, time);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, customer.getUserName());

            preparedStatement.execute();
            System.out.println("successfully insert into operationLog");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<OperationLog> getOperationList(String startDate, String endDate, String customer_username) {
        try {
            Connection connection = connectionDao.getConnection();
            String query = "SELECT * FROM `operationlog` as o  inner join customer as c on o.customer_username =c.username" +
                    " WHERE username = '" + customer_username + "'" +
                    " and operation_date BETWEEN '" + startDate + "' AND '" + endDate + "' ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            ArrayList<OperationLog> operationLogs = new ArrayList<>();

            while (resultSet.next()) {
                OperationLog operationLog = new OperationLog();
                String name = (resultSet.getString("operation"));
                operationLog.setOperation(enumFromString(name));
                operationLog.setTime(resultSet.getTime("operation_time"));
                operationLog.setDate(resultSet.getDate("operation_date"));
                operationLog.setAuthority(resultSet.getString("customer_username"));
                operationLogs.add(operationLog);
            }
            preparedStatement.close();
            connection.close();
            return operationLogs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OperationsType enumFromString(String name) {
        return getEnumFromString(OperationsType.class, name);
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }
}
