/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoGrafico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Clase para la estructura de datos de un grafo.
 * @author wildg
 */
class Grafo {
    private int nV;
    private boolean matAdyacencia[][];
    private double[][] matDistacia;
    private int[] padres; 
    private LinkedList<Integer>[] lista;
    private static double INF = Double.MAX_VALUE;
    
    
    /**
     * Constructor para clase Grafo.
     * @param nV: número de vertices que tiene el grafo.
     */
    public Grafo(int nV) {
        this.nV = nV;
        matAdyacencia = new  boolean[nV][nV];
        matDistacia = new double [nV][nV];
        for (int i = 0; i < nV; i++) {
            for (int j = 0; j < nV; j++) {
                if(i==j){
                    matDistacia[i][j]= 0;
                }else {
                    matDistacia[i][j]= INF;
                }
            }
        }
        padres = new int[nV];
        lista = new LinkedList[nV]; 
        for (int i=0; i<nV; ++i) {
            lista[i] = new LinkedList(); 
        }    
    }

    
    /**
     * Metodo para agregar una arista al grafo, no dirigido y ponderado.
     * @param source: primer vertice adyacente a la arista.
     * @param destiny: segundo vertice adyacente a la arista.
     * @param distancia: int distancia entre los dos vertices adyacentes a la 
     * arista. 
     */
    public void addArista(int source, int destiny, double distancia) {
        matAdyacencia[source][destiny]=true;
        matAdyacencia[destiny][source]=true;
        matDistacia[source][destiny] = distancia;
        matDistacia[destiny][source] = distancia;
        lista[source].add(destiny);
        lista[destiny].add(source);
    }
    
    
    /**
     * Metodo que escribe en consola la matriz de adyacencia del grafo.
     */
    public void showM() {
        for (int i = 0; i < matAdyacencia.length; i++) {
            for (int j = 0; j < matAdyacencia.length; j++) {
                if(matAdyacencia[i][j]){
                    System.out.print(matAdyacencia[i][j]+" |");
                }else {
                    System.out.print(matAdyacencia[i][j]+"|");
                }
            }
            System.out.println("");
        }
    }
    
    
    /**
     * Metodo que escribe en consola la lista de adyacencia del grafo.
     */
    public void showL() {
        System.out.println("Lista de Adyacencia");
        int i=0;
        for (LinkedList<Integer> linkedList : lista) {
            System.out.print(i+"|");
            linkedList.forEach((integer) -> {
                System.out.print("->"+integer+"|");
            });
            System.out.println("");
            i++;
        }
    }
    
    
    /**
     * Función para retornar la matriz de distancia del grafo.
     * @return int[][] matriz de distancia
     */
    public double[][] getMatDistancia(){
        return matDistacia;
    }
    
    
    /**
     * Método que escribe en consola la matriz de distancias del grafo, si un
     * vertice no es adyacente a otro se retorna INF y a la distancia de un 
     * vertice a si mismo es INF.
     */
    public void showMa(){
        for (int i = 0; i < nV; i++) {
            for (int j = 0; j < nV; j++) {
                if(matDistacia[i][j]==INF){
                    System.out.print("INF|");
                }else {
                System.out.print(matDistacia[i][j]+"|");
                }
            }
            System.out.println("");
        }
    }
    
    
    //KRUSKAL-------------------------------------------------------------------
    /**
     * Función para encontrar vértice i en el arreglo padres.
     * @param i: indice de un vertice.
     * @return int indice en padres.
     */
    private int encontrar(int i) { 
        while (padres[i] != i) 
            i = padres[i]; 
        return i; 
    } 
    
    
    /**
     * Método que une i y j (indice de vértice del grafo), retorna falso si i y j estan en 
     * el mismo arreglo.
     * @param i: indice vertice 1.
     * @param j: indice vertice 2. 
     */
    private void union(int i, int j) { 
        int a = encontrar(i); 
        int b = encontrar(j); 
        padres[a] = b; 
    }
    
    
    /**
     * Función para hallar el arbol de expansión mínima usando el algorito Kuskal
     * a partir de la matriz de distancias del grafo.
     * @return ArrayList con informacion de las aristas del AEM, cada 
     * int[] contiene el par de vertices adyacentes a la arista.
     */
    public ArrayList<int[]> kruskalMST() { 
        System.out.println("KRUSKAL:");
        double costMin = 0; 
        ArrayList<int[]> aristas= new ArrayList();
        for (int i = 0; i < nV; i++) 
            padres[i] = i; 

        int contArist = 0; 
        while (contArist < nV - 1){ 
            double min = INF;
            int a = -1, b = -1; 
            for (int i = 0; i < nV; i++) { 
                for (int j = 0; j < nV; j++)  { 
                    if (encontrar(i) != encontrar(j) && matDistacia[i][j] != INF && i!=j && matDistacia[i][j] < min){ 
                        min = matDistacia[i][j]; 
                        a = i; 
                        b = j; 
                    } 
                } 
            } 
 
            union(a, b);
            System.out.println("Arista: ("+a+","+b+") costo: "+min);
            contArist++;
            costMin += min;
            int[] ver= {a,b};
            aristas.add(ver);
        }
        System.out.println("Distancia mínima total: "+costMin+" mts");
        return aristas;
    } 
    
