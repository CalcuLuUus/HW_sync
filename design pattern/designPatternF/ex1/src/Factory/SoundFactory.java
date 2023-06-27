package Factory;

import Map.Map;
import Sound.BGM1;
import Sound.BGM2;
import Sound.Sound;
import Weather.Weather;

public class SoundFactory extends AbstractFactory {

    @Override
    public Map getMap(String MapName) throws Exception {
        return null;
    }

    @Override
    public Sound getSound(String SoundName) throws Exception {
        if(SoundName.equalsIgnoreCase("BGM1")){
            return new BGM1();
        }else if(SoundName.equalsIgnoreCase("BGM2")){
            return new BGM2();
        }else{
            throw new Exception("NO SUCH Sound");
        }
    }

    @Override
    public Weather getWeather(String WeatherName) {
        return null;
    }
    
}
