public class App {
    public static void main(String[] args) throws Exception {
        String op1 = "COPY VIEW FROM srcDB TO desDB";
        String op2 = "MOVETABLE Student FROM srcDB TO desDB";
        String op3 = "MOVETABLE Student FROM srcDB TO UnknownDB";
        String[] dbs = {"srcDB", "desDB"};
        TerminalExp terminalExp = new TerminalExp(dbs);

        System.out.println(op1 + ": " + new CpExp(terminalExp).interpret(op1));
        System.out.println(op2 + ": " + new MovaTable(terminalExp).interpret(op2));
        System.out.println(op3 + ": " + new MovaTable(terminalExp).interpret(op3));

    }
}
