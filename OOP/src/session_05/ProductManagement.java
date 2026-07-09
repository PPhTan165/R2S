package session_05;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductManagement {
    private final byte MAX_SIZE = 10;

    private Product[] listProduct;
    private byte numberOfProduct;

    public ProductManagement() {
        listProduct = new Product[MAX_SIZE];
    }

    public void addProduct() throws IllegalArgumentException {

        if (numberOfProduct == 0) {
            System.out.println("Product is not exists");
        }

        if (numberOfProduct < MAX_SIZE) {
            Scanner sc = new Scanner(System.in);
            try {
                System.out.println("Enter product detail: ");
                System.out.print("Product ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Product name: ");
                String name = sc.nextLine();

                System.out.print("Product Price: ");
                double price = sc.nextDouble();
                sc.nextLine();

                System.out.print("Product Quantity");
                int quantity = sc.nextInt();
                sc.nextLine();

                Product newProduct = new Product(id, name, price, quantity);

                for (Product p : listProduct) {
                    if (p.getProductId() == newProduct.getProductId()) {
                        throw new IllegalArgumentException("Product id " + newProduct.getProductId() + " exists");
                    }
                }

                listProduct[numberOfProduct++] = newProduct;

                System.out.print("Product added successful");
            } catch (InputMismatchException e) {
                System.out.print("Error input: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("Caught an exception: " + e.getMessage());
            }
        }
    }

    public Product retrieveProductById(int id) throws ProductNotFoundException {

        for (int i = 0; i < numberOfProduct; i++) {
            if (listProduct[i].getProductId() != id) {
                throw new ProductNotFoundException("Product not found with id " + id);
            } else {
                return listProduct[i];
            }
        }
        return null;
    }

    public void updateQuantityProductById(int id, int newQuantity) throws ProductNotFoundException {


        if (numberOfProduct > 0) {

            for (int i = 0; i < numberOfProduct; i++) {
                if (listProduct[i].getProductId() != id) {
                    throw new ProductNotFoundException("Product not found with id " + id);
                } else {
                    listProduct[i].setQuantity(newQuantity);
                    return;
                }
            }
        }
    }
}
