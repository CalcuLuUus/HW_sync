public class ChunkBuilder extends Builder{
    @Override
    public void makeBody() {
        System.out.println("Chunk: makeBody finish!");
    }

    @Override
    public void makeEng() {
        System.out.println("Chunk: makeEng finish!");
    }

    @Override
    public void makeWheel() {
        System.out.println("Chunk: makeWheel finish!");
    }

    @Override
    public void makeOther() {
        System.out.println("Chunk: makeOther finish!");
    }
    
}
