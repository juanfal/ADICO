/*
 * Forma.java
 *
 * ADICO: Desarrollo de dos versiones de una Aplicación para el DIseño de COcinas,
 * una con interfaz de usuario tipo WIMP y otra con interfaz IGO.
 *
 * Realizado por: Manuel Flores Vivas <mflores at alu.uma.es>
 * Tutor: Antonio Luis Carrillo León <alcarrillo at uma.es>
 *
 * Proyecto Fin de Carrera
 * Ingeniería Técnica en Informática de Sistemas (Universidad de Málaga)
 */

package cocina;

import java.util.*;
import java.awt.*;
import javax.media.j3d.*;
import java.io.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Forma implements Serializable {
    //Paredes
    private java.util.List<Pared> paredes = new ArrayList<Pared>();
    private int x,y;
    private final int margen = 50;
    private final int borde = 10;
    
    /** Creates a new instance of Forma */
    public Forma() {
    }
    
    //metodos para establecer la forma y dimensiones
    private void forma(Point[] vertices) {
        for (int v=0; v<vertices.length; v++) {
            //1-2, 2-3, 3-4, 4-1
            paredes.add(new Pared(vertices[v], vertices[(v+1)%vertices.length]));
        }
    }
    
    public void formaRectangular(int a, int b) { //formas 1 y 2
        Point[] vertices = {new Point(margen, margen),
                            new Point(b+margen+borde, margen),
                            new Point(b+margen+borde,a+margen+borde),
                            new Point(margen,a+margen+borde)};
        forma(vertices);
        x=b;
        y=a;
    }
    
    public void formaL3(int a, int b, int c, int d) { //forma 3
        Point[] vertices = {new Point(margen, margen),
                            new Point(b+margen+borde, margen),
                            new Point(b+margen+borde, a-d+margen+borde),
                            new Point(c+margen+borde, a-d+margen+borde),
                            new Point(c+margen+borde, a+margen+borde),
                            new Point(margen, a+margen+borde)};
        forma(vertices);
        x=b;
        y=a;
    }
    
    //son igual
    public void formaL4(int a, int b, int c, int d) { //forma 4
        Point[] vertices = {new Point(margen, margen),
                            new Point(a-d+margen+borde, margen),
                            new Point(a-d+margen+borde, b-c+margen),
                            new Point(a+margen+borde, b-c+margen),
                            new Point(a+margen+borde, b+margen+borde),
                            new Point(margen, b+margen+borde)};
        forma(vertices);
        x=a;
        y=b;
    }
    
    public void formaL5(int a, int b, int c, int d) { //forma 5
        Point[] vertices = {new Point(margen, margen),
                            new Point(a+margen+borde, margen),
                            new Point(a+margen+borde, b+margen+borde),
                            new Point(d+margen, b+margen+borde),
                            new Point(d+margen, c+margen+borde),
                            new Point(margen, c+margen+borde)};
        forma(vertices);
        x=a;
        y=b;
    }
    
    public void formaL6(int a, int b, int c, int d) { //forma 6
        Point[] vertices = {new Point(b-c+margen, margen),
                            new Point(b+margen+borde, margen),
                            new Point(b+margen+borde, a+margen+borde),
                            new Point(margen, a+margen+borde),
                            new Point(margen, d+margen),
                            new Point(b-c+margen, d+margen)};
        forma(vertices);
        x=b;
        y=a;
    }
    
    public void pintarPlanta(Graphics2D g2) { //metodo para dibujar la planta de la cocina
        
        for (Pared p : paredes) { //primero se pinta la pared
            p.pintarPlanta(g2);
        }
        
        for (Pared p : paredes) { //despues se repinta y se pintan las paredes
            p.pintarPlanta2(g2);
        }
        
        for (Pared p : paredes) { //despues se repinta y se pintan las paredes
            p.pintarPlanta3(g2);
        }
    }
    
    public boolean contains(int x, int y) { //contenido en el poligono con la forma de la cocina
        Polygon poligonoforma = new Polygon();
        for (Pared p: paredes) {
            poligonoforma.addPoint((int)p.getP1().getX(), (int)p.getP1().getY());
        }
        return poligonoforma.contains(x,y);
    }
    
    public boolean contains(Rectangle rect) { //contenido en el poligono con la forma de la cocina
        Polygon poligonoforma = new Polygon();
        for (Pared p: paredes) {
            poligonoforma.addPoint((int)p.getP1().getX(), (int)p.getP1().getY());
        }
        return poligonoforma.contains(rect);
    }
    
    public Pared pincharPared(int x, int y) { //devuelve la pared mas cercana si la hay
        for (Pared p: paredes) {
            if (p.zonaCercana(x, y)) return p;
        }
        
        return null;
    }
    
    public Puerta pincharPuerta(int x, int y) { //devuelve la puerta mas cercana si la hay
        Puerta pue;
        for (Pared p: paredes) {
            pue = p.pincharPuerta(x, y);
            if (pue!=null) {
                return pue;
            }
        }
        
        return null;
    }
    
    public int numPuertas() { //numero de puertas que hay en la cocina
        int suma = 0;
        for (Pared p: paredes) {
            suma += p.numPuertas();
        }
        return suma;
    }
    
    public ArrayList<Puerta> getListaPuertas() {
        ArrayList<Puerta> resultado = new ArrayList<Puerta>();
        for (Pared pa: paredes) {
            resultado.addAll(pa.getListaPuertas());
        }
        
        return resultado;
    }
    
    public void pintar3D(BranchGroup bg) {
        for(Pared pa: paredes) {
            pa.pintar3D(bg); //paredes y puertas
        };
    }
    
    public void quitarPuertas() {
        for (Pared pa: paredes) {
            pa.getListaPuertas().clear();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public java.util.List<Pared> getParedes() {
        return paredes;
    }
    
}
