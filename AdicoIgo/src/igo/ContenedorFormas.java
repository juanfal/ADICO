/*
 * ContenedorFormas.java
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

package igo;

import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorFormas extends javax.swing.JPanel implements vista.InterfazBarraEstado {
    private Timer t;

    /** Creates new form ContenedorFormas */
    public ContenedorFormas() {
        initComponents();
        jLabelEstado.setVisible(false);
        String ruta = "imagenes"+File.separator+"formas"+File.separator;
        jLabel1.setIcon(new ImageIcon(ruta+"forma1.gif"));
        jLabel2.setIcon(new ImageIcon(ruta+"forma2.gif"));
        jLabel3.setIcon(new ImageIcon(ruta+"forma3.gif"));
        jLabel4.setIcon(new ImageIcon(ruta+"forma4.gif"));
        jLabel5.setIcon(new ImageIcon(ruta+"forma5.gif"));
        jLabel6.setIcon(new ImageIcon(ruta+"forma6.gif"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelEstado = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(0, 3));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(632, 25));

        jLabelEstado.setBackground(new java.awt.Color(255, 99, 93));
        jLabelEstado.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabelEstado.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(136, 6, 6), 2));
        jLabelEstado.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(6);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(5);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(4);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(3);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(2);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if (isEnabled()) {
            controlador.Controlador.getInstance().cambiarEstado("Listo");
            controlador.Controlador.getInstance().formaClick(1);
        }
    }//GEN-LAST:event_jLabel1MouseClicked
    
    public void seleccionar(int num) { //le añade un borde
        jLabel1.setBorder(null);
        jLabel2.setBorder(null);
        jLabel3.setBorder(null);
        jLabel4.setBorder(null);
        jLabel5.setBorder(null);
        jLabel6.setBorder(null);
        switch (num) {
            case 1: jLabel1.setBorder(new EtchedBorder()); break;
            case 2: jLabel2.setBorder(new EtchedBorder()); break;
            case 3: jLabel3.setBorder(new EtchedBorder()); break;
            case 4: jLabel4.setBorder(new EtchedBorder()); break;
            case 5: jLabel5.setBorder(new EtchedBorder()); break;
            case 6: jLabel6.setBorder(new EtchedBorder()); break;
        }
    }

    //cambia u oculta el mensaje en la barra superior de estado
    public void cambiarEstado(String estado) {
            if (t!=null) { //timer
                t.stop();
            }

        if (estado.equals("Listo") || estado.equals("")) {
            jLabelEstado.setVisible(false);
        } else {
            jLabelEstado.setVisible(true);
            jLabelEstado.setText(" ".concat(estado));

            //timer blink
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    
}
