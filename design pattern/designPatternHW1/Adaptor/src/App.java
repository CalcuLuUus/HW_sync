import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Input string");
        String input;
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();

        DBEnc dbEnc = new EncAdaptor(input);
        System.out.println("after Enc : " + dbEnc.Enc());
        
        sc.close();
    }
}
