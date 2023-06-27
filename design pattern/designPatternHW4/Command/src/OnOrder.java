public class OnOrder extends Order{

    private Rooms rooms;
    public OnOrder(int id){
        this.rooms = new Rooms(id);
    }

    @Override
    public void excute() {
        rooms.turnOn();
    }
    
}
