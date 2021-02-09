/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaBarras;

import Modelo.ModeloDatos;
import Mvc.Model;
import Mvc.View;
import javax.swing.JFrame;

/**
 *
 * @author Equipo1
 */
public class VentanaBarras extends View {
    private GraficaBarras grafica;

    public VentanaBarras(Model model) {
        super(model);
        initUI((ModeloDatos) model);
    }

    private void initUI(ModeloDatos m) {
        grafica = new GraficaBarras(m);
        grafica.setSizeGrafica(50);
        add(grafica);
        setSize(500, 400);
        setTitle("Grafica de barras");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GraficaBarras getGrafica() {
        return grafica;
    }

    public void setGrafica(GraficaBarras grafica) {
        this.grafica = grafica;
    }

    @Override
    public void update() {
        grafica.repaint();
    }
    
}
