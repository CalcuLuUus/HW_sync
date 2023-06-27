public class PlayerA extends Observer{

    @Override
    public void update(GameSys sys) {
        System.out.println("player1 update: sys state " + sys.state);
    }
    
}
