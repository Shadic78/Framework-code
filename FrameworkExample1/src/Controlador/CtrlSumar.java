/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Mvc.Controller;
import Mvc.Model;
import Mvc.View;
import Transactions.Event;
import Vista.FormSumar;

/**
 *
 * @author Equipo1
 */
public class CtrlSumar extends Controller {

    public CtrlSumar(Model model) {
        super(model);
    }
    
    public void sumar(Event event) {
        // Calcular la suma
        int result = (int)super.getModel().executeService(event);
        event.addParam("resultado", result);
        // Mostrar el resultado en la vista
        FormSumar form = (FormSumar)event.getParam("view");
        form.getLbResult().setText(Integer.toString(result));
    }
    
}
