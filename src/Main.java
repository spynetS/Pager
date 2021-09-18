/*
           we have to take in every file with a .htmls
           everything in the body will be written to a single html file
           in a separate div with a index

           the js handle the display of theise divs



<!doctype html>
<html lang="en">
<head >

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <script type="text/javascript" src="../js/SidebarNavigate.js"></script>
    <script type="text/javascript" src="../js/pageloader.js"></script>

    <title>Home</title>
</head>
<body>

</body>

</html>

        */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String mainPage = "html/index.html";
    public static void ResetBody()
    {
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage));
            ArrayList<String> lines1 = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage));
            boolean first = false;
            int i = 0;

            for (String line:
                 lines) {
                if(line.contains("</k>"))
                {
                    break;
                }
                if(first)
                {
                    lines1.remove(line);

                }
                if(line.contains("<k id=\"app\">"))
                {
                    first = true;
                    lines1.add(i+1," ");
                    lines1.add(i+1," ");
                }
                i++;

            }
            System.out.println(i);
            Files.write(Paths.get(mainPage),lines1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getExtenstion(String filename)
    {
        String extension = "";

        int index = filename.lastIndexOf('.');
        if (index > 0) {
            extension = filename.substring(index + 1);
        }
        return extension;
    }

    public static ArrayList<File> GetHtmlsFiles(File folder,ArrayList<File> files )
    {
        if(files ==null)
            files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                GetHtmlsFiles(fileEntry,files);
            } else if(getExtenstion(fileEntry.getName()).equals("htmls")){
                files.add(fileEntry);
            }
        }
        return files;
    }
    public static void compile()
    {
        int index = 0;

        ArrayList<File> htmlsFiles = GetHtmlsFiles(new File("htmls"),null);
        System.out.println(htmlsFiles);
        for (File page:
                htmlsFiles) {
            System.out.println(page.getName());
            try {
                List<String> lines = Files.readAllLines(Paths.get(mainPage));
                int lineNumber = 0;
                //Gets the line where to start writing
                for (String line:
                        lines) {
                    lineNumber++;
                    if(line.contains("<k id=\"app\">"))
                    {
                        lineNumber++;
                        break;
                    }
                }

                System.out.println(index);
                int i = 1;
                List<String> pagelines = Files.readAllLines(Paths.get(page.getAbsolutePath()));

                lines.add(lineNumber,"<div id=\""+index+"\">");

                for (String pageLine:
                        pagelines) {

                    lines.add(lineNumber+i,pageLine);
                    System.out.println(pageLine);
                    i++;

                }
                lines.add(lineNumber+i,"</div>");

                Files.write(Paths.get(mainPage),lines);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            index++;
        }
    }
    public static void main(String[] args) throws Exception
    {
        ResetBody();
        compile();
    }
}
