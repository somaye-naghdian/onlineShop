package dto;

import dao.CustomerDao;
import sun.plugin.dom.exception.InvalidAccessException;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private int id;
    private String name;
    private String family;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String address;

    public Customer( String name, String family, String userName, String password, String phone, String email, String address) {
        if (!phoneValidation(phone)) {
            throw new IllegalArgumentException("your phone pattern is invalid");
        }
        if (!checkEmail(email)) {
            throw new IllegalArgumentException(" your email is invalid ");
        }
        if (userName.length() < 8 || userName.length() > 20) {
            throw new IllegalArgumentException("username is not too short or too long");
        }
        if (password.length() < 8 || password.length() > 10) {
            throw new IllegalArgumentException("password is not safe");
        }
        this.name = name;
        this.family = family;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public static boolean phoneValidation(String phone) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkEmail(String email) {
        char ch = '@';
        char ch2 = '.';
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < email.length(); i++) {
            if (Objects.equals(email.charAt(i), ch))
                count++;
            if (Objects.equals(email.charAt(i), ch2))
                count2++;
        }
        if (count == 0 && count2 == 0) {
            return false;
        }
        return true;
    }
    public static Customer customerRegister() {
        Scanner scanner = new Scanner(System.in);
        CustomerDao customerDao = new CustomerDao();
        System.out.println("enter your name");
        String name = scanner.nextLine();
        System.out.println("enter your family");
        String family = scanner.nextLine();
        System.out.println("enter your phone number with pattern xxx-xxxxxxx");
        String phone = scanner.nextLine();
        System.out.println("enter your email");
        String email = scanner.nextLine();
        System.out.println("enter your address->province");
        String province = scanner.next();
        System.out.println("enter your address->city");
        String city = scanner.next();
        System.out.println("enter your address->street");
        String street = scanner.next();
        System.out.println("enter your address->postalCode");
        String postalCode = scanner.next();
        String address = province +"-"+ city +"-"+ street +"-"+ postalCode;
        System.out.println("enter a username Including at least 8 character");
        String username = scanner.next();
        if (customerDao.searchDuplicateUserName(username)) {
            System.out.println("enter password Including at least 8 character");
            String password = scanner.next();
            Customer customer = new Customer(name, family, username, password, phone, email, address);
            customerDao.insertCustomer(customer);
            return customer;
        } else {
            throw new InvalidAccessException("username is previously selected ");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName.length() < 8 || userName.length() > 20) {
            throw new IllegalArgumentException("username is not too short or too long");
        }
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8 || password.length() > 10) {
            throw new IllegalArgumentException("password is not safe");
        }
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!phoneValidation(phone)) {
            throw new IllegalArgumentException("your phone pattern is invalid");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!checkEmail(email)) {
            throw new IllegalArgumentException(" your email is invalid ");
        }
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