    //PRIM----------------------------------------------------------------------
    /**
     * Función para hallar el peso menor entre los vertices no agregados al 
     * arbol de expansión mínima para la función PRIM
     * @param pesos: arreglo con peso de los vertices no agregados 
     * @param verFaltantes: arreglo con vertices faltantes por agregal al AEM.
     * @return int peso menor 
     */
    private int minKey(double[] pesos, Boolean[] verFaltantes) { 
        // Initialize min value 
        double min = INF;
        int min_index = -1; 
  
        for (int v = 0; v < nV; v++) 
            if (verFaltantes[v] == false && pesos[v] < min) { 
                min = pesos[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 


    /**
     * Función para retornar el Arbol de expansión mínima construido a partir 
     * del metodo PRIM.
     * @param parent: arreglo que contiene el arbol de expansión minima
     * @param graph: matriz de distancias del grafo.
     * @return ArrayList de vectores, cada int[] representa una arista y 
     * contiene los indices de los vertices de cada arista.
     */
    private ArrayList<int[]> printMST(int parent[], double graph[][]) { 
        System.out.println("PRIM:");
        double costMin = 0;
        ArrayList<int[]> aristas= new ArrayList();
        System.out.println("Edge \tWeight"); 
        for (int i = 1; i < nV; i++){ 
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
            costMin += graph[i][parent[i]]; 
            int[] ver= {parent[i],i};
            aristas.add(ver);
        }
        System.out.println("Distancia mínima total: "+costMin+" mts");
        return aristas;
    } 
    
    
    /**
     * Función PRIM para hallar el Arbol de expansión mínima del grafo usando su
     * matriz de distancia.
     * @return ArrayList con informacion de las aristas del AEM, cada 
     * int[] contiene el par de vertices adyacentes a la arista. 
     */
    public ArrayList<int[]> primMST(int n) { 
        int[] AEM = new int[nV]; 
  
        double[] pesos = new double[nV]; 
  
        Boolean[] verFaltantes = new Boolean[nV]; 
  
        for (int i = 0; i < nV; i++) { 
            pesos[i] = Integer.MAX_VALUE; 
            verFaltantes[i] = false; 
        } 
  
        pesos[0] = n;
        AEM[0] = -1;
  
        for (int count = 0; count < nV - 1; count++) { 
            int u = minKey(pesos, verFaltantes); 
  
            verFaltantes[u] = true; 
  
            for (int v = 0; v < nV; v++) 
  
                if (matDistacia[u][v] != INF && u!=v && verFaltantes[v] == false && matDistacia[u][v] < pesos[v]) { 
                    AEM[v] = u; 
                    pesos[v] = matDistacia[u][v]; 
                } 
        } 
        
        return printMST(AEM, matDistacia); 
    } 
    
    
    //Conexo--------------------------------------------------------------------
    /**
     * Método para realizar recorrido DFS a partir de la lista de adyacencia del
     * grafo.
     * @param v: número de vertices.
     * @param visitados: arreglo para guardar los vertices que se van visitando.
     */
    private void recorridoDFS(int v,boolean[] visitados) { 
        visitados[v] = true; 
  
        Iterator<Integer> i = lista[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
            if (!visitados[n]) 
                recorridoDFS(n, visitados); 
        } 
    } 
  
    /**
     * Función para determinar si en el grafo se puede llegar de un vértice a 
     * otro siguiendo un camino de aristas usando el recorrido DFS y la lista de
     * adyacencia del grafo.
     * @return true si es conexo, falso si no es conexo.
     */
    public boolean esConexo() { 
        boolean[] visitados = new boolean[nV]; 
        int i; 
        for (i = 0; i < nV; i++) visitados[i] = false; 
        
        i--;
        recorridoDFS(i, visitados); 
  
        for (i = 0; i < nV; i++) 
           if (visitados[i] == false && lista[i].size() > 0) 
                return false; 
  
        return true; 
    }
}
