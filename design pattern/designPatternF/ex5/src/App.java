public class App {
    public static void main(String[] args) throws Exception {
        Cube c1 = new Cube("1");
        Cube c2 = new Cube("2");
        Cylinder cy1 = new Cylinder("1");
        Cylinder cy2 = new Cylinder("2");
        Pyramid p1 = new Pyramid("1");
        Pyramid p2 = new Pyramid("2");
        Blocks b1 = new Blocks("1");
        Blocks b2 = new Blocks("2");

        b1.add(c1);
        b1.add(cy1);
        b1.add(p1);

        b2.add(c2);
        b2.add(cy2);

        b1.add(b2);

        Building building = new Building("114514");
        building.addObject(b1);
        building.addObject(p2);

        building.fillColor("red");
    }
}
