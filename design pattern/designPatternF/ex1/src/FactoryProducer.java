import Factory.AbstractFactory;
import Factory.MapFactory;
import Factory.SoundFactory;
import Factory.WeatherFactory;

public class FactoryProducer {
    public static AbstractFactory geFactory(String FactoryName) throws Exception{
        if(FactoryName.equalsIgnoreCase("Map")){
            return new MapFactory();
        }else if(FactoryName.equalsIgnoreCase("Weather")){
            return new WeatherFactory();
        }else if(FactoryName.equalsIgnoreCase("Sound")){
            return new SoundFactory();
        }else{
            throw new Exception("NO SUCH Factory");
        }
    }
}
