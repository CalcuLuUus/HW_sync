public class SingleA {
    private static SingleA singleA = new SingleA();
    private SingleA(){
        System.out.println("饿汉式单例");
    }
    public static SingleA getInstance(){
        return singleA;
    }
    public void print(){
        System.out.println("I am singleA");
    }
}
