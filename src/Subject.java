import java.util.ArrayList;

public class Subject {
    private ArrayList<Observer> observers;

    public Subject() {
        ArrayList<Observer> observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void notify() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
}