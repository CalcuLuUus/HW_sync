public class Cube extends Object{
    String name;
    
    public Cube(String name) {
        this.name = name;
    }

    @Override
    public void fillColor(String pre, String color) {
        System.out.println(pre + "Cube " + this.name + " Set color = " + color);
    }
    
}
