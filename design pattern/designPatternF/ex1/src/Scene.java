import Map.Map;
import Sound.Sound;
import Weather.Weather;

public abstract class Scene {
    String mapName;
    String weatherName;
    String soundName;
    Map map;
    Weather weather;
    Sound sound;
    abstract void init();
    public void run(){
        if(map == null || weather == null || sound == null){
            System.out.println("Init failed");
            return ;
        }
        map.display();
        sound.play();
        weather.show();
    }
}
