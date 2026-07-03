package session_03;

import java.util.Scanner;

public class Painting extends Item {
    private int height;
    private int width;
    private boolean isWaterColor;
    private boolean isFramed;

    public Painting() {
        super();
    }

    public Painting(int value, String creator, int height, int width, boolean isWaterColor, boolean isFramed) {
        super(value, creator);
        this.height = height;
        this.width = width;
        this.isWaterColor = isWaterColor;
        this.isFramed = isFramed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isWaterColor() {
        return isWaterColor;
    }

    public boolean isFramed() {
        return isFramed;
    }

    public void setHeight(int height) {
        if (height < 0 || height > 2000) {
            throw new IllegalArgumentException("Height must be in 0-2000");
        }
        this.height = height;
    }

    public void setWidth(int width) {
        if (width < 0 || width > 3000) {
            throw new IllegalArgumentException("Width must be in 0-3000");
        }
        this.width = width;
    }

    public void setWaterColor(boolean waterColor) {
        isWaterColor = waterColor;
    }

    public void setFramed(boolean framed) {
        isFramed = framed;
    }

    @Override
    public void input() {
        Scanner scanner = new Scanner(System.in);
        super.input();

        System.out.println("Enter height: ");
        int height = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter width: ");
        int width = scanner.nextInt();
        scanner.nextLine();

        setHeight(height);
        setWidth(width);
    }

    @Override
    public String toString() {
        return String.format("Value: %d, Creator: %s, Height: %d, Width: %d, Water Color: %b,Frame: %b", value, creator, height, width, isWaterColor, isFramed);
    }
}
