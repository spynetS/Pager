import Initilize.InitProject;
import Utilitis.FileHandler;

public class Main {

    public static void main(String[] args)
    {
        try {
            InitProject.Init();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
