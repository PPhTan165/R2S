package session_06.entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private String code;
    private String name;
    private boolean status;
    private short duration;
    private String flag;

    public Course() {
    }

    public Course(String code, String name, boolean status, short duration, String flag) {
        setCode(code);
        this.name = name;
        this.status = status;
        this.duration = duration;
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public short getDuration() {
        return duration;
    }

    public String getFlag() {
        return flag;
    }

    public void setCode(String code) {
        if(code == null || code.trim().isEmpty()){
            throw new IllegalArgumentException("Code cannot be empty");
        }
        code = code.trim();
        if(code.length() != 5){
            throw new IllegalArgumentException("Length of Code must be equal 5");
        }
        if(!code.matches("RA\\d{3}")){
            throw new IllegalArgumentException("Code must start with RA and end with 3 digits");
        }
        this.code = code;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-20s | %-6s | %-6d | %-12s",
                code, name, status ? "active" : "in-act", duration, flag);
    }

    public void input(Scanner sc, ArrayList<Course> listCourse) {
        System.out.print("Enter code: ");
        String code = sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter status: ");
        boolean status = sc.nextBoolean();

        System.out.print("Enter duration: ");
        short duration = sc.nextShort();

        System.out.print("Enter flag: ");
        String flag = sc.nextLine();

        Course newCourse = new Course(code, name, status, duration, flag);
        listCourse.add(newCourse);
    }
}
