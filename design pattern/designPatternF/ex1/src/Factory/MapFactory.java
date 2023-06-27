package Factory;

import Map.Dust2;
import Map.Map;
import Map.Mirage;
import Sound.Sound;
import Weather.Weather;

public class MapFactory extends AbstractFactory{

    @Override
    public Map getMap(String MapName) throws Exception {
        if(MapName.equalsIgnoreCase("dust2")){
            return new Dust2();
        }else if(MapName.equalsIgnoreCase("mirage")){
            return new Mirage();
        }else{
            throw new Exception("NO SUCH MAP");
        }
    }

    @Override
    public Sound getSound(String SoundName) {
        return null;
    }

    @Override
    public Weather getWeather(String WeatherName) {
        return null;
    }
    
}
