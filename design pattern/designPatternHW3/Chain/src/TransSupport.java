public class TransSupport extends Support{

    public TransSupport(String name) {
        super(name, 2);
    }

    @Override
    public boolean solve(Trouble trouble) {
        return trouble.getId() == id || trouble.getId() < 0;
    }
    
}
