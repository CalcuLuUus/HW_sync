public abstract class Visitor {
    public abstract void visit(CodeElement codeElement);
    public abstract void visit(LineElement lineElement);
    public abstract void visit(IdElement idElement);
}
