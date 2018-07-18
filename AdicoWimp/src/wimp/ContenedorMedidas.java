/*
 * ContenedorMedidas.java
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

import java.io.*;
import javax.swing.*;
import java.awt.*;
import controlador.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorMedidas extends javax.swing.JPanel {

    /** Creates new form ContenedorFormas */
    public ContenedorMedidas() {
        initComponents();
        jComboForma.removeAllItems();
        jComboForma.addItem(1); //añadir las 6 formas
        jComboForma.addItem(2);
        jComboForma.addItem(3);
        jComboForma.addItem(4);
        jComboForma.addItem(5);
        jComboForma.addItem(6);
        jComboForma.setSelectedIndex(1); //seleccionar rectangular
        jComboForma.setRenderer(new ElementoCatalogo()); //cambiar renderer para que se vean las imagenes
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        softBevelBorder1 = new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED);
        softBevelBorder2 = new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED);
        softBevelBorder3 = new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED);
        etchedBorder1 = (javax.swing.border.EtchedBorder)javax.swing.BorderFactory.createEtchedBorder();
        jTextFieldA = new javax.swing.JTextField();
        jTextFieldB = new javax.swing.JTextField();
        jTextFieldC = new javax.swing.JTextField();
        jTextFieldD = new javax.swing.JTextField();
        jLabelA = new javax.swing.JLabel();
        jLabelB = new javax.swing.JLabel();
        jLabelC = new javax.swing.JLabel();
        jLabelD = new javax.swing.JLabel();
        jLabelA2 = new javax.swing.JLabel();
        jLabelB2 = new javax.swing.JLabel();
        jLabelC2 = new javax.swing.JLabel();
        jLabelD2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboForma = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        marco = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(250, 556));

        jTextFieldA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAActionPerformed(evt);
            }
        });

        jTextFieldB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBActionPerformed(evt);
            }
        });

        jTextFieldC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCActionPerformed(evt);
            }
        });

        jTextFieldD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDActionPerformed(evt);
            }
        });

        jLabelA.setText("Lado A");

        jLabelB.setText("Lado B");

        jLabelC.setText("Lado C");

        jLabelD.setText("Lado D");

        jLabelA2.setText("cm");

        jLabelB2.setText("cm");

        jLabelC2.setText("cm");

        jLabelD2.setText("cm");

        jButton1.setText("Aplicar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboForma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboFormaActionPerformed(evt);
            }
        });

        jLabel9.setText("Seleccionar forma:");

        marco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        marco.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText("Introducir dimensiones:");

        jButton2.setText("Amueblar");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboForma, javax.swing.GroupLayout.Alignment.TRAILING, 0, 220, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabelD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldD, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelB)
                                    .addComponent(jLabelA)
                                    .addComponent(jLabelC))
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldC, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(jTextFieldB, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(jTextFieldA, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelB2)
                            .addComponent(jLabelA2)
                            .addComponent(jLabelC2)
                            .addComponent(jLabelD2)))
                    .addComponent(jLabel11)
                    .addComponent(marco, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboForma, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marco)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelA)
                    .addComponent(jTextFieldA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelA2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelB)
                    .addComponent(jTextFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelB2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelC)
                    .addComponent(jTextFieldC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelC2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelD)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelD2)))
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(184, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

//clase que implementa ListCellRenderer para poder usar JLabel con iconos en una lista
    protected class ElementoCatalogo extends JLabel implements ListCellRenderer {
     public Component getListCellRendererComponent(
       JList list,              // the list
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // does the cell have focus
     {
         setText("Forma "+value.toString()); //texto
         setIcon(new ImageIcon("imagenes"+File.separator+"formas"+File.separator+"forma"+value.toString()+".gif")); //cargar foto
         if (isSelected) {
             setBackground(list.getSelectionBackground());
             setForeground(list.getSelectionForeground());
         } else {
             setBackground(list.getBackground());
             setForeground(list.getForeground());
         }
         setEnabled(list.isEnabled());
         setFont(list.getFont());
         setOpaque(true);
         return this;
     }
 }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //datos
    int formaSeleccionada = ((Integer)jComboForma.getSelectedItem());
    JTextField sincomprobar = jTextFieldA;
    
    //convertir a integer
    try {
        int dimA = Integer.parseInt(jTextFieldA.getText());
        sincomprobar = jTextFieldB;
        int dimB = formaSeleccionada==1?dimA:Integer.parseInt(jTextFieldB.getText());
        sincomprobar = jTextFieldC;
        int dimC = formaSeleccionada<=2?0:Integer.parseInt(jTextFieldC.getText());
        sincomprobar = jTextFieldD;
        int dimD = formaSeleccionada<=2?0:Integer.parseInt(jTextFieldD.getText());

        //definir forma
        if (Controlador.getInstance().definirForma(formaSeleccionada, dimA, dimB, dimC, dimD))
        {
            jButton2.setEnabled(true);
        }
    } catch (NumberFormatException e) { //error
        Controlador.getInstance().cambiarEstado("Error al introducir la medida, se esperaba un número");
        seleccionarTextField(sincomprobar);
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jComboFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboFormaActionPerformed
    if (jComboForma.getSelectedItem() != null) {
        //cambiar imagen del marco
        marco.setIcon(new ImageIcon("imagenes"+File.separator+"formas"+File.separator+"forma"+jComboForma.getSelectedItem().toString()+"b.gif"));

        //habilitar lado B solo cuando es necesario
        boolean estado1 =  ((Integer)jComboForma.getSelectedItem())>1;
        if (jTextFieldB.isVisible() && !estado1) { //y borrar B cuando desaparece
            jTextFieldB.setText("");
        }
        jLabelB.setVisible(estado1);
        jTextFieldB.setVisible(estado1);
        jLabelB2.setVisible(estado1);

        //habilitar lados C y D solo cuando son necesarios
        boolean estado2 = ((Integer)jComboForma.getSelectedItem())>2;

        if (jTextFieldC.isVisible() && !estado2) { //y borrar C y D cuando desaparecen
            jTextFieldC.setText("");
            jTextFieldD.setText("");
        }
        jLabelC.setVisible(estado2);
        jLabelD.setVisible(estado2);
        jTextFieldC.setVisible(estado2);
        jTextFieldD.setVisible(estado2);
        jLabelC2.setVisible(estado2);
        jLabelD2.setVisible(estado2);
    }
}//GEN-LAST:event_jComboFormaActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Controlador.getInstance().puerta();
}//GEN-LAST:event_jButton2ActionPerformed

private void jTextFieldAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAActionPerformed
    if (jTextFieldB.isVisible()) {
        seleccionarTextField(jTextFieldB);
    } else {
        jButton1.doClick();
    }
}//GEN-LAST:event_jTextFieldAActionPerformed

