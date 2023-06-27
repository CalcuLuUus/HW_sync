public class Originator {
    private String state;
    private CareTaker careTaker = new CareTaker();

    private void setState(String state){
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    private Memento saveState2Memento(){
        return new Memento(state);
    }

    private void getStateFromMemento(Memento memento){
        this.state = memento.getState();
    }

    public void update(String str){
        careTaker.add(saveState2Memento());
        setState(str);
    }

    public void undo(){
        Memento memento = careTaker.undo(saveState2Memento());
        getStateFromMemento(memento);
    }

    public void redo(){
        Memento memento = careTaker.redo(saveState2Memento());
        getStateFromMemento(memento);
    }

    @Override
    public String toString() {
        return "Originator [state=" + state + "]";
    }

    
}
