public class Role extends Resources{
    String name;
    int state;
    public Role(String name){
        this.name = name;
    }

    @Override
    public void setParams(String pre, int State) {
        this.state = State;
        System.out.println(pre + "Role " + this.name + " set params ==> " + this.state);
    }
    
}
