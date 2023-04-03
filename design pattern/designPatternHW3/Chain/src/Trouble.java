public class Trouble {
    private int id;

    public Trouble(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Trouble [id=" + id + "]";
    }
    
}
