package session_03;

import java.util.Scanner;

public class AntiqueShop {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ItemList itemList = new ItemList();
        byte choice = 0;
        do {
            System.out.println("1. Add a new Vase");
            System.out.println("2. Add a new Statue");
            System.out.println("3. Add a new Painting");
            System.out.println("4. Display all items");
            System.out.println("5. Find the items by the creator ");
            System.out.println("6. Display the list of vase items ");
            System.out.println("7. Quit");
            System.out.print("Input your choice: ");

            choice = sc.nextByte();
            switch (choice){
                case 1:
                    Vase vaseItem = new Vase();
                    vaseItem.input();

                    if(itemList.addItem(vaseItem)){
                        System.out.println("added");
                    }
                    break;
                case 2:
                    Statue statueItem = new Statue();
                    statueItem.input();

                    if(itemList.addItem(statueItem)){
                        System.out.println("added");
                    }
                    break;
                case 3:
                    Painting paintingItem = new Painting();
                    paintingItem.input();

                    if(itemList.addItem(paintingItem)){
                        System.out.println("added");
                    }
                    break;
                case 4:
                    itemList.displayAll();
                    break;
                case 5:
                    System.out.print("Enter creator: ");
                    String creator = sc.nextLine().trim();

                    System.out.println(itemList.findItemByCreator(creator));
                    break;
                case 6:
                    System.out.print("Enter type item: ");
                    String type = sc.nextLine().trim().toUpperCase();

                    itemList.displayItemByType(type);
                    break;
                case 7:
                    System.out.println("Bye! ");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }while (choice <=6);
    }
}
