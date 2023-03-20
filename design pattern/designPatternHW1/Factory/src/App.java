import HTTP.HTTPPro;
import framwork.Protocol;

public class App {
    public static void main(String[] args) throws Exception {
        Protocol protocol = new HTTPPro();
        protocol.createAndUse();
    }
}
