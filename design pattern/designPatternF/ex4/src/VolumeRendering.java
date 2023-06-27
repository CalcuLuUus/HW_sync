public class VolumeRendering extends NewAlgo{

    public VolumeRendering(Scene scene) {
        super(scene);
    }

    public void apply(){
        System.out.print("VolumeRendering --> ");
        scene.apply();
    }
    
}
