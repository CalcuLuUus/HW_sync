package MySQL;

import Factory.AbstractFactory;
import Factory.Connection;
import Factory.Statememt;

public class SQLFactory extends AbstractFactory {

    @Override
    public Connection createConnection(String str) {
        return new SQLConnection(str);
    }

    @Override
    public Statememt createStatememt(String str) {
        return new SQLStatememt(str);
    }
    
}
