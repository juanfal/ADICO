/*
 * ContenedorCocinaPlanta.java
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

import java.awt.*;
import cocina.*;
import controlador.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorCocinaPlanta extends javax.swing.JPanel implements InterfazBarraEstado {
    private Cocina cocina;
    //puertas y ventanas
    private boolean modoPonerPuerta = false;
    private boolean modoSeleccionarPuerta = false;
    private Puerta mipuerta, puertatemporal;
    
    //muebles y electrodomesticos
    private boolean modoPonerMueble = false;
    private boolean modoSeleccionarMueble = false;
    private Mueble mimueble, muebletemporal;
    
    //parte wimp
    private boolean modoWimp=false;
    private Mueble muebleWimpSeleccionado, copiaMueble;
    private Puerta puertaWimpSeleccionada, copiaPuerta;
    private int botonpulsado;
    
    //Controlador
    private InterfazControlador controlador;
    private Timer t;
    
    /** Creates new form ContenedorCocinaPlanta */
    public ContenedorCocinaPlanta(InterfazControlador controlador) {
        this.controlador = controlador;
        initComponents();
        jLabelEstado.setVisible(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelEstado = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jLabelEstado.setBackground(new java.awt.Color(255, 99, 93));
        jLabelEstado.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelEstado.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(136, 6, 6), 2));
        jLabelEstado.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (modoPonerPuerta) {
            if (mipuerta.puesta()) {
                if (cocina.colisionaConMuebles(mipuerta)) {
                    controlador.cambiarEstado("No se puede poner cerca de un mueble");
                } else {
                    controlador.paredClick();
                    mipuerta.setCotas(false);
                    this.repaint();
                }
            }
        }
        
        if (modoSeleccionarPuerta) {
            Puerta p = cocina.pincharPuerta(evt.getX(), evt.getY());
            if (p!=null) {
                puertatemporal=null;
                p.setBorde(true);
                this.repaint();
                controlador.puertaClick(p);
            }
        }
        
        if (modoPonerMueble) {
            if (cocina.ponerMueble(evt.getX(), evt.getY(), mimueble)) {
                if (cocina.intersectaConPuertas(mimueble)) { //la comprobacion se hace aqui para evitar parpadeos
                    cocina.quitarMueble(mimueble);
                    controlador.cambiarEstado("No se puede poner cerca de una puerta o ventana");
                } else {
                    this.repaint();    
                    controlador.muebleClick();
                }
            }
        }
        
        if (modoSeleccionarMueble) {
            Mueble m = cocina.pincharMueble(evt.getX(), evt.getY());
            if (m!=null) {
                muebletemporal=null;
                m.setBorde(true);
                this.repaint();
                controlador.muebleClick(m);
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

        if (modoPonerPuerta) {
            mipuerta.setCotas(true);
            Pared pa = cocina.pincharPared(evt.getX(), evt.getY());
            
            if ( pa != null) {
                if (mipuerta.getPared()!=null) {
                    mipuerta.getPared().quitarPuerta(mipuerta);
                }
                
                pa.ponerPuerta(evt.getX(), evt.getY(), mipuerta);
                
            } else if (mipuerta.getPared()!=null) {            
                mipuerta.getPared().quitarPuerta(mipuerta); 
                mipuerta.setPared(null);
            }

            if (mipuerta.getPared()!=null && cocina.colisionaConMuebles(mipuerta)) { //comprobacion para poner texto
                controlador.cambiarEstado("No se puede poner cerca de un mueble");
            } else {
                controlador.cambiarEstado("Listo");
            }

            this.repaint();
        }
        
        if (modoSeleccionarPuerta) { //efecto de resaltado opcional
            if (puertatemporal!=null) {
                puertatemporal.setBorde(false);
                puertatemporal=null;
                this.repaint();
            }
            puertatemporal = cocina.pincharPuerta(evt.getX(), evt.getY());
            if (puertatemporal!=null) {
                puertatemporal.setBorde(true);
                this.repaint();
            }
        }
        
        if (modoPonerMueble) {
            controlador.setMueblePuesto(false); //ante un movimiento: ya no se puede poner automaticamente
            
            cocina.ponerMueble(evt.getX(), evt.getY(), mimueble);
            if (cocina.intersectaConPuertas(mimueble)) { //comprobacion para poner texto
                controlador.cambiarEstado("No se puede poner cerca de una puerta o ventana");
            } else {
                controlador.cambiarEstado("Listo");
            }
            this.repaint();    
        }
        
        if (modoSeleccionarMueble) {
            if (muebletemporal!=null) {
                muebletemporal.setBorde(false);
                muebletemporal=null;
                this.repaint();
            }
            muebletemporal = cocina.pincharMueble(evt.getX(), evt.getY());
            if (muebletemporal!=null) {
                muebletemporal.setBorde(true);
                this.repaint();
            }
        }
    }//GEN-LAST:event_formMouseMoved

private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
    //vbles para recordar posicion anterior al movimiento
    int x,y;
    Pared p2;

    if (modoWimp==true && botonpulsado==MouseEvent.BUTTON1) { //drag&drop
        if (puertatemporal!=null) { //arrastrar puerta
            //mover de sitio
            p2=puertatemporal.getPared();
            Pared pa = cocina.pincharPared(evt.getX(), evt.getY()); //pared cercana
            if (pa!=null) { //cambiar de pared
                if (puertatemporal.getPared()!=null) { //quitar de la anterior
                    puertatemporal.getPared().quitarPuerta(puertatemporal);
                }
                x=puertatemporal.getX();
                y=puertatemporal.getY();
                if (!pa.ponerPuerta(evt.getX(), evt.getY(), puertatemporal)) { //si no se puede poner, dejar donde estaba
                    p2.ponerPuerta(x,y, puertatemporal);
                }
            } else if (puertatemporal.getPared()!=null){ //es necesaria comprobacion? mover en la misma pared
                puertatemporal.getPared().quitarPuerta(puertatemporal);
                x=puertatemporal.getX();
                y=puertatemporal.getY();
                if (!puertatemporal.getPared().ponerPuerta(evt.getX(), evt.getY(), puertatemporal)) { //si no se puede poner, dejar donde estaba
                    p2.ponerPuerta(x,y, puertatemporal);
                }
            }
            
            //deseleccionar anterior
            if (muebleWimpSeleccionado!=null) {
                muebleWimpSeleccionado.setBorde(false);
                muebleWimpSeleccionado=null;
            }
            if(puertaWimpSeleccionada!=null) {
                puertaWimpSeleccionada.setBorde(false);
            }
            
            //actualizar dibujo
            puertaWimpSeleccionada=puertatemporal;
            puertaWimpSeleccionada.setBorde(true);
            puertaWimpSeleccionada.setCotas(true);
            this.repaint();
            
            //comprobar colisiones
            if (cocina.colisionaConMuebles(puertaWimpSeleccionada)) {
                controlador.cambiarEstado("No se puede poner una puerta o ventana donde ya hay un mueble, retirelo primero");
            } else {
                controlador.cambiarEstado("Listo");
            }
            
        } else if (muebletemporal!=null) { //si no, arrastrar mueble
            x=muebletemporal.getX(); //posicion actual
            y=muebletemporal.getY();
            int ang=muebletemporal.getAngulo();

            if (cocina.ponerMueble(evt.getX(), evt.getY(), muebletemporal)) { //intentar mover
                if (cocina.intersectaConPuertas(muebletemporal)) { //comprobar colisiones con puertas
                    controlador.cambiarEstado("No se puede poner cerca de una puerta o ventana");
                } else {
                    controlador.cambiarEstado("Listo");
                }
                //deseleccionar anterior
                if (muebleWimpSeleccionado!=null) {
                    muebleWimpSeleccionado.setBorde(false);
                }
                if(puertaWimpSeleccionada!=null) {
                    puertaWimpSeleccionada.setBorde(false);
                    puertaWimpSeleccionada=null;
                }
                
                //actualizar dibujo
                muebleWimpSeleccionado=muebletemporal;
                muebleWimpSeleccionado.setBorde(true);
                this.repaint();
            } else { //si no se puede poner, dejar donte estaba
                muebletemporal.setX(x);
                muebletemporal.setY(y);
                
                cocina.devolverMueble(muebletemporal);

                this.repaint();
            }
        }
    }
}//GEN-LAST:event_formMouseDragged

