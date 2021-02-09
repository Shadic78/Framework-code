/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mvc;

import java.util.ArrayList;

/**
 *
 * @author Equipo1
 */
public class Observable {
    private ArrayList<Observer> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(Observer ob) {
        observers.add(ob);
    }
    
    public void deleteObserver(Observer ob) {
        observers.remove(ob);
    }
    
    public void notifyObservers() {
        observers.forEach(ob -> ob.update());
    }
    
}
