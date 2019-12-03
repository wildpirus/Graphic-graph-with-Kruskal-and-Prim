/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoGrafico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Clase Arista para dibujar con Graphics.
 * @author wildg
 */
public class Arista {
    private Vertice inicio, fin;
    private int x1,y1,x2,y2;
    private double distancia;
    
    /**
     * Constructor clase Arista 
     * @param inicio: Vertice inicial adyacente a la arista.
     * @param fin: Vertice final adyacente a la arista
     * @param distancia int con distancia entre los vertices adyacentes.
     */
    public Arista(Vertice inicio, Vertice fin, double distancia) {
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = distancia;
        inicio.addArista(this);
        fin.addArista(this);
        x1=inicio.getX()+(Vertice.getD()/2);
        y1=inicio.getY()+(Vertice.getD()/2);
        x2=fin.getX()+(Vertice.getD()/2);
        y2=fin.getY()+(Vertice.getD()/2);
    }
    
    
    /**
     * Método para borrar arista de los vértices que conecta. 
     */
    public void borrar(){
        fin.borrarArista(this);
        inicio.borrarArista(this);
    }
    
    
    /**
     * Metodo para borrar arista del vértice adyacente al de la entrada. 
     * @param vertice: vertice inicial o final.
     */
    public void borrar (Vertice vertice){
        if(vertice == inicio){
            fin.borrarArista(this);
        }else {
            inicio.borrarArista(this);
        }
    }
    
    /**
     * Función que retorna la posicion inicial respecto al eje X en el Frame.
     * @return int X1.
     */
    public int getX1() {
        return x1;
    }
    
    
    /**
     * Función que retorna la posicion inicial respecto al eje Y en el Frame.
     * @return int Y1.
     */
    public int getY1() {
        return y1;
    }
    
    
    /**
     * Función que retorna la posicion final respecto al eje X en el Frame.
     * @return int X2.
     */
    public int getX2() {
        return x2;
    }
    
    
    /**
     * Función que retorna la posicion final respecto al eje Y en el Frame..
     * @return int Y2.
     */
    public int getY2() {
        return y2;
    }

    
    /**
     * Función para retornar distancia que representa la arista.
     * @return double distancia.
     */
    public double getDistancia() {
        return distancia;
    }
    
    
    /**
     * Función para saber si un vértice es adyacente a la arista.
     * @param vertice Vertice
     * @return true si es adyacente, false si no es adyacente.
     */
    public boolean conectaCon(Vertice vertice){
        return vertice==inicio || vertice ==fin;
    }
    
    
    /**
     * Función que retorna vértice inicial.
     * @return Vertice inicial.
     */
    public Vertice getInicio() {
        return inicio;
    }

    
    /**
     * Función que retorna vértice final.
     * @return Vertice final.
     */
    public Vertice getFin() {
        return fin;
    }
    
    
    /**
     * Método para pintar una arista.
     * @param g Graphics del Frame donde se dibujará.
     * @param color Color con que se dibujará la arista.
     * @param sw boolean true para dibujar el peso de la arista, si es 
     * false no se dibuja.
     */
    public void pintar(Graphics g, Color color, boolean sw) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
        if(sw){
            String str = Double.toString(distancia);
            str = str.substring(0, str.indexOf(".")+2);
            if (x1 > x2 && y1 > y2) {
                g.drawString(str, x1 - Math.abs(x1 - x2) / 2, y1 - Math.abs(y1 - y2) / 2);
            }
            if (x1 < x2 && y1 < y2) {
                g.drawString(str, x2 - Math.abs(x1 - x2) / 2, y2 - Math.abs(y1 - y2) / 2);
            }
            if (x1 > x2 && y1 < y2) {
                g.drawString(str, x1 - Math.abs(x1 - x2) / 2, y2 - Math.abs(y1 - y2) / 2);
            }
            if (x1 < x2 && y1 > y2) {
                g.drawString(str, x2 - Math.abs(x1 - x2) / 2, y1 - Math.abs(y1 - y2) / 2);
            }
        }
    }
}
