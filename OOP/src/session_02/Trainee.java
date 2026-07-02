package session_02;

public class Trainee {
    private String id;
    private String name;
    private String gender;
    private byte age;

    public Trainee() {
    }

    public Trainee(String id, String name, String gender, byte age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new IllegalArgumentException("Id cannot be empty");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
            this.name = name.trim();

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
       if(gender == null) throw new IllegalArgumentException("Gender cannot be null");
       String normalizedGender = gender.trim().toLowerCase();

       if(!normalizedGender.equals("male") && !normalizedGender.equals("female")){
           throw new IllegalArgumentException("Gender must be 'male' or 'female'");
       }

       this.gender = normalizedGender;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
       if(age < 6){
           throw new IllegalArgumentException("Age must be >= 6");
       }

       this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-18s | %-6s | %3d",id,name,gender,age);
    }
}
