public class App {
    public static void main(String[] args) throws Exception {
        GameSys gameSys = new GameSys();
        
        Observer p1 = new PlayerA();
        Observer p2 = new Player2();

        gameSys.addObserver(p1);
        gameSys.addObserver(p2);

        gameSys.excute();

        gameSys.delObserver(p1);

        gameSys.excute();
    }
}
