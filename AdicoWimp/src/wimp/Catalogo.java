/*
 * Catalogo.java
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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import cocina.*;
import controlador.*;
import java.text.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Catalogo extends javax.swing.JPanel {

    private int modo; //1->puertas, 2->muebles

    //nombres de los directorios donde buscar los ficheros xml
    private String[] arrayNombresDirectoriosXML = new String[] { "bajos", "pared", "electrodomesticos", "otros" };
    //nombres a mostrar en el desplegable de categorias de muebles (usar mismo orden en los dos arrays)
    private String[] arrayNombresComboBox = new String[] { "Muebles bajos", "Muebles de pared", "Electrodomésticos", "Otro mobiliario" };

    /** Creates new form Catalogo */
    public Catalogo() {
        initComponents();
        modoPuertas();
    }   

    public void modoMuebles() {
        jComboCategoria.setVisible(true);
        jLabel1.setText("Poner muebles y electrodomesticos:");
        jComboCategoria.setModel(new DefaultComboBoxModel(arrayNombresComboBox));
        cargar(".."+File.separator+"ficheros"+File.separator+"muebles"+File.separator+arrayNombresDirectoriosXML[0]); //categoria que se carga inicialmente
        modo=2;
        if (Controlador.getInstance().getCategoriaCatalogo()!=null) {
            jComboCategoria.setSelectedItem(Controlador.getInstance().getCategoriaCatalogo());
        }
    }

    public void modoPuertas() {
        jComboCategoria.setVisible(false);
        jLabel1.setText("Poner puertas y ventanas:");
        cargar(".."+File.separator+"ficheros"+File.separator+"puertas");
        modo=1;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListElementos = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        nombre = new javax.swing.JLabel();
        tipo = new javax.swing.JLabel();
        descripcion = new javax.swing.JLabel();
        dimensiones = new javax.swing.JLabel();
        precio = new javax.swing.JLabel();
        jComboCategoria = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(250, 468));
        setRequestFocusEnabled(false);
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                formAncestorRemoved(evt);
            }
        });

        jListElementos.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListElementos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListElementos.setFocusCycleRoot(true);
        jListElementos.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jListElementos.setVerifyInputWhenFocusTarget(false);
        jListElementos.setVisibleRowCount(3);
        jListElementos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListElementosValueChanged(evt);
            }
        });
        jListElementos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jListElementosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jListElementos);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(220, 117));
        jPanel1.setMinimumSize(new java.awt.Dimension(220, 117));

        nombre.setText(" ");

        tipo.setFont(new java.awt.Font("Dialog", 0, 12));
        tipo.setText(" ");

        descripcion.setFont(new java.awt.Font("Dialog", 0, 12));
        descripcion.setText(" ");

        dimensiones.setFont(new java.awt.Font("Dialog", 0, 12));
        dimensiones.setText(" ");

        precio.setFont(new java.awt.Font("Dialog", 0, 12));
        precio.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(dimensiones, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(precio, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(tipo, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dimensiones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "../ficheros/muebles/bajos", "../ficheros/muebles/pared", "../ficheros/muebles/electrodomesticos", "../ficheros/muebles/otros", "../ficheros/puertas", "../ficheros/encimeras", "../ficheros/tiradores", "../ficheros/maderas" }));
        jComboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoriaActionPerformed(evt);
            }
        });

        jLabel1.setText("Poner muebles:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jComboCategoria, 0, 220, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
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
         setText(((Mostrable)value).getNombre()); //mostrar nombre
         String ruta = ((Mostrable)value).getFichero(); //tomar ruta
         ruta = ruta.substring(0,ruta.lastIndexOf(File.separator)+1);
         setIcon(new ImageIcon(ruta+((Mostrable)value).getFoto())); //cargar foto
         if (isSelected) {
             setBackground(list.getSelectionBackground());
             setForeground(list.getSelectionForeground());
         } else {
             setBackground(list.getBackground());
             setForeground(list.getForeground());
         }
         setEnabled(list.isEnabled());
         setFont(list.getFont());
         setHorizontalTextPosition(CENTER);
         setVerticalTextPosition(BOTTOM);
         setOpaque(true);
         return this;
     }
 }

    private void cargar(String ruta) {
        Vector<Mostrable> vector = new Vector<Mostrable>();

        //construir vector con los elementos
        File directorio = new File(ruta);
        if (directorio.isDirectory()) {
            File[] elementos = directorio.listFiles();

            java.util.Arrays.sort(elementos);
            
            if (elementos!=null) {
                for (int i=0;i<elementos.length;i++) {
                    if (elementos[i].getAbsolutePath().endsWith(".xml")) { //deteccion de extension
                        Mostrable elementoActual = new Mostrable();
                        elementoActual.abrir(elementos[i].getPath());
                        vector.add(elementoActual);
                    }
                }
            }
        }

        //añadir contenido a la lista
        jListElementos.setListData(vector);
        jListElementos.setCellRenderer(new ElementoCatalogo());

        //establecer visibleRowCount a la mitad
        jListElementos.setVisibleRowCount((jListElementos.getModel().getSize()+1)/2);
    }

    public void deseleccionar() {
        jListElementos.removeSelectionInterval(jListElementos.getSelectedIndex(), jListElementos.getSelectedIndex());
    }

    private void jListElementosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListElementosValueChanged
        Controlador.getInstance().cancelarPoner();

        //mostrar info
        Mostrable elemento = (Mostrable)jListElementos.getSelectedValue();
        DecimalFormat df = new DecimalFormat("0.##");
        if (elemento !=null) {
            nombre.setText(elemento.getNombre());
            tipo.setText(elemento.getTipo());

            if (elemento.getDescripcion()!=null) {
                descripcion.setText(elemento.getDescripcion());
                if (elemento.getAltura()!=0) { //mostrar solo dimensiones existentes
                    if (elemento.getProfundidad()!=0) {
                        //flechas unicode: ↕ ↔ ➚
                        dimensiones.setText(Integer.toString(elemento.getAltura())+"↕ "+Integer.toString(elemento.getAnchura()) + "↔ "+Integer.toString(elemento.getProfundidad()) +"➚ cm");
                    } else {
                        dimensiones.setText(Integer.toString(elemento.getAltura())+"↕ "+Integer.toString(elemento.getAnchura()) +"↔ cm");
                    }
                    if (elemento.getPrecio()!=0) {
                        precio.setText(df.format(elemento.getPrecio()) + " €");
                    } else {
                        precio.setText(" ");
                    }
                } else {
                    //reajuste
                    if (elemento.getPrecio()!=0) {
                        dimensiones.setText(df.format(elemento.getPrecio()) + " €");
                    } else {
                        dimensiones.setText(" ");
                    }
                    precio.setText(" ");
                }
            } else {
                if (elemento.getAltura()!=0) { //mostrar solo dimensiones existentes
                    if (elemento.getProfundidad()!=0) {
                        descripcion.setText(Integer.toString(elemento.getAltura())+"↕ "+Integer.toString(elemento.getAnchura()) + "↔ "+Integer.toString(elemento.getProfundidad()) +"➚ cm");
                    } else {
                        descripcion.setText(Integer.toString(elemento.getAltura())+"↕ "+Integer.toString(elemento.getAnchura()) +"↔ cm");
                    }
                    if (elemento.getPrecio()!=0) {
                        dimensiones.setText(df.format(elemento.getPrecio()) + " €");
                    } else {
                        dimensiones.setText(" ");
                        precio.setText(" ");
                    }
                } else {
                    //reajuste
                    if (elemento.getPrecio()!=0) {
                        descripcion.setText(df.format(elemento.getPrecio()) + " €");
                    } else {
                        descripcion.setText(" ");
                    }
                    dimensiones.setText(" ");
                    precio.setText(" ");
                }
            }

            //notificar al controlador
            if (modo==1) {
                controlador.Controlador.getInstance().ponerPuerta(elemento.getFichero());
            } else if (modo==2) {
                controlador.Controlador.getInstance().ponerMueble(elemento.getFichero());
            }
        } else {
            nombre.setText(" ");
            tipo.setText(" ");
            descripcion.setText(" ");
            dimensiones.setText(" ");
            precio.setText(" ");
        }
}//GEN-LAST:event_jListElementosValueChanged

    private void jComboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoriaActionPerformed
        //usar el valor del jComboCategoria para generar la ruta
        cargar(".."+File.separator+"ficheros"+File.separator+"muebles"+File.separator+arrayNombresDirectoriosXML[jComboCategoria.getSelectedIndex()]);
        Controlador.getInstance().setCategoriaCatalogo(jComboCategoria.getSelectedItem());
        Controlador.getInstance().cancelarPoner();
}//GEN-LAST:event_jComboCategoriaActionPerformed

    private void formAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorRemoved
        Controlador.getInstance().cancelarPoner();
    }//GEN-LAST:event_formAncestorRemoved

    private void jListElementosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListElementosKeyPressed
        if (evt.getKeyCode()==27) { //tecla escape
            Controlador.getInstance().cancelarPoner();
            deseleccionar();
        }
    }//GEN-LAST:event_jListElementosKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descripcion;
    private javax.swing.JLabel dimensiones;
    private javax.swing.JComboBox jComboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListElementos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel precio;
    private javax.swing.JLabel tipo;
    // End of variables declaration//GEN-END:variables

}
