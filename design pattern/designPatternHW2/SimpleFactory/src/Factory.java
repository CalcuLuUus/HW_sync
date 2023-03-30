public class Factory {
    public static void menu(){
        System.out.println("input a number");
        System.out.println("1. Circle");
        System.out.println("2. Triangle");
        System.out.println("3. Rectangle");
    }
    public static shape createShape(int shapeOption) throws UnsupportedShapeException{
        switch (shapeOption){
            case 1:return new circle();
            case 2:return new Triangle();
            case 3: return new Rectangle();
            default:
            throw new UnsupportedShapeException();
        }
    }
}
    