public class App {
    public static void main(String[] args) throws Exception {
        CodeElement codeElement = new CodeElement(4, 20, 3);
        LineElement lineElement = new LineElement(200);
        IdElement idElement = new IdElement(70);

        Code code = new Code(codeElement, lineElement, idElement);
        Visitor visitor = new StatVisitor();

        code.start(visitor);
    }
}
