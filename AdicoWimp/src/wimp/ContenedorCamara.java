/*
 * ContenedorCamara.java
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

package wimp;

import cocina.*;
import vista.*;
import java.awt.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class ContenedorCamara extends javax.swing.JPanel {

    private ContenedorCocina3D contCoc3D;

    /** Creates new form ContenedorCamara */
    public ContenedorCamara(Cocina cocina) {
        initComponents();
        contCoc3D = new ContenedorCocina3D(cocina);
        this.add(contCoc3D, BorderLayout.CENTER);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDer = new javax.swing.JButton();
        jButtonIzq = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jButtonDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/go-next.png"))); // NOI18N
        jButtonDer.setToolTipText("Girar cámara a la derecha");
        jButtonDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDerActionPerformed(evt);
            }
        });
        add(jButtonDer, java.awt.BorderLayout.LINE_END);

        jButtonIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/go-previous.png"))); // NOI18N
        jButtonIzq.setToolTipText("Girar cámara a la izquierda");
        jButtonIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIzqActionPerformed(evt);
            }
        });
        add(jButtonIzq, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIzqActionPerformed
        contCoc3D.girarIzq();
}//GEN-LAST:event_jButtonIzqActionPerformed

    private void jButtonDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDerActionPerformed
        contCoc3D.girarDer();
}//GEN-LAST:event_jButtonDerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDer;
    private javax.swing.JButton jButtonIzq;
    // End of variables declaration//GEN-END:variables

}