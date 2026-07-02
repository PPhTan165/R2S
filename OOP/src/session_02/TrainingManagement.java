package session_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TrainingManagement {
    private final TraineeForm traineeForm;
    private final Scanner scanner;
    private final Trainee[] listOfTrainee = new Trainee[100];
    private byte count;

    public TrainingManagement() {
        this.scanner = new Scanner(System.in);
        this.traineeForm = new TraineeForm(scanner);
    }

    static void main(String[] args) {
        new TrainingManagement().runMenu();
    }

    private void runMenu() {
        while (true) {
            System.out.println("\n============ Trainee Management ===========");
            System.out.println("1. Add Trainee");
            System.out.println("2. Display all Trainee");
            System.out.println("3. Find trainee by id");
            System.out.println("4. Find trainee by name");
            System.out.println("5. Update trainee by id");
            System.out.println("0. Exit");

            System.out.print("Choose option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTrainee();
                case "2" -> displayAllTrainees();
                case "3" -> {
                    System.out.print("Enter id to find: ");
                    String id = scanner.nextLine().trim();

                    Trainee trainee = findTraineeById(id);
                    System.out.println(trainee != null ? header()+"\n"+trainee : "Not found");
                }
                case "4" -> {
                    System.out.print("Enter name to find: ");
                    String name = scanner.nextLine().trim();

                    Trainee[] result = findTraineeByName(name);
                    if(result.length == 0) System.out.println("No match");
                    else {
                        System.out.println(header());
                        for( Trainee t : result){
                            System.out.println(t);
                        }
                    }
                }
                case "5" -> {
                    System.out.println("Enter id to update: ");
                    String id = scanner.nextLine().trim();

                    Trainee newTrainee = traineeForm.getTrainee();
                    updateTrainee(id, newTrainee);
                }
                case "0" -> {
                    System.out.println("BYE! ");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addTrainee() {
        if (count >= listOfTrainee.length) {
            System.out.println("Storage full");
            return;

        }

        String id;
        while (true) {
            id = traineeForm.getId();
            if (indexOfid(id) == -1) break;
            System.out.println("Id already exists. Enter another.");
        }

        Trainee trainee = traineeForm.getTrainee();
        trainee.setId(id);
        listOfTrainee[count++] = trainee;

        System.out.println("Created successfully!");

    }

    private void displayAllTrainees() {
        if (count == 0) {
            System.out.println("No Trainees yet.");
            return;
        }

        System.out.println(header());
        for (int i = 0; i < count; i++) {
            System.out.println(listOfTrainee[i]);
        }
    }

    private Trainee findTraineeById(String id) {
        int idx = indexOfid(id);
        return idx == -1 ? null : listOfTrainee[idx];
    }

    private Trainee[] findTraineeByName(String name) {
        String key = name.toLowerCase();
        Trainee[] temp = new Trainee[count];
        int k = 0;

        for (int i = 0; i < count; i++) {
            if (listOfTrainee[i].getName().toLowerCase().contains(key)) {
                temp[k++] = listOfTrainee[i];
            }
        }
        return Arrays.copyOf(temp, k);
    }

    private void updateTrainee(String id, Trainee newTrainee) {
        int idx = indexOfid(id);
        if (idx == -1) {
            System.out.println("Id not found");
            return;
        }

        listOfTrainee[idx].setName(newTrainee.getName());
        listOfTrainee[idx].setGender(newTrainee.getGender());
        listOfTrainee[idx].setAge(newTrainee.getAge());

        System.out.println("Updated!");

    }

    private int indexOfid(String id) {
        for (int i = 0; i < count; i++) {
            if (listOfTrainee[i].getId().equalsIgnoreCase(id)) return i;
        }

        return -1;
    }

    private String header() {
        return String.format("%-8s | %-18s | %-6s | %3s", "ID", "NAME", "SEX", "AGE");
    }

}
