public class App {
    public static void main(String[] args) throws Exception {
        Scene scene = new Scene();

        Scene s1 = new SurtaceRendering(scene);
        Scene s2 = new VolumeRendering(scene);

        Algo algo = new Algo();
        AlgoAdaptor algoAdaptor = new AlgoAdaptor(algo);
        Scene s3 = new AlgoTrans(scene, algoAdaptor);
        Scene s4 = new VolumeRendering(s1);

        s1.apply();
        s2.apply();
        s3.apply();
        s4.apply();
    }
}
