public class Cylinder extends Object{
    String name;
    
    public Cylinder(String name) {
        this.name = name;
    }

    @Override
    public void fillColor(String pre, String color) {
        System.out.println(pre + "Cylinder " + this.name + " Set color = " + color);
    }
    
}
