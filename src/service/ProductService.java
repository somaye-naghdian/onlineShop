package service;

import utility.PriceComparator;
import dao.OperationLogDao;
import dao.ProductsDao;
import entity.Customer;
import entity.OperationLog;
import entity.Products;
import entity.ShoppingCart;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

public class ProductService {
    ShoppingCart shoppingCart = new ShoppingCart();
    ProductsDao productsDao = new ProductsDao();
    Products product = null;


    public void executeMenu(Customer customer) {
        List<Products> shoppingCartProduct = new ArrayList<>();
        Map<Products, Integer> productStock = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        Character choice;
        do {
            printMenu();
            choice = scanner.next().charAt(0);
            Character.toLowerCase(choice);

            if (choice != 's' && choice != 'a' && choice != 'd' &&
                    choice != 'p' && choice != 't' && choice != 'f') {
                System.out.println("try again & enter an option");
            } else {
                switch (choice) {
                    case 's': {
                        productsDao.printCategory();
                        System.out.println("enter selected category ");
                        String productCategory = scanner.next();
                        productsDao.showProductsList(productCategory);

                        break;
                    }
                    case 'a': {
                        try {
                            System.out.println("enter product first category and then name  for add in shopping basket: ");
                            String productCategory = scanner.next();
                            String productName = scanner.next();
                            System.out.println("enter item count");
                            int itemCount = scanner.nextInt();
                            product = productsDao.searchProduct(productCategory, productName);
                            shoppingCartProduct.add(product);
                            shoppingCart.setCustomer(customer);
                            shoppingCart.setItemCount(itemCount);
                            shoppingCart.setProductList(shoppingCartProduct);
                            productStock.put(product, itemCount);
                            addActivity(customer,OperationsType.ADD);
                            if (shoppingCartProduct.size() > shoppingCart.getCapacity()) {
                                System.out.println(" basket is full");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("invalid input");
                        }
                        break;
                    }
                    case 'd': {
                        try {
                            System.out.println("enter product first category and then name for delete from shopping basket");
                            String category = scanner.next();
                            String productName = scanner.next();

                            Products products = productsDao.searchProduct(category, productName);
                            if (product.equals(products)) {
                                shoppingCartProduct.remove(product);
                            }
                            shoppingCart.setProductList(shoppingCartProduct);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("invalid input");
                        }
                    }
                    case 'p': {

                        System.out.println(productStock.toString());
                        addActivity(customer,OperationsType.PRINT);
                        break;
                    }
                    case 't': {
                        double totalPrice = 0;
                        for (Products item :
                                shoppingCartProduct) {
                            totalPrice += item.getPrice() * shoppingCart.getItemCount();
                        }
                        System.out.println("total price = " + totalPrice);
                        break;
                    }
                    case 'f': {
                        Collections.sort(shoppingCartProduct, new PriceComparator());
                        for (Products products :
                                shoppingCartProduct) {
                            System.out.println(products.toString());
                        }
                        for (Products products :
                                shoppingCartProduct) {
                            productsDao.updateProductsStock(products.getName(), shoppingCart.getItemCount());
                        }
                        addActivity(customer, OperationsType.PURCHASE);
                        shoppingCartProduct.clear();
                        System.out.println(" Thank you ");
                        break;
                    }
                    case 'q':
                        System.exit(0);
                }
            }
        } while (choice != 'q' || choice != 'Q');
    }

    public void printMenu() {
        System.out.println("Menu :");
        System.out.println("S-show all products");
        System.out.println("A-add item to shopping Basket");
        System.out.println("D-remove item from shopping Basket");
        System.out.println("P-print shopping Basket items");
        System.out.println("T_print total price");
        System.out.println("F_Final approval");
        System.out.println("q - Quit");
        System.out.print("Enter an option:");
    }

    public void addActivity(Customer customer, OperationsType operationType) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new Date(millis);
        Time time = new Time(millis);
        OperationLog operationLog = new OperationLog(time, date, operationType, customer.getUserName());
        OperationLogDao operationLogDao = new OperationLogDao();
        operationLogDao.insertOperationLog(operationLog, customer);
    }

}