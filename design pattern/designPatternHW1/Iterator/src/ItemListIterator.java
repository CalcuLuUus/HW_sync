public class ItemListIterator implements MyIterator {
    private ItemList itemList;
    private int index;
    private int cntOfPage;

    public ItemListIterator(ItemList itemList) {
        this.itemList = itemList;
        index = 0;
    }

    

    public void setCntOfPage(int cntOfPage) {
        this.cntOfPage = cntOfPage;
    }



    @Override
    public boolean hasNext() {
        if(index < itemList.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object[] next() {
        int st = index;
        int ed = index + cntOfPage;
        ed = Math.min(ed, itemList.size());
        item[] ret = new item[ed - st];
        for(int i = st; i < ed; i++)
        {
            ret[i - st] = itemList.itemAt(i);
        }
        index = ed;
        return ret;
    }
    
}
