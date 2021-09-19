package Utilitis;

import java.util.Scanner;

public class Debug {

    public static void Log(String log)
    {
        System.out.println(log);
    }

    public static String Input(String log)
    {
        Log(log);
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = myObj.nextLine();  // Read user input
        return input;
    }

}
