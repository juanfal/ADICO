/*
 * ContenedorPpal.java
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
import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;
import controlador.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorPpal extends JPanel {
                 
    private JPanel contenedorVacio1;
    private JLabel estado;
    private JLabel tareaActual;
    private VCO2 vco2;
    
    private Controlador cont = Controlador.getInstance();
    private JPanel panelactual;
    
    /** Creates new form ContenedorPpal */
    public ContenedorPpal() {
        initComponents();
    }
    
    public void cambiarTarea(String tarea) {
        tareaActual.setText(tarea);
    }
    
    public void cambiarEstado(String estado) {
        try {
            ((vista.InterfazBarraEstado)panelactual).cambiarEstado(estado);
        } catch (Exception e) {
            //el panel no implementa la interfaz
        }
    }
    
    public void cambiarContenedor(JPanel nuevo) {
        if (panelactual==null) {
            panelactual=contenedorVacio1;
        }
        this.remove(panelactual);
        panelactual=nuevo;
        this.add(nuevo, BorderLayout.CENTER);
        this.validateTree();
    }
                
    private void initComponents() {
        tareaActual = new JLabel();
        estado = new JLabel();
        contenedorVacio1 = new JPanel();
        contenedorVacio1.setBackground(Color.WHITE);
        contenedorVacio1.setBorder(new EmptyBorder(80, 100, 80, 100));
        contenedorVacio1.setLayout(new BorderLayout());

        //añadir pantalla inicio
        JLabel p_inicio = new JLabel();
        p_inicio.setVerticalAlignment(SwingConstants.TOP);

        try{
            File imagen = new File("ayuda"+File.separator+"imagen.gif"); //imagen a cargar
            File texto = new File("ayuda"+File.separator+"texto.txt"); //texto a cargar

            if (imagen.exists()) { //si el fichero de la imagen existe
                p_inicio.setIcon(new ImageIcon("ayuda"+File.separator+"imagen.gif")); //lo cargamos

            } else if (texto.exists()) { //sino, si el fichero de texto existe
                    BufferedReader br = new BufferedReader(new FileReader(texto)); //lo cargamos
                    String linea, micadena="<html>"; //construimos la cadena
                    while ((linea = br.readLine()) != null) {
                        micadena=micadena+linea+"<br>";
                    }
                    micadena=micadena+"</html>";
                    //p_inicio.set
                    p_inicio.setText(micadena); //y lo mostramos
            }
        } catch (Exception e) {
               
        }

        contenedorVacio1.add(p_inicio, BorderLayout.CENTER);
        vco2 = cont.getVco2();

        setLayout(new BorderLayout());

        tareaActual.setText("<html>Objetivo actual: </html>");
        tareaActual.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, SystemColor.controlHighlight, null, null));
        tareaActual.setPreferredSize(new Dimension(24, 24));
        add(tareaActual, BorderLayout.NORTH);

        //contenedor vacio
        add(contenedorVacio1, BorderLayout.CENTER);

        vco2.setPreferredSize(new Dimension(260, 260));
        add(vco2, BorderLayout.WEST);
        setPreferredSize(new Dimension(950, 700));
    }                   
}
