import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Factory.menu();
        int op = 0;
        Scanner sc = new Scanner(System.in);
        op = sc.nextInt();
        try {
            shape sh = Factory.createShape(op);
            sh.draw();
            sh.erase();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
