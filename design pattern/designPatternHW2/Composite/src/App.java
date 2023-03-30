public class App {
    public static void main(String[] args) throws Exception {
        Group g1 = new Group("Big group");
        Group g2 = new Group("Small group");

        Member m1 = new Member("mem1");
        Member m2 = new Member("mem2");
        Member m3 = new Member("mem3");

        g1.add(m1);
        g1.add(m2);
        g2.add(m3);
        g1.add(g2);
        m1.add(m2);

        g1.print();
        g2.print();
    }

}
