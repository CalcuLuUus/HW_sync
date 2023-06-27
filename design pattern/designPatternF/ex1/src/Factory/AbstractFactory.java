package Factory;
import Map.Map;
import Sound.Sound;
import Weather.Weather;

public abstract class AbstractFactory {
    public abstract Map getMap(String MapName) throws Exception;
    public abstract Sound getSound(String SoundName) throws Exception;
    public abstract Weather getWeather(String WeatherName) throws Exception;
}
