/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Candidato;
import Modelo.ModeloDatos;
import Mvc.Controller;
import Mvc.Model;
import Transactions.Event;
import Vista.FormVotar;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Equipo1
 */
public class CtrlVotar extends Controller {

    public CtrlVotar(Model model) {
        super(model);
    }
    
    public void iniciarFormulario(Event event) {
        FormVotar form = (FormVotar) event.getParam("view");
        ArrayList<Candidato> votos = (ArrayList<Candidato>) super.getModel().getData("Votos");
        rellenarComboCandidatos(form, votos);
    }
    
    public void emitirVoto(Event event) {
        super.getModel().executeService(event);
    }
    
    public void reiniciarVotos(Event event) {
        super.getModel().executeService(event);
    }
    
    public void agregarCandidato(Event event) {
        String nuevoCandidato = JOptionPane.showInputDialog("Nombre del candidato a agregar: ");
        event.addParam("candidato", new Candidato(nuevoCandidato));
        super.getModel().executeService(event);
        
        ArrayList<Candidato> votosActualizados = (ArrayList<Candidato>) super.getModel().getData("Votos");    
        FormVotar form = (FormVotar) event.getParam("view");     
        rellenarComboCandidatos(form, votosActualizados);
    }
    
    public void eliminarCandidato(Event event) {
        String nuevoCandidato = JOptionPane.showInputDialog("Nombre del candidato a borrar: ");
        event.addParam("candidato", new Candidato(nuevoCandidato));
        super.getModel().executeService(event);
        
        ArrayList<Candidato> votosActualizados = (ArrayList<Candidato>) super.getModel().getData("Votos");    
        FormVotar form = (FormVotar) event.getParam("view");     
        rellenarComboCandidatos(form, votosActualizados);
    }    

    private void rellenarComboCandidatos(FormVotar form,  ArrayList<Candidato> votos) {
        form.getComboCandidatos().removeAllItems();

        for (int i = 0; i < votos.size(); i++) {
            form.getComboCandidatos().addItem(votos.get(i).getNombre());
        }
    }

}
