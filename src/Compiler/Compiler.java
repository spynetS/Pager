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

    private static String outputfile="output/index.html";
    private static String inputfile ="htmls";
    private static String getJsFolder()
    {
        return getInputFile().replace(getInputFile().split("/")[getInputFile().split("/").length-1],"js");
    }
    private static ArrayList<String> CompiledFile = new ArrayList<>();
    private static String pagestring = "p";
    private static String mainpagestring = "mp";


    public static String getInputFile() {
        return inputfile;
    }

    public static void setInputFile(String inputFile) {
        inputfile = inputFile;
    }

    public static String getOutputfile() {
        // if the string is set reteun it else return defult
        if(Files.exists(Paths.get(outputfile)))
            return outputfile;
        else
        {
            try {
                Files.createFile(Paths.get(outputfile));
                FileHandler.WritePage(outputfile,"<!doctype html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head >\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "    <script type=\"text/javascript\" src=\"../js/navigation.js\"></script>\n" +
                        "    <title>Home</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<mainpages>\n" +
                        "\n" +
                        "</mainpages>\n" +
                        "\n" +
                        "<pages>\n" +
                        "    \n" +
                        "</pages>\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputfile;
        }
    }

    public static void setOutputfile(String outputFile) {
        outputfile = outputFile;
    }

    public static void ResetBody()
    {
        ArrayList<String> lines = (ArrayList<String>) CompiledFile.clone();
        boolean first = false;
        int i = 0;

        for (String line:
             lines) {
            if(line.contains("</pages>"))
            {
                break;
            }
            if(first)
            {
                CompiledFile.remove(line);

            }
            if(line.contains("<pages>"))
            {
                first = true;
                CompiledFile.add(i+1," ");
                CompiledFile.add(i+1," ");
            }
            i++;
        }
    }

    public static void ResetMainPages()
    {
        ArrayList<String> lines = (ArrayList<String>) CompiledFile.clone();
        boolean first = false;
        int i = 0;

        for (String line:
                lines) {
            if(line.contains("</mainpages>"))
            {
                break;
            }
            if(first)
            {
                CompiledFile.remove(line);

            }
            if(line.contains("<mainpages>"))
            {
                first = true;
                CompiledFile.add(i+1," ");
                CompiledFile.add(i+1," ");
            }
            i++;
        }
    }

    public static void CompileToJs() throws Exception
    {
        FileWriter fw1 = new FileWriter(getJsFolder()+"/navigation.js",false);
        fw1.write("");
        fw1.close();
        FileWriter fw = new FileWriter(getJsFolder()+"/navigation.js",true);
        fw.write("function changePage(index){\n");
        fw.write("var pages = [");
        ArrayList<Page> pages = FileHandler.GetHtmlsFiles(new File(getInputFile()),pagestring,null,0);
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


        fw.write("function changeMasterPage(index){\n");
        fw.write("var mainpages = [");
        ArrayList<Page> mainpages = FileHandler.GetHtmlsFiles(new File(getInputFile()),mainpagestring,null,0);
        for (Page page: pages) {
            if(page.getId().equals("0"))
                fw.write("\"main"+page.getId()+"\"");
            else
                fw.write(",\"main"+page.getId()+"\"");
        }
        fw.write("]\n");
        fw.write("var arrayLength1 = mainpages.length;\nfor(var i = 0;i<arrayLength1;i++)\n" +
                "                {\n" +
                "                    if(mainpages[i]==\"main\"+index)\n" +
                "                    {\n" +
                "                        document.getElementById(mainpages[i]).style.display = \"flex\";\n" +
                "                    }\n" +
                "                    else\n" +
                "                    {\n" +
                "                        document.getElementById(mainpages[i]).style.display = \"none\";\n" +
                "                    }\n" +
                "                }\n" +
                "        }");

        fw.close();

        fw.close();
    }

    public static void CompileToOutputFile() throws Exception
    {
        ArrayList<Page> pages = FileHandler.GetHtmlsFiles(new File(getInputFile()),pagestring,null,0);
        ArrayList<Page> mainpages = FileHandler.GetHtmlsFiles(new File(getInputFile()),mainpagestring,null,0);
        ArrayList<String> lines =CompiledFile;
        ArrayList<String> newMainpageLines = CompiledFile;
        //Loops through the mainpage lines
        for(int i = 0;i<lines.size();i++)
        {
            if(lines.get(i).contains("<pages>"))
            {
                //newMainpageLines.add(i+1,"ghello");
                //loop through all the pages on the project
                for (Page page:
                        pages) {
                    //loops through the lines in the pagesfiles
                    CompiledFile.add(i + 1, "</div>");

                    for (int x = page.getHtmlCode().size() - 1; x >= 0; x--) {
                        CompiledFile.add(i + 1, page.getHtmlCode().get(x));
                    }
                    if(page.getId().equals("0"))
                        CompiledFile.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:flex;\" >");
                    else
                        CompiledFile.add(i + 1, "<div id=\"" + page.getId() + "\" style=\"display:none;\" >");
                }
            }
            else if(lines.get(i).contains("<mainpages>"))
            {
                for(Page mainpage:mainpages)
                {
                    CompiledFile.add(i + 1, "</div>");

                    for (int x = mainpage.getHtmlCode().size() - 1; x >= 0; x--) {
                        CompiledFile.add(i + 1, mainpage.getHtmlCode().get(x));
                    }
                    if(mainpage.getId().equals("0"))
                        CompiledFile.add(i + 1, "<div id=\"main" + mainpage.getId() + "\" style=\"display:flex;\" >");
                    else
                        CompiledFile.add(i + 1, "<div id=\"main" + mainpage.getId() + "\" style=\"display:none;\" >");
                }
            }
        }
    }
    public static void WriteToOutputFile()
    {
        try {
            Files.write(Paths.get(getOutputfile()),CompiledFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        if(args.length>0)
        {
            System.out.println("setoutput "+args[0]);
            if(args.length>1)
            {
                setInputFile(args[1]);
            }
        }
        CompiledFile = (ArrayList<String>) Files.readAllLines(Paths.get(getOutputfile()));
        ResetBody();
        ResetBody();
        ResetBody();
        ResetMainPages();
        CompileToOutputFile();
        CompileToJs();
        WriteToOutputFile();
        Debug.Log("Compiled to output/index.html successfully");
    }
}
