public class IdElement implements Element{

    private int cntOfId;

    public IdElement(int cntOfId){
        this.cntOfId = cntOfId;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "IdElement [cntOfId=" + cntOfId + "]";
    }

    
    
}
