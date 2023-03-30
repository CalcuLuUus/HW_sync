public class App {
    public static void main(String[] args) throws Exception {
        Filter f1 = new Filter(new JpgImg("Jpg"));
        Filter f2 = new Filter(new JpgImg("png"));
        Texture f3 = new Texture(new JpgImg("Jpg"));

        f1.open();
        f2.open();
        f3.open();

        f1.blur();
        f2.blur();
        f3.blur();
        f3.texture();

        f1.close();
        f2.close();
        f3.close();
    }
}
