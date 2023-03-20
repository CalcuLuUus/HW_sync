package framwork;
public abstract class Protocol {
    public void createAndUse(){
        ProConnect proConnect = createConn();
        proConnect.use();
    }
    protected abstract ProConnect createConn();
}
