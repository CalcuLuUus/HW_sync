public class App {
    public static void main(String[] args) throws Exception {
        Manager m1 = new Manager(new PreCopy());
        Manager m2 = new Manager(new PostCopy());

        m1.apply();
        m2.apply();
    }
}
