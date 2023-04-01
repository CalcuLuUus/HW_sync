public class Mid extends Mediator{

    private AirportSubsystem airportSubsystem;
    private HotelsSubsystem hotelsSubsystem;
    private RavelCompaniesSubsystem ravelCompaniesSubsystem;
    private RestaurantsSubsystem restaurantsSubsystem;
    private TourismAttractionsSubsystem tourismAttractionsSubsystem;

    
    public void setAirportSubsystem(AirportSubsystem airportSubsystem) {
        this.airportSubsystem = airportSubsystem;
    }


    public void setHotelsSubsystem(HotelsSubsystem hotelsSubsystem) {
        this.hotelsSubsystem = hotelsSubsystem;
    }


    public void setRavelCompaniesSubsystem(RavelCompaniesSubsystem ravelCompaniesSubsystem) {
        this.ravelCompaniesSubsystem = ravelCompaniesSubsystem;
    }


    public void setRestaurantsSubsystem(RestaurantsSubsystem restaurantsSubsystem) {
        this.restaurantsSubsystem = restaurantsSubsystem;
    }


    public void setTourismAttractionsSubsystem(TourismAttractionsSubsystem tourismAttractionsSubsystem) {
        this.tourismAttractionsSubsystem = tourismAttractionsSubsystem;
    }


    @Override
    public void send(String msg, SubSystem subSystem) {
        if(subSystem == ravelCompaniesSubsystem){
            hotelsSubsystem.receiveMsg(msg);
            restaurantsSubsystem.receiveMsg(msg);
            airportSubsystem.receiveMsg(msg);
            tourismAttractionsSubsystem.receiveMsg(msg);
        }else if(subSystem == hotelsSubsystem){
            ravelCompaniesSubsystem.receiveMsg(msg);
            restaurantsSubsystem.receiveMsg(msg);
            airportSubsystem.receiveMsg(msg);
            tourismAttractionsSubsystem.receiveMsg(msg);
        }else if(subSystem == airportSubsystem){
            ravelCompaniesSubsystem.receiveMsg(msg);
            restaurantsSubsystem.receiveMsg(msg);
            hotelsSubsystem.receiveMsg(msg);
            tourismAttractionsSubsystem.receiveMsg(msg);
        }
    }
    
}
