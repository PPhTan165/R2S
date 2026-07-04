package session_03;

import java.util.Scanner;

public class Vase extends Item {
    private int height;
    private String material;

    public Vase() {
        super();
    }

    public Vase(int value, String creator, int height, String material) {
        super(value, creator);
        this.height = height;
        this.material = material;
    }

    public int getHeight() {
        return height;
    }

    public String getMaterial() {
        return material;
    }

    public void setHeight(int height) {
        if (height < 0 || height > 2000) {
            throw new IllegalArgumentException("Height must be around in 0 - 2000");
        }
        this.height = height;
    }

    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty()) {
            throw new IllegalArgumentException("Material cannot be empty");
        }
        this.material = material.trim();
    }

    @Override
    public void input() {
        Scanner scanner = new Scanner(System.in);
        super.input();

        System.out.println("Enter height: ");
        int height = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter material: ");
        String material = scanner.nextLine();

        setHeight(height);
        setMaterial(material);
    }

    @Override
    public String toString() {
        return String.format("Value: %d, Creator: %s, Height: %d, Material: %s", value, creator, height, material);
    }
}
