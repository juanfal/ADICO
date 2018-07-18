/*
 * Controlador.java
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

package controlador;

import cocina.*;
import vista.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import wimp.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Controlador implements InterfazControlador { //Sigue el patron Singleton

    private static Controlador instancia;
    
    //cocina
    private Cocina micocina;
    private String ruta;
    
    //forma
    private int dimA, dimB, dimC, dimD;
    
    //vista
    private ContenedorWimp contWimp;
    private ContenedorCocinaPlanta contCocPla1;
    private ContenedorCamara contCamara;
    private JPanel contVacio1;
    private ContenedorPresupuesto contPresu1;
    private ContenedorMedidas contMedidas1;
    private ContenedorMateriales contMateriales1;
    
    //estados
    private int formaSeleccionada = -1;
    
    //otros elementos
    private Puerta mipuerta;
    private boolean puertapuesta = false;
    
    //muebles
    private Mueble mimueble;
    private boolean mueblepuesto = false;
    private Color colorSeleccionado = new Color(255,0,0,180);

    //deshacer-rehacer
    private LinkedList<byte[]> listaCambios = new LinkedList<byte[]>();
    private int punteroListaCambios = 0;
    private boolean hayCambios=false;

    //catalogo
    private Catalogo catalogo;
    private Object categoriaCatalogo;
    
    private Controlador() { //Constructor        
    }
    
    public static Controlador getInstance(){ //devuelve la unica instancia de la clase
        if (instancia==null) {
            instancia = new Controlador();
        }
        return instancia;
    }
    
    public void mostrarVentana() { //crea la ventana con el contenedor principal
        //cargar ventana
        contWimp = new ContenedorWimp(this);
        contWimp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //EXIT_ON_CLOSE);
        contWimp.setTitle("Adico Wimp");
        contWimp.pack();
        contWimp.setExtendedState(ContenedorWimp.MAXIMIZED_BOTH);

        //habilitar menus
        contWimp.setEnabledCocina(false);
        contWimp.setEnabledDeshacer(false);
        contWimp.setEnabledRehacer(false);
        contWimp.setEnabled2D(false);
        contWimp.setEnabledPuertas(false);
        
        contWimp.setVisible(true);
        crearContenedorVacio();
        contWimp.validate();
        
        //empezar con nuevo proyecto
        nuevo();
    }
    
    public ContenedorCocinaPlanta getContCocinaPlanta() { //devuelve el contenedor de la cocina en 2D
        return this.contCocPla1;
    }
    
    private void crearContenedorVacio() { //muestra un contenedor en blanco
        contVacio1 = new JPanel();
        contVacio1.setBackground(Color.WHITE);
        contWimp.cambiarContenedor(contVacio1);
    }
    
    private void crearContenedorCocina(){ //muestra la planta de la cocina
        contCocPla1 = new ContenedorCocinaPlanta(this);
        contWimp.cambiarContenedor(contCocPla1);
    }
    
    private void crearCocina(){ //se crea un objeto cocina
        //crearla
        try {
            micocina = new cocina.Cocina();
        
            //asignar forma
            switch (formaSeleccionada) {
                case 1: case 2:
                    micocina.getForma().formaRectangular(dimA,dimB);
                    break;
                case 3:
                    micocina.getForma().formaL3(dimA,dimB,dimC,dimD);
                    break;
                case 4:
                    micocina.getForma().formaL4(dimA,dimB,dimC,dimD);
                    break;
                case 5:
                    micocina.getForma().formaL5(dimA,dimB,dimC,dimD);
                    break;
                case 6:
                    micocina.getForma().formaL6(dimA,dimB,dimC,dimD);
                    break;
            }

            //asociarla al contenedor
            contCocPla1.setCocina(micocina);
            limpiarListaCambios();
            cambio();

            micocina.setControlador(this); //le asociamos el controlador

            //habilitar menus
            contWimp.setEnabledCocina(true);
            contWimp.setEnabled2D(true);
            contWimp.setEnabledPuertas(false);
        } catch(NoClassDefFoundError er) {
            JOptionPane.showMessageDialog(null, "Error al crear la cocina. Asegurese de que está instalado Java3D.", "AdicoWimp", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public void cambiarEstado(String estado){ //cambiar el texto de la barra de estado
        contWimp.cambiarEstado(estado);
    }

    public void paredClick() { //recibe click en la pared
        puertapuesta = true;
        contCocPla1.setModoPonerPuerta(false);
        contCocPla1.setModoWimp(true);
        catalogo.deseleccionar();
        contWimp.botonArrastrar(true);
        contCocPla1.seleccionar(mipuerta);
        mipuerta.setCotas(false);
        cambio();

        //habilitar menus
        contWimp.setEnabledPuertas(true);
    }
    
    public void muebleClick() { //mueble puesto
        mueblepuesto = true;
        contCocPla1.setModoPonerMueble(false);
        contCocPla1.setModoWimp(true);
        catalogo.deseleccionar();
        contCocPla1.seleccionar(mimueble);
        cambio();

        //habilitar menus
        contWimp.setEnabledMuebles(true);
        contWimp.setEnabled2D(true);
    }
    public void puertaClick(Puerta puerta) { //recibe la puerta seleccionada
        contWimp.setEnabledPuertas(puerta!=null);
    }
    public void muebleClick(Mueble mueble) { //recibe el mueble seleccionado
        contWimp.setEnabledMuebles(mueble!=null);
    }
    
    public void setMueblePuesto(boolean puesto) {
        mueblepuesto = puesto;
    }
    
    //recupera una cocina serializada
    private void cargarCocina(String ruta) {
        //abrir fichero
        try {
            InputStream is = new FileInputStream(ruta);
            ObjectInput oi = new ObjectInputStream(is);
            micocina = (Cocina)oi.readObject(); //tenemos el objeto
            this.ruta = ruta; //esta abierta, cambiamos la ruta del proyecto actual

            //habilitar menus
            contWimp.setEnabledCocina(true);
            contWimp.setEnabled2D(true);
            contWimp.setEnabledPuertas(false);

            //deseleccionar todo
            for(Puerta p: micocina.getForma().getListaPuertas()) {
                p.setBorde(false);
            }
            for(Mueble m: micocina.getListaMuebles()) {
                m.setBorde(false);
            }
            
            micocina.setControlador(this); //le asociamos el controlador
            for (Mueble m: micocina.getListaMuebles()) m.setColorSeleccionado(colorSeleccionado);
            for (Puerta p: micocina.getForma().getListaPuertas()) p.setColorSeleccionado(colorSeleccionado);
            limpiarListaCambios();
            cambio();
            hayCambios=false;
            oi.close();

            crearContenedorCocina();
            contCocPla1.setCocina(micocina);
            contCocPla1.setModoWimp(true);
            deseleccionarObjetosCont2D();
            mueble();
            contWimp.validate();
            cambiarEstado("Listo");
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el fichero", "AdicoWimp", JOptionPane.ERROR_MESSAGE);
        } catch(NoClassDefFoundError er) {
            JOptionPane.showMessageDialog(null, "Error al crear la cocina. Asegurese de que está instalado Java3D.", "AdicoWimp", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    //guarda una cocina serializada
    private void guardarCocina(String ruta) {
        try {
            OutputStream os = new FileOutputStream(ruta);
            ObjectOutput oo = new ObjectOutputStream(os);
            oo.writeObject(micocina);
            oo.close();
            this.ruta = ruta; //guardamos la ruta del proyecto actual guardado
            //cambiarEstado("Cocina guardada");
            hayCambios=false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el fichero", "AdicoWimp", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //muestra en el panel la vista de la cocina en 2D
    public void mostrarCocina2D() {
        if(contCocPla1.getParent()==null) {
            mueble();
        }
    }

    //muestra en el panel el presupuesto
    public void mostrarPresupuesto() {
        if (micocina!=null) {
            contPresu1 = new ContenedorPresupuesto();
            contPresu1.nuevoPresupuesto(micocina);
            contWimp.cambiarContenedor(contPresu1);
            contWimp.cambiarPanelLateral(null);
            contWimp.validate();

            //habilitar menus
            contWimp.setEnabled2D(false);
            contWimp.setEnabledPuertas(false);
            contWimp.setEnabledDeshacer(punteroListaCambios>1);
            contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());
        }
    }
    
    //muestra en el panel la vista de la cocina en 3D
    public void mostrarCocina3D() {    
        if (micocina!=null) {
            contCamara = new ContenedorCamara(micocina);
            contWimp.cambiarContenedor(contCamara);
            contWimp.cambiarPanelLateral(null);
            contWimp.validate();

            //habilitar menus
            contWimp.setEnabled2D(false);
            contWimp.setEnabledPuertas(false);
            contWimp.setEnabledDeshacer(false);
            contWimp.setEnabledRehacer(false);
        }
    }
    
    public Cocina getCocina() {
        return micocina;
    }

    //define las dimensiones de una cocina
    public boolean definirForma(int formaSeleccionada, int dimA, int dimB, int dimC, int dimD) {
        //comprobacion de dimensiones
        if (dimA<100) { cambiarEstado("El lado A no puede ser menor de un metro");
                        contMedidas1.seleccionarTextField('a'); return false; }
        if (dimA>600) { cambiarEstado("El lado A no puede ser mayor de seis metros");
                        contMedidas1.seleccionarTextField('a'); return false; }
        if (dimB<100) { cambiarEstado("El lado B no puede ser menor de un metro");
                        contMedidas1.seleccionarTextField('b'); return false; }
        if (dimB>600) { cambiarEstado("El lado B no puede ser mayor de seis metros");
                        contMedidas1.seleccionarTextField('b'); return false; }
        if (formaSeleccionada>=2) {
            if (dimC<0) {       cambiarEstado("El lado C no puede ser negativo");
                                contMedidas1.seleccionarTextField('c'); return false; }
            if (dimC>dimB) {    cambiarEstado("El lado C no puede ser mayor que el lado B");
                                contMedidas1.seleccionarTextField('c'); return false; }
            if (dimD<0) {       cambiarEstado("El lado D no puede ser negativo");
                                contMedidas1.seleccionarTextField('d'); return false; }
            if (dimD>dimA) {    cambiarEstado("El lado D no puede ser mayor que el lado A");
                                contMedidas1.seleccionarTextField('d'); return false; }
        }

        //crear forma
        this.formaSeleccionada = formaSeleccionada;
        this.dimA=dimA;
        this.dimB=dimB;
        this.dimC=dimC;
        this.dimD=dimD;
        crearCocina();
        cambiarEstado("Listo");
        return true;
    }

    //muestra el dialogo para abrir una cocina
    public void abrir() {
        if (hayCambios && micocina!=null) {
            switch (JOptionPane.showConfirmDialog(null, "¿Desea guardar la cocina actual?", "AdicoWimp", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.CANCEL_OPTION: return;
                case JOptionPane.YES_OPTION: guardar(); break;
            }
        }

        //abrir fichero
        String ruta2 = ".."+File.separator+"cocinas"+File.separator;
        JFileChooser chooser = new JFileChooser(ruta2);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Ficheros cocina", "ktch");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.contWimp);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           //cargar cocina
           cargarCocina(chooser.getSelectedFile().getPath());
        }
    }

    //guardar cocina
    public void guardar() {
        if (micocina==null) {
            JOptionPane.showMessageDialog(null, "No se puede guardar, cree una cocina primero", "AdicoWimp", JOptionPane.WARNING_MESSAGE);
        } else if (ruta.compareTo("")==0) { //si no tiene nombre
            guardarComo();
        } else {
            guardarCocina(ruta);
        }
    }

    //guardar cocina como muestra el dialogo
    public void guardarComo() {
        if (micocina==null) {
            JOptionPane.showMessageDialog(null, "No se puede guardar, cree una cocina primero", "AdicoWimp", JOptionPane.WARNING_MESSAGE);
        } else {
            //guardar fichero
            String ruta2 = ".."+File.separator+"cocinas"+File.separator;
            JFileChooser chooser = new JFileChooser(ruta2);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Ficheros cocina", "ktch");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(this.contWimp);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                //guardar cocina
                ruta2 = chooser.getSelectedFile().getPath();

                if (chooser.getFileFilter() == filter) {
                    if (ruta2.substring(ruta2.length()-5).compareToIgnoreCase(".ktch")!=0) {
                        ruta2=ruta2.concat(".ktch");
                    }
                }
               guardarCocina(ruta2);
            }
        }
    }

    //crea una nueva cocina sin dimensiones
    public void nuevo() {
        if (hayCambios && micocina!=null) {
            switch (JOptionPane.showConfirmDialog(null, "¿Desea guardar la cocina actual?", "AdicoWimp", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.CANCEL_OPTION: return;
                case JOptionPane.YES_OPTION: guardar(); break;
            }
        }
        ruta="";
        crearContenedorCocina();
        micocina=null;
        limpiarListaCambios();
        contMedidas1 = new ContenedorMedidas();
        contWimp.cambiarPanelLateral(contMedidas1);
        contWimp.validate();
        hayCambios=false;
        cambiarEstado("Listo");

        //habilitar menus
        contWimp.setEnabledCocina(false);
        contWimp.setEnabled2D(false);
        contWimp.setEnabledPuertas(false);
    }

    //cierra la cocina actual
    public void cerrar() {
        if (hayCambios && micocina!=null) {
            switch (JOptionPane.showConfirmDialog(null, "¿Desea guardar la cocina actual?", "AdicoWimp", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.CANCEL_OPTION: return;
                case JOptionPane.YES_OPTION: guardar(); break;
            }
        }
        
        micocina=null;
        contWimp.cambiarContenedor(contVacio1);
        contWimp.cambiarPanelLateral(null);
        contWimp.validate();
        contWimp.repaint();
        limpiarListaCambios();
        cambiarEstado("Listo");

        //habilitar menus
        contWimp.setEnabledCocina(false);
        contWimp.setEnabled2D(false);
        contWimp.setEnabledPuertas(false);
    }

    //salir de la aplicacion
    public void salir() {
        if (hayCambios && micocina!=null) {
            switch (JOptionPane.showConfirmDialog(null, "¿Desea guardar la cocina actual?", "AdicoWimp", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.CANCEL_OPTION: return;
                case JOptionPane.YES_OPTION: guardar(); break;
            }
        }
        System.exit(0);
    }

    public void puerta() {
        catalogo = new Catalogo();
        catalogo.modoPuertas();
        contWimp.cambiarContenedor(contCocPla1);
        contWimp.cambiarPanelLateral(catalogo);
        contCocPla1.repaint();
        contWimp.validate();

        //habilitar menus
        contWimp.setEnabled2D(true);
        if (contCocPla1.getPuertaWimpSeleccionada()!=null) {
            contWimp.setEnabledPuertas(true);
        } else {
            contWimp.setEnabledMuebles(contCocPla1.getMuebleWimpSeleccionado()!=null);
        }
        contWimp.setEnabledDeshacer(punteroListaCambios>1);
        contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());

        contWimp.requestFocus(); //forzar el foco para que funcionen los atajos de teclado
    }

    public void mueble() {
        catalogo = new Catalogo();
        catalogo.modoMuebles();
        contWimp.cambiarContenedor(contCocPla1);
        contWimp.cambiarPanelLateral(catalogo);
        contCocPla1.repaint();
        contWimp.validate();

        //habilitar menus
        contWimp.setEnabled2D(true);
        if (contCocPla1.getPuertaWimpSeleccionada()!=null) {
            contWimp.setEnabledPuertas(true);
        } else {
            contWimp.setEnabledMuebles(contCocPla1.getMuebleWimpSeleccionado()!=null);
        }
        contWimp.setEnabledDeshacer(punteroListaCambios>1);
        contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());
        
        contWimp.requestFocus(); //forzar el foco para que funcionen los atajos de teclado
    }

    public void materiales() {
        mostrarPresupuesto();
        contMateriales1 = new ContenedorMateriales();
        if (micocina!=null) {
            contMateriales1.seleccionar(micocina.getEncimera(), micocina.getMaderas(), micocina.getTiradores());
        }
        contWimp.cambiarPanelLateral(contMateriales1);
        contWimp.validate();
    }

    public void borrar() {
        if (contCocPla1.isModoWimp()) {
            if (contCocPla1.getMuebleWimpSeleccionado()!=null) {
                micocina.quitarMueble(contCocPla1.getMuebleWimpSeleccionado());
                cambio();
            } else if (contCocPla1.getPuertaWimpSeleccionada()!=null) {
                contCocPla1.getPuertaWimpSeleccionada().getPared().quitarPuerta(contCocPla1.getPuertaWimpSeleccionada());
                cambio();
            }
            contCocPla1.repaint();
            contCocPla1.deseleccionarTodo();

            contWimp.setEnabled2D(true);
        }        
    }
    
    public void girar(int angulo) {
        Mueble mueble = contCocPla1.getMuebleWimpSeleccionado();
        if (contCocPla1.isModoWimp() && mueble!=null) {
            int anguloAnt = mueble.getAngulo();
            mueble.girar(angulo);
            if (micocina.colisionaConMuebles(mueble) || micocina.intersectaConPuertas(mueble) || !micocina.contains(mueble.getRectangle())) {
                mueble.setAngulo(anguloAnt);
                cambiarEstado("No se puede girar");
            } else {
                cambio();
            }
            contCocPla1.repaint();
        }
    }

    public void deseleccionar() {
        if (contCocPla1!=null) {
            contCocPla1.deseleccionarTodo();
            contWimp.setEnabledMuebles(false);
            contCocPla1.repaint();
        }
    }

    public void menuEmergente(int x, int y) {
        if (contCocPla1!=null) {
            contCocPla1.updateUI();
            contWimp.menuEmergente(contCocPla1, x, y);
        }
    }

    public void acercade() {
        AcercaDe ad = new AcercaDe();
        ad.setLocationRelativeTo(contWimp);
        ad.setVisible(true);
    }

    public void borrarTodosLosMuebles() {
        if (contCocPla1.isModoWimp()) {
            micocina.borrarMuebles();
            contCocPla1.repaint();
            cambio();
            contWimp.setEnabled2D(true);
        }
    }

    public void ponerPuerta(String ruta) {
        mipuerta = new Puerta();
        mipuerta.abrir(ruta);
        mipuerta.setColorSeleccionado(colorSeleccionado);
        contCocPla1.setPuerta(mipuerta);
        contCocPla1.setModoPonerPuerta(true);
        contCocPla1.setModoWimp(false);
        puertapuesta=false;
        contWimp.botonArrastrar(false);
    }

    public void ponerMueble(String ruta) {
        mimueble = new Mueble();
        mimueble.abrir(ruta);
        mimueble.setColorSeleccionado(colorSeleccionado);
        contCocPla1.setMueble(mimueble);
        contCocPla1.setModoPonerMueble(true);
        contCocPla1.setModoWimp(false);
        mueblepuesto=false;
        contWimp.botonArrastrar(false);
    }

    public void elegirMateriales(Mostrable encimera, Mostrable madera, Mostrable tirador) {
        if (micocina!=null) {
            micocina.setEncimera(encimera);
            micocina.setMaderas(madera);
            micocina.setTiradores(tirador);
            if (this.contPresu1!=null) {
                contPresu1.nuevoPresupuesto(micocina);
            }
            cambio();
        }
    }

    //metodos para establecer y recuperar la categoria seleccionada en el catalogo
    public void setCategoriaCatalogo(Object sel) {
        categoriaCatalogo=sel;
    }

    public Object getCategoriaCatalogo() {
        return categoriaCatalogo;
    }

    private void cancelarPonerPuerta() {
        if (micocina!=null) {
            if (!puertapuesta) {
                if (mipuerta!=null) {
                    if (mipuerta.getPared()!=null) {
                        mipuerta.getPared().quitarPuerta(mipuerta);
                    }
                    mipuerta=null;
                }
                cambiarEstado("Listo");
                contCocPla1.setModoPonerPuerta(false);
                contCocPla1.setPuerta(null);
                contCocPla1.setModoWimp(true);
                contCocPla1.repaint();
            }
        }
    }

    private void cancelarPonerMueble() {
        if (!mueblepuesto) {
            if (micocina!=null) {
                micocina.quitarMueble(mimueble);
                mimueble=null;
                contCocPla1.setModoPonerMueble(false);
                contCocPla1.setMueble(null);
                cambiarEstado("Listo");
                contCocPla1.setModoWimp(true);
                contCocPla1.repaint();
            }
        }
    }

    public void cancelarPoner() {
        cancelarPonerPuerta();
        cancelarPonerMueble();
        contWimp.botonArrastrar(true);
    }

    public void catalogoDeseleccionar() {
        if (micocina!=null) {
            catalogo.deseleccionar();
        }
    }

    private void aplicarCambio(int posicion) {
        //recupera un estado de la lista y lo aplica
        try {
            InputStream is = new ByteArrayInputStream(listaCambios.get(posicion));
            ObjectInput oi = new ObjectInputStream(is);
            micocina=(Cocina)oi.readObject();

            deseleccionarObjetosCont2D();

            micocina.setControlador(this);
            if (contCocPla1!=null ) {
                contCocPla1.setCocina_deshacer(micocina);
            }
            if (contPresu1!=null) {
                contPresu1.nuevoPresupuesto(micocina);
            }

            } catch(Exception e) {
                System.out.println("error de serializacion");
            }
    }

    public void deshacer() {
        //aplicar el anterior cambio
        if (punteroListaCambios>1) {
            aplicarCambio(punteroListaCambios-2);
            punteroListaCambios--;
            //habilitar menus
            contWimp.setEnabledDeshacer(punteroListaCambios>1);
            contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());
            contWimp.setEnabled2D(true);

            cambiarEstado("Listo");
        } else {
            cambiarEstado("No se puede deshacer mas");
        }
    }
    public void rehacer() {
        //aplicar el siguiente cambio
        if (punteroListaCambios<listaCambios.size()) {
            aplicarCambio(punteroListaCambios);
            punteroListaCambios++;
            //habilitar menus
            contWimp.setEnabledDeshacer(punteroListaCambios>1);
            contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());
            contWimp.setEnabled2D(true);

            cambiarEstado("Listo");
        } else {
            cambiarEstado("No se puede rehacer mas");
        }
    }

    public void cambio() {
        //si no esta al final (hay posibilidad de rehacer) borrar a partir de ahi
        while (listaCambios.size()>punteroListaCambios) {
            listaCambios.removeLast();
        }

        //añadir elemento a listaCambios
        capturar();

        //si supera el limite, quitar el primero
        while (listaCambios.size()>20) { //capacidad de cambios
            listaCambios.remove(0);
            punteroListaCambios--;
        }

        //habilitar menus
        contWimp.setEnabledDeshacer(punteroListaCambios>1);
        contWimp.setEnabledRehacer(punteroListaCambios<listaCambios.size());
    }
    
    private void capturar() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(os);
            oo.writeObject(micocina);
            listaCambios.add(((ByteArrayOutputStream)os).toByteArray());
            punteroListaCambios=listaCambios.size();
            hayCambios=true;
        } catch(Exception e) {
            System.out.println("error de serializacion");
        }
        
    }

    private void limpiarListaCambios() {
        punteroListaCambios=0;
        listaCambios.clear();
        hayCambios=false;
        
        //habilitar menus
        contWimp.setEnabledDeshacer(false);
        contWimp.setEnabledRehacer(false);
    }

    private void deseleccionarObjetosCont2D() { //deseleccionar puertas y muebles
        for (Mueble m: micocina.getListaMuebles()) { m.setBorde(false); }
        for (Puerta p :micocina.getForma().getListaPuertas()) { p.setBorde(false); }
        contCocPla1.deseleccionarTodo();
        contWimp.setEnabledMuebles(false);
    }

    public boolean isIGO() { //devuelve true si es el proyecto AdicoIGO
        return false;
    }

}
