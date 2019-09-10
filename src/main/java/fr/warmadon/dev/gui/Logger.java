package fr.warmadon.dev.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    public Logger() {
    }

    public static void write(String s) {
        System.out.println("{" + getTime() + "} " + GUIJFX.appprefix + " " + s);
    }

    public static void debug(String s) {
        System.out.println("{DEBUG-" + getTime() + "} " + GUIJFX.appprefix + " " + s);
    }

    public static void err(String s) {
        System.err.println("{" + getTime() + "} " + GUIJFX.appprefix + " " + s);
    }

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}


