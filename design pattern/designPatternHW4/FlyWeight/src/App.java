public class App {
    public static void main(String[] args) throws Exception {
        User user1 = new Customer();
        User user2 = new Manager();

        user1.getList();
        user2.getList();
    }
}
