/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import GraficaBarras.VentanaBarras;
import GraficaPastel.VentanaPastel;
import Logs.LogsManager;
import Modelo.Candidato;
import Modelo.ModeloDatos;
import Mvc.Model;
import Transactions.TransactionInvoker;
import java.awt.EventQueue;
import java.util.HashMap;

/**
 *
 * @author Equipo1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Iniciar el Framework
        LogsManager.init();        
        TransactionInvoker.init();
     
        Candidato c1 = new Candidato("Carlos");
        Candidato c2 = new Candidato("Moises");
        Candidato c3 = new Candidato("Henry");

        // Obtener el modelo y agregarle datos
        TransactionInvoker invoker = TransactionInvoker.getInstance();        
        ModeloDatos modelo = (ModeloDatos) invoker.getModel("modelo-votos");
        modelo.agregarDato(c1);
        modelo.agregarDato(c2);
        modelo.agregarDato(c3);

        // Crear el menu principal
        FormVotar menu = new FormVotar(modelo);

        // Crear las ventanas de las graficas
        VentanaPastel pastel = new VentanaPastel(modelo);
        VentanaBarras barras = new VentanaBarras(modelo);

        // Agregar los observadores al modelo de datos
        modelo.addObserver(pastel);
        modelo.addObserver(barras);

        barras.setVisible(true);
        pastel.setVisible(true);
        menu.setVisible(true);
    }

}
