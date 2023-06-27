public class Music extends Resources{
    String name;
    int state;
    public Music(String name){
        this.name = name;
    }

    @Override
    public void setParams(String pre, int State) {
        this.state = State;
        System.out.println(pre + "Music " + this.name + " set params ==> " + this.state);
    }
    
}
