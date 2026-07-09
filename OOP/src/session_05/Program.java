package session_05;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ProductNotFoundException {
        ProductManagement pm = new ProductManagement();
        Scanner sc = new Scanner(System.in);
        byte choice;
        while (true) {
            System.out.println("=== Product Management Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. Retrieve Product by ID");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            choice = sc.nextByte();

            switch (choice) {
                case 1:
                    pm.addProduct();
                    break;
                case 2:

                    System.out.print("Enter productID to retrieve: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    pm.retrieveProductById(id);
                    break;
                case 3:
                    System.out.print("Enter productID to update: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new quantity: ");
                    int newQuantity = sc.nextInt();
                    sc.nextLine();

                    pm.updateQuantityProductById(id, newQuantity);

                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid option ");
            }

        }

    }
}
