import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Page {

    private String id;
    private ArrayList<String> htmlCode;
    private File file;

    public Page(String id, ArrayList<String> htmlCode ,File file)
    {
        this.id = id;
        this.htmlCode = htmlCode;
        this.file = file;
    }
    public Page(File file)
    {
        this.file = file;
        CompilePage();
    }
    public void setHtmlCode(ArrayList<String> htmlCode) {
        this.htmlCode = htmlCode;
    }

    public ArrayList<String> getHtmlCode() {
        return htmlCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    private void CompilePage()
    {
        //This just sets all the lines in the htmlCode from the file
        try {
            this.htmlCode = (ArrayList<String>) Files.readAllLines(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
