/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoGrafico;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Clase GrafoG representa vertices y aristas para construir grafo graficamente.
 * @author wildg
 */
public class GrafoG {
    private LinkedList<Vertice> vertices ;
    private LinkedList<Arista> aristas ;
    private final Window window;

    /**
     * Constructor clase GrafoG
     * @param window: JFrame donde se dibujará el grafo.
     */
    public GrafoG(Window window) {
        vertices = new LinkedList();
        aristas = new LinkedList();
        this.window = window;
    }

    /**
     * Función que retorna aristas del grafo.
     * @return LinkedList aristas del grafo.
     */
    public LinkedList<Arista> getAristas() {
        return aristas;
    }
    
    
    /**
     * Función que retorna vértices del grafo.
     * @return LinkedList de vértices del grafo.
     */
    public LinkedList<Vertice> getVertices() {
        return vertices;
    }
    
    
    /**
     * Metodo para agregar un vértice al grafo.
     * @param xc int posición x del clic en pantalla.
     * @param yc int posición y del clic en pantalla.
     * @param nombre String con el nombre del vértice.
     */
    public void addVertice(int xc, int yc, String nombre){
        int x=xc-(Vertice.getD()/2);
        int y=yc-(Vertice.getD()/2);
        Rectangle nuevoRectangulo = new Rectangle(x, y, Vertice.getD(), Vertice.getD());
        int i=0;
        boolean sw=false;
        while(i<vertices.size() && !sw){
            if(vertices.get(i).getAreaOcupada().intersects(nuevoRectangulo)){
                sw=true;
            }
            i++;
        }
        if(!sw){
            this.vertices.add(new Vertice(x,y,nombre));
        } else{
            window.mensaje("Se intersecan");
        } 
        
    }
    
    
    /**
     * Método para agregar arista al grafo.
     * @param v1 vértice inicial.
     * @param v2 vértice final.
     * @param distancia distancia entre los vértices.
     */
    public void addArista(Vertice v1, Vertice v2, double distancia){
        if(!v1.esAdyacenteA(v2)){
            this.aristas.add(new Arista(v1,v2, distancia));
        }else {
            this.window.mensaje(v1.getNombre()+" ya es adyacente a "+v2.getNombre());
        }
    }
    
    
    /**
     * Método para borrar vértice seleccionado.
     * @param vertice vertice seleccionado.
     */
    public void borrarVertice(Vertice vertice){
        window.mensaje("se eliminó el vertice "+vertice.getNombre());
        vertice.getAristas().forEach((arista) -> {
            aristas.remove(arista);
        });
        vertice.borrar();
        vertices.remove(vertice);
    }
    
    
    /**
     * Método para borrar arista entre los vértices seleccionados.
     * @param v1 vertice inicial.
     * @param v2 vertice final.
     */
    public void borrarArrista(Vertice v1, Vertice v2){
        if(v1.esAdyacenteA(v2)){
            window.mensaje("se eliminara arista de "+v1.getNombre()+" a "+v2.getNombre());
            Arista arista = v1.buscarArista(v2);
            arista.borrar();
            aristas.remove(arista);
        }else {
            window.mensaje(v1.getNombre()+" no es adyacente a "+v2.getNombre());
        }
    }
    
    
    /**
     * Función para crear un objeto con la estructura de datos de un gráfo a 
     * partir de las aristas de GrafoG.
     * @return Grafo 
     */
    private Grafo crearGrafo(){
        Grafo grafo = new Grafo(vertices.size());
        for (Arista arista : aristas) {
            grafo.addArista(vertices.indexOf(arista.getInicio()), vertices.indexOf(arista.getFin()), arista.getDistancia());
            System.out.println("Arista: inicio: "+arista.getInicio().getNombre()+", i: " +vertices.indexOf(arista.getInicio())+" -> fin: "+arista.getFin().getNombre()+", i: "+vertices.indexOf(arista.getFin()));
        }
        grafo.showL();
        return grafo;
    }
    
    
    /**
     * Función para hallar el arbol de expansión minima a partir del grafo 
     * usando el algoritmo Kruskal.
     * @return ArrayList de aristas que forman el AEM.
     */
    public ArrayList<Arista> kruskal(){
        ArrayList<Arista> AEM= new ArrayList();
        if(!aristas.isEmpty()){
            Grafo grafo = crearGrafo();
            if(grafo.esConexo()) {
                grafo.kruskalMST().forEach((edges) -> {
                    AEM.add(vertices.get(edges[0]).buscarArista(vertices.get(edges[1])));
                });
            }else {
                window.mensaje("El grafo no es conexo");
            }
        }else {
            window.mensaje("No hay aristas");
        }
        return AEM;
    }
    
    
    /**
     * Función para hallar el arbol de expansión minima a partir del grafo 
     * usando el algoritmo PRIM. 
     * @param origen
     * @return ArrayList de aristas que forman el AEM.
     */
    public ArrayList<Arista> PRIM(Vertice origen){
        ArrayList<Arista> AEM= new ArrayList();
        if(!aristas.isEmpty()){
            Grafo grafo = crearGrafo();
            if(grafo.esConexo()) {
                grafo.primMST(vertices.indexOf(origen)).forEach((edges) -> {
                    AEM.add(vertices.get(edges[0]).buscarArista(vertices.get(edges[1])));
                });  
            }else {
                window.mensaje("El grafo no es conexo");
            }
        }else {
            window.mensaje("no hay aristas");
        }
        return AEM;
    }
    
    
    /**
     * Método para guardar la informacion de vertices y aristas del grafo 
     * gráfico en un archivo txt.
     * @param path String con el nombre del archivo en el que se guardará.  
     */
    public void guardarGrafo(String path){
        try {
            String ruta =("src/Files/"+path+".txt");
            File file = new File(ruta);
            file.createNewFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            try( BufferedWriter bww = new BufferedWriter(new FileWriter(file))){
                    int i=0;
                    for (Vertice vertice : vertices) {
                        bww.write(i+","+(vertice.getX()+(Vertice.getD()/2))+","+(vertice.getY()+(Vertice.getD()/2))+","+vertice.getNombre());
                        bww.newLine();
                        i++;
                    }
                    for (Arista arista : aristas) {
                        bww.write(vertices.indexOf(arista.getInicio())+","+vertices.indexOf(arista.getFin())+","+arista.getDistancia());
                        bww.newLine();
                    }
                    bww.close();
                }catch(Exception e){
                    
                }
            }catch(IOException e){
                
            }
    }
    
    
    /**
     * Método para cargar la informacion de vertices y aristas del grafo 
     * gráfico en un archivo txt.
     * @param path String con el nombre del archivo en el que se cargará.
     */
    public void cargarGrafo(String path){
        vertices = new LinkedList();
        aristas = new LinkedList();
        File file = new File("src/Files/"+path+".txt");
        try(Scanner lector = new Scanner(file)){
            while(lector.hasNextLine()){

                String linea = lector.nextLine();
                String[] datos = linea.split(",");
                
                int a = Integer.parseInt(datos[0]);
                int b = Integer.parseInt(datos[1]);
                if(datos.length==4){
                    int c = Integer.parseInt(datos[2]);
                    String d = datos[3];
                    this.addVertice(b, c, d);
                }else {
                    double c = Double.parseDouble(datos[2]);
                    this.addArista(vertices.get(a), vertices.get(b), c);
                }
            }
        }catch (Exception e){

        }
        window.dibujar();
    }
    
