package session_04;

public class Electronics extends Product {
    private String brand;

    public Electronics(int id, String name, float price, String brand) {
        super(id, name, price);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) throw new IllegalArgumentException("Brand cannot be empty");
        this.brand = brand.trim();
    }


    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: %3f, Brand: %s", getId(), getName(), getPrice(), getBrand());
    }
}
