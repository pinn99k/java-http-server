package util;

public class InputParse {

    public static Integer parseInt(String msg) {
        if (msg == null) return null;

        try {
            return Integer.parseInt(msg);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
