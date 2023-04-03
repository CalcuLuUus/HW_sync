public class TourismAttractionsSubsystem extends SubSystem{

    public TourismAttractionsSubsystem(Mediator mediator) {
        super(mediator);
        //TODO Auto-generated constructor stub
    }

    public void sendMsg(){
        mediator.send("Msg of TourismAttractionsSubsystem", this);
    }

    public void receiveMsg(String Msg){
        System.out.println("Tourism     Attractions  receive: " + Msg);
    }
    
}
