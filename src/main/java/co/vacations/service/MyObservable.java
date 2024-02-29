package co.vacations.service;

import java.util.Observable;

public class MyObservable extends Observable {
    public void setItemChanged(String response) {
        setChanged();
        notifyObservers(response);
    }
}
