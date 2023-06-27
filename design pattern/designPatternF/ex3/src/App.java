public class App {
    public static void main(String[] args) throws Exception {
        Music music1 = new Music("M1");
        Music music2 = new Music("M2");
        Role role1 = new Role("R1");
        Role role2 = new Role("r2");
        Scene scene1 = new Scene("s1");
        Scene scene2 = new Scene("s2");

        GameManager gameManager = GameManager.getInstance();

        /*
         * music1 ----- role1 ----- scene1
         *                            |
         *                            |
         *                     music2----scene2
         *                                  |
         *                                 role2 
         */

        scene2.add(role2);
        scene1.add(music2);
        scene1.add(scene2);
        gameManager.addResource(music1);
        gameManager.addResource(role1);
        gameManager.addResource(scene1);

        gameManager.setParams(1);
    }
}
