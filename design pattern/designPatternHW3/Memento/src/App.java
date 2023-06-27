public class App {
    public static void main(String[] args) throws Exception {
        Originator originator = new Originator();
        String text = "Hello World!";

        originator.update(text);
        System.out.println(originator);

        text += " I add some text";
        originator.update(text);
        System.out.println("After update: " + originator);

        originator.undo();
        System.out.println("After undo: " + originator);

        originator.redo();
        System.out.println("After redo: " + originator);
    }
}
