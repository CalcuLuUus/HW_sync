public class App {
    public static void main(String[] args) throws CloneNotSupportedException {

        Manager manager = new Manager();
        Picture originalPicture = new Picture();

        CVTemp template = new CVTemp("jack", originalPicture);
        manager.registerTemplate("template1", template);

        CVTemp template1 = manager.create("template1", 0);
        if(template.getPicture() == template1.getPicture()) {
            System.out.println("Clone SUCCESS");
        } else {
            System.out.println("Clone FAILED");
        }
        CVTemp template2 = manager.create("template1", 1);
        if (template.getPicture() != template2.getPicture()) {
            System.out.println("Deep Clone SUCCESS");
        } else {
            System.out.println("Deep Clone FAILED");
        }
    }
}
