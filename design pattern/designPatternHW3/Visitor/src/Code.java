import java.util.ArrayList;
import java.util.List;

public class Code {
    private List<Element> elements = new ArrayList<>();

    public Code(CodeElement codeElement, LineElement lineElement, IdElement idElement){
        elements.add(codeElement);
        elements.add(lineElement);
        elements.add(idElement);
    }

    public void start(Visitor visitor){
        for(Element element : elements){
            element.accept(visitor);
        }
    }
}
