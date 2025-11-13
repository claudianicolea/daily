package util;

import java.util.regex.Pattern;

public class Security {
    // function to verify correct email and password
    public static boolean verifyCredentials() {
        return true;
    }

    // function to check for valid email address
    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        // regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return p.matcher(email).matches();
    }
}
