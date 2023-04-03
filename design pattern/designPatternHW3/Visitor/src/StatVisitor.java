public class StatVisitor extends Visitor{

    @Override
    public void visit(CodeElement codeElement) {
        System.out.println("STAT: " + codeElement);
    }

    @Override
    public void visit(LineElement lineElement) {
        System.out.println("STAT: " + lineElement);
    }

    @Override
    public void visit(IdElement idElement) {
        System.out.println("STAT: " + idElement);
    }
    
}
