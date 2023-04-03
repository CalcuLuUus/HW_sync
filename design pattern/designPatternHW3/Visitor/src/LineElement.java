public class LineElement implements Element{
    private int cntOfLine;

    public LineElement(int cntOfLine){
        this.cntOfLine = cntOfLine;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LineElement [cntOfLine=" + cntOfLine + "]";
    }
    
}
