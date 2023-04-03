public class CodeElement implements Element{

    private int cntOfClass;
    private int cntOfMethod;
    private int cntOfProperty;

    public CodeElement(int cntOfClass, int cntOfMethod, int cntOfProperty){
        this.cntOfClass = cntOfClass;
        this.cntOfMethod = cntOfMethod;
        this.cntOfProperty = cntOfProperty;
    }

    
    @Override
    public String toString() {
        return "CodeElement [cntOfClass=" + cntOfClass + ", cntOfMethod=" + cntOfMethod + ", cntOfProperty="
                + cntOfProperty + "]";
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
}
