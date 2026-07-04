package session_04;

import java.util.Scanner;

public class Program {
    private Product[] products;
    private byte numOfProduct;
    private static final byte MAX = 100;

    public Program() {
        products = new Product[MAX];
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }

        if (numOfProduct > 100) {
            System.out.println("Maximum number of product is 100");
        } else {
            products[numOfProduct++] = product;
            System.out.println("Product added");
        }
    }

    public void displayProducts() {
        if (numOfProduct == 0) {
            System.out.println("No product exists");
        } else {
            for (int i = 0; i < numOfProduct; i++) {
                System.out.println(products[i]);
            }
        }
    }

    public Product findProductById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be > 0");
        if (products == null) {
            System.out.println("Product list is null");
        }
        for (int i = 0; i < numOfProduct; i++) {
            if (products[i].getId() == id) {
                return products[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Program program = new Program();
        Scanner sc = new Scanner(System.in);
        byte choice = 0;

        do {
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Find Product");
            System.out.println("0. Exit");
            System.out.println("Enter your choice");
            choice = sc.nextByte();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter type of product (C/E): ");
                    String type = sc.nextLine();

                    System.out.print("Enter product ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter product price: ");
                    float price = sc.nextFloat();
                    sc.nextLine();

                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();

                    if (type.equals("C")) {
                        System.out.print("Enter size of product: ");
                        String size = sc.nextLine();

                        Clothing clothing = new Clothing(id, name, price, size);
                        program.addProduct(clothing);
                        System.out.println(clothing);

                    } else if (type.equals("E")) {
                        System.out.print("Enter brand of product: ");
                        String brand = sc.nextLine();

                        Electronics electronics = new Electronics(id, name, price, brand);
                        program.addProduct(electronics);
                        System.out.println(electronics);
                    }
                    break;
                case 2:
                    program.displayProducts();
                    break;
                case 3:
                    System.out.print("Enter product ID: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    Product product = program.findProductById(id);
                    if (product == null) {
                        System.out.println("Product is not exists");
                    } else {
                        System.out.println(product);
                    }
                    break;
                case 0:
                    System.out.println("Exit Program!");
                    return;
                default:
                    System.out.println("Invalid choice");

            }
        } while (choice != 0);

        sc.close();
    }

}
