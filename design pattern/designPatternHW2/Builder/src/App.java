public class App {
    public static void main(String[] args) throws Exception {

        CarBuilder carBuilder = new CarBuilder();
        ChunkBuilder chunkBuilder = new ChunkBuilder();

        Director director = new Director(carBuilder);
        director.construct();

        director = new Director(chunkBuilder);
        director.construct();
    }
}
