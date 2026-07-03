package session_03;

import java.util.Scanner;

public class Item {
    protected String id;
    protected int value;
    protected String creator;

    public Item() {
    }

    public Item(int value, String creator) {
        this.value = value;
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public String getCreator() {
        return creator;
    }

    public void setId(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new IllegalArgumentException("Id cannot be empty");
        }
        this.id = id.trim();
    }

    public void setValue(int value) {
        if(value < 0 ){
            throw new IllegalArgumentException("Value must be >= 0");
        }
        this.value = value;
    }

    public void setCreator(String creator) {
        if(creator == null || creator.trim().isEmpty()){
            throw new IllegalArgumentException("Creator cannot be empty");
        }
        this.creator = creator.trim();
    }

    public void input(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter value: ");
        int value = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter creator: ");
        String creator = scanner.nextLine();

        setValue(value);
        setCreator(creator);
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Value: %d, Creator: %s",id,value,creator);
    }
}
