package session_04;

public class Clothing extends Product{
    private String size;

    public Clothing(int id, String name, float price, String size) {
        super(id, name, price);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if(size == null || size.trim().isEmpty())throw new IllegalArgumentException("Size cannot be empty");
        this.size = size.trim();
    }


    @Override
    public String toString(){
        return String.format("ID: %d, Name: %s, Price: %3f, Size: %s", getId(), getName(), getPrice(), getSize());
    }
}
