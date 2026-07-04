package session_04;

public abstract class Product {
    protected int id;
    protected String name;
    protected float price;

    public Product(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        if(id <= 0) throw new IllegalArgumentException("Id must be > 0");
        this.id = id;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty())throw new IllegalArgumentException("Name cannot be empty");
        this.name = name.trim();
    }

    public void setPrice(float price) {
        if(price < 0) throw new IllegalArgumentException("Price must be >= 0");
        this.price = price;
    }


    public abstract String toString();
}
