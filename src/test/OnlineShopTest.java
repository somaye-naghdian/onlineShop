package test;

import dao.CustomerDao;
import dao.ProductsDao;
import dao.ShoppingBasketDao;
import dto.Customer;
import dto.Products;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OnlineShopTest {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        CustomerDao customerDao = new CustomerDao();
        int input = 0;
        System.out.print("  Log in     - press 1" + "\n" + "new customer - press 2" + "\n");
        try {
            input = scanner.nextInt();
            switch (input) {
                case 1: {
                    System.out.println("enter username : ");
                    String username = scanner.next();
                    System.out.println("enter password : ");
                    String password = scanner.next();
                   menu(customerDao.passwordValidation(username, password));
                    break;
                }
                case 2: {
                    Customer customer = Customer.customerRegister();
                    menu(customer);
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("your input : " + input + " is invalid");
        }
    }
    public static void menu(Customer customer) {
        ProductsDao productsDao = new ProductsDao();
        ShoppingBasketDao shoppingBasketDao = new ShoppingBasketDao();
        Products product = new Products();
        Scanner scanner = new Scanner(System.in);
        char choice;
        do {
            System.out.println("Menu :");
            System.out.println("S-show all products");
            System.out.println("A-add item to shopping Basket");
            System.out.println("D-remove item from shopping Basket");
            System.out.println("P-print shopping Basket items");
            System.out.println("T_print total price");
            System.out.println("F_Final approval");
            System.out.println("q - Quit");
            System.out.print("Enter an option:");
            String input = scanner.nextLine().toLowerCase();
            choice = input.trim().charAt(0);
            if (choice != 's' && choice != 'a' && choice != 'd' &&
                    choice != 'p' && choice != 't' && choice != 'f') {
                System.out.println("try again & enter an option");
            } else {
                switch (choice) {
                    case 's': {
                        productsDao.showProductsList();
                        break;
                    }
                    case 'a': {
                        try {
                            System.out.println("enter product Id for add in shopping basket: ");
                            int productId = scanner.nextInt();
                            product = productsDao.searchProduct(productId);

                            System.out.println("enter item count");
                            int itemCount = scanner.nextInt();
                            shoppingBasketDao.insertShoppingBasket(customer, productId,itemCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("invalid input");
                        }
                        break;
                    }
                    case 'd': {
                        try {
                            System.out.println("enter product id for delete from shopping basket");
                            int productId = scanner.nextInt();
                            product = productsDao.searchProduct(productId);
                            shoppingBasketDao.deleteOfBasket();
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("invalid input");
                        }
                    }
                    case 'p': {
                        shoppingBasketDao.printBasket();
                        break;
                    }
                    case 't': {
                        shoppingBasketDao.totalPrice();
                        break;
                    }
                    case 'f': {
                            productsDao.updateProductsStock();
                            shoppingBasketDao.deleteBasket();
                            System.out.println(" Thank you ");
                        break;
                    }
                }
            }
        } while (choice != 'q' || choice != 'Q');
    }
}
