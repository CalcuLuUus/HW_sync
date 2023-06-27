import java.util.HashMap;
import java.util.Map;

public class ProxyDataSearch extends DataSearch{

    private int query;
    private Map<Integer, String> mp = new HashMap<>();

    @Override
    public void setQuery(int query){
        this.query = query;
    }

    @Override
    public String search() {
        String res = mp.get(query);
        if(res == null){
            DataRealSearch dataRealSearch = new DataRealSearch(query);
            res = dataRealSearch.search();
            mp.put(query, res);
        }
        else
        {
            System.out.println("ALREADY EXISTED");
        }

        return res;
    }
    
}
