import java.util.ArrayList;

public class Blocks extends Object{
    String name;
    ArrayList<Object> objects = new ArrayList<>();
    
    public Blocks(String name) {
        this.name = name;
    }
    public void add(Object object){
        objects.add(object);
    }
    public void del(Object object){
        objects.remove(object);
    }
    @Override
    public void fillColor(String pre, String color) {
        System.out.println(pre + "Blocks " + this.name + " Set color = " + color);
        for(Object object : objects){
            object.fillColor(pre + "----", color);
        }
    }
    
}
