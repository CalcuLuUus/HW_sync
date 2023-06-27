public class Rooms {
    private int id;
    public Rooms(int id)
    {
        this.id = id;
    }

    public void turnOn(){
        System.out.println("Room " + id + " turns on the light");
    }

    public void turnOff(){
        System.out.println("Room " + id + " turns off the light");
    }
}
