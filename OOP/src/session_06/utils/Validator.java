package session_06.utils;

import session_06.entities.Course;

import java.util.ArrayList;
import java.util.Arrays;

public final class Validator {
    private Validator(){}

    public static boolean validateCode(String code) {
        return code != null && code.matches(Constants.COURSE_CODE_REGEX);
    }

    public static boolean isDuplicatedCode(String code, ArrayList<Course> courses) {
       return courses.stream().anyMatch(c-> c.getCode().equalsIgnoreCase(code));
    }

    public static boolean validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        status = status.trim().toLowerCase();
        return status.equals("true")
                || status.equals("false")
                || status.equals("active")
                || status.equals("in-active");
    }

    public static boolean parseStatus(String status) {
        status = status.trim().toLowerCase();
        return status.equals("true") || status.equals("active");
    }

    public static boolean validateFlag(String flag){
        if(flag == null || flag.trim().isEmpty()) return false;
        String normalizedFlag = flag.trim();

        return Arrays.stream(Constants.ALLOWED_FLAGS).anyMatch(a -> a.equalsIgnoreCase(normalizedFlag));
    }

    public static boolean validateDuration(short duration){
        return duration > 0;
    }
}
