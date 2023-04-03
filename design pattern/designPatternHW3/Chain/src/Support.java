public abstract class Support {
    private Support next;
    protected int id;
    private String name;

    public Support(String name, int id){
        this.name = name;
        this.id = id;
    }
    
    public abstract boolean solve(Trouble trouble);
    
    @Override
    public String toString() {
        return "Support [name=" + name + "]";
    }

    public Support setNext(Support support){
        this.next = support;
        return this.next;
    }
    public void fail(Trouble trouble){
        System.out.println(trouble + " cannot solve");
    }

    public void done(Trouble trouble){
        System.out.println(trouble + " is solved by " + this);
    }
    public void support(Trouble trouble){
        if(solve(trouble)){
            done(trouble);
        }else if(next == null){
            fail(trouble);
        }else{
            next.support(trouble);
        }
    }
}
