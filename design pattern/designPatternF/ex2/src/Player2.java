public class Player2 extends Observer{

    @Override
    public void update(GameSys sys) {
        System.out.println("player2 update: sys state " + sys.state);
    }
    
}
