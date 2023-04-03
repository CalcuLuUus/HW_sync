public class HotelsSubsystem extends SubSystem {

    public HotelsSubsystem(Mediator mediator) {
        super(mediator);
    }
    
    public void sendMsg(){
        mediator.send("Msg of HotelsSubsystem", this);
    }

    public void receiveMsg(String Msg){
        System.out.println("Hotels receive: " + Msg);
    }
}
