/*
 * Controlador.java
 *
 * ADICO: Desarrollo de dos versiones de una Aplicaci√≥n para el DIse√±o de COcinas,
 * una con interfaz de usuario tipo WIMP y otra con interfaz IGO.
 *
 * Realizado por: Manuel Flores Vivas <mflores at alu.uma.es>
 * Tutor: Antonio Luis Carrillo Le√≥n <alcarrillo at uma.es>
 *
 * Proyecto Fin de Carrera
 * Ingenier√≠a T√©cnica en Inform√°tica de Sistemas (Universidad de M√°laga)
 */

package controlador;

import cocina.*;
import galeria.*;
import igo.*;
import vista.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import main.*;

/**
 *
 * @author Manuel Flores Vivas
 */

public class Controlador implements InterfazControlador { //Sigue el patron Singleton
    private static Controlador instancia;
    
    //cocina
    private Cocina micocina;
    
    //forma
    private int dimA, dimB, dimC, dimD;
    
    //vista
    private JFrame ventana;
    private ContenedorPpal contenedorPpal1;
    private VCO2 vco2;
    private ContenedorCocinaPlanta contCocPla1;
    private ContenedorCocina3D contCoc3D;
    private JPanel contVacio1;
    private ContenedorFormas catalogoFormas;
    private ContenedorMedidas contMedidas1;
    private Catalogo catalogo;
    private ContenedorDatos contDatos1;
    
    //estados
    private int formaSeleccionada = -1;
    private String elementoCatSeleccionado;
    
    //otros elementos
    private Puerta mipuerta, puertaantigua;
    private boolean puertapuesta = false;
    
    //muebles
    private String tipoMueble;
    private Mueble mimueble, muebleantiguo;
    private boolean mueblepuesto = false;
    private java.util.List<Mueble> listaMueblesNoDefinitivos;
    
    //materiales
    private Mostrable miencimera;
    private Mostrable mismaderas;
    private Mostrable mistiradores;
    
