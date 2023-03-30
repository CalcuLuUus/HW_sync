package MySQL;

import Factory.Statememt;

public class SQLStatememt extends Statememt{

    public SQLStatememt(String str) {
        super(str);
    }

    @Override
    public void output() {
        System.out.println("MySQL stat: " + conn);
    }
    
}
