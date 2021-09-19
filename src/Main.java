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

    public static String mainPage = "output/index.html";
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

    public static ArrayList<Page> GetHtmlsFiles(File folder,ArrayList<Page> files,int index)
    {
        if(files ==null)
            files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                GetHtmlsFiles(fileEntry,files,index);
            } else if(getExtenstion(fileEntry.getName()).equals("html")){
                Page page = new Page(fileEntry);
                System.out.println("index "+index);
                page.setId(String.valueOf(index));
                files.add(page);
                index++;
            }
        }
        return files;
    }

    public static void CompileToJs() throws Exception
    {
        //Make a list of all pages (comes from the compiler)
        /*
                function changePage(index)
                    {
                            var list = ["0","1","2"]

                var arrayLength = list.length;

                for(var i = 0;i<arrayLength;i++)
                {
                    if(list[i]==index)
                    {
                        document.getElementById(list[i]).style.display = "block";
                    }
                    else
                    {
                        document.getElementById(list[i]).style.display = "none";
                    }
                }
        }*/
        FileWriter fw1 = new FileWriter("js/navigation.js",false);
        fw1.write("");
        fw1.close();
        FileWriter fw = new FileWriter("js/navigation.js",true);
        fw.write("function changePage(index){\n");
        fw.write("var pages = [");
        ArrayList<Page> pages = GetHtmlsFiles(new File("htmls"),null,0);
        for (Page page: pages) {
            if(page.getId().equals("0"))
                fw.write("\""+page.getId()+"\"");
            else
                fw.write(",\""+page.getId()+"\"");
        }
        fw.write("]\n");
        fw.write("var arrayLength = pages.length;\nfor(var i = 0;i<arrayLength;i++)\n" +
                "                {\n" +
                "                    if(pages[i]==index)\n" +
                "                    {\n" +
                "                        document.getElementById(pages[i]).style.display = \"flex\";\n" +
                "                    }\n" +
                "                    else\n" +
                "                    {\n" +
                "                        document.getElementById(pages[i]).style.display = \"none\";\n" +
                "                    }\n" +
                "                }\n" +
                "        }");

        fw.close();
    }

    public static void CompileToIndex() throws Exception
    {
        ArrayList<Page> files = GetHtmlsFiles(new File("htmls"),null,0);
        System.out.println(files.get(0).getHtmlCode().get(1));
        ArrayList<String> mainPageLines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage));
        ArrayList<String> newMainpageLines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage));
        //Loops through the mainpage lines
        for(int i = 0;i<mainPageLines.size();i++)
        {
            if(mainPageLines.get(i).contains("<k id=\"app\">"))
            {
                //newMainpageLines.add(i+1,"ghello");
                //loop through all the pages on the project
                for (Page page:
                     files) {
                    //loops through the lines in the pagesfiles
                    newMainpageLines.add(i + 1, "</div>");

                    for (int x = page.getHtmlCode().size() - 1; x >= 0; x--) {
                        System.out.println(page.getHtmlCode().get(x));
                        newMainpageLines.add(i + 1, page.getHtmlCode().get(x));
                    }
                    if(page.getId().equals("0"))
                        newMainpageLines.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:flex;\" >");
                    else
                        newMainpageLines.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:none;\" >");
                }
            }
        }
        Files.write(Paths.get(mainPage),newMainpageLines);
    }
    public static void main(String[] args) throws Exception
    {
        ResetBody();
        CompileToIndex();
        CompileToJs();
    }
}