    private Controlador() { //Constructor
        
        //Crear Ventana conductora de objetivos
        vco2 = new VCO2();

        //usar argumentos
        if (Main.getModoDecide()!=0) {
            vco2.setModoDecide(Main.getModoDecide());
        }
        vco2.setAutoClick(Main.getAutoClick());

        //funciones para rellenar estructura
        //'M' = Metodo, 'S' = Selector
        
        //DISE√ëAR COCINA -------------------------------------------------------
        vco2.definePanel(0, "DISEÑAR COCINA:", 'M'); //objetivo principal
        vco2.defineBotonInit(0,   "Dibujar plano", 1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                crearContenedorVacio(); //limpiar ventana de trabajo
                contVacio1.repaint();
            }});
        //vco2.defineBotonDecide(0, "¿Corregir plano?", 1);
        vco2.defineBotonInit(0,   "Amueblar", 11);
        vco2.defineBotonDecide(0, "¿Comenzar de nuevo (desde el principio)?", 1);
        vco2.defineBotonInit(0,   "Encargar cocina", 24);
        vco2.defineBotonReturn(0, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0); //salir del programa
            }});
        
        //subobjetivos:
        //DIBUJAR COCINA -------------------------------------------------------
        vco2.definePanel(1, "DIBUJAR PLANO COCINA:", 'M');
        vco2.defineBotonInit(1, "Elegir forma cocina", 2);
        vco2.defineBotonInit(1, "Introducir medidas", 3);
        vco2.defineBotonInit(1, "Colocar puertas", 4);
   // el siguiente se puede anular si no queremos posibilidad corregir (reiniciar) cocina
        // vco2.defineBotonDecide(1, "¿Seguir colocando puertas o ventanas?", 3);
        vco2.defineBotonReturn(1);
       
        // ALUIS: se puede llamar al siguiente metodo, pasandole (o no) en 1º lugar un mensaje a concatenar a "Cancelar"
        vco2.defineBotonCancelar("  ¡todo el dibujo!", 1, new ActionListener(){   
            public void actionPerformed(ActionEvent e) {
                crearContenedorVacio();
            }});


        //ELEGIR FORMA ---------------------------------------------------------
        vco2.definePanel(2, "ELEGIR FORMA COCINA:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearCatalogoFormas();
                formaSeleccionada=-1;
            }}, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switch (formaSeleccionada) {
                case 1: case 2:
                    vco2.effect("forma",0);
                    break;
                case 3: case 4: case 5: case 6:
                    vco2.effect("forma",1);
                    break;
                }
                crearContenedorVacio();
            }});
        
        vco2.defineBotonMake(2, "Pinche la FORMA (de la cocina a amueblar)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (formaSeleccionada==-1) {
                    vco2.bloquear();
                    cambiarEstado("Pinche sobre la forma que tiene la cocina a dibujar, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                catalogoFormas.setEnabled(false);
            }});

        vco2.defineBotonDecide(2, "Ya ha elegido una forma, ¿quiere cambiarla?", 1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                catalogoFormas.setEnabled(true);
            }});

        vco2.defineBotonReturn(2);
        /*vco2.defineBotonCancelar(2, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorVacio();
            }});*/
            
        //seleccion para el sistema (forma==0 ? metodo0 : metodo1 )
        vco2.defineSeleccionSistema(3,"forma",9,10);            

                    
        // ESPECIFICAR MEDIDAS -------------------------------------------------
        //metodo para 2 medidas
        vco2.definePanel(9, "INTRODUCIR MEDIDAS PAREDES:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorMedidas();
                contMedidas1.activa('a');
            }});

        vco2.defineBotonMake(9, "Lado A (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getA() < 100) {
                        vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                        vco2.bloquear();
                        cambiarEstado("La medida mínima permitida es de 1 metro (100cm)");
                        contMedidas1.foco('a');
                        return;
                    } else if( contMedidas1.getA() > 600) {
                        vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                        vco2.bloquear();
                        cambiarEstado("La medida máxima permitida es de 6 metros (600cm)");
                        contMedidas1.foco('a');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado A, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('a');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                if (formaSeleccionada == 1) {
                    contMedidas1.getJTextFieldB().setText(contMedidas1.getJTextFieldA().getText()); //A=B (cuadrado)
                }
                contMedidas1.activa('b');
            }});

        vco2.defineBotonMake(9, "Lado B (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getB() < 100) {
                        vco2.bloquear();
                        cambiarEstado("La medida mínima permitida es de 1 metro (100cm)");
                        contMedidas1.foco('b');
                        return;
                    } else if( contMedidas1.getB() > 600) {
                        vco2.bloquear();
                        cambiarEstado("La medida máxima permitida es de 6 metros (600cm)");
                        contMedidas1.foco('b');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado B, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('b');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                contMedidas1.activa(' ');
            }});

        vco2.defineBotonDecide(9, "¿Corregir medidas?", 1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                contMedidas1.activa('a');
            }});

        vco2.defineBotonReturn(9, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorCocina();
                salvarDimensiones();
            }});

        vco2.defineBotonCancelar(9, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorVacio();
                vco2.desbloquear();
            }});
        
        //metodo para 4 medidas
        vco2.definePanel(10, "INTRODUCIR MEDIDAS PAREDES:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorMedidas();
                contMedidas1.activa('a');
            }});

        vco2.defineBotonMake(10, "Lado A (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getA() < 100) {
                        vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                        vco2.bloquear();
                        cambiarEstado("La medida mínima permitida es de 1 metro (100cm)");
                        contMedidas1.foco('a');
                        return;
                    } else if( contMedidas1.getA() > 600) {
                        vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                        vco2.bloquear();
                        cambiarEstado("La medida máxima permitida es de 6 metros (600cm)");
                        contMedidas1.foco('a');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado A, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('a');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                contMedidas1.activa('b');
            }});

        vco2.defineBotonMake(10, "Lado B (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getB() < 100) {
                        vco2.bloquear();
                        cambiarEstado("La medida mínima permitida es de 1 metro (100cm)");
                        contMedidas1.foco('b');
                        return;
                    } else if( contMedidas1.getB() > 600) {
                        vco2.bloquear();
                        cambiarEstado("La medida máxima permitida es de 6 metros (600cm)");
                        contMedidas1.foco('b');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado B, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('b');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                contMedidas1.activa('c');
            }});

        vco2.defineBotonMake(10, "Lado C (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getC() < 0) {
                        vco2.bloquear();
                        cambiarEstado("Esta medida no puede ser un valor negativo");
                        contMedidas1.foco('c');
                        return;
                    } else if( contMedidas1.getC() > contMedidas1.getB()) {
                        vco2.bloquear();
                        cambiarEstado("El lado C no puede ser mayor que el lado B");
                        contMedidas1.foco('c');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado C, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('c');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                contMedidas1.activa('d');
            }});

        vco2.defineBotonMake(10, "Lado D (y luego pinche aquí)", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try { //controlar errores
                    if (contMedidas1.getD() < 0) {
                        vco2.bloquear();
                        cambiarEstado("Esta medida no puede ser un valor negativo");
                        contMedidas1.foco('d');
                        return;
                    } else if( contMedidas1.getD() > contMedidas1.getA()) {
                        vco2.bloquear();
                        cambiarEstado("El lado D no puede ser mayor que el lado A");
                        contMedidas1.foco('d');
                        return;
                    }
                } catch(Exception ex) {
                    vco2.bloquear();
                    cambiarEstado("No ha introducido la medida del Lado D, o no es correcta (sólo números; sin decimales; sin espacios). Bórrela si necesario");
                    contMedidas1.foco('d');
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                contMedidas1.activa(' ');
            }});

        vco2.defineBotonDecide(10, "¿Corregir alguna medida?", 1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                contMedidas1.activa('a');
            }});

        vco2.defineBotonReturn(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorCocina();
                salvarDimensiones();
            }});

        vco2.defineBotonCancelar(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearContenedorVacio();
                vco2.desbloquear();
            }});


        //UBICAR PUERTAS Y VENTANAS --------------------------------------------
        vco2.definePanel(4, "COLOCAR PUERTAS/VENTANAS:", 'M');
        vco2.defineBotonInit(4, "Hacer cambios", 5);
        vco2.defineBotonDecide(4, "¿Más cambios?", 1);
        vco2.defineBotonMake(4,"No más puertas ni ventanas");    // ALUIS

        vco2.defineBotonReturn(4);
 // 03-feb-2015
         vco2.defineBotonCancelar(4, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //cancelar todo lo ubicado
                micocina.getForma().quitarPuertas();
                vco2.effect("puertas", micocina.numPuertas()); //estado con el numero de puertas
                contCocPla1.repaint();
            }});


        //ELEGIR UNA TAREA -----------------------------------------------------
        vco2.definePanel(5, "PUERTAS/VENTANAS:", 'S');
        vco2.defineBotonInit(5,          "Añadir", 6);
        vco2.defineBotonInitDisableIf(5, "Desplazar", 8,"puertas",0);
        vco2.defineBotonInitDisableIf(5, "Quitar", 7,"puertas",0);
