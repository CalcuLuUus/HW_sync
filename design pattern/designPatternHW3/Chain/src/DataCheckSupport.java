public class DataCheckSupport extends Support{

    public DataCheckSupport(String name) {
        super(name, 3);
    }

    @Override
    public boolean solve(Trouble trouble) {
        return trouble.getId() > 2;
    }
    
}
