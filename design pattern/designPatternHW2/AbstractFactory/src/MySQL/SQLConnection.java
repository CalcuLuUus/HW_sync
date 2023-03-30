package MySQL;

import Factory.Connection;

public class SQLConnection extends Connection{

    public SQLConnection(String str) {
        super(str);
    }

    @Override
    public void output() {
        System.out.println("MySQL Connection:" + conn);
    }
    
}
