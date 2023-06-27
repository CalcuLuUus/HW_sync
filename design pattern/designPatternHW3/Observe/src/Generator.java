import java.util.ArrayList;
import java.util.List;

public class Generator {
    private List<Observer> observers = new ArrayList<>();
    private String state;

    public String getState(){
        return state;
    }

    public void setState(String str){
        state = str;
        notifyAllObserver();
    }

    public void notifyAllObserver(){
        for(Observer observer : observers){
            observer.update();
        }
    }

    public void add(Observer observer){
        observers.add(observer);
    }
}
