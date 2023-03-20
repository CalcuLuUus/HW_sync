import java.util.ArrayList;
import java.util.List;

public class AlgoB {
    private List<Integer> data;
    private List<List<Integer>> ret;

    public AlgoB(List<Integer> list){
        data = list;
    }

    public void work(){
        ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        ret.add(new ArrayList<>());

        int half = data.size() / 2;
        for(int i = 0; i < half; i++)
        {
            ret.get(0).add(data.get(i));
        }
        for(int i = half; i < data.size(); i++)
        {
            ret.get(1).add(data.get(i));
        }
    }

    public void print(){
        System.out.println("ALGO 2");
        System.out.println("Cat 1:");
        for(int x : ret.get(1)){
            System.out.println(x);
        }
        System.out.println("Cat 2:");
        for(int x : ret.get(0)){
            System.out.println(x);
        }
    }
}
