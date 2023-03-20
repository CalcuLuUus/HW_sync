
public class ItemList implements Aggregate{
    private item[] items;
    private int last = 0;
    public ItemList(int size){
        items = new item[size];
    }
    public item itemAt(int index){
        return items[index];
    }
    public void add(item i)
    {
        items[last] = i;
        last++;
    }
    public int size()
    {
        return last;
    }
    @Override
    public MyIterator iterator() {
        return new ItemListIterator(this);
    }

}    

