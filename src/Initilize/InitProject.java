package Initilize;

import Utilitis.Debug;
import Utilitis.FileHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class InitProject {

    public static void Init(String path) throws Exception
    {
        FileHandler.setProjectPath(path);
        CreateFiles();
    }

    private static void CreateFiles() throws Exception
    {
        java.nio.file.Files.createDirectories(Paths.get(FileHandler.SetPath("htmls")));
        Debug.Log("htmls dir created successfully");

        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("htmls/Page1.p")));
        Debug.Log("page1 created successfully");

        WritePage(FileHandler.SetPath("htmls/Page1.p"),"<div><h1>This is page 1!</h1></div>");

        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("htmls/Page2.p")));
        Debug.Log("page2 created successfully");

        WritePage(FileHandler.SetPath("htmls/Page2.p"),"<div><h1>This is page 2!</h1></div>");


        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("htmls/masterpage1.mp")));
        Debug.Log("masterpage1 created successfully");

        WritePage(FileHandler.SetPath("htmls/masterpage1.mp"),"<button onclick=\"changePage(0)\" >1</button>\n<button onclick=\"changePage(1)\" >2</button>");

        java.nio.file.Files.createDirectories(Paths.get(FileHandler.SetPath("output")));
        Debug.Log("output dir created successfully");
        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("output/index.html")));
        Debug.Log("output file created successfully");
        WritePage(FileHandler.SetPath("output/index.html"),"<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head >\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <script type=\"text/javascript\" src=\"../js/navigation.js\"></script>\n" +
                "    <title>Home</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<mainpages>\n" +

                "</mainpages>\n" +
                "<pages>\n" +
                "\n" +
                "</pages>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>\n");
        java.nio.file.Files.createDirectories(Paths.get(FileHandler.SetPath("js")));
        Debug.Log("js dir created successfully");
        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("js/navigation.js")));
        Debug.Log("navigation created successfully");
        Debug.Log("  ");
        Debug.Log("project initialized successfully at "+FileHandler.getProjectPath());
    }

    private static void WritePage(String path,String command) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write(command);
        fw.close();
    }

    public static void main(String[] args)
    {
        try {
            if (args.length > 0) {
                Init(args[0]);
            } else {
                Init("");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
