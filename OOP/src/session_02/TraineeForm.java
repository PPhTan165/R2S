package session_02;

import java.util.Scanner;

public class TraineeForm {
    private final  Scanner scanner;

    public TraineeForm(Scanner scanner) {
        this.scanner = scanner;
    }

   public String getId(){
        while (true){
            System.out.print("Enter trainee id: ");
            String id = scanner.nextLine().trim();

            if(!id.isEmpty())return id;
            System.out.println("Id cannot be empty. Try again. ");
        }
   }

   public Trainee getTrainee(){
        String name = readNonEmpty("Enter name: ");
        String gender = readGender("Enter gender(male/female): ");
        byte age = readAge("Enter age(>=6): ");
        return new Trainee("TEMP",name, gender,age);
   }

   private String readNonEmpty(String prompt){
        while (true){
            System.out.println(prompt);
            String value = scanner.nextLine().trim();

            if(!value.isEmpty())return value;
            System.out.println("Value cannot be empty");
        }
   }

   private String readGender(String prompt){
       while (true){
           System.out.println(prompt);
           String gender = scanner.nextLine().trim();

           if(gender.equals("male") || gender.equals("female"))return gender;
           System.out.println("Gender must be 'male' or 'female'");
       }
   }

   private byte readAge(String prompt){
       while (true){
           try {
               System.out.println(prompt);
               String line = scanner.nextLine().trim();
               byte age = Byte.parseByte(line);

               if(age >= 6)return age;
               System.out.println("Age must be >= 6");

           }catch (NumberFormatException e){
               System.out.println("Invalid number. Try again. ");
           }
       }
   }
}
