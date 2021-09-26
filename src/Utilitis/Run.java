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

        if(args.length>0)
        {
            output=(args[0]);
            if(args.length>1)
            {
                input=(args[1]);
            }
        }

        long last = 0;

        String fileName = "F:/dev/java/Pager/res/project.txt";
        Path file = Paths.get(fileName);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        while(true) {
            Thread.sleep(100);
            for(Page page: FileHandler.GetHtmlsFiles(new File(input),"p",null,0))
            {
                fileName = page.getFile().getAbsolutePath();
                file = Paths.get(fileName);
                attr = Files.readAttributes(file, BasicFileAttributes.class);

                if (attr.lastModifiedTime().to(TimeUnit.SECONDS) > last) {
                    Compiler.Compile(output,input);
                    last = attr.lastModifiedTime().to(TimeUnit.SECONDS);

                    System.out.println("Compile");
                }
                else
                {
                }
            }
            for(Page page: FileHandler.GetHtmlsFiles(new File(input),"mp",null,0))
            {
                fileName = page.getFile().getAbsolutePath();
                file = Paths.get(fileName);
                attr = Files.readAttributes(file, BasicFileAttributes.class);

                if (attr.lastModifiedTime().to(TimeUnit.SECONDS) > last) {
                    Compiler.Compile(output,input);
                    last = attr.lastModifiedTime().to(TimeUnit.SECONDS);

                    System.out.println("Compile");
                }
                else
                {
                }
            }

        }
    }

}