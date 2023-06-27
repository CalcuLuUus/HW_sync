import java.util.Set;
import java.util.TreeSet;

public class WordsUnique extends Observer{

    private Set<String> set = new TreeSet<>();

    public WordsUnique(Generator generator){
        this.generator = generator;
        generator.add(this);
    }

    @Override
    public void update() {
        String content = generator.getState();
        String[] words = content.split(" ");
        for(String word : words){
            set.add(word);
        }
        System.out.println(this.getClass().getName() + ": " + set);
    }
    
}
