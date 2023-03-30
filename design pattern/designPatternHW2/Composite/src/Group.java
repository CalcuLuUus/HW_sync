import java.util.ArrayList;

public class Group extends ListItem{
    private String name;
    private ArrayList<ListItem> groupMember;

    public Group(String str){
        this.name = str;
        groupMember = new ArrayList<>();
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void printInfo(String pre) {
        for(ListItem item : groupMember){
            item.printInfo(pre + name + '/');
        }
    }

    public void add(ListItem item){
        groupMember.add(item);
    }
    
}