// vco2.defineBotonMake(5, "¡ Hecho !");                           // 03-feb-2015
vco2.defineBotonMakeDisableIf(5, "¡ Hecho !", "puertas", 0); 
        
        //vco2.defineBotonCancelar(5);


        //COLOCAR PUERTAS/VENTANAS ---------------------------------------------
        vco2.definePanel(6, "PONER Puerta o Ventana:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crearGaleriaPuertas();
                elementoCatSeleccionado="";
                mipuerta=null;
                puertapuesta = false;
            }});

        vco2.defineBotonMake(6, "Pinche objeto a poner", new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //no seguir si no se ha seleccionado una puerta
                if (elementoCatSeleccionado.equals("")) {
                    vco2.bloquear();
                    cambiarEstado("Debe decidir qué tipo de objeto desea poner, y pinchar sobre él, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                
                //quitar la puerta puesta
                if (mipuerta != null) {
                    mipuerta.getPared().quitarPuerta(mipuerta);
                    mipuerta = null;
                }
                    
                //crear objeto Puerta
                mipuerta=new Puerta();
                mipuerta.abrir(elementoCatSeleccionado);
                //despues se ubica
                
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
                contCocPla1.setModoPonerPuerta(true);
                contCocPla1.setPuerta(mipuerta);
            }});

        vco2.defineBotonMake(6, "Pinche su lugar", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (!mipuerta.puesta() || !puertapuesta) { //no avanzar hasta que la puerta no se coloque
                    cambiarEstado("Debe de mover el ratón hasta el muro adecuado, y situar la "+(mipuerta.esPuerta()?"puerta":"ventana")+" (pinchando) sobre el lugar deseado, antes de continuar");
                    vco2.bloquear();
                    return;
                }
                contCocPla1.setModoPonerPuerta(false); //deshabilitar el raton del contenedor
                cambiarEstado("Listo");
                vco2.desbloquear();
            }});
        /*
        vco2.defineBotonDecide(6, "¿Corregir?", 1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //para que funcione el paso 1
                elementoCatSeleccionado="";
                puertapuesta = false;
                crearGaleriaPuertas();
            }});
        */
        vco2.defineBotonReturn(6, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //dejar la puerta
               vco2.effect("puertas", micocina.numPuertas()); //estado con el numero de puertas
               mipuerta=null;
               contenedorPpal1.cambiarContenedor(contCocPla1);
               contCocPla1.repaint();
           }});

        vco2.defineBotonCancelar(6, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //quitar la puerta
               if (mipuerta!=null) {
                   if (mipuerta.getPared()!=null) {
                        mipuerta.getPared().quitarPuerta(mipuerta);
                   }
                   mipuerta=null;   
               }
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoPonerPuerta(false);
               contenedorPpal1.cambiarContenedor(contCocPla1);
               contCocPla1.repaint();
           }});

        
        //QUITAR PUERTAS/VENTANAS ----------------------------------------------
        vco2.definePanel(7, "QUITAR Puerta o Ventana:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //permitir seleccionar puerta a borrar
               mipuerta=null;
                contCocPla1.setModoSeleccionarPuerta(true);
           }});

        vco2.defineBotonMake(7, "Pinche un objeto", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //obtener puerta
               if (mipuerta==null) {
                   cambiarEstado("Debe pinchar sobre la puerta o ventana que desea quitar");
                   vco2.bloquear();
                   return;
               }
               contCocPla1.setModoSeleccionarPuerta(false);
               vco2.desbloquear();
               cambiarEstado("Listo");
               mipuerta.setBorde(true);
               contCocPla1.repaint();
               
           }});

        vco2.defineBotonMake(7, "Confirmar el borrado (en caso contrario pinche el botón Cancelar)",  new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //visualizar cambios
               mipuerta.getPared().quitarPuerta(mipuerta); // es temporal
               contCocPla1.repaint();
           }});

        /* no tiene sentido el decide aqui
        vco2.defineBotonDecide(7, "¿Corregir?", 1, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //dar opcion a corregir
               mipuerta.setBorde(false);
               mipuerta.getPared().devolverPuerta(mipuerta);
               contCocPla1.setModoSeleccionarPuerta(true);
           }});*/
           
        vco2.defineBotonReturn(7, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //aplicar cambios
               vco2.effect("puertas", micocina.numPuertas()); //estado con el numero de puertas
               mipuerta = null;
           }});

        vco2.defineBotonCancelar(7, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //cancelar cambios
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoSeleccionarPuerta(false);
               if (mipuerta!=null) {
                    mipuerta.setBorde(false);
                    mipuerta.getPared().devolverPuerta(mipuerta);
               }
               
               contCocPla1.repaint();
               mipuerta=null;
           }});


        //DESPLAZAR PUERTAS/VENTANAS -------------------------------------------
        vco2.definePanel(8, "DESPLAZAR Puerta o Ventana:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //permitir seleccionar puerta a mover
               mipuerta=null;
               contCocPla1.setModoSeleccionarPuerta(true);
           }});

        vco2.defineBotonMake(8, "Pinche objeto a mover", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //obtener puerta
               if (mipuerta==null) {
                   cambiarEstado("Debe pinchar sobre la puerta o ventana que desea desplazar");
                   vco2.bloquear();
                   return;
               }
               puertaantigua = (Puerta)mipuerta.clone(); //clonar el objeto, deben de ser dos instancias diferentes
               contCocPla1.setModoSeleccionarPuerta(false);
               vco2.desbloquear();
               cambiarEstado("Listo");
               mipuerta.setBorde(false);
               contCocPla1.repaint();
               //pasar a modo poner puerta
               contCocPla1.setModoPonerPuerta(true);
               contCocPla1.setPuerta(mipuerta);
           }});

        vco2.defineBotonMake(8, "Pinche su nuevo lugar", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (!mipuerta.puesta() || !puertapuesta) { //no avanzar hasta que la puerta no se coloque
                    cambiarEstado("Debe de mover el ratón hasta el muro adecuado, y situar la "+(mipuerta.esPuerta()?"puerta":"ventana")+" (pinchando) sobre el lugar deseado, antes de continuar");
                    vco2.bloquear();
                    return;
                }
                contCocPla1.setModoPonerPuerta(false); //deshabilitar el raton del contenedor
                cambiarEstado("Listo");
                vco2.desbloquear();   
            }});
        
        //misma razon que en el panel 7
        // vco2.defineBotonDecide(8, "¿Corregir posición?", 1);
        
        vco2.defineBotonReturn(8, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //para que funcione el paso 1
                elementoCatSeleccionado="";
                puertapuesta = false;
                puertaantigua = null;
            }});

        vco2.defineBotonCancelar(8, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //dejar la puerta donde estaba
               if (mipuerta!=null) {
                   //eliminar nueva
                   if (mipuerta.getPared()!=null) {
                        mipuerta.getPared().quitarPuerta(mipuerta);
                   }
                   //poner vieja
                   puertaantigua.setBorde(false);
                   puertaantigua.getPared().devolverPuerta(puertaantigua);
                   mipuerta=null;
                   puertaantigua=null;
                   contCocPla1.repaint();
               }
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoPonerPuerta(false);
               contCocPla1.setModoSeleccionarPuerta(false);
           }});
        
        
        //AMUEBLAR -------------------------------------------------------------
        vco2.definePanel(11, "AMUEBLAR COCINA:", 'M');
        vco2.defineBotonInit(11, "Elegir tarea", 12, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
           }});

        vco2.defineBotonDecide(11, "¿Seguir amueblando?", 1);
        vco2.defineBotonMake(11,"No más muebles");
        vco2.defineBotonInitDisableIf(11, "Elegir materiales comunes", 23,"elegirmateriales", 0);
        vco2.defineBotonDecide(11, "¿Seguir amueblando?", 1, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //mostrar el presupuesto
               ContenedorPresupuesto contPresu1 = new ContenedorPresupuesto();
               contPresu1.nuevoPresupuesto(micocina);
               contenedorPpal1.cambiarContenedor(contPresu1);
           }});

        vco2.defineBotonMake(11,"Confirmar presupuesto (ya no podrá hacer más cambios)", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
           }});

        vco2.defineBotonReturn(11);
