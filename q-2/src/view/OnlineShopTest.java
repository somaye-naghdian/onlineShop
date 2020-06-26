package view;

import entity.Customer;
import service.ProductService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OnlineShopTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CustomerView customerView = new CustomerView();
        ProductService productService = new ProductService();
        AdminView adminView = new AdminView();
        int input = 0;
        System.out.print("  Log in     - press 1" + "\n" + "new customer - press 2\n" +
                "\tAdmin    - press 3\n");
        try {
            input = scanner.nextInt();
            switch (input) {
                case 1: {
                    customerView.loginCustomer();
                    break;
                }
                case 2: {
                    Customer customer = customerView.customerRegister();
                    productService.executeMenu(customer);
                    break;
                }
                case 3: {
                    adminView.loginAdmin();
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("your input : " + input + " is invalid");
        }
    }
}
