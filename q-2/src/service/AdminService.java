package service;

import dao.OperationLogDao;
import entity.Customer;
import entity.OperationLog;
import utility.AgeComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static service.CustomerService.customerDao;

public class AdminService {

    public void getCustomerAge() {
        ArrayList<Customer> customers;
        customers = customerDao.getCustomerList();
        Collections.sort(customers, new AgeComparator());
        for (Customer customer :
                customers) {
            System.out.println(customer);
        }
    }

    public void getCustomerActivity(String inputDate) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, 30);
        String endDate = sdf.format(c.getTime());

        OperationLogDao operationLogDao = new OperationLogDao();
        List<OperationLog> operationLogs = operationLogDao.getOperationList(inputDate, endDate);
        for (OperationLog operationLog :
                operationLogs) {
            System.out.print("user: " + operationLog.getAuthority() + " | ");
            System.out.print("operation: " + operationLog.getOperation() + " | ");
            System.out.print("date: " + operationLog.getDate() + " | ");
            System.out.print("time: " + operationLog.getTime() + "\n");
        }
    }
}
