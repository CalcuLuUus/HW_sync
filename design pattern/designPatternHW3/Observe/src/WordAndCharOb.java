public class WordAndCharOb extends Observer {

    public WordAndCharOb(Generator generator){
        this.generator = generator;
        generator.add(this);
    }

    @Override
    public void update() {
        String content = generator.getState();
        String[] words = content.split(" ");
        int cntOfChar = 0;
        for(String word : words){
            cntOfChar += word.length();
        }
        System.out.println(this.getClass().getName() + ": Words: " + words.length + " Char: " + cntOfChar);
    }
    
}
