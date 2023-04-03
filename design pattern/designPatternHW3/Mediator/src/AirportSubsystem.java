public class AirportSubsystem extends SubSystem{

    public AirportSubsystem(Mediator mediator) {
        super(mediator);
        //TODO Auto-generated constructor stub
    }

    public void sendMsg(){
        mediator.send("Msg of AirportSubsystem", this);
    }

    public void receiveMsg(String Msg){
        System.out.println("Airport  receive: " + Msg);
    }
    
}
