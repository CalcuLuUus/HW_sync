public class Rectangle extends shape{
    @Override
    public void draw() {
        System.out.println("I am rectangle");
    }

    @Override
    public void erase() {
        System.out.println("rectangle has been erased");
    }
}
