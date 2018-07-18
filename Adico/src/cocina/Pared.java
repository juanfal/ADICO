/*
 * Pared.java
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

import java.awt.*;
import java.util.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.Box;
import javax.vecmath.*;
import java.io.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Pared implements Serializable {
    //Puntos
    private Point p1, p2;
    //puertas
    private ArrayList<Puerta> listaPuertas = new ArrayList<Puerta>();
    
    /** Creates a new instance of Pared */
    public Pared() {
    }
    
    public Pared(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    //puntos que definen la pared
    public Point getP1() {
        return p1;
    }
    
    public Point getP2() {
        return p2;
    }
    
    public void pintarPlanta(Graphics2D g2) { //pinta la pared
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.BLACK); //DARK_GRAY
        g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }
    
    public void pintarPlanta2(Graphics2D g2) { //repinta la pared y añade las puertas
        
        g2.setStroke(new BasicStroke(8));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }
    
    public void pintarPlanta3(Graphics2D g2) { //repinta la pared y añade las puertas
        //pintar puertas y ventanas de la pared
        for (Puerta pu: listaPuertas) {
            pu.pintarPlanta(g2);
        }
    }
    
    //click cerca de la pared
    public boolean zonaCercana(int x, int y, int cota) {
        int xmin = Math.min((int)p1.getX(),(int)p2.getX());
        int xmax = Math.max((int)p1.getX(),(int)p2.getX());
        int ymin = Math.min((int)p1.getY(),(int)p2.getY());
        int ymax = Math.max((int)p1.getY(),(int)p2.getY());
        
        return ((x>xmin-cota)&&(x<xmax+cota)&&(y>ymin-cota)&&(y<ymax+cota));
    }
    
    public boolean zonaCercana(int x, int y) {
        return zonaCercana(x,y,30);
    }
    
    public boolean ponerPuerta(int x, int y, Puerta puerta) { //intenta añadir una puerta a la pared
        //calculos
        int margen = 10;
        int distancia = puerta.getAnchura()/2;
        int xmin = Math.min((int)p1.getX(),(int)p2.getX());
        int xmax = Math.max((int)p1.getX(),(int)p2.getX());
        int ymin = Math.min((int)p1.getY(),(int)p2.getY());
        int ymax = Math.max((int)p1.getY(),(int)p2.getY());
        
        //alinear y rectificar bordes
        if (ymin==ymax) {
            if (puerta.getAnchura() > xmax-xmin-2*margen) { //comprobacion de paredes pequeñas
                puerta.setPared(null);
                return false; //abortar
            }
            if (x<xmin+distancia+margen) x=xmin+distancia+margen;
            if (x>xmax-distancia-margen) x=xmax-distancia-margen;
            y=ymin;
        }
        if (xmin==xmax) {
            if (puerta.getAnchura() > ymax-ymin-2*margen) { //comprobacion de paredes pequeñas
                puerta.setPared(null);
                return false; //abortar
            }
            if (y<ymin+distancia+margen) y=ymin+distancia+margen;
            if (y>ymax-distancia-margen) y=ymax-distancia-margen;
            x=xmin;
        }
        
        //añadir
        puerta.setPared(this);
        puerta.setX(x);
        puerta.setY(y);
        listaPuertas.add(puerta);
        
        //comprobar colisiones
        for (Puerta p: listaPuertas) {
            if (p!=puerta && p.intersects(puerta)) {
                puerta.setPared(null);
                listaPuertas.remove(puerta);
                return false; //abortar
            }
        }
        
        return true; //correcto
    }
    
    public void devolverPuerta(Puerta puerta) { //vuelve a añadir una puerta que fue quitada temporalmente
        if (!listaPuertas.contains(puerta)) {
            listaPuertas.add(puerta);
        }
    }
    
    public Puerta pincharPuerta(int x, int y) { //devuelve una puerta si esta muy cerca
        for (Puerta p: listaPuertas) {
            if (p.getRectangle().contains(x, y)) {
                return p;
            }
        }
        return null;
    }
    
    public void quitarPuerta(Puerta puerta) { //elimina una puerta de la pared
        listaPuertas.remove(puerta);
    }
    
    public boolean esHorizontal() { //es una pared horizontal
        return (p1.getY()==p2.getY());
    }
    
    public boolean esVertical() { //es una pared vertical
        return (p1.getX()==p2.getX());
    }
    
    public int numPuertas() { //numero de puertas en la pared
        return this.listaPuertas.size();
    }
    
    public ArrayList<Puerta> getListaPuertas() {
        return this.listaPuertas;
    }
    
    //añade la pared al BranchGroup
    public void pintar3D(BranchGroup bg) {
        //añadir pared
        //establecer apariencia de la caja
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(1,1,1));
        mat.setSpecularColor(new Color3f(1,1,1));
        mat.setShininess(5.0f);
        app.setMaterial(mat);

        Box box = new Box(longitud3D()/2f+0.1f, 1.25f, 0.1f, app); //caja con la anchura de la pared

        //ubicar
        Transform3D translate = new Transform3D();
        translate.setTranslation(new Vector3f(x3D(),0f,y3D()));
        Transform3D rotate = new Transform3D();
        rotate.rotY(esVertical()?Math.PI/2:0);
        translate.mul(rotate);

        TransformGroup tg = new TransformGroup(translate); //crear TG
        tg.addChild(box); //añadir la caja al TG
        bg.addChild(tg); //añadirlo al BG
        
        //y pintar las puertas y ventanas
        for (Puerta p: listaPuertas) {
            p.pintar3D(bg);
        }
    }
    
    //funciones auxiliares para el dibujo en 3D
    private float longitud3D() {
        if (esHorizontal()) {
            return Math.abs((float)p1.x-(float)p2.x)/100;
        } else {
            return Math.abs((float)p1.y-(float)p2.y)/100;
        }
    }
    
    private float x3D() {
        return (((float)p1.x+(float)p2.x)/2)/100;
    }
    
    private float y3D() {
        return (((float)p1.y+(float)p2.y)/2)/100;
    }
    
}
