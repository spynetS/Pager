package Utilitis;

import Initilize.InitProject;
import com.sun.jndi.toolkit.url.Uri;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static String ProjectPath="";

    public static String getProjectPath() {
        return ProjectPath;
    }

    public static void setProjectPath(String projectPath) {
        ProjectPath = projectPath;
    }

    public static void WritePage(String path,String command) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write(command);
        fw.close();
    }
    public static void WritePage(String path,ArrayList<String> lines) throws IOException {
        FileWriter fw = new FileWriter(path);
        for(String line : lines)
        {
            fw.write(line);
            fw.write("\n");
        }
        fw.close();
    }
    public static String SetPath(String localpath)
    {
        //Never start with / in localpath
        if(getProjectPath().lastIndexOf('/')==getProjectPath().length()-1)
        {
            return getProjectPath()+localpath;
        }
        else
            return getProjectPath()+"/"+localpath;
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
    public static ArrayList<Page> GetHtmlsFiles(InputStream path, String keyword) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(path));
        String currentline = "";
        ArrayList<Page> files = new ArrayList<>();
        ArrayList<String> linesInPage = new ArrayList<>();
        int index = 0;
        boolean startedFetshing = false;
        while((currentline= br.readLine())!=null)
        {
            if(currentline.contains(keyword))
            {
                if(startedFetshing)
                {
                    //If we find new keyword and we are fetshing we now it is the end
                    startedFetshing = false;
                    Page page;
                    if(currentline.contains("masterpage"))
                    {
                        page = new Page(String.valueOf(index), (ArrayList<String>) linesInPage.clone(),new File(currentline.replace("-","")+".mp"));
                    }
                    else
                        page = new Page(String.valueOf(index), (ArrayList<String>) linesInPage.clone(),new File(currentline.replace("-","")+".p"));
                    files.add(page);
                    linesInPage.clear();
                }
                else
                {
                    startedFetshing = true;
                    continue;
                }
                index++;
            }
            if(startedFetshing)
            {
                linesInPage.add(currentline);
            }        }

        return files;
    }
    public static ArrayList<Page> GetHtmlsFiles(File folder,String extenstion, ArrayList<Page> files, int index)
    {
        if(files ==null)
            files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                GetHtmlsFiles(fileEntry,extenstion,files,index);
            } else if(getExtenstion(fileEntry.getName()).equals(extenstion)){
                Page page = new Page(fileEntry);
                page.setId(String.valueOf(index));
                page.setFile(fileEntry);
                files.add(page);
                index++;
            }
        }
        return files;
    }


}
