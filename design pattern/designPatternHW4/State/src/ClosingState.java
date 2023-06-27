public class ClosingState implements State {
    @Override
    public void doUse(Context context) {
        System.out.println("TCP closed state");
        context.setState(this);
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "......";
    }

    @Override
    public void toNext(Context context) {
        System.out.println("Already closed.");
    }
}
