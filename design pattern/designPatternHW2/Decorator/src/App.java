public class App {
    public static void main(String[] args) throws Exception {
        Display onlyString = new Chart("Hello world");
        Display border1 = new Border1(onlyString);
        Display border2 = new Border2(onlyString);
        Display borderAll = new Border2(border1);

        onlyString.show();
        border1.show();
        border2.show();
        borderAll.show();
    }
}
