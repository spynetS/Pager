package Utilitis;

import java.util.Scanner;

public class Debug {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void Log(String log)
    {
        System.out.println(System.currentTimeMillis()*200+log);
    }
    public static void Error(String log)
    {
        System.out.println(ANSI_RED + log + ANSI_RESET);    }

    public static String Input(String log)
    {
        Log(log);
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = myObj.nextLine();  // Read user input
        return input;
    }

}