private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
    if (modoWimp) { //modo seleccionar, deseleccionar y arrastrar
        botonpulsado = evt.getButton();
        muebletemporal = cocina.pincharMueble(evt.getX(), evt.getY());
        puertatemporal = cocina.pincharPuerta(evt.getX(), evt.getY());

        if (puertatemporal !=null) { //pinchar en puerta o ventana
            copiaPuerta=(Puerta)puertatemporal.clone();
            if (puertaWimpSeleccionada!=null) {
                puertaWimpSeleccionada.setBorde(false);
            }
            puertatemporal.setBorde(true);
            puertaWimpSeleccionada=puertatemporal;
            if (muebleWimpSeleccionado!=null) {
                muebleWimpSeleccionado.setBorde(false);
                muebleWimpSeleccionado=null;
            }
            this.repaint();
        } else if (muebletemporal!=null) { //pinchar en mueble
            copiaMueble=(Mueble)muebletemporal.clone();
            if (muebleWimpSeleccionado!=null) {
                muebleWimpSeleccionado.setBorde(false);
            }
            muebletemporal.setBorde(true);
            muebleWimpSeleccionado=muebletemporal;
            if (puertaWimpSeleccionada!=null) {
                puertaWimpSeleccionada.setBorde(false);
                puertaWimpSeleccionada=null;
            }
            this.repaint();
        } else { //pinchar fuera
            if (muebleWimpSeleccionado!=null) {
                muebleWimpSeleccionado.setBorde(false);
                this.repaint();
             }
            if (puertaWimpSeleccionada!=null) {
                puertaWimpSeleccionada.setBorde(false);
                this.repaint();
            }
            muebleWimpSeleccionado=null;
            puertaWimpSeleccionada=null;
        }

        //notificar al controlador
        if (puertaWimpSeleccionada!=null) {
            controlador.puertaClick(puertaWimpSeleccionada);
        } else {
            controlador.muebleClick(muebleWimpSeleccionado);
        }

        controlador.cambiarEstado("Listo");
        
        if (botonpulsado==MouseEvent.BUTTON3) { //boton derecho
            controlador.menuEmergente(evt.getX(), evt.getY());
        }
    }
}//GEN-LAST:event_formMousePressed

