package Factory;

public abstract class Statememt {
    protected String conn;
    public Statememt(String str){
        conn = str;
    }
    public abstract void output();
}
