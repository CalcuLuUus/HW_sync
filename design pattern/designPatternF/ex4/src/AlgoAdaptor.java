public class AlgoAdaptor {
    private Algo algo;
    public AlgoAdaptor(Algo algo){
        this.algo = algo;
    }
    public void apply(){
        algo.run();
    }
}
