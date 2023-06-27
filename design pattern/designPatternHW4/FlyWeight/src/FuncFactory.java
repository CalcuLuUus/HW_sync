import java.util.HashMap;
import java.util.Map;

public class FuncFactory {
    private static FuncFactory factory = new FuncFactory();
    private Map<Integer, func> mp = new HashMap<>();

    private FuncFactory(){}
    public static FuncFactory getInstance(){
        return factory;
    }

    public func getFunc(int id){
        func funcx = mp.get(id);
        if(funcx == null){
            System.out.println("First call, create object <func" + id + ">");
            if(id == 1){
                funcx = new func1();
            }else{
                funcx = new func2();
            }
            mp.put(id, funcx);
        }

        return funcx;
    }
}
