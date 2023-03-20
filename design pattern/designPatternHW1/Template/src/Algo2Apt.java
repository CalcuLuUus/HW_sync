public class Algo2Apt extends AbAlgo{
    private AlgoB algoB;

    public Algo2Apt(){
        readData();
        algoB = new AlgoB(getdata());
    }

    @Override
    public void trans() {
        System.out.println("Trans data for Algo 2");
    }

    @Override
    public void solve() {
        algoB.work();
    }

    @Override
    public void print() {
        algoB.print();
    }
}
