public class App {
    public static void main(String[] args) throws Exception {
        SingleA singleA = SingleA.getInstance();
        SingleB singleB = SingleB.getInstance();
        SingleC singleC = SingleC.getInstance();
        singleA.print();
        singleB.print();
        singleC.print();
    }
}
