package Utilitis;
import Compiler.Compiler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Run {




    public static void main(String[] args) throws Exception {
        String output="output/index.html";
        String input="htmls";
        long last = 0;

        if(args.length>0)
        {
            output=(args[0]);
            if(args.length>1)
            {
                input=(args[1]);
            }
        }
        String fileName = "";
        Path file = Paths.get(fileName);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        while(true) {
            Thread.sleep(100);
            ArrayList<Page> pages = FileHandler.GetHtmlsFiles(new File(input),"p",null,0);
            pages.addAll(FileHandler.GetHtmlsFiles(new File(input),"mp",null,0));
            for(Page page: pages)
            {
                //reads files properties
                fileName = page.getFile().getAbsolutePath();
                file = Paths.get(fileName);
                attr = Files.readAttributes(file, BasicFileAttributes.class);

                //Checks if latest modification is greater then the last time
                if (attr.lastModifiedTime().to(TimeUnit.SECONDS) > last) {

                    Compiler.Compile(output,input);
                    last = attr.lastModifiedTime().to(TimeUnit.SECONDS);
                    System.out.println("Compiled");

                }
            }
        }
    }
}