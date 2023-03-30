public class Member extends ListItem{
    private String name;
    public Member(String str){
        this.name = str;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void printInfo(String pre) {
        System.out.println(pre + name);
    }

    
    
}
