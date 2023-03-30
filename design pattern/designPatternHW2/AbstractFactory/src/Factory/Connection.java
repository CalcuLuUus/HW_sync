package Factory;

public abstract class Connection {
    protected String conn;
    public Connection(String str){
        this.conn = str;
    }
    public abstract void output();
}
