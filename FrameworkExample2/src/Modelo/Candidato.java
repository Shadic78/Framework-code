/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Equipo1
 */
public class Candidato {
    private String nombre;
    private int dato;

    public Candidato(String nombre) {
        this.nombre = nombre;
        this.dato = 0;
    }
    
    public void sumarVoto() {
        dato++;
    }
    
    public void reiniciar() {
        dato = 0;
    }
    
    public int getDato() {
        return dato;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Candidato{" + "nombre =" + nombre + ", numVotos =" + dato + '}';
    }    
    
}
