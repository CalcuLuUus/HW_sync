public class App {
    public static void main(String[] args) throws Exception {
        Generator generator = new Generator();

        Observer ob1 = new WordAndCharOb(generator);
        Observer ob2 = new WordsUnique(generator);
        Observer ob3 = new WordsStat(generator);

        String txt = "Hello World!";
        System.out.println("First update:" + txt);
        generator.setState(txt);

        txt = "This is TJU DesignPattern homework3. Author is 2022229044 LHX";
        System.out.println("Second update:" + txt);
        generator.setState(txt);
    }
}