import Factory.AbstractFactory;

public class Scene2 extends Scene{
    
    public Scene2(){
        this.mapName = "Dust3";
        this.soundName = "BGM1";
        this.weatherName = "Sunny";
        init();
    }

    @Override
    void init() {
        try {
            AbstractFactory mapFactory = FactoryProducer.geFactory("Map");
            AbstractFactory soundFactory = FactoryProducer.geFactory("Sound");
            AbstractFactory weatherFactory = FactoryProducer.geFactory("Weather");

            map = mapFactory.getMap(this.mapName);
            sound = soundFactory.getSound(this.soundName);
            weather = weatherFactory.getWeather(this.weatherName);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    
}
