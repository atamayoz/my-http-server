package co.vacations.service;

import java.util.ArrayList;
import java.util.List;

public class MyEventSource {
    private final List<MyEventListener> listeners = new ArrayList<>();

    public void addEventListener(MyEventListener eventListener) {
        listeners.add(eventListener);
    }

    public void removeEventListener(MyEventListener eventListener) {
        listeners.remove(eventListener);
    }

    public void fireEvent(String event) {
        System.out.println("Event occurred: " + event);
        notifyListeners(event);
    }

    private void notifyListeners(String event) {
        for (MyEventListener listener: listeners) {
            listener.onEventOccurred(event);
        }
    }
}
