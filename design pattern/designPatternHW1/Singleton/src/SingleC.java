public class SingleC {
    private SingleC(){
        System.out.println("IoDH单例");
    }
    private static class HolderClass{
        private final static SingleC singleC = new SingleC();
    }
    public static SingleC getInstance(){
        return HolderClass.singleC;
    }
    public void print(){
        System.out.println("I am singleC");
    }
}
