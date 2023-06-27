package Factory;

import Map.Map;
import Sound.Sound;
import Weather.Rainy;
import Weather.Sunny;
import Weather.Weather;

public class WeatherFactory extends AbstractFactory{

    @Override
    public Map getMap(String MapName) throws Exception {
        return null;
    }

    @Override
    public Sound getSound(String SoundName) throws Exception {
        return null;
    }

    @Override
    public Weather getWeather(String WeatherName) throws Exception {
        if(WeatherName.equalsIgnoreCase("Sunny")){
            return new Sunny();
        }else if(WeatherName.equalsIgnoreCase("Rainy")){
            return new Rainy();
        }else{
            throw new Exception("NO SUCH Weather");
        }
    }
    
}
