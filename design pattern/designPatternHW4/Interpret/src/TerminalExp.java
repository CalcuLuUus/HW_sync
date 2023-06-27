import java.util.HashSet;
import java.util.Set;

public class TerminalExp extends Expression{
    private Set<String> dbName = new HashSet<>();
    public TerminalExp(String[] s){
        for(String str : s){
            dbName.add(str);
        }
    }
    @Override
    public boolean interpret(String context) {
        return dbName.contains(context);
    }
}
