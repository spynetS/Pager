package Compiler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Compiler2 {




    public static String outputfile="./output/index.html"; // final file
    public static String mainfile = "./htmls/main.html"; // first file

    private static String get_props(String whole_tag)
    {
        HashMap<String, String> props = new HashMap<>();
        String[] propsNames = whole_tag.split(" ");
        for(String propname : propsNames){
            //System.out.println(whole_tag);
            if(propname.contains("Id"))
            {
                props.put(propname.split("=")[0],propname.split("=")[1]);
            }
        }
        String s = "";
        for (Map.Entry<String, String> entry : props.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            s+=" id='idk'";
        }
        return s;
    }

    private static Map<String, String> get_Component_Props(String line,String parent_id)
    {
        char[] chars = line.toCharArray();
        boolean priv_prop = false;

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
                if(priv_prop) {
                    prop.put(prop_name.toString(),parent_id+prop_value.toString());
                } else {
                    prop.put(prop_name.toString(),prop_value.toString());

                }
                listen_prop_value = false;
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
            if (c == '@')
            {
                priv_prop = true;
                listens_prop_name = true;
                hasListen = true;
            }
        }

        return prop;
    }

    //change prop?
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
                // System.out.println(key);
                //    System.out.println(line);
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

    private static List<String> changeId(List<String> lines, String id)
    {
        List<String> newLines = new ArrayList<>();
        outerloop:
        for(String line : lines)
        {
            if((line.contains("id")&&line.contains("<"))||line.contains("getElementById"))
            {
                String newLine = "";
                for(String prop : line.split(" "))
                {
                    if(prop.contains("id"))
                    {
                        System.out.println(line);
                        newLine = line.replace("id="+prop.split("=")[1] ,"id=\""+id+prop.split("=")[1].substring(1));
                        System.out.println(newLine);
                        newLines.add(newLine);
                        continue outerloop;
                    }
                    else if(prop.contains("getElementById"))
                    {
                        //System.out.println("getElementById(\""+prop.split("\"")[1]+"\")");
                        newLine = line.replace("getElementById(\""+prop.split("\"")[1]+"\")","getElementById(\""+id+prop.split("\"")[1]+"\")");
                        newLines.add(newLine);
                        continue outerloop;
                    }
                }
            }
            newLines.add(line);
        }

        return newLines;
    }

    public static List<String> Compile(String filepath) throws Exception
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
                        Map props = get_Component_Props(line,"page2");
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

            String path = Compiler2.outputfile;
            Files.write(Paths.get(path),list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
