/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficaPastel;

import Modelo.ModeloDatos;
import Mvc.Observer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Equipo1
 */
public class GraficaPastel extends JPanel {
    private ArrayList<Color> colores = new ArrayList<Color>();
    private ModeloDatos modelo;
    private int sizeGrafica;
    private int correccionCoordenadas;
    private int coordX, coordY;
    
    public GraficaPastel(ModeloDatos modelo) {
        this.modelo = modelo;
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
        coordX = size.width / 2 - correccionCoordenadas;
        coordY = size.height / 2 - correccionCoordenadas;   
        int sumaDatos = getSumaDatos();
        int anguloAnterior = 0;
        int xStrings = 20;
        int yStrings = 50;
        
        for(int i = 0; i < modelo.getDatos().size(); i++) {
            g2d.setColor(colores.get(i));            
            if(sumaDatos > 0) {
                int anguloActual = calcularAngulo(sumaDatos, modelo.getDatos().get(i).getDato());
                anguloActual = fixGrafica(i, anguloAnterior, anguloActual);
                g2d.fillArc(coordX, coordY, sizeGrafica, sizeGrafica, anguloAnterior, anguloActual);  
                anguloAnterior = anguloActual + anguloAnterior;                
            }
            
            g2d.drawString(modelo.getDatos().get(i).getNombre() + " - " + modelo.getDatos().get(i).getDato(), xStrings, yStrings);
            yStrings += 20;
        }
           
    }
    
    private int fixGrafica(int posicion, int anguloAnterior, int anguloActual) {
        int fix = anguloActual;
        if(posicion == modelo.getDatos().size() - 1) {
            if((anguloAnterior + anguloActual) < 360) {
                int faltante = 360 - (anguloAnterior + anguloActual);
                fix += faltante;
            }
        }
        return fix;
    }
    
    private int getSumaDatos() {
        int suma = 0;
        for(int i = 0; i < modelo.getDatos().size(); i++) {
            suma += modelo.getDatos().get(i).getDato();
        }
        return suma;
    }
    
    private int calcularAngulo(int sumaTotal, int datoIndividual) {
        if(sumaTotal == 0) {
            return 0;
        }
        int anguloTotal = 360;
        double porcentaje = (datoIndividual * 100) / sumaTotal;
        //System.out.println("%: " + porcentaje);
        double anguloIndividual = (anguloTotal * porcentaje) / 100;
        //System.out.println(anguloIndividual);
        return (int) anguloIndividual;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujar(g);
    }  
    
}
