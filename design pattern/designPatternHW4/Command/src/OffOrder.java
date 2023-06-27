public class OffOrder extends Order{
    private Rooms rooms;
    public OffOrder(int id){
        this.rooms = new Rooms(id);
    }

    @Override
    public void excute() {
        rooms.turnOff();
    }
}
