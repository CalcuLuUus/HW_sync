public class RestaurantsSubsystem extends SubSystem {

    public RestaurantsSubsystem(Mediator mediator) {
        super(mediator);
        //TODO Auto-generated constructor stub
    }

    public void sendMsg(){
        mediator.send("Msg of RestaurantsSubsystem", this);
    }

    public void receiveMsg(String Msg){
        System.out.println("Restaurants  receive: " + Msg);
    }
    
}