//ALUIS: ANULO BOTON CANCELAR, PORQUE ES MUY ARRIESGADO: VACIARIA TODA LA COCINA, 
        // (Y PARA ESO HAY OPC EXPLICITA ENTRE TAREAS AMUEBLADO)
        /* vco2.defineBotonCancelar(" ¡y vaciar cocina!",11, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //cancelar todos los muebles
                micocina.borrarMuebles();
                micocina.simularBorrado(false);
                
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();

                //y cancelar materiales
                micocina.setEncimera(null);
                micocina.setMaderas(null);
                micocina.setTiradores(null);

                actualizarEstadosDeMateriales(); //effects
            }});
        */

        //ELEGIR TAREA DE AMUEBLADO --------------------------------------------
        vco2.definePanel(12, "MUEBLES Y ELECTRODOMÉSTICOS:", 'S');
        vco2.defineBotonInit(12,          "Poner", 13, new ActionListener(){ // CAMBIO 15 POR 13 !!! 
            public void actionPerformed(ActionEvent e){
                listaMueblesNoDefinitivos=new ArrayList<Mueble>(); //recordar los muebles que se ponen en este subobjetivo
            }});
        vco2.defineBotonInitDisableIf(12, "Desplazar", 18, "muebles", 0);
        vco2.defineBotonInitDisableIf(12, "Girar", 21, "muebles", 0);
        vco2.defineBotonInitDisableIf(12, "Quitar", 17, "muebles", 0);  
        
        vco2.defineBotonInitDisableIf(12, "Vaciar cocina", 16, "muebles", 0);

        vco2.defineBotonInitDisableIf(12, "Elegir materiales comunes", 23, "sumamateriales", 0); //20);
        vco2.defineBotonInit(12, "Ver en 3D", 22);
        vco2.defineBotonInitDisableIf(12, "Consultar presupuesto", 19, "muebles", 0);
        vco2.defineBotonMakeDisableIf(12, "Hecho", "muebles", 0);


        //PONER MUEBLES---------------------------------------------------------
