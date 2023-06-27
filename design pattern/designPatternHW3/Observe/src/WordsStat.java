import java.util.Map;
import java.util.TreeMap;

public class WordsStat extends Observer{

    private Map<String, Integer> map = new TreeMap<>();

    public WordsStat(Generator generator){
        this.generator = generator;
        generator.add(this);
    }

    @Override
    public void update() {
        String content = generator.getState();
        String[] words = content.split(" ");
        for(String word : words){
            int cntOfNowWord = 0;
            if(map.containsKey(word)){
                cntOfNowWord = map.get(word);
            }
            map.put(word, cntOfNowWord + 1);
        }
        System.out.println(this.getClass().getName() + ": " + map);
    }
    
}
