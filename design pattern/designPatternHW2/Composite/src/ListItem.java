public abstract class ListItem {
    public abstract String getName();
    public abstract void printInfo(String pre);
    public void print(){
        printInfo("");
    }
    public void add(ListItem item){
        System.out.println("Can not add a item into a member");
    }
}
