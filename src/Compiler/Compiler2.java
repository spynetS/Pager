package Compiler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Compiler2 {




    private static String outputfile="output/index.html"; // final file
    private static String mainfile = "Pager_Project/htmls/main.html"; // first file

    private static String get_props(String whole_tag)
    {
        String[] props = whole_tag.split(" ");
        for(String prop : props)
        {
            //System.out.println(prop);
        }
        return "class='center'";
    }

    private static Map<String, String> get_Component_Props(String line)
    {
        char[] chars = line.toCharArray();

        HashMap<String,String> prop = new HashMap<>();

        boolean listens_prop_name = false;
        StringBuilder prop_name = new StringBuilder();

        boolean hasListen = false;
        boolean listen_prop_value = false;
        StringBuilder prop_value = new StringBuilder();

        for(char c : chars)
        {
            if((c=='"'||c=='\'')&&listen_prop_value)
            {
               // System.out.println(prop_value);
                listen_prop_value = false;
                prop.put(prop_name.toString(),prop_value.toString());
                prop_name = new StringBuilder();
                prop_value = new StringBuilder();
            }
            if(listen_prop_value)
            {
                prop_value.append(c);
            }
            if((c=='"'||c=='\'')&&hasListen)
            {
                listen_prop_value = true;
                hasListen = false;
            }
            //check if we shall stop listen for prop_name
            if(c=='=')
            {
               // System.out.println(prop_name);
                listens_prop_name = false;
            }
            //adds to prop_name
            if(listens_prop_name)
            {
                prop_name.append(c);
            }
            //check if we shall listen
            if (c == ':')
            {
                listens_prop_name = true;
                hasListen = true;
            }
        }

        return prop;
    }

    private static List<String> changeId(List<String> lines,Map<String, String> props)
    {
        List<String> newLines = new ArrayList<>();
        outerloop:
        for(String line : lines)
        {
            boolean found = false;
            String newLine = line;
            for (Map.Entry<String, String> entry : props.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key);
                System.out.println(line);
                if(line.contains(key))
                {

                    //can fuck up!!!
                    char[] chars = line.toCharArray();

                    newLine = newLine.replaceAll(key,value);
                        //change the value of the prop with the value of the key
                    found =true;
                }
            }
            if(!found)
            newLines.add(line);
            else
                newLines.add(newLine);

        }
        return newLines;
    }

    private static List<String> Compile(String filepath) throws Exception
    {
        //bad code do it better
        String fileRemove = filepath.split("/")[filepath.split("/").length-1];
        String currentdir = filepath.replace(fileRemove,"");


        List<String> fileLines = Files.readAllLines(Paths.get(filepath));
        HashMap<String,List<String>> components = new HashMap<>();
        List<String> newLines = new ArrayList<>();

        outerloop:
        for(String line : fileLines){
            if(line.contains("import"))
            {
                String new_path = line.split(" ")[3].replace("'","");
                components.put(line.split(" ")[1], Compiler2.Compile(currentdir+new_path.substring(0)));
               // System.out.println(new_path);
                continue ;
            }
            //check if we encounter a custom component
            if(components.size()>0) {

                for (Map.Entry<String, List<String>> entry : components.entrySet())
                {
                    String key = entry.getKey();
                    List<String> value = entry.getValue();

                    if (line.contains("<" + key))
                    {
                        //check for props
                        Map props = get_Component_Props(line);
                        // if find prop check the value lines for the prop and change its value
                        // prop = :my_text="id1" check for id="my_text" and change it to id="id1"

                        newLines.add("<div "+Compiler2.get_props(line)+" >");
                        newLines.addAll(changeId(value,props));
                        newLines.add("</div>");
                        continue outerloop;
                    }
                }

            }

            newLines.add(line);
        }
        return newLines;
    }


    public static void main(String[] args)
    {
        try {
            List<String> list = Compiler2.Compile(mainfile);

            String path = "./Pager_Project/output/main.html";
            Files.write(Paths.get(path),list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
