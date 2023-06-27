public class AlgoTrans extends NewAlgo{
    private AlgoAdaptor adaptor;
    protected AlgoTrans(Scene scene, AlgoAdaptor adaptor) {
        super(scene);
        this.adaptor = adaptor;
    }
    public void apply(){
        adaptor.apply();
        scene.apply();
    }
    
}
