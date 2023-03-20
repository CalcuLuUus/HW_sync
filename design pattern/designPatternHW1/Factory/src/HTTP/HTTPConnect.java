package HTTP;

import framwork.ProConnect;

public class HTTPConnect extends ProConnect {
    public HTTPConnect(){
        System.out.println("HTTP CONNECTING SUCCESS");
    }
    @Override
    public void use() {
        System.out.println("HTTP Trans Info");
    }
    
}
