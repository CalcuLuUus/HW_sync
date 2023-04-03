public class CharSupport extends Support {

    public CharSupport(String name) {
        super(name, 1);
    }

    @Override
    public boolean solve(Trouble trouble) {
        return trouble.getId() == id;
    }
    
}
