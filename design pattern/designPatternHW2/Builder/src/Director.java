public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder = builder;
    }

    public void construct(){
        System.out.println("车身制作环节：");
        builder.makeBody();
        System.out.println("引擎制作环节：");
        builder.makeEng();
        System.out.println("车轮制作环节：");
        builder.makeWheel();
        System.out.println("其他部件制作环节：");
        builder.makeOther();
        System.out.println("COMPLETED!");
    }
}
