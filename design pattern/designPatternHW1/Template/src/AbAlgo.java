import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbAlgo {
    private List<Integer> data;
    public void readData(){
        data = new ArrayList<>();
        int n = 7;
        Random random = new Random();
        for(int i = 0; i < n; i++)
        {
            int num = Math.abs(random.nextInt(100));
            data.add(num);
        }
    }
    public List<Integer> getdata(){
        return data;
    }
    public abstract void trans();
    public abstract void solve();
    public abstract void print();
    public void display(){
        trans();
        solve();
        print();
    }

}
