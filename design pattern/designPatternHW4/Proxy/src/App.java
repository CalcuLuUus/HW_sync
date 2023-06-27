public class App {
    public static void main(String[] args) throws Exception {
        DataSearch dataSearch = new ProxyDataSearch();

        dataSearch.setQuery(1);
        dataSearch.printres();

        dataSearch.setQuery(1);
        dataSearch.printres();

        dataSearch.setQuery(2);
        dataSearch.printres();
    };
}
