public class Manager {
    private Algorithm algorithm;
    public Manager(Algorithm algorithm){
        this.algorithm = algorithm;
    }

    public void apply(){
        System.out.print("Apply algo --> ");
        algorithm.use();
    }
}
