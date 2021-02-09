/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mvc;

import Transactions.Event;
import javax.swing.JFrame;


/**
 *
 * @author Equipo1
 */
public abstract class View extends JFrame implements Observer {
    private Model model;

    public View(Model model) {
        this.model = model;
    }   

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "View{ class=" + this.getClass() + ", model=" + model.getClass() + '}';
    }
    
}
