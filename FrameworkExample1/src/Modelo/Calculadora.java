/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Mvc.Model;
import Transactions.Event;

/**
 *
 * @author Equipo1
 */
public class Calculadora extends Model {

    @Override
    public Object callService(Event event) {
        Object result = null;
        switch (event.getEvent()) {
            case "Sumar":
                result = Integer.parseInt((String) event.getParam("numA")) + Integer.parseInt((String) event.getParam("numB"));
                break;
            case "multiplicar":
                result = Integer.parseInt((String) event.getParam("numA")) * Integer.parseInt((String) event.getParam("numB"));
                break;                
            default:
                System.out.println("No existe ese evento");
        }
        return result;
    }

    @Override
    public Object getData(String query) {
        return "No hay nada que devolver";
    }

}
