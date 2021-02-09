/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaBarras;

import Modelo.ModeloDatos;
import Mvc.Observer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Equipo1
 */
public class GraficaBarras extends JPanel {

    private ArrayList<Color> colores = new ArrayList<Color>();
    private ModeloDatos modelo;
    private int sizeGrafica;
    private int correccionCoordenadas;
    private int coordX, coordY;
    private int altoGrafica;

    public GraficaBarras(ModeloDatos modelo) {
        this.modelo = modelo;
        this.altoGrafica = 0;
        colores.add(Color.RED);
        colores.add(Color.BLUE);
        colores.add(Color.GREEN);
        colores.add(Color.BLACK);
        colores.add(Color.CYAN);
        colores.add(Color.MAGENTA);    
        colores.add(Color.ORANGE);
        colores.add(Color.YELLOW);        
    }

    public int getSizeGrafica() {
        return sizeGrafica;
    }

    public void setSizeGrafica(int sizeGrafica) {
        this.sizeGrafica = sizeGrafica;
        this.correccionCoordenadas = sizeGrafica / 2;
    }

    public void setModelo(ModeloDatos modelo) {
        this.modelo = modelo;
        this.repaint();
    }

    private void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = getSize();
        coordX = size.width / 2;
        coordY = size.height / 2;
        int sumaDatos = getSumaDatos();
        int anchoTotalGrafica = sizeGrafica * modelo.getDatos().size();
        int coordXInicial = (size.width - anchoTotalGrafica) / 2;
        int coordY = size.height - 300;
        altoGrafica = size.height - 130;
        int xStrings = 20;
        int yStrings = 50;        

        for (int i = 0; i < modelo.getDatos().size(); i++) {
            g2d.setColor(colores.get(i));
            int altoBarra = calcularAltoBarra(modelo.getDatos().get(i).getDato(), altoGrafica);
            int coordYBarra = calcularY(altoBarra, altoGrafica);
            g2d.fillRect(coordXInicial, coordYBarra, sizeGrafica, altoBarra);
            coordXInicial += sizeGrafica;
            
            g2d.drawString(modelo.getDatos().get(i).getNombre() + " - " + modelo.getDatos().get(i).getDato(), xStrings, yStrings);
            yStrings += 20;            
        }

    }
    
    private int calcularY(int altoBarra, int altoMaximoGrafica) {
        int coord = altoMaximoGrafica - altoBarra + 50;
        return coord;
    }
    
    private int calcularAltoBarra(int dato, int altoMaximoGrafica) {
        int barraMasAlta = getDatoMayor();
        if(barraMasAlta == 0) {
            barraMasAlta = 1;
        }
        /*
            altoGrafica -> barraMasAlta
            ?           ->  dato
            ? = (dato * altoGrafica) / barraMasAlta
        */
        return (dato * altoMaximoGrafica) / barraMasAlta;
    }
    
    private int getDatoMayor() {
        int mayor = modelo.getDatos().get(0).getDato();
        for(int i = 0; i < modelo.getDatos().size(); i++) {
            if(modelo.getDatos().get(i).getDato() > mayor) {
                mayor = modelo.getDatos().get(i).getDato();
            }
        }
        return mayor;
    }

    private int getSumaDatos() {
        int suma = 0;
        for (int i = 0; i < modelo.getDatos().size(); i++) {
            suma += modelo.getDatos().get(i).getDato();
        }
        return suma;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujar(g);
    }

}
