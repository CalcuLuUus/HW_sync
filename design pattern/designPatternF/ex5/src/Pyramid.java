public class Pyramid extends Object{
    String name;
    
    public Pyramid(String name) {
        this.name = name;
    }

    @Override
    public void fillColor(String pre, String color) {
        System.out.println(pre + "Pyramid " + this.name + " Set color = " + color);
    }
    
}
