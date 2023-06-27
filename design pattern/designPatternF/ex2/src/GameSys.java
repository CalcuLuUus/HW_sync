import java.util.ArrayList;

public class GameSys {
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void delObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyAllObserver(){
        for(Observer observer : observers){
            observer.update(this);
        }
    }

    public int state = 0;

    public void excute(){
        state++;
        notifyAllObserver();
    }
}
