import java.util.ArrayList;

public class GameManager {
    private static GameManager gameManager = new GameManager();
    private GameManager(){
        System.out.println("Single Manager");
    }
    public static GameManager getInstance(){
        return gameManager;
    }

    private ArrayList<Resources> resources = new ArrayList<>();

    public void addResource(Resources resource){
        resources.add(resource);
    }

    public void setParams(int State){
        for(Resources resource : resources){
            resource.setParams("", State);
        }
    }
}
