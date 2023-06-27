public class DataRealSearch extends DataSearch{

    private int query;

    public DataRealSearch(int query){
        this.query = query;
    }


    @Override
    public String search() {
        System.out.println("FINDING.......");
        return "Respoonse " + query + ": FINISHED";
    }

    @Override
    public void setQuery(int query) {
       this.query = query;
    }
    
}
