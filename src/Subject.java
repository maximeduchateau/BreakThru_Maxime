import java.util.ArrayList;

public class Subject {
    private ArrayList<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
}