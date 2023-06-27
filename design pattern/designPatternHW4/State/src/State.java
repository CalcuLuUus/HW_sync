public interface State {
    public abstract void doUse(Context context);
    public abstract void toNext(Context context);
}
