public class App {
    public static void main(String[] args) throws Exception {
        Context TCP = new Context();

        EstablishedState establishedState = new EstablishedState();
        establishedState.doUse(TCP);
        System.out.println(TCP.getState());

        TCP.getState().toNext(TCP);
        System.out.println(TCP.getState());

        TCP.getState().toNext(TCP);
        System.out.println(TCP.getState());

        TCP.getState().toNext(TCP);
        System.out.println(TCP.getState());
    }
}
