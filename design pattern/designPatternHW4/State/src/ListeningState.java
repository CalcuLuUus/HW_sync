public class ListeningState implements State{

    @Override
    public void doUse(Context context) {
        System.out.println("TCP listening state");
        context.setState(this);
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "......";
    }

    @Override
    public void toNext(Context context) {
        System.out.println("Changing State......");
        ClosingState closingState = new ClosingState();
        closingState.doUse(context);
    }
    
}
