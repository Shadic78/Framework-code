/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mvc;

import Transactions.Event;

/**
 *
 * @author Equipo1
 */
public abstract class Model extends Observable {

    public Object executeService(Event event) {
        Object result = callService(event);
        return result;
    }

    protected abstract Object callService(Event event);

    public abstract Object getData(String query);

}
