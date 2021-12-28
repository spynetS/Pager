import Initilize.InitProject;
import Utilitis.AutoCompile;
import Utilitis.Debug;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
            if(args[0].equals("run"))
            {
                try {
                    AutoCompile.pain(getArgs(args,0));
                }
                catch (IOException e)
                {
                    Debug.Error("WARNING --- The project is probably not set up currently.");
                } catch (Exception e) {
                    Debug.Error("WARNING --- The project is probably not set up currently.");
                }
            }
            else if(args[0].equals("init"))
            {
                try {
                    InitProject.main(getArgs(args,0));
                }
                catch (IOException e)
                {
                    Debug.Error("WARNING --- The project is probably not set up currently.");
                } catch (Exception e) {
                    //Debug.Error("WARNING --- The project is probably not set up currently.");
                    Debug.Error(e.toString());
                }
            }
            else if(args[0].equals("compile"))
            {
                try {
                    InitProject.main(getArgs(args,0));
                }
                catch (IOException e)
                {
                    Debug.Error("WARNING --- The project is probably not set up currently.");
                } catch (Exception e) {
                    Debug.Error("WARNING --- The project is probably not set up currently.");
                }
            }
            else if(args[0].equals("help")||args[0].equals("-h"))
            {
                Debug.Log("do init followed with name of your project to start a new project (./ to do in the same folder)");
                Debug.Log("do compile to compile your files");
                Debug.Log("do run to automatically compile your files");
            }
            //439
    }

    public static String[] getArgs(String[] array,int indexToRemove){

        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));

        list.remove(indexToRemove);

        String[] a = new String[list.size()];
        
        int i = 0;
        for(String aa : list)
        {
            a[i] = aa;

            i++;
        }
        return a;
    }



}
