package shadowbuild.helper;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

    public static void log(Object obj) {
        System.out.println("LOG: " + ft.format(new Date()) + " [DEBUG] " + obj.toString());
    }

    public static void log(String format, Object... args) {
        String message = MessageFormat.format(format, args);
        System.out.println("LOG: " + ft.format(new Date()) + " [DEBUG] " + message);
    }

    public static void info(String format, Object... args) {
        String message = MessageFormat.format(format, args);
        System.out.println("LOG: " + ft.format(new Date()) + " [INFO] " + message);
    }

    public static void error(String format, String... args) {
        String message = MessageFormat.format(format, args);
        System.out.println("LOG: " + ft.format(new Date()) + " [ERROR] " + message);
    }

    public static void warn(String format, String... args) {
        String message = MessageFormat.format(format, args);
        System.out.println("LOG: " + ft.format(new Date()) + " [WARNING] " + message);
    }
}
