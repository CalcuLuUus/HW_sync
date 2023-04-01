public class App {
    public static void main(String[] args) throws Exception {
        Mid mediator = new Mid();

        AirportSubsystem airportSubsystem = new AirportSubsystem(mediator);
        HotelsSubsystem hotelsSubsystem = new HotelsSubsystem(mediator);
        RavelCompaniesSubsystem ravelCompaniesSubsystem = new RavelCompaniesSubsystem(mediator);
        RestaurantsSubsystem restaurantsSubsystem = new RestaurantsSubsystem(mediator);
        TourismAttractionsSubsystem tourismAttractionsSubsystem = new TourismAttractionsSubsystem(mediator);

        mediator.setAirportSubsystem(airportSubsystem);
        mediator.setHotelsSubsystem(hotelsSubsystem);
        mediator.setRavelCompaniesSubsystem(ravelCompaniesSubsystem);
        mediator.setTourismAttractionsSubsystem(tourismAttractionsSubsystem);
        mediator.setRestaurantsSubsystem(restaurantsSubsystem);

        ravelCompaniesSubsystem.sendMsg();
        System.out.println("===================");
        hotelsSubsystem.sendMsg();
        System.out.println("===================");
        airportSubsystem.sendMsg();
        System.out.println("===================");

    }
}
