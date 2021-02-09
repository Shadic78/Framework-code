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
public abstract class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }
    
    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
    
}
