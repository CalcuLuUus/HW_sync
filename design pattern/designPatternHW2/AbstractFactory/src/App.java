import java.util.Scanner;

import Factory.AbstractFactory;
import Factory.Connection;
import Factory.Statememt;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("1. MySQL");
        System.out.println("2. Oracle");

        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();

        String option = (op == 1 ? "MySQL.SQLFactory" : "Oracle.OracleFactory");

        AbstractFactory Factory = AbstractFactory.getFactory(option);

        Connection connection = Factory.createConnection("CONNECTING");
        connection.output();

        Statememt statement = Factory.createStatememt("STATMENT");
        statement.output();

        sc.close();
    }
}