private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
    if (modoWimp) {
        if (puertaWimpSeleccionada!=null) { //quitar cotas a la puerta
            puertaWimpSeleccionada.setCotas(false);
            
            if (cocina.colisionaConMuebles(puertaWimpSeleccionada)) { //devolver puerta a su sitio, si colisiona con un mueble
                puertaWimpSeleccionada.getPared().quitarPuerta(puertaWimpSeleccionada);
                copiaPuerta.getPared().ponerPuerta(copiaPuerta.getX(),copiaPuerta.getY(),copiaPuerta);
                puertaWimpSeleccionada=copiaPuerta;
                puertaWimpSeleccionada.setBorde(true);
                controlador.cambiarEstado("Listo");
            } else {
                if (puertaWimpSeleccionada.getX()!=copiaPuerta.getX() || puertaWimpSeleccionada.getY()!=copiaPuerta.getY()) {
                    controlador.cambio();
                }
            }
            this.repaint();
        }
        
        if (muebleWimpSeleccionado!=null && cocina.intersectaConPuertas(muebleWimpSeleccionado)) { //devolver muebles a su sitio
            cocina.quitarMueble(muebleWimpSeleccionado);
            cocina.devolverMueble(copiaMueble);
            muebleWimpSeleccionado=copiaMueble;
            muebleWimpSeleccionado.setBorde(true);
            controlador.cambiarEstado("Listo");
            this.repaint();
        } else {
            if (muebleWimpSeleccionado!=null && (muebleWimpSeleccionado.getX()!=copiaMueble.getX() || muebleWimpSeleccionado.getY()!=copiaMueble.getY())) {
                controlador.cambio();
            }
        }
    }
}//GEN-LAST:event_formMouseReleased
    
    public Mueble getMuebleWimpSeleccionado() {
        return muebleWimpSeleccionado;
    }
    
    public Puerta getPuertaWimpSeleccionada() {
        return puertaWimpSeleccionada;
    }
    
    public void deseleccionarTodo() {
        if (puertaWimpSeleccionada!=null) {
            puertaWimpSeleccionada.setBorde(false);
            puertaWimpSeleccionada=null;
        }
        if (muebleWimpSeleccionado!=null) {
            muebleWimpSeleccionado.setBorde(false);
            muebleWimpSeleccionado=null;
        }
    }

    public void seleccionar(Puerta p) {
        deseleccionarTodo();
        puertaWimpSeleccionada=p;
        puertaWimpSeleccionada.setBorde(true);
    }

    public void seleccionar(Mueble m) {
        deseleccionarTodo();
        muebleWimpSeleccionado=m;
        muebleWimpSeleccionado.setBorde(true);
    }
    
    public void setCocina(Cocina c) { //Se le asocia una cocina
        cocina = c;
        paint(this.getGraphics());
    }

    public void setCocina_deshacer(Cocina c) { //Se le asocia una cocina (variante para que al deshacer no parpadee)
        cocina = c;
        repaint();
    }
    
    public void paint(Graphics g) { //dibuja la cocina
        super.paint(g);

        if (cocina!=null) {
            cocina.pintarPlanta((Graphics2D)g, (ImageObserver)this);
        }
    }
    
    //activa el modo de seleccionar una pared para poner una puerta
    public void setModoPonerPuerta(boolean permitir) {
        modoPonerPuerta=permitir;
    }
    
    //activa el modo de elegir una puerta ya puesta
    public void setModoSeleccionarPuerta(boolean permitir) {
        modoSeleccionarPuerta=permitir;
        if (puertatemporal!=null) {
            puertatemporal.setBorde(false);
            repaint();
            puertatemporal=null;
        }
        
    }
    
    //activa el modo wimp (seleccionar, deseleccionar y arrastrar)
    public void setModoWimp(boolean permitir) {
        modoWimp=permitir;
    }

    public boolean isModoWimp() {
        return modoWimp;
    }
    
    //establece la puerta que se esta manejando
    public void setPuerta(Puerta puerta) {
        mipuerta=puerta;
    }
    
    //activa el modo de seleccionar un lugar para poner un mueble
    public void setModoPonerMueble(boolean permitir) {
        modoPonerMueble=permitir;
    }
    
    //activa el modo de elegir un mueble ya puesto
    public void setModoSeleccionarMueble(boolean permitir) {
        modoSeleccionarMueble=permitir;
        if (muebletemporal!=null) {
            muebletemporal.setBorde(false);
            repaint();
            muebletemporal=null;
        }
    }
    
    //establece la puerta que se esta manejando
    public void setMueble(Mueble mueble) {
        mimueble=mueble;
    }

    //cambia u oculta el mensaje en la barra superior de estado
    public void cambiarEstado(String estado) {
        if (controlador.isIGO() && t!=null) { //timer
                t.stop();
        }

        if (estado.equals("Listo") || estado.equals("")) {
            jLabelEstado.setVisible(false);
        } else {
            jLabelEstado.setVisible(true);
            jLabelEstado.setText(" ".concat(estado));
        }
        
        //timer blink
        if (controlador.isIGO()) {
            t = new Timer(400, new ActionListener() {
                Color rosa = new Color(255,99,93);
                Color marron = new Color(220,46,46);
                boolean estado = false;
                public void actionPerformed(ActionEvent e) {
                    jLabelEstado.setBackground(estado?marron:rosa);
                    estado=!estado;
                }
            });
            t.setInitialDelay(200);
            t.start();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelEstado;
    // End of variables declaration//GEN-END:variables

}