    /**
     * Metodo para dibujar los vértices y aristas del grafo.
     * @param g Graphics del panel donde se dibujará el grafo.
     * @param color Color con que se dibujaran las aristas.
     * @param sw boolean true para escribir distancia de la arista o false para
     * no escribirla.
     */
    public void dibujarGrafo(Graphics g, Color color,  boolean sw){
        aristas.forEach((arista) -> {
            arista.pintar(g,color,sw);
        });
        vertices.forEach((vertice) -> {
            vertice.pintar(g);
        });
    }
    
    /**
     * Función para saber si el grafo tiene al menos un vertice.
     * @return boolean true si tiene vertices, false si no.
     */
    public boolean hasVertices(){
        return !vertices.isEmpty();
    }
    
    /**
     * Función para saber si el grafo tiene al menos una arista.
     * @return boolean true si tiene aristas, false si no.
     */
    public boolean hasEdges(){
        return !aristas.isEmpty();
    }
    
    /**
     * Función para saber si el grafo tiene al menos dos vertices.
     * @return boolean true si al menos tiene dos vertices, false si no.
     */
    public boolean canAddEdge(){
        return vertices.size()>=2;
    }
    
    /**
     * Método para obtener vértice ubicado en la posición del clic.
     * @param point punto donde se dió clic.
     * @return Vertice ubicado en la posición del clic.
     */
    public Vertice getVertice(Point point){
        int i = 0;
        Vertice vertice = null;
        boolean sw=false;
        while(i<vertices.size()&& !sw){
            Vertice temp = vertices.get(i);
            if(temp.getAreaOcupada().contains(point)){
                sw = true;
                vertice = temp;
            }else {
                i++;
            }
        }
        return vertice;
    }
    
    /**
     *Método para colocar los vértices de color negro.
     */
    public void setVerticesBlack(){
        vertices.forEach((vertice) -> { vertice.setColor(Color.BLACK); });
    }
}