private void jTextFieldBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBActionPerformed
    if (jTextFieldC.isVisible()) {
        seleccionarTextField(jTextFieldC);
    } else {
        jButton1.doClick();
    }
}//GEN-LAST:event_jTextFieldBActionPerformed

private void jTextFieldCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCActionPerformed
    seleccionarTextField(jTextFieldD);
}//GEN-LAST:event_jTextFieldCActionPerformed

private void jTextFieldDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDActionPerformed
    jButton1.doClick();
}//GEN-LAST:event_jTextFieldDActionPerformed

    //metodos para seleccionar y pasar el foco en un jtextfield
    public void seleccionarTextField(char lado) {
        switch (lado) {
            case 'a': seleccionarTextField(jTextFieldA); break;
            case 'b': seleccionarTextField(jTextFieldB); break;
            case 'c': seleccionarTextField(jTextFieldC); break;
            case 'd': seleccionarTextField(jTextFieldD); break;
        }
    }

    private void seleccionarTextField(JTextField jtf) {
        jtf.requestFocus();
        jtf.setSelectionStart(0);
        jtf.setSelectionEnd(jtf.getText().length());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.border.EtchedBorder etchedBorder1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboForma;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelA;
    private javax.swing.JLabel jLabelA2;
    private javax.swing.JLabel jLabelB;
    private javax.swing.JLabel jLabelB2;
    private javax.swing.JLabel jLabelC;
    private javax.swing.JLabel jLabelC2;
    private javax.swing.JLabel jLabelD;
    private javax.swing.JLabel jLabelD2;
    private javax.swing.JTextField jTextFieldA;
    private javax.swing.JTextField jTextFieldB;
    private javax.swing.JTextField jTextFieldC;
    private javax.swing.JTextField jTextFieldD;
    private javax.swing.JLabel marco;
    private javax.swing.border.SoftBevelBorder softBevelBorder1;
    private javax.swing.border.SoftBevelBorder softBevelBorder2;
    private javax.swing.border.SoftBevelBorder softBevelBorder3;
    // End of variables declaration//GEN-END:variables

}