// ALUIS: ESTE DECIDE LO HE HABILITADO        
        // ESTE BUCLE PARA PONER MUEBLES DE UNO EN UNO SE HA SUPRIMIDO (NO DIRECCIONANDO AQUI) 
        // PORQUE SU 'CANCELAR' QUITABA TODOS LOS MUEBLES PUESTOS DENTRO BUCLE,
        // Y PRODUCIA "ANSIEDAD" O DUDAS EN EL USUARIO
        //  !!!!!!!!!!!!!!
        // TB LO COMENTO YA QUE NO SE REFERENCIA NUNCA
        /*
        vco2.definePanel(15, "PONER muebles", 'M');
        vco2.defineBotonInit(15,   "Poner uno", 13);
        vco2.defineBotonDecide(15, "¿Poner otro mueble?", 1);
        vco2.defineBotonReturn(15);
        vco2.defineBotonCancelar(15, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                for (Mueble m : listaMueblesNoDefinitivos) { //borrar todos esos muebles
                    micocina.quitarMueble(m);
                }
                contCocPla1.repaint();
                listaMueblesNoDefinitivos=null;
                vco2.effect("muebles", micocina.numMuebles());
            }});
        */

        //PONER MUEBLE----------------------------------------------------------
        vco2.definePanel(13, "PONER mueble:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mimueble=null;
            }});

        vco2.defineBotonInit(13, "Elegir tipo de mueble", 14);
        vco2.defineBotonMake(13, "Pinche un mueble", new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //no seguir si no se ha seleccionado un mueble
                if (elementoCatSeleccionado.equals("")) {
                    vco2.bloquear();
                    cambiarEstado("Debe decidir qué tipo de mueble desea poner, y pinchar sobre él, antes de continuar");          
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                    
                //crear objeto Mueble
                mimueble=new Mueble();
                mimueble.setCocina(micocina);
                mimueble.abrir(elementoCatSeleccionado);
                //despues se ubica
                
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
                contCocPla1.setModoPonerMueble(true);
                contCocPla1.setMueble(mimueble);
                mueblepuesto=false;
            }});
            
        vco2.defineBotonMake(13, "Pinche su lugar", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!mueblepuesto) {
                    vco2.bloquear();
                    cambiarEstado("Debe decidir y pinchar sobre el lugar donde desea poner el mueble, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
            }});

        vco2.defineBotonReturn(13, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarEstadosDeMateriales(); //effects
                listaMueblesNoDefinitivos.add(mimueble);
            }});

        vco2.defineBotonCancelar(13, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contCocPla1.setModoPonerMueble(false);
                contCocPla1.setMueble(null);
                micocina.quitarMueble(mimueble);
                mimueble=null;
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
                vco2.desbloquear();
            }});


        //ELEGIR TIPO DE MUEBLE-------------------------------------------------
        vco2.definePanel(14, "ELEGIR TIPO DE MUEBLE A PONER:", 'S', new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "";
            }}, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elementoCatSeleccionado="";
                //cargar galeria del tipo de mueble
                if (!tipoMueble.equals("")) {
                    crearGaleriaMuebles(tipoMueble);
                }
            }});

        vco2.defineBotonMake(14, "Muebles bajos", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "bajos"; //se usar√° como nombre del directorio que contiene los xml
            }});

        vco2.defineBotonMake(14, "Muebles altos", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "pared"; //se usar√° como nombre del directorio que contiene los xml
            }});

        /*vco2.defineBotonMake(14, "Armarios", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "armarios"; //se usar√° como nombre del directorio que contiene los xml
            }});*/
        vco2.defineBotonMake(14, "Electrodomésticos", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "electrodomesticos"; //se usar√° como nombre del directorio que contiene los xml
            }});

        vco2.defineBotonMake(14, "Otro mobiliario", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoMueble = "otros";
            }});

       vco2.defineBotonCancelar(14, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
            }});
            

        //BORRAR TODOS LOS MUEBLES----------------------------------------------
        vco2.definePanel(16, "VACIAR cocina:", 'M');        
        vco2.defineBotonMake(16, "Confirmar que desea quitar todos los muebles (en caso contrario pinche el botón Cancelar)", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                micocina.simularBorrado(true);
                contCocPla1.repaint();
            }});

        vco2.defineBotonReturn(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                micocina.borrarMuebles();
                micocina.simularBorrado(false);
                actualizarEstadosDeMateriales(); //effects
                contCocPla1.repaint();
            }});

        vco2.defineBotonCancelar(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                micocina.simularBorrado(false);
                contCocPla1.repaint();
            }});


       //BORRAR UN MUEBLE-------------------------------------------------------
        vco2.definePanel(17, "BORRAR mueble:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //permitir seleccionar mueble a borrar
               mimueble=null;
                contCocPla1.setModoSeleccionarMueble(true);
           }});

        vco2.defineBotonMake(17, "Elija el mueble a borrar", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //obtener mueble
               if (mimueble==null) {
                   cambiarEstado("Debe pinchar sobre el mueble que desea quitar de la cocina");
                   vco2.bloquear();
                   return;
               }
               contCocPla1.setModoSeleccionarMueble(false);
               vco2.desbloquear();
               cambiarEstado("Listo");
               mimueble.setBorde(true);
               contCocPla1.repaint();
               
           }});

        vco2.defineBotonMake(17, "Confirmar el borrado (en caso contrario pinche el botón Cancelar)",  new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //visualizar cambios
               micocina.quitarMueble(mimueble); // es temporal
               contCocPla1.repaint();
           }});

        vco2.defineBotonReturn(17, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               actualizarEstadosDeMateriales(); //effects
               mimueble = null;
           }});

        vco2.defineBotonCancelar(17, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //cancelar cambios
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoSeleccionarMueble(false);
               if (mimueble!=null) {
                    mimueble.setBorde(false);
                    micocina.devolverMueble(mimueble);
               }
               
               contCocPla1.repaint();
               mipuerta=null;
           }});
           
           
        //DESPLAZAR MUEBLES ----------------------------------------------------
        vco2.definePanel(18, "DESPLAZAR mueble:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //permitir seleccionar mueble a mover
               mimueble=null;
               contCocPla1.setModoSeleccionarMueble(true);
           }});

        vco2.defineBotonMake(18, "Pinche el mueble", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //obtener mueble
               if (mimueble==null) {
                   cambiarEstado("Debe pinchar sobre el mueble que desea desplazar");
                   vco2.bloquear();
                   return;
               }
               muebleantiguo = (Mueble)mimueble.clone(); //clonar el objeto, deben de ser dos instancias diferentes
               contCocPla1.setModoSeleccionarMueble(false);
               vco2.desbloquear();
               cambiarEstado("Listo");
               mimueble.setBorde(false);
               contCocPla1.repaint();
               //pasar a modo poner mueble
               contCocPla1.setModoPonerMueble(true);
               contCocPla1.setMueble(mimueble);    
           }});

        vco2.defineBotonMake(18, "Pinche su nuevo lugar", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (!mueblepuesto) { //no avanzar hasta que el mueble no se coloque
                    cambiarEstado("Debe pinchar sobre el nuevo lugar donde colocar el mueble, antes de continuar");
                    vco2.bloquear();
                    return;
                }
                contCocPla1.setModoPonerMueble(false); //deshabilitar el raton del contenedor
                cambiarEstado("Listo");
                vco2.desbloquear();   
            }});
        
        vco2.defineBotonReturn(18, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //para que funcione el paso 1
                elementoCatSeleccionado="";
                mueblepuesto = false;
                muebleantiguo = null;
            }});

        vco2.defineBotonCancelar(18, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //dejar el mueble donde estaba
               if (mimueble!=null) {
                   //eliminar nuevo
                   micocina.quitarMueble(mimueble);
                   //poner viejo
                   muebleantiguo.setBorde(false);
                   micocina.devolverMueble(muebleantiguo);
                   mimueble=null;
                   muebleantiguo=null;
                   contCocPla1.repaint();
               }
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoPonerMueble(false);
               contCocPla1.setModoSeleccionarMueble(false);
           }});   
           
           
        //GIRAR MUEBLES --------------------------------------------------------
        vco2.definePanel(21, "GIRAR mueble:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //permitir seleccionar mueble a mover
               mimueble=null;
               contCocPla1.setModoSeleccionarMueble(true);
           }});

        vco2.defineBotonMake(21, "Pinche el mueble", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //obtener mueble
               if (mimueble==null) {
                   cambiarEstado("Debe pinchar sobre el mueble que desea girar");
                   vco2.bloquear();
                   return;
               }
               muebleantiguo = (Mueble)mimueble.clone(); //clonar el objeto, deben de ser dos instancias diferentes
               contCocPla1.setModoSeleccionarMueble(false);
               vco2.desbloquear();
               cambiarEstado("Listo");
               mimueble.setBorde(false);
               mimueble.setAutoGirable(false);
               contCocPla1.repaint();
           }});
           
        vco2.defineBotonMake(21,"Girar (90 grados)", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                mimueble.girar(90);
                contCocPla1.repaint();
           }}); 
        
        vco2.defineBotonDecide(21, "¿Realizar otro giro?", 2);
        vco2.defineBotonMake(21, "Continuar", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                //pasar a modo poner mueble
                mueblepuesto=micocina.fijarMueble(mimueble); //false; //true
                contCocPla1.setModoPonerMueble(true);
                contCocPla1.setMueble(mimueble);
           }});
           
        vco2.defineBotonMake(21, "Pinche su nuevo lugar", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (!mueblepuesto) { //no avanzar hasta que el mueble no se coloque
                    cambiarEstado("Debe pinchar sobre el lugar donde desea poner el mueble, antes de continuar");
                    vco2.bloquear();
                    return;
                }
                contCocPla1.setModoPonerMueble(false); //deshabilitar el raton del contenedor
                cambiarEstado("Listo");
                vco2.desbloquear();   
            }});
        
        vco2.defineBotonReturn(21, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //para que funcione el paso 1
                elementoCatSeleccionado="";
                mimueble.setAutoGirable(muebleantiguo.esGirable()); //devolver la propiedad de auto-giro
                mueblepuesto = false;
                muebleantiguo = null;
            }});

        vco2.defineBotonCancelar(21, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //dejar el mueble donde estaba
               if (mimueble!=null) {
                   //eliminar nuevo
                   micocina.quitarMueble(mimueble);
                   //poner viejo
                   muebleantiguo.setBorde(false);
                   micocina.devolverMueble(muebleantiguo);
                   mimueble=null;
                   muebleantiguo=null;
                   contCocPla1.repaint();
               }
               cambiarEstado("Listo");
               vco2.desbloquear();
               contCocPla1.setModoPonerMueble(false);
               contCocPla1.setModoSeleccionarMueble(false);
           }});   
           
        
        //VER PRESUPUESTO-------------------------------------------------------
        vco2.definePanel(19, "CONSULTAR PRESUPUESTO:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //mostrar el presupuesto
               ContenedorPresupuesto contPresu1 = new ContenedorPresupuesto();
               contPresu1.nuevoPresupuesto(micocina);
               contenedorPpal1.cambiarContenedor(contPresu1);
           }});

        vco2.defineBotonReturn(19, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //mostrar la cocina
               contenedorPpal1.cambiarContenedor(contCocPla1);
               contCocPla1.repaint();
           }});
           
        //ELEGIR MATERIALES
        vco2.definePanel(23, "ELEGIR MATERIALES COMUNES:", 'M', new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //mostrar la primera galeria que se necesite
               if (micocina.metrosCuadradosDeEncimera()>0) {
                    mostrarGaleria("encimeras");
               } else if (micocina.numPuertasMuebles()>0) {
                    mostrarGaleria("maderas");
               } else if (micocina.numTiradoresMuebles()>0) {
                    mostrarGaleria("tiradores");
               } else {
                    //mostrar cocina
                    contenedorPpal1.cambiarContenedor(contCocPla1);
                    contCocPla1.repaint();
               }
           }});

        vco2.defineBotonMakeDisableIf(23, "Pinche la encimera que prefiera", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                //no seguir si no se ha seleccionado una encimera
                if (elementoCatSeleccionado.equals("")) {
                    vco2.bloquear();
                    cambiarEstado("Pinche el tipo de encimera que desea, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                    
                //crear objeto Encimera
                miencimera=new Mostrable(); //esta clase es util
                miencimera.abrir(elementoCatSeleccionado);
               
               //mostrar la siguiente galeria que se necesite
               if (micocina.numPuertasMuebles()>0) {
                    mostrarGaleria("maderas");
               } else if (micocina.numTiradoresMuebles()>0) {
                    mostrarGaleria("tiradores");
               } else {
                    //mostrar cocina
                    contenedorPpal1.cambiarContenedor(contCocPla1);
                    contCocPla1.repaint();
               }
           }}, "encimera", 0);

        vco2.defineBotonMakeDisableIf(23, "Pinche la madera que prefiera", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //no seguir si no se ha seleccionado una encimera
                if (elementoCatSeleccionado.equals("")) {
                    vco2.bloquear();
                    cambiarEstado("Pinche el tipo de material que desea para todas las puertas y cajones, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                    
                //crear objeto maderas
                mismaderas=new Mostrable(); //esta clase es util
                mismaderas.abrir(elementoCatSeleccionado);
               
               //mostrar la galeria de tiradores si se necesita
               if (micocina.numTiradoresMuebles()>0) {
                    mostrarGaleria("tiradores");
               } else {
                    //mostrar cocina
                    contenedorPpal1.cambiarContenedor(contCocPla1);
                    contCocPla1.repaint();
               }
           }}, "puertasmuebles", 0);

        vco2.defineBotonMakeDisableIf(23, "Pinche el tirador que prefiera", new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //no seguir si no se ha seleccionado un tirador
                if (elementoCatSeleccionado.equals("")) {
                    vco2.bloquear();
                    cambiarEstado("Pinche el tipo de tirador que desea para todas las puertas y cajones, antes de continuar");
                    return;
                }
                vco2.desbloquear();
                cambiarEstado("Listo");
                    
                //crear objeto maderas
                mistiradores=new Mostrable(); //esta clase es util
                mistiradores.abrir(elementoCatSeleccionado);
                catalogo.setEnabled(false);
                
                //mostrar cocina
                contenedorPpal1.cambiarContenedor(contCocPla1);
                contCocPla1.repaint();
           }},"tiradoresmuebles", 0);

        vco2.defineBotonReturn(23, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //actualizar effect si se seleccionan materiales
               vco2.effect("elegirmateriales", 0);

               micocina.setEncimera(miencimera);
               micocina.setMaderas(mismaderas);
               micocina.setTiradores(mistiradores);
           }});

        vco2.defineBotonCancelar(23, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               //mostrar la cocina
               contenedorPpal1.cambiarContenedor(contCocPla1);
               contCocPla1.repaint();
           }});


        //REALIZAR PEDIDO
        vco2.definePanel(24, "GUARDAR COCINA:", 'M', new ActionListener(){
            public void actionPerformed(ActionEvent e){
                contDatos1 = new ContenedorDatos();
                Controlador.instancia.contenedorPpal1.cambiarContenedor(contDatos1);
                contDatos1.activa('a');
            }});

        vco2.defineBotonMake(24, "Introduzca su nombre", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (contDatos1.getNombre().length() == 0) {
                        vco2.cambiarPaso(0); //es necesario desactivar el resto de botones si este paso no esta completo
                        vco2.bloquear();
                        cambiarEstado("Debe de escribir un nombre que permita identificarle");
                        contDatos1.activa('a');
                        return;
                    }
                vco2.desbloquear();
                contDatos1.activa('0');
                cambiarEstado("Listo");
            }});
        
        vco2.defineBotonDecide(24, "¿Desea modificar sus datos?",1, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                contDatos1.activa('a');
            }});

        vco2.defineBotonReturn(24, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Controlador.instancia.contenedorPpal1.cambiarContenedor(new ContenedorMensaje());
                contenedorPpal1.repaint();
                
                //Guardar objeto cocina serializado
                try {
                    String nombre = contDatos1.getNombre();
                    nombre=nombre.replaceAll(" ", "_");
                    OutputStream os = new FileOutputStream(".."+File.separator+"cocinas"+File.separator+"cocina_"+nombre+".ktch");
                    ObjectOutput oo = new ObjectOutputStream(os);
                    oo.writeObject(micocina);
                    oo.close();    
                } catch (Exception ex) {
                    System.err.println("Error de escritura en fichero");
                }
            }});


        //VER EN 3D-------------------------------------------------------------
        vco2.definePanel(22, "Ver en 3D:", 'M', new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //mostrar la cocina en 3D
                crearContenedorCocina3D(micocina);
            }});

        vco2.defineBotonMake(22, "Girar", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contCoc3D.girarDer();
                contCoc3D.girarDer();
            }});
        
        vco2.defineBotonDecide(22, "¿Otro giro?", 1);
        vco2.defineBotonReturn(22, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //mostrar la cocina en 2D
               contenedorPpal1.cambiarContenedor(contCocPla1);
               contCocPla1.repaint();
            }});
        
        //----------------------------------------------------------------------
        
        //cambios
        vco2.setListenerCambio(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contenedorPpal1.cambiarTarea(vco2.camino()); //mostrar el camino
            }});
        
        //montrar panel con el objetivo principal
        vco2.cargarPanelInicial();
    }
    
    public static Controlador getInstance(){ //devuelve la unica instancia de la clase
        if (instancia==null) {
            instancia = new Controlador();
        }
        return instancia;
    }
    
    public void mostrarVentana() { //crea la ventana con el contenedor principal
        ventana = new JFrame();
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setTitle("Adico Igo");
        contenedorPpal1 = new ContenedorPpal();
        contenedorPpal1.cambiarTarea(vco2.camino());
        ventana.add(contenedorPpal1);
        ventana.pack();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
    }
    
    public VCO2 getVco2() { //devuelve la ventana conductora de objetivos
        return vco2;
    }
    
    public ContenedorCocinaPlanta getContCocinaPlanta() { //devuelve el contenedor de la cocina en 2D
        return this.contCocPla1;
    }
    
    private void crearContenedorVacio() { //muestra un contenedor en blanco
        contVacio1 = new JPanel();
        contVacio1.setBackground(Color.WHITE);
        contenedorPpal1.cambiarContenedor(contVacio1);
    }
    
    private void crearCatalogoFormas() { //muestra formas de cocina
        catalogoFormas = new ContenedorFormas();
        //mostrar contenedor
        contenedorPpal1.cambiarContenedor(catalogoFormas);
    }
    
    public void formaClick(int num) { //gestiona el click en una forma de cocina
        formaSeleccionada = num;
        catalogoFormas.seleccionar(num);
        vco2.simularClick();
        
    }
    
    private void crearGaleriaPuertas(){ //muestra una galeria de puertas
        catalogo = new Catalogo();
        catalogo.rellenarCatalogo(".." + File.separator + "ficheros" + File.separator + "puertas" + File.separator);
        contenedorPpal1.cambiarContenedor(catalogo);
    }
    
    private void crearContenedorMedidas() { //muestra un formulario para introducir las dimensiones de la forma seleccionada
        contMedidas1 = new ContenedorMedidas();
        contMedidas1.setForma(formaSeleccionada);
        contenedorPpal1.cambiarContenedor(contMedidas1);
    }
    
    private void crearGaleriaMuebles(String tipo){ //muestra una galeria de muebles
        catalogo = new Catalogo();
        catalogo.rellenarCatalogo(".." + File.separator + "ficheros" + File.separator + "muebles" + File.separator + tipo + File.separator); //controlar excepcion
        contenedorPpal1.cambiarContenedor(catalogo);
    }
    
    private void salvarDimensiones() { //guarda las medidas de la forma
        try {
            dimA = contMedidas1.getA();
            dimB = contMedidas1.getB();
            if (formaSeleccionada > 2) {
                dimC = contMedidas1.getC();
                dimD = contMedidas1.getD();
            }

            crearCocina();
            
        } catch (Exception e) {
            contenedorPpal1.cambiarEstado("Error de dimensiones");
        }
    }
    
    private void crearContenedorCocina(){ //muestra la planta de la cocina
        contCocPla1 = new ContenedorCocinaPlanta(this);
        contenedorPpal1.cambiarContenedor(contCocPla1);
    }
    
    private void crearContenedorCocina3D(Cocina cocina){ //muestra la cocina en 3D
        contCoc3D = new ContenedorCocina3D(cocina);
        contenedorPpal1.cambiarContenedor(contCoc3D);
    }
    
    private void crearCocina(){ //se crea un objeto cocina
        //crearla
        try {
            micocina = new cocina.Cocina();
            micocina.setControlador(this);
            vco2.effect("puertas", micocina.numPuertas()); //estado con el numero de puertas
            
            actualizarEstadosDeMateriales(); //effects

            //asignar forma
            switch (formaSeleccionada) {
                case 1: case 2:
                    micocina.getForma().formaRectangular(dimA,dimB);
                    vco2.effect("forma",0);
                    break;
                case 3:
                    micocina.getForma().formaL3(dimA,dimB,dimC,dimD);
                    vco2.effect("forma",1);
                    break;
                case 4:
                    micocina.getForma().formaL4(dimA,dimB,dimC,dimD);
                    vco2.effect("forma",1);
                    break;
                case 5:
                    micocina.getForma().formaL5(dimA,dimB,dimC,dimD);
                    vco2.effect("forma",1);
                    break;
                case 6:
                    micocina.getForma().formaL6(dimA,dimB,dimC,dimD);
                    vco2.effect("forma",1);
                    break;
            }

            //asociarla al contenedor
            contCocPla1.setCocina(micocina);

        } catch(NoClassDefFoundError er) {
            JOptionPane.showMessageDialog(null, "Error al crear la cocina. Asegurese de que est√° instalado Java3D.", "AdicoIgo", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void mostrarGaleria(String galeria) { //muestra un catalogo con los elementos del directorio especificado
        //mostrar galeria
        catalogo = new Catalogo();
        catalogo.rellenarCatalogo(".." + File.separator + "ficheros"+File.separator+galeria+File.separator);
        contenedorPpal1.cambiarContenedor(catalogo);
        elementoCatSeleccionado="";
    }

    private void actualizarEstadosDeMateriales() { //Todos los effects relativos a los estados sobre materiales
        //estado con el numero de muebles
        vco2.effect("muebles", micocina.numMuebles());
        //estados relativos a materiales
        vco2.effect("encimera", micocina.metrosCuadradosDeEncimera());
        vco2.effect("puertasmuebles", micocina.numPuertasMuebles());
        vco2.effect("tiradoresmuebles", micocina.numTiradoresMuebles());
        //desactiva elegir materiales si no hace falta
        vco2.effect("sumamateriales", micocina.metrosCuadradosDeEncimera() + micocina.numPuertasMuebles() + micocina.numTiradoresMuebles());
        //desactiva elegir materiales ademas cuando ya se han elegido antes
        vco2.effect("elegirmateriales", ((micocina.metrosCuadradosDeEncimera()!=0 && micocina.getEncimera()==null) || (micocina.numPuertasMuebles()!=0 && micocina.getMaderas()==null) || (micocina.numTiradoresMuebles()!=0 && micocina.getTiradores()==null)) ? 1 : 0);
    }

    public void cambiarEstado(String estado){ //cambiar el texto de la barra de estado
        contenedorPpal1.cambiarEstado(estado);
    }
    
    public void elementoCatClick(String id) { //recibe el nombre del fichero xml
        elementoCatSeleccionado=id;
        vco2.simularClick();
    }
    
    public void paredClick() { //recibe click en la pared
        puertapuesta = true;
        contCocPla1.setModoPonerPuerta(false);
        vco2.simularClick(); //pasar al siguiente paso
    }
    
    public void puertaClick(Puerta puerta) { //recibe la puerta seleccionada
        mipuerta = puerta;
        contCocPla1.setModoSeleccionarPuerta(false);
        vco2.simularClick(); //pasar al siguiente paso
    }
    
    public void muebleClick() { //mueble puesto
        mueblepuesto = true;
        contCocPla1.setModoPonerMueble(false);
        vco2.simularClick(); //pasar al siguiente paso
    }
    
    public void muebleClick(Mueble mueble) { //recibe el mueble seleccionado
        mimueble = mueble;
        contCocPla1.setModoSeleccionarMueble(false);
        vco2.simularClick();
    }
    
    public void setMueblePuesto(boolean puesto) {
        mueblepuesto = puesto;
    }

    public void cambio() {
        //no se usa pero hay que implementarla
    }

    public void menuEmergente(int x, int y) {
        //no se usa pero hay que implementarla
    }

    public boolean isIGO() { //devuelve true si es el proyecto AdicoIGO
        return true;
    }

}
