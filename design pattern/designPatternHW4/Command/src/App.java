import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<Order> list = new ArrayList<>();
        list.add(new OnOrder(0));
        list.add(new OffOrder(0));
        list.add(new OnOrder(1));

        for(Order order : list){
            order.excute();
        }
    }
}
