/*
 * ContenedorPresupuesto.java
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
import java.util.*;
import java.text.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorPresupuesto extends javax.swing.JPanel implements InterfazBarraEstado {
    
    /** Creates new form ContenedorPresupuesto */
    public ContenedorPresupuesto() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabelEstado = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Descripción", "Precio unitario", "Cantidad", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(3).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setResizable(false);
        jTable1.getColumnModel().getColumn(5).setResizable(false);

        jPanel2.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(632, 25));

        jLabelEstado.setBackground(new java.awt.Color(255, 99, 93));
        jLabelEstado.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelEstado.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(136, 6, 6), 2));
        jLabelEstado.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void nuevoPresupuesto(Cocina cocina) {
        //vbles
        ArrayList<Mueble> listaMuebles = cocina.getListaMuebles();
        javax.swing.table.DefaultTableModel datos = new javax.swing.table.DefaultTableModel();
        Set<Integer> conjunto = new TreeSet<Integer>();
        float total = 0;
        
        //cabecera
        datos.addColumn("Código");
        datos.addColumn("Nombre");
        datos.addColumn("Descripción");
        datos.addColumn("Precio unitario");
        datos.addColumn("Cantidad");
        datos.addColumn("Precio total");
        
        //muebles y electrodomesticos
        Object[] arrayMuebles = listaMuebles.toArray(); //convertir a array
        Arrays.sort(arrayMuebles); //ordenar array por el codigo de los muebles
        DecimalFormat df = new DecimalFormat("0.##");

        for(Object o: arrayMuebles) {
            Mueble m = (Mueble)o;
            if (!conjunto.contains(m.getCodigo())) {
                int cantidad = cuentaMuebles(listaMuebles, m);
                conjunto.add(m.getCodigo());

                Object arrayDatos[] = {m.getCodigo(), m.getNombre(), m.getDescripcion(), df.format(m.getPrecio()), cantidad, df.format(m.getPrecio()*cantidad)};
                total+=m.getPrecio()*cantidad;
                datos.addRow(arrayDatos);
            }
        }
        
        //otros materiales
        Mostrable encimera = cocina.getEncimera();
        if ((encimera!=null) && (cocina.metrosCuadradosDeEncimera()>0)) {
            Object arrayDatosE[] = {encimera.getCodigo(),encimera.getNombre(), encimera.getDescripcion(),encimera.getPrecio(), cocina.metrosCuadradosDeEncimera(), df.format(encimera.getPrecio()*cocina.metrosCuadradosDeEncimera())};
            total+=encimera.getPrecio()*cocina.metrosCuadradosDeEncimera();
             datos.addRow(arrayDatosE);
        }
        
        
        Mostrable maderas = cocina.getMaderas();
        if ((maderas!=null) && (cocina.numPuertasMuebles()>0)) {
            Object arrayDatosM[] = {maderas.getCodigo(),maderas.getNombre(), maderas.getDescripcion(),maderas.getPrecio(), cocina.numPuertasMuebles(), df.format(maderas.getPrecio()*cocina.numPuertasMuebles())};
            total+=maderas.getPrecio()*cocina.numPuertasMuebles();
            datos.addRow(arrayDatosM);
        }
        
        Mostrable tiradores = cocina.getTiradores();
        if ((tiradores!=null)&&(cocina.numTiradoresMuebles()>0)) {
            Object arrayDatosT[] = {tiradores.getCodigo(), tiradores.getNombre(), tiradores.getDescripcion(),tiradores.getPrecio(), cocina.numTiradoresMuebles(), df.format(tiradores.getPrecio()*cocina.numTiradoresMuebles())};
            total+=tiradores.getPrecio()*cocina.numTiradoresMuebles();
            datos.addRow(arrayDatosT);
        }
        
        //total
        Object arrayDatos1[] = {"", "", "","", "", ""};
        datos.addRow(arrayDatos1);
        Object arrayDatos2[] = {"", "", "","", "TOTAL", df.format(total)};
        datos.addRow(arrayDatos2);
        
        //establecer modelo de datos a la tabla
        jTable1.setModel(datos);
        
        //proporcion del ancho de columnas
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40); //cod
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(110); //nombre
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(400); //descr
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(70); //precio unitario
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(60); //cantidad
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(60); //precio
        
        
        //alinear columnas numericas a la derecha
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new TableCellRendererSimple());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererSimple());
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererSimple());
    }
    
    //devuelve las unidades de un mueble determinado en la lista de muebles
    private int cuentaMuebles(ArrayList<Mueble> listaMuebles, Mueble mueble) {
        int contador = 0;
        for (Mueble m: listaMuebles) {
            if (m.getCodigo() == mueble.getCodigo()) {
                contador++;
            }
        }
        return contador;
    }
    
    //table cell renderer que alinea a la derecha (para campos numericos)
    private class TableCellRendererSimple extends javax.swing.table.DefaultTableCellRenderer {
        public TableCellRendererSimple() {
            super();
            this.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        }
    }

    //cambia u oculta el mensaje en la barra superior de estado
    public void cambiarEstado(String estado) {
        if (estado.equals("Listo") || estado.equals("")) {
            jLabelEstado.setVisible(false);
        } else {
            jLabelEstado.setVisible(true);
            jLabelEstado.setText(" ".concat(estado));
        }
    }
    
}