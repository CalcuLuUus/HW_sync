public abstract class DataSearch {
    public abstract String search();
    public abstract void setQuery(int query);
    public void printres(){
        System.out.println(search());
    }
}
