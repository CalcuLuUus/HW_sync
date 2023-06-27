public class SurtaceRendering extends NewAlgo{

    public SurtaceRendering(Scene scene) {
        super(scene);
    }

    public void apply(){
        System.out.print("SurtaceRendering --> ");
        scene.apply();
    }
    
}
