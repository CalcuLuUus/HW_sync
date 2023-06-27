import java.util.Stack;

public class CareTaker {
    private Stack<Memento> stackOfUndo = new Stack<>();
    private Stack<Memento> stackOfRedo = new Stack<>();

    public void add(Memento memento){
        stackOfUndo.push(memento);
    }

    public Memento undo(Memento memento){
        stackOfRedo.push(memento);
        return stackOfUndo.pop();
    }

    public Memento redo(Memento memento){
        stackOfUndo.push(memento);
        return stackOfRedo.pop();
    }
}
