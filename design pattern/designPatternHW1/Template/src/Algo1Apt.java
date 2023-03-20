public class Algo1Apt extends AbAlgo {
    private AlgoA algoA;

    public Algo1Apt(){
        readData();
        algoA = new AlgoA(getdata());
    }

    @Override
    public void trans() {
        System.out.println("Trans data for Algo 1");
    }

    @Override
    public void solve() {
        algoA.work();
    }

    @Override
    public void print() {
        algoA.print();
    }
}
