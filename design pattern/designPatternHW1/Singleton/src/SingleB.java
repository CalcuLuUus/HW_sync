public class SingleB {
    private static SingleB singleB;
    private SingleB(){
        System.out.println("双重锁单例");
    }
    public static SingleB getInstance(){
        if(singleB == null){
            synchronized(SingleB.class){
                if(singleB == null){
                    singleB = new SingleB();
                }
            }
        }
        return singleB;
    }
    public void print(){
        System.out.println("I am singleB");
    }
}
