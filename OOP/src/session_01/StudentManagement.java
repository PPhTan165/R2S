package session_01;

import java.util.Scanner;

public class StudentManagement {

    public static void init(Student student, int size, int index, Scanner scanner) {
        if (size < 0 || size > 100) {
            System.out.println("Invalid");
        }
            student.id = index;
            System.out.print("Enter name student: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter gender: ");
            String gender = scanner.nextLine().trim();

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();


            System.out.print("Enter address: ");
            String address = scanner.nextLine().trim();


            if (!name.isEmpty()) {
                student.name = name;
            } else {
                System.out.println("Invalid");
            }

            if (age >= 18) {
                student.age = age;
            } else {
                System.out.println("Age must be greater than 18");
            }

            if (gender.equals("male") || gender.equals("female")) {
                student.gender = gender;
            } else {
                System.out.println("Invalid");
            }

            if (!email.isEmpty()) {
                student.email = email;
            } else {
               student.email = "";
            }

            if(!address.isEmpty()){
                student.address = address;
            }else {
                student.address = "";
            }



    }

    public static void displayAllStudent(Student[] students, int size) {
        if (size == 0) {
            System.out.println("Nothing to display");
        } else {
            for (int i = 0; i < size; i++) {
                System.out.printf("ID: %d, ", students[i].id);
                System.out.printf("Name: %s, ", students[i].name);
                System.out.printf("Age: %d, ", students[i].age);
                System.out.printf("Gender: %s, ", students[i].gender);
                System.out.printf("Address: %s, ", students[i].address);

                if (i == size - 1) {
                    System.out.printf("Email: %s%n", students[i].email);
                } else {
                    System.out.printf("Email: %s,%n", students[i].email);
                }
            }
        }

    }

    public static void findStudentById(Student[] students, int size, int idx) {
        if (size == 0) {
            System.out.println("Nothing to display");
        } else {
            for (int i = 0; i < size; i++) {
                if (students[i].id == idx) {
                    System.out.printf("ID: %d, ", students[i].id);
                    System.out.printf("Name: %s, ", students[i].name);
                    System.out.printf("Age: %d, ", students[i].age);
                    System.out.printf("Address: %s, ", students[i].address);
                    System.out.printf("Gender: %s, ", students[i].gender);
                    if (i == size - 1) {
                        System.out.printf("Email: %s%n", students[i].email);
                    } else {
                        System.out.printf("Email: %s,%n", students[i].email);
                    }
                    return;
                }
            }
            System.out.println("Nothing to display");
        }
    }

    public static void updateStudentById(Student[] students, int size, int id, Scanner scanner) {
        if (size == 0) {
            System.out.println("Nothing to display");
        } else {
            for (int i = 0; i < size; i++) {
                if (students[i].id == id) {
                    init(students[i], size, id, scanner);
                    return;
                }
            }
            System.out.println("Nothing to display");
        }
    }

    static void main(String[] args) {
        Student[] students = new Student[100];

        Scanner scanner = new Scanner(System.in);


        int choice = 0;
        boolean running = true;
        int count = 0;


        while (running) {
            System.out.println("1. Add student");
            System.out.println("2. Display all students");
            System.out.println("3. Find student by id");
            System.out.println("4. Update student by id");
            System.out.println("5. Quit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("Enter amount student: ");
                    int size = scanner.nextInt();
                    scanner.nextLine();

                    if (size <= 0 || count + size > students.length) {
                        System.out.println("Invalid amount student");
                        break;
                    }

                    for(int i = 0; i < size; i++ ){
                        Student student = new Student();
                        init(student, students.length, count, scanner);
                        students[count] = student;
                        count++;
                    }

                    break;
                }
                case 2: {
                    displayAllStudent(students, count);
                    break;
                }
                case 3: {
                    System.out.println("Enter student id: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    findStudentById(students, count, index);
                    break;
                }
                case 4: {
                    System.out.println("Enter student id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    updateStudentById(students, count, id, scanner);
                    break;
                }
                case 5: {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Enter value in 1 - 5");
                    break;
            }
        }
    }
}
