package session_03;

import java.util.Scanner;

public class Statue extends Item{
    private int weight;
    private String color;

    public Statue() {
        super();
    }

    public Statue(int value, String creator, int weight, String color) {
        super(value, creator);
        this.weight = weight;
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public void setWeight(int weight) {
        if(weight < 0 || weight > 1000){
            throw new IllegalArgumentException("Weight must be around in 0 - 1000");
        }
        this.weight = weight;
    }

    public void setColor(String color) {
        if(color == null || color.trim().isEmpty()){
            throw new IllegalArgumentException("Color cannot be empty");
        }
        this.color = color.trim();
    }

    @Override
    public void input(){
        Scanner scanner = new Scanner(System.in);
        super.input();

        System.out.println("Enter weight: ");
        int weight = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter color: ");
        String color = scanner.toString();

        setWeight(weight);
        setColor(color);
    }

    @Override
    public String toString() {
        return String.format("Value: %d, Creator: %s, Weight: %d, Color: %s",value,creator,weight,color);
    }
}
