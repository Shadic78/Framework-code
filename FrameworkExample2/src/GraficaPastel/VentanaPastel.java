/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaPastel;

import Modelo.ModeloDatos;
import Mvc.Model;
import Mvc.View;
import javax.swing.JFrame;

/**
 *
 * @author Equipo1
 */
public class VentanaPastel extends View {
    private GraficaPastel grafica;

    public VentanaPastel(Model model) {
        super(model);
        initUI((ModeloDatos) model);
    }

    private void initUI(ModeloDatos m) {
        grafica = new GraficaPastel(m);
        grafica.setSizeGrafica(200);
        add(grafica);
        setSize(500, 400);
        setTitle("Grafica de pastel");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GraficaPastel getGrafica() {
        return grafica;
    }

    public void setGrafica(GraficaPastel grafica) {
        this.grafica = grafica;
    }

    @Override
    public void update() {
        grafica.repaint();
    }
        
}
