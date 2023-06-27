import java.util.ArrayList;

public class Scene extends Resources{
    String name;
    private ArrayList<Resources> resources;
    int state;
    public Scene(String name){
        this.name = name;
        resources = new ArrayList<>(); 
    }

    public void add(Resources resource){
        resources.add(resource);
    }

    @Override
    public void setParams(String pre, int State) {
        this.state = State;
        System.out.println(pre + "Scene " + this.name + " set params: " + this.state + ", all element in this scene:");
        for(Resources resource : resources){
            resource.setParams(pre + "---->", State);
        }
        System.out.println(pre + "scene "+ this.name + " Finish");
    }
    
}
