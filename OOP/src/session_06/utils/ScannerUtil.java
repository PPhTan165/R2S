package session_06.utils;

import java.util.Scanner;

public final class ScannerUtil {
    private static final Scanner sc = new Scanner(System.in);

    private ScannerUtil() {
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) return value;
            System.out.println("Value cannot be empty");
        }
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (true/false): ");
            String value = sc.nextLine().trim().toLowerCase();

            if ("true".equals(value)) return true;
            if ("false".equals(value)) return false;

            System.out.print("Enter true or false");
        }
    }

    public static short readPositiveShort(String prompt) {
        while (true) {
            System.out.print(prompt);
            String strValue = sc.nextLine().trim();

            try {
                short shortValue = Short.parseShort(strValue);
                if (shortValue > 0) return shortValue;
                System.out.print("Must be > 0");

            } catch (NumberFormatException e) {
                System.out.print("Invalid number");
            }
        }
    }

    public static int readMenuChoice(){
        while (true){
            System.out.print("Choose: ");
            String value = sc.nextLine().trim();

            try{
                return Integer.parseInt(value);
            }catch (NumberFormatException e){
                System.out.print("Enter a Number: ");
            }
        }
    }
}
