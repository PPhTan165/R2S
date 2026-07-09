package session_06.utils;

public final class Constants {
    private Constants(){}

    public static final String FLAG_OPTIONAL = "optional";
    public static final String FLAG_PREREQUISITE = "prerequisite";
    public static final String FLAG_NA = "N/A";

    public static final String[] ALLOWED_FLAGS = {FLAG_OPTIONAL,FLAG_PREREQUISITE,FLAG_NA};

    public static final String COURSE_CODE_REGEX = "^RA\\d{3}$";

    public static final int MENU_CREATE = 1;
    public static final int MENU_SEARCH = 2;
    public static final int MENU_DISPLAY_BY_FLAG = 3;
    public static final int MENU_QUIT = 0;

    public static final String TABLE_HEADER = String.format(
            "%-6s | %-20s | %-6s | %-8s| %-12s",
            "CODE", "NAME", "STATUS","DURATION","FLAG"
    );
}
