/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Mvc.Model;
import Transactions.Event;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Equipo1
 */
public class ModeloDatos extends Model {
    private ArrayList<Candidato> datos;

    public ModeloDatos() {
        this.datos = new ArrayList<>();
    }
    
    private void votar(String c) {
        int indice = -1;
        for(int i = 0; i < datos.size(); i++) {
            if(datos.get(i).getNombre().equals(c)) {
                indice = i;
                break;
            }
        }
        if(indice >= 0) {
            datos.get(indice).sumarVoto();   
            notifyObservers();
        }
        else {
            JOptionPane.showMessageDialog(null, "No existe el candidato " + c);
        }
    }
    
    private void reiniciarVotos() {
        for(int i = 0; i < datos.size();  i++) {
            datos.get(i).reiniciar();
        }
    }
    
    @Override
    protected Object callService(Event event) {
        Object obj = null;
        switch(event.getEvent()) {
            case "Votar":
                String candidato = (String) event.getParam("candidato");
                votar(candidato);
                break;
            case "Reiniciar-votos":
                reiniciarVotos();
                break;
            case "Set-votos":
                ArrayList<Candidato> votosNuevos = (ArrayList<Candidato>) event.getParam("votos");
                this.datos = votosNuevos;
                break;
            case "Agregar-candidato":
                Candidato c = (Candidato) event.getParam("candidato");
                agregarDato(c);
                break;
            case "Borrar-candidato":
                Candidato c2 = (Candidato) event.getParam("candidato");
                borrarDato(c2);
                break;
            default:
                System.out.println("No existe ese evento");
        }
        notifyObservers();
        return obj;
    }

    @Override
    public Object getData(String query) {
        Object obj = null;
        switch(query) {
            case "Votos":
                obj = datos;
                break;
            default:
                System.out.println("No hay coincidencias");
        }
        return obj;
    }    
    
    public void borrarDato(Candidato c) {
        for(int i = 0; i < datos.size(); i++) {
            if(datos.get(i).getNombre().equals(c.getNombre())) {
                datos.remove(i);
                break;
            }
        }
    }
    
    public void agregarDato(Candidato c) {
        datos.add(c);
    }

    public ArrayList<Candidato> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Candidato> datos) {
        this.datos = datos;
    }

    public ModeloDatos(ArrayList<Candidato> datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "ModeloDatos{" + "datos=" + datos + '}';
    }
    
}
