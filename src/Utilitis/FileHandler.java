package Utilitis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    public static String SetPath(String localpath)
    {
        //Never start with / in localpath
        if(getProjectPath().lastIndexOf('/')==getProjectPath().length()-1)
        {
            System.out.println("yey");
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
                files.add(page);
                index++;
            }
        }
        return files;
    }


}
