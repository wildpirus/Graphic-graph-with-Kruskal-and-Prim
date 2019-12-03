/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoGrafico;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * Clase Vertice para dibujar con Graphics.
 * @author wildg
 */
public class Vertice {
    private int x,y;
    private String nombre;
    private LinkedList<Arista> aristas;
    private Color color;
    private static int diametro = 20;
    
    
    /**
     * Constructor clase Vertice.
     * @param x: posición en el Frame respecto al eje X.
     * @param y: posición en el Frame respecto al eje Y.
     * @param nombre: nombre que se le dará al vértice.
     */
    public Vertice(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.color = Color.BLACK;
        aristas = new LinkedList();
    }
    
    
     /**
     * Función que retorna la posicion del vertice respecto al eje X en el 
     * Frame. 
     * @return int X
     */
    public int getX() {
        return x;
    }
    
    
    /**
     * Función que retorna la posicion del vertice respecto al eje Y en el 
     * Frame. 
     * @return int Y
     */
    public int getY() {
        return y;
    }
    
    
    /**
     * Fúnción que retorna nombre del vértice.
     * @return String nombre del vértice.
     */
    public String getNombre() {
        return nombre;
    }
    
    
    /**
     * Método para asignar color con que se dibjará el vértice.
     * @param color: Color para el vértice.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    /**
     * Añade arista a este vertice.
     * @param arista Arista
     */
    public void addArista(Arista arista){
        this.aristas.add(arista);
    }
    
    
    /**
     * Borra la arista del vertice si se encuentra.
     * @param arista: Arista a borrar.
     */
    public void borrarArista(Arista arista){
        this.aristas.remove(arista);
    }
    
    
    /**
     * Borra de los vértices a los que este es adyacente la arista que los 
     * conecta.
     */
    public void borrar(){
        for (Arista arista : aristas) {
            arista.borrar(this);
        }
        this.aristas=null;
    }
    
    
    /**
     * Funcion que retorna el Diametro del vértice.
     * @return int diametro
     */
    public static int getD(){
        return diametro;
    }
    
    
    /**
     * Fúnción que retorna color con el que se dibuja el vértice.
     * @return Color
     */
    public Color getColor() {
        return color;
    }
    
    
    /**
     * Función para determinar si el vértice de entrada es adyacente a este.
     * @param vertice Vertice.
     * @return true si hay arista que conecte a ambos o false si no lo son.
     */
    public boolean esAdyacenteA (Vertice vertice){
        for (Arista arista : aristas) {
            if(arista.conectaCon(vertice)) return true;
        }
        return false;
    }
    
    
    /**
     * Función para encontrar arista que conecta al vértice de entrada con este.
     * @param vertice Vertice
     * @return Arista si hay una arista que conecta a los vértice, null si no 
     * hay arista que conecte a los vértices.
     */
    public Arista buscarArista(Vertice vertice){
        for (Arista arista : aristas) {
            if(arista.conectaCon(vertice))return arista;
        }
        return null;
    }
    
    
    /**
     * Función para retornar aristas adyacentes este vértice.
     * @return LinkedList de aristas adyacentes al vertice.
     */
    public LinkedList<Arista> getAristas() {
        return aristas;
    }
    
    
    /**
     * Función que retorna un Rectangle que representa el area que ocupa el vértice en pantalla.
     * @return Rectangle
     */
    public Rectangle getAreaOcupada(){
        return new Rectangle(x, y, diametro, diametro);
    }
    
    /**
     * Método para cambiar el diámetro de los vértices;
     * @param diametro int diametro de los vertices
     */
    public static void setDiametro(int diametro) {
        Vertice.diametro = diametro;
    }
    
    
    /**
     * Método para pintar un vértice.
     * @param g Graphics del Frame donde se dibujará.
     */
    public void pintar(Graphics g){
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(nombre+"")+ 9;
        g.setColor(color);
        g.fillOval(x, y, width, Vertice.getD());
        g.setColor(Color.WHITE);
        g.drawString(nombre, x+ 3, y+(diametro/2)+3);
    }
}
