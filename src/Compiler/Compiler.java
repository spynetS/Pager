package Compiler;/*
           we have to take in every file with a .htmls
           everything in the body will be written to a single html file
           in a separate div with a index

           the js handle the display of theise divs

        */
import Utilitis.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Compiler {

    public static String mainPage()
    {
        if(Files.exists(Paths.get("output/index.html")))
        {
            return "output/index.html";
        }
        else
        {
            return "output/index.html";
        }
    }
    public static void ResetBody()
    {
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage()));
            ArrayList<String> lines1 = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage()));
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
            Files.write(Paths.get(mainPage()),lines1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void CompileToJs() throws Exception
    {
        FileWriter fw1 = new FileWriter("js/navigation.js",false);
        fw1.write("");
        fw1.close();
        FileWriter fw = new FileWriter("js/navigation.js",true);
        fw.write("function changePage(index){\n");
        fw.write("var pages = [");
        ArrayList<Page> pages = FileHandler.GetHtmlsFiles(new File("htmls"),null,0);
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
        ArrayList<Page> files = FileHandler.GetHtmlsFiles(new File("htmls"),null,0);
        ArrayList<String> mainPageLines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage()));
        ArrayList<String> newMainpageLines = (ArrayList<String>) Files.readAllLines(Paths.get(mainPage()));
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
                        newMainpageLines.add(i + 1, page.getHtmlCode().get(x));
                    }
                    if(page.getId().equals("0"))
                        newMainpageLines.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:flex;\" >");
                    else
                        newMainpageLines.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:none;\" >");
                }
            }
        }
        Files.write(Paths.get(mainPage()),newMainpageLines);
    }
    public static void main(String[] args) throws Exception
    {
        ResetBody();
        CompileToIndex();
        CompileToJs();
        Debug.Log("Compiled to output/index.html successfully");
    }
}
