import java.util.ArrayList;

public class Building {
    private ArrayList<Object> objects = new ArrayList<>();
    String name;
    public Building(String name){
        this.name = name;
    }
    public void addObject(Object object){
        objects.add(object);
    }
    public void delObject(Object object){
        objects.remove(object);
    }
    public void fillColor(String color){
        System.out.println("=========================================");
        System.out.println("Building " + this.name + " set color = " + color);
        for(Object object : objects){
            object.fillColor("|----", color);
        }
        System.out.println("=========================================");
    }
}
