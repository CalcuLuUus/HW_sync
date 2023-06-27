public abstract class User {
    protected int id;
    public void getList() {
        System.out.println("User level: " + id);
        System.out.println("PowerList: ");
        FuncFactory factory = FuncFactory.getInstance();
        for(int i = 1; i <= id; i++)
        {
            func funcx = factory.getFunc(i);
            funcx.Use();
        }
    }
}
