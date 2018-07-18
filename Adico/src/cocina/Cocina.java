/*
 * Cocina.java
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
import com.sun.j3d.utils.geometry.Box;
import javax.vecmath.*;
import controlador.*;
import java.io.*;
import java.awt.image.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Cocina implements Serializable {
    //forma de la cocina
    private Forma forma = new Forma();

    //Elementos unicos
    private Mostrable encimera;
    private Mostrable maderas;
    private Mostrable tiradores;
    
    //muebles
    private ArrayList<Mueble> listaMuebles = new ArrayList<Mueble>();
    
    private boolean simularBorradoMuebles = false;
    
    //Controlador
    transient private InterfazControlador controlador;
        
    /** Creates a new instance of Cocina */
    public Cocina() {
        
    }

    public void pintarPlanta(Graphics2D g2, ImageObserver obs){
        //Pintar muebles y electrodomesticos
        if (!simularBorradoMuebles) {
            for (Mueble mu: listaMuebles) {
                mu.pintarPlanta(g2, obs);
            }
        }
        
        //Pintar forma
        forma.pintarPlanta(g2);
        
        //Pintar precio
        float precio = 0;
        for (Mueble m: listaMuebles) { //muebles
            precio += m.getPrecio();
        }
        
        if (encimera != null) precio+=encimera.getPrecio()*metrosCuadradosDeEncimera(); //materiales
        if (maderas != null) precio+=maderas.getPrecio()*numPuertasMuebles();
        if (tiradores != null) precio+=tiradores.getPrecio()*numTiradoresMuebles();
        
        if (precio > 0) {
            String precio2 = "Precio = ";
            precio2 = precio2.concat(Float.toString(precio));
            precio2 = precio2.concat(" €");
            g2.setColor(Color.BLACK);
            g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,14)); //imprimir a la derecha del contenedor
            if (controlador != null) {
                g2.drawString(precio2, controlador.getContCocinaPlanta().getSize().width - 164, 42);
            }
        }
    }
    
    public Forma getForma() {
        return forma;
    }
    
    public boolean contains(int x, int y) {
        return forma.contains(x, y);
    }
    
    public boolean contains(Rectangle rect) {
        return forma.contains(rect);
    }
    
    public Pared pincharPared(int x, int y) {
        return forma.pincharPared(x, y);
    }
    
    public Puerta pincharPuerta(int x, int y) {
        return forma.pincharPuerta(x, y);
    }
    
    public Mueble pincharMueble(int x, int y) {
        int pos = listaMuebles.size();
        while (pos>0) {
            pos--;
            if (listaMuebles.get(pos).contains(x, y)) {
                return listaMuebles.get(pos);
                
            }
        }
        
        return null;
    }
        
    public int numPuertas() {
        return forma.numPuertas();
    }
    
    public int numMuebles() {
        return listaMuebles.size();
    }
    
    public boolean ponerMueble(int x, int y, Mueble mueble) { //intenta añadir un mueble a la pared
        //calculos
        int xmin;
        int ymin;
        int anguloAnt;
        int xAnt;
        int yAnt;
        
        //alinear y rectificar bordes
        
        //posicionar
        mueble.setX(x);
        mueble.setY(y);
        
        //comprobar que el cursor esta dentro de la cocina
        if (!contains(x,y)) {
            listaMuebles.remove(mueble);
            return false;
        } else {
            
            iman_simple(mueble);

            anguloAnt = mueble.getAngulo();
            xAnt=mueble.getX();
            yAnt=mueble.getY();
            
            if (mueble.esGirable()) {
                for (Pared p: forma.getParedes()) { //auto-giro
                    //calculos
                    xmin = Math.min((int)p.getP1().getX(),(int)p.getP2().getX());
                    ymin = Math.min((int)p.getP1().getY(),(int)p.getP2().getY());

                    if (p.zonaCercana(x, y, 44)) {
                        if (p.esHorizontal()) {
                            if (contains(xmin,ymin+1)) { //es una pared superior
                                mueble.setAngulo(0);
                            } else {
                                mueble.setAngulo(180);
                            }
                        } else {
                            if (contains(xmin+1,ymin)) { //es una pared izquierda
                                mueble.setAngulo(270);
                            } else {
                                mueble.setAngulo(90);
                            }
                        }
                    }
                }

                //arreglar comportamiento de giro en las paredes
                /*if (anguloAnt!=mueble.getAngulo()) { //si se ha girado

                    if (colisionaConMuebles(mueble)) { //y colisiona
                        mueble.setX(xAnt); //dejar donde estaba
                        mueble.setY(yAnt);
                        mueble.setAngulo(anguloAnt); //y deshacer el giro
                    }
                }*/
            }

            imanConParedes(x,y,mueble);
            
            //iman a diferente altura
            for(Mueble mu: listaMuebles) {
                if (mu!=mueble && mueble.getRectangle(0).intersects(mu.getRectangle(0))) {
                    if (Math.abs(mueble.getX()-mueble.getAnchura()/2-mu.getX()+mu.getAnchura()/2)<10) {
                        mueble.setX(mu.getX()-mu.getAnchura()/2+mueble.getAnchura()/2);
                    }
                    
                    if (Math.abs(mueble.getX()+mueble.getAnchura()/2-mu.getX()-mu.getAnchura()/2)<10) {
                        mueble.setX(mu.getX()+mu.getAnchura()/2-mueble.getAnchura()/2);
                    }
                    
                    if (Math.abs(mueble.getY()-mueble.getProfundidad()/2-mu.getY()+mu.getProfundidad()/2)<10) {
                        mueble.setY(mu.getY()-mu.getProfundidad()/2+mueble.getProfundidad()/2);
                    }
                    
                    if (Math.abs(mueble.getY()+mueble.getProfundidad()/2-mu.getY()-mu.getProfundidad()/2)<10) {
                        mueble.setY(mu.getY()+mu.getProfundidad()/2-mueble.getProfundidad()/2);
                    }
                    
                }
            }

            if (!contains(mueble.getRectangle(-3))) { //controlar que quede algun trozo fuera (pasillos pequeños)
                listaMuebles.remove(mueble);
                return false;
            }
            
            //comprobar colisiones
            if (colisionaConMuebles(mueble)) {
                listaMuebles.remove(mueble);
                return false;
            }
            
            //añadir
            listaMuebles.remove(mueble);
            sortedAdd_listaMuebles(mueble);

            return true; //correcto
        }
    }

    private void iman_simple(Mueble mueble) {
        //iman simple
        for (Mueble mu : listaMuebles) { //para cada mueble
            if (mueble != mu) { //si no es el mismo

                int xant=mueble.getX(); //recordar posicion
                int yant=mueble.getY();
                if (mu.iman(mueble)) { //probar iman
                    if (colisionaConMuebles(mueble)) { //si en el nuevo sitio colisiona
                        mueble.setX(xant); //lo dejamos donde esta
                        mueble.setY(yant);
                    }
                }
            }
        }
    }
    
    private void imanConParedes(int x, int y, Mueble mueble) {
        if (mueble.esRinconera() && mueble.esGirable()) {
            imanConParedesParaRinconeras(x,y,mueble,60);
        } else {
            imanConParedes(x, y, mueble, 30);
        }
    }
    
    private void imanConParedes(int x, int y, Mueble mueble, int cota) {
        int xmin;
        int xmax;
        int ymin;
        int ymax;

        for (Pared p: forma.getParedes()) { //intentar iman con cada pared
            //calculos
            xmin = Math.min((int)p.getP1().getX(),(int)p.getP2().getX());
            xmax = Math.max((int)p.getP1().getX(),(int)p.getP2().getX());
            ymin = Math.min((int)p.getP1().getY(),(int)p.getP2().getY());
            ymax = Math.max((int)p.getP1().getY(),(int)p.getP2().getY());

            //si es horizontal y el cursor esta alineado
            if (p.esHorizontal() && (xmin - 6 < x+mueble.getAnchura()/2) && (xmax + 6 > x-mueble.getAnchura()/2)) {

                if (contains(xmin,ymin+1)) { //es una pared superior
                    if ( y>=ymin && y-mueble.getProfundidad()/2-cota<ymin ) { //esta cerca                            
                        mueble.setY(ymin+mueble.getProfundidad()/2 + 6); //pegar
                    }
                } else { //es una pared inferior
                    if ( y<ymax && y+mueble.getProfundidad()/2+cota>ymax ) { //esta cerca
                        mueble.setY(ymin - mueble.getProfundidad()/2 - 6); //pegar
                    }
                }

            //si es vertical y el cursor esta alineado
            } else if ( (ymin - 6 < y+mueble.getProfundidad()/2) && (ymax + 6 > y-mueble.getProfundidad()/2)) {

                if (contains(xmin+1,ymin)) { //es una pared izquierda
                    if ( x>=xmin && x-mueble.getAnchura()/2-cota<xmin ) { //esta cerca
                        mueble.setX(xmin+mueble.getAnchura()/2 + 6); //pegar
                    }
                } else { //es una pared derecha
                    if ( x<xmax && x+mueble.getAnchura()/2+cota>xmax ) { //esta cerca
                        mueble.setX(xmin - mueble.getAnchura()/2 - 6); //pegar
                    }
                }   
            }                
        }
    }
    
    private void imanConParedesParaRinconeras(int x, int y, Mueble mueble, int cota) {
        int xmin;
        int xmax;
        int ymin;
        int ymax;
        boolean sup=false;
        boolean inf=false;
        boolean izq=false;
        boolean der=false;
        int paredSup = 0;
        int paredInf = 0;
        int paredIzq = 0;
        int paredDer = 0;
    
        for (Pared p: forma.getParedes()) { //intentar iman con cada pared
            //calculos
            xmin = Math.min((int)p.getP1().getX(),(int)p.getP2().getX());
            xmax = Math.max((int)p.getP1().getX(),(int)p.getP2().getX());
            ymin = Math.min((int)p.getP1().getY(),(int)p.getP2().getY());
            ymax = Math.max((int)p.getP1().getY(),(int)p.getP2().getY());

            //si es horizontal y el cursor esta alineado
            if (p.esHorizontal() && (xmin - 6 < x+mueble.getAnchura()/2) && (xmax + 6 > x-mueble.getAnchura()/2)) {

                if (contains(xmin,ymin+1)) { //es una pared superior
                    if ( y>=ymin && y-mueble.getProfundidad()/2-cota<ymin ) { //esta cerca                            
                        sup=true;
                        paredSup=ymin;
                        mueble.setY(ymin+mueble.getProfundidad()/2 + 6); //pegar
                    }
                } else { //es una pared inferior
                    if ( y<ymax && y+mueble.getProfundidad()/2+cota>ymax ) { //esta cerca
                        inf=true;
                        paredInf=ymin;
                        mueble.setY(ymin - mueble.getProfundidad()/2 - 6); //pegar
                    }
                }

            //si es vertical y el cursor esta alineado
            } else if ( (ymin - 6 < y+mueble.getProfundidad()/2) && (ymax + 6 > y-mueble.getProfundidad()/2)) {

                if (contains(xmin+1,ymin)) { //es una pared izquierda
                    if ( x>=xmin && x-mueble.getAnchura()/2-cota<xmin ) { //esta cerca
                        izq=true;
                        paredIzq=xmin;
                        mueble.setX(xmin+mueble.getAnchura()/2 + 6); //pegar
                    }
                } else { //es una pared derecha
                    if ( x<xmax && x+mueble.getAnchura()/2+cota>xmax ) { //esta cerca
                        der=true;
                        paredDer=xmin;
                        mueble.setX(xmin - mueble.getAnchura()/2 - 6); //pegar
                    }
                }   
            }                
        }
        
        if (izq && sup) {
            mueble.setAngulo(0);
            mueble.setY(paredSup+mueble.getProfundidad()/2 + 6); //pegar
            mueble.setX(paredIzq+mueble.getAnchura()/2 + 6); //pegar
        } else if (sup && der) {
            mueble.setAngulo(90);
            mueble.setY(paredSup+mueble.getProfundidad()/2 + 6); //pegar
            mueble.setX(paredDer-mueble.getAnchura()/2 - 6); //pegar
        } else if (der && inf) {
            mueble.setAngulo(180);
            mueble.setY(paredInf-mueble.getProfundidad()/2 - 6); //pegar
            mueble.setX(paredDer-mueble.getAnchura()/2 - 6); //pegar
        } else if (inf && izq) {
            mueble.setAngulo(270);
            mueble.setY(paredInf-mueble.getProfundidad()/2 - 6); //pegar
            mueble.setX(paredIzq+mueble.getAnchura()/2 + 6); //pegar
        }

    }

    public boolean fijarMueble(Mueble mueble) {
        if (!contains(mueble.getRectangle(-3))) { //controlar que quede algun trozo fuera (pasillos pequeños)
            listaMuebles.remove(mueble);
            return false;
        }

        //comprobar colisiones
        if (colisionaConMuebles(mueble)) {
            listaMuebles.remove(mueble);
                return false;
        }

        if (intersectaConPuertas(mueble)) { //hay que mirar que no quede en la zona de una puerta/ventana
            listaMuebles.remove(mueble);
            return false;
        }
        //añadir
        listaMuebles.remove(mueble);
        sortedAdd_listaMuebles(mueble);

        return true; //correcto
    }

    public boolean colisionaConMuebles(Mueble mueble) {
        //comprobar colisiones
        for (Mueble mu : listaMuebles) {
            if (mueble != mu && mueble.intersects(mu)) {
                return true;
            }
        }
        return false;
    }

    public boolean colisionaConMuebles(Puerta puerta) {
        //comprobar colisiones
        for (Mueble mu : listaMuebles) {
            if (mu.intersects(puerta)) {
                return true;
            }
        }
        return false;
    }

    public boolean intersectaConPuertas(Mueble mueble) {
        for (Puerta pue: forma.getListaPuertas()) {
            if (mueble.intersects(pue)) {
                    return true;
            }
        }
        return false;
    }
    
    public void simularBorrado(boolean estado) {
        simularBorradoMuebles = estado;
    }
    
    public void borrarMuebles() {
        listaMuebles.clear();
    }

    public void quitarMueble(Mueble mueble) {
        listaMuebles.remove(mueble);
    }
    
    public void devolverMueble(Mueble mueble) {
        sortedAdd_listaMuebles(mueble);
    }
    
    public ArrayList<Mueble> getListaMuebles() {
        return listaMuebles;
    }
    
    //metodos get y set
    public void setEncimera(Mostrable encimera) {
        this.encimera=encimera;
    }
    
    public Mostrable getEncimera() {
        return encimera;
    }
    
    public void setMaderas(Mostrable maderas) {
        this.maderas=maderas;
    }
    
    public Mostrable getMaderas() {
        return maderas;
    }
    
    public void setTiradores(Mostrable tiradores) {
        this.tiradores=tiradores;
    }
    
    public Mostrable getTiradores() {
        return tiradores;
    }
    
    public int metrosCuadradosDeEncimera() {
        int suma = 0;
        for (Mueble m: listaMuebles) {
            suma+=m.centimetrosCuadradosDeEncimera();
        }
        
        return (int)Math.ceil((double)suma/10000); //conventir de cm^2 a m^2 y redondear
    }
    
    public int numTiradoresMuebles() {
        int suma = 0;
        for (Mueble m: listaMuebles) {
            suma+=m.numTiradores();
        }
        return suma;
    }
    
    public int numPuertasMuebles() {
        int suma = 0;
        for (Mueble m: listaMuebles) {
            suma+=m.numPuertas();
        }
        return suma;
    }
    
    private void sortedAdd_listaMuebles(Mueble m) { //se insertan ordenados para que al pintar desde arriba se superpongan bien
        int altura = m.getAltura()+m.getDistanciaSuelo();
        int pos = 0;
        if (!listaMuebles.isEmpty()) {
            Mueble mueblei = listaMuebles.get(pos);
            int alturai=mueblei.getAltura()+mueblei.getDistanciaSuelo();
            while (pos < listaMuebles.size() && alturai<altura) {
                mueblei = listaMuebles.get(pos);
                alturai = mueblei.getAltura()+mueblei.getDistanciaSuelo();
                pos++;
            }
        }
        listaMuebles.add(pos,m); 
    }
    
    public void pintar3D(BranchGroup bg) {
        //poner un suelo
        //establecer apariencia de la caja

        Appearance app = new Appearance();
        Material mat = new Material();

        mat.setDiffuseColor(new Color3f(.6f,.6f,.1f));
        mat.setSpecularColor(new Color3f(.6f,.6f,.1f));

        mat.setShininess(5.0f);
        app.setMaterial(mat);

        Box box = new Box(50f, 0.12f, 50f, app); //caja con la anchura de la pared

        //ubicar
        Transform3D translate = new Transform3D();
        translate.setTranslation(new Vector3f(0,-1.1f-.12f,0));

        TransformGroup tg = new TransformGroup(translate); //crear TG
        tg.addChild(box); //añadir la caja al TG
        bg.addChild(tg); //añadirlo al BG
            
        
        forma.pintar3D(bg); //paredes y ventanas
        
        for (Mueble m: listaMuebles) {
            m.pintar3D(bg); //y muebles
        }
    }
    
    public void setControlador(InterfazControlador controlador) {
        this.controlador = controlador;
    }
    
}
