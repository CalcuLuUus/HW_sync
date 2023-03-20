package HTTP;

import framwork.ProConnect;
import framwork.Protocol;

public class HTTPPro extends Protocol {
    @Override
    protected ProConnect createConn() {
        System.out.println("HTTP CONNETING...");
        return new HTTPConnect();
    }

}
