import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import data.Dataset;
import domain.Activity;
import domain.AddAct;
import domain.SAct;

public class App {
    public static void main(String[] args) throws Exception {
        Dataset.init();
        final int userid = 1;
        final int kind = 0;
        final int lastingDay = 10;
        Random random = new Random();

        System.out.println("Your user id is 1");

        int op = 0;
        Scanner sc = new Scanner(System.in);
        

        while (op != 7) {
            menu();
            System.out.println("choose your Service:");
            op = sc.nextInt();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, lastingDay);
            Date expiredTime = calendar.getTime();

            Activity activity = null;
            if (op == 1 || op == 2) {
                activity = new AddAct(userid, kind, expiredTime, op - 1, Math.abs(random.nextInt() % 10));
            } else if (op >= 3 && op <= 5) {
                System.out.println("Please input page:");
                int page = sc.nextInt();
                activity = new SAct(userid, kind, page, op - 2);
            } else if (op == 6) {
                activity = new SAct(userid, kind, 0, 0);
            } else {
                System.out.println("Bye");
                break;
            }

            activity.work();
        }
        sc.close();

    }

    public static void menu() {
        System.out.println("1. Add credit");
        System.out.println("2. Minus credit");
        System.out.println("3. Query credit");
        System.out.println("4. Query add credit");
        System.out.println("5. Query minus credit");
        System.out.println("6. Query num of credit");
        System.out.println("7. exit");
    }
}
