/*
 * ContenedorCocina3D.java
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

package vista;

import cocina.*;
import java.awt.*;
import javax.swing.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.*;
import javax.media.j3d.*;

/**
 *
 * @author  Manuel Flores Vivas
 */

public class ContenedorCocina3D extends JPanel {
    private Cocina cocina;
    private SimpleUniverse simpleU; //universo simple
    private BranchGroup rootBranchGroup; //BranchGroup raiz
    private double angulo = -Math.PI/32; //angulo de giro en cada paso

    public ContenedorCocina3D(Cocina cocina) {
        this.cocina=cocina;

        initial_setup(); //configurar el canvas y el universo
        
        cocina.pintar3D(rootBranchGroup);

        BoundingSphere bounds = new BoundingSphere(); //crear una esfera para la luz
        bounds.setRadius(100d);
        float x=0.5f+(float)cocina.getForma().getX()/200f; //centro de la cocina
        float z=0.5f+(float)cocina.getForma().getY()/200f;
        PointLight lightD = new PointLight(new Color3f(1f, 1f, 1f), new Point3f(x, 2f, z), new Point3f(0.5f, 0f, 0f)); //crear la luz direccional
        lightD.setInfluencingBounds(bounds);
        rootBranchGroup.addChild(lightD); //y añadirla al BranchGroup raiz
        
        finalise(); //añade el BG al universo y establece la camara

        girarIzq();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void initial_setup() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration(); //obtenemos la configuracion
        Canvas3D canvas3D = new Canvas3D(config); //creamos el canvas con dicha configuracion

        setLayout(new BorderLayout()); //establecemos el layout del JPanel
        add("Center", canvas3D); //y añadimos el canvas
        
        simpleU = new SimpleUniverse(canvas3D); //creamos el universo asociado al canvas
        rootBranchGroup = new BranchGroup(); //primero creamos el BranchGroup
    }

    private void finalise() {
        simpleU.addBranchGraph(rootBranchGroup); //ahora añadimos el BranchGroup al universo
        simpleU.getViewingPlatform().setNominalViewingTransform(); //y establecemos la posicion de la camara
    }

    public void girarDer() {
        giro(-Math.PI/32);
    }

    public void girarIzq() {
        giro(Math.PI/32);
    }
    
    private void giro(double desplazamiento) {
        angulo+=desplazamiento;

        TransformGroup vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();

        Transform3D desplaza1 = new Transform3D();
        Transform3D giro1 = new Transform3D();
        Transform3D giro2 = new Transform3D();
        Transform3D desplaza2 = new Transform3D();

        desplaza1.setTranslation(new Vector3f(0,0,3.8f)); //retroceder
        giro1.rotY(angulo); //girar angulo
        giro2.rotX(-Math.PI/7); //girar hacia abajo
        float x=0.5f+(float)cocina.getForma().getX()/200f; //centro de la cocina
        float z=0.5f+(float)cocina.getForma().getY()/200f;
        desplaza2.set(new Vector3f(x,1.2f,z));  //desplaza al centro

        desplaza2.mul(giro1);
        desplaza2.mul(giro2);
        desplaza2.mul(desplaza1);

        vpTrans.setTransform(desplaza2);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}