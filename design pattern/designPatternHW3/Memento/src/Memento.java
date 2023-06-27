public class Memento {
    private String state;

    public Memento(String str){
        this.state = str;
    }

    public String getState(){
        return this.state;
    }
}
