public class RavelCompaniesSubsystem extends SubSystem{

    public RavelCompaniesSubsystem(Mediator mediator) {
        super(mediator);
    }

    public void sendMsg(){
        mediator.send("Msg of RavelCompaniesSubsystem", this);
    }

    public void receiveMsg(String Msg){
        System.out.println("Ravel Companies receive: " + Msg);
    }
    
}
