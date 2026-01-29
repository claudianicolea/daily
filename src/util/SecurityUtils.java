package util;

import java.util.regex.Pattern;

public class SecurityUtils {
    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return p.matcher(email).matches();
    }

    public static boolean verifyPassword(String input, String storedHash) {
        return input.equals(storedHash);
    }
}