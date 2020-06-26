package view;

import dao.CustomerDao;
import entity.Address;
import entity.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ProductService;
import sun.plugin.dom.exception.InvalidAccessException;

import java.util.Scanner;

public class CustomerView {
    static CustomerDao customerDao = new CustomerDao();
    static ProductService productService = new ProductService();
    private static final Logger logger = LogManager.getLogger(CustomerView.class);
    Scanner scanner = new Scanner(System.in);

    public void loginCustomer() {

        System.out.println("enter username : ");
        String username = scanner.next();
        System.out.println("enter password : ");
        String password = scanner.next();
        Customer customer = customerDao.passwordValidation(username, password);
        if (customerDao.passwordValidation(username, password) != null) {
            System.out.println("successful login");
            productService.executeMenu(customer);
            logger.info("Login");
        } else {
            System.out.println("customer not found ");
            return;
        }
    }

    public Customer customerRegister() {

        CustomerDao customerDao = new CustomerDao();

        System.out.println("enter  name");
        String name = scanner.nextLine();
        System.out.println("enter  family");
        String family = scanner.nextLine();
        System.out.println("enter  age");
        int age = scanner.nextInt();
        System.out.println("enter  phone number with pattern xxx-xxxxxxx");
        String phone = scanner.next();
        System.out.println("enter  email");
        String email = scanner.next();
        System.out.println("enter  address->province");
        String province = scanner.next();
        System.out.println("enter  address->city");
        String city = scanner.next();
        System.out.println("enter  address->street");
        String street = scanner.next();
        System.out.println("enter  address->postalCode");
        int zipCode = scanner.nextInt();
        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        address.setStreet(street);
        address.setZipCode(zipCode);
        System.out.println("enter a username Including at least 8 character ");
        String username = scanner.next();
        if (customerDao.searchDuplicateUserName(username)) {
            System.out.println("enter password Including at least 8 character and les than 10");
            String password = scanner.next();

            Customer customer = new Customer();
            customer.setName(name);
            customer.setFamily(family);
            customer.setAge(age);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setUserName(username);
            customer.setPassword(password);
            customer.setAddress(address);
            address.setCustomer(customer);
            customerDao.insertCustomer(customer);
            logger.info("register");
            return customer;
        } else {
            throw new InvalidAccessException("username is previously selected ");
        }
    }

}
