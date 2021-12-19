package Initilize;

import Utilitis.Debug;
import Utilitis.FileHandler;
import Utilitis.Page;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InitProject {

    public static void Init(String path) throws Exception
    {
        FileHandler.setProjectPath(path);
        CreateFiles();
    }

    private static void CreateFiles() throws Exception
    {
        String path = "/project.txt";
        System.out.print("project ");
        System.out.println(InitProject.class.getResourceAsStream(path).toString());
        ArrayList<Page> pages = FileHandler.GetHtmlsFiles(InitProject.class.getResourceAsStream(path),"-----");
        java.nio.file.Files.createDirectories(Paths.get(FileHandler.SetPath("htmls")));
        Debug.Log("htmls dir created successfully");

        for(Page page : pages)
        {
            Files.createFile(Paths.get(FileHandler.SetPath("htmls/"+page.getFile().getPath())));
            FileHandler.WritePage((FileHandler.SetPath("htmls/"+page.getFile().getPath())),page.getHtmlCode());
            Debug.Log(page.getFile().getName()+" has been created successfully");
        }//Try to get the files from the txt file instead from the files

        java.nio.file.Files.createDirectories(Paths.get(FileHandler.SetPath("output")));
        Debug.Log("output dir created successfully");
        java.nio.file.Files.createFile(Paths.get(FileHandler.SetPath("output/index.html")));
        Debug.Log("output file created successfully");
        FileHandler.WritePage(FileHandler.SetPath("output/index.html"),"<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head >\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <script type=\"text/javascript\" src=\"../js/navigation.js\"></script>\n" +
                "    <title>Home</title>\n" +
                "<script>reload()</script>"+
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

    public static void main(String[] args) throws Exception
    {
        if (args.length > 0) {
            if(args[0].equals("-h"))
            {
                Debug.Log("Write name of project after init");
            }
            else
                Init(args[0]);
        }
        else {
            Init("Pager_Project");
        }

    }

}
