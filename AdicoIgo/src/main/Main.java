/*
 * Main.java
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

package main;

import controlador.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Main{

    static private int md = 0;
    static private boolean autoclick=true;

    //metodos estaticos para acceder a los argumentos
    public static int getModoDecide() {
        return md;
    }

    public static boolean getAutoClick() {
        return autoclick;
    }
        
    public static void main (String args[]) {
        //vbles
        String msgerror = "Argumentos: [--noautoclick] [<modoDecide>]\n\n --noautoclick No se pulsa el boton automaticamente al realizar ciertas tareas en la ventana de trabajo\n <modoDecide>  Modo del Decide en la Ventana Conductora de Objetivos\n\t\t1=Con boton Decide\n\t\t2=Mensaje en estado\n\t\t3=Mensaje en ventana emergente\n\t\t4=Mensaje incrustado en VCO\n\t\t5=Mensaje incrustado siempre visible";

        if (args.length > 2) { //mas de dos argumentos
            System.out.println (msgerror);
            return;
        }

        if (args.length>=1) { //primer argumento
            if (args[0].compareToIgnoreCase("--noautoclick") == 0) { //noautoclick
                autoclick=false;
            } else {
                try {
                    md = Integer.parseInt(args[0]); //mododecide
                    if (md<1 || md>5) {
                        System.out.println (msgerror);
                        return;
                    }
                } catch (Exception e) {
                    System.out.println (msgerror);
                    return;
                }
            }
        }

        if (args.length==2) { //segundo argumento
            if (md!=0) { //si el primer argumento era modoDecide
                if (args[1].compareToIgnoreCase("--noautoclick") == 0) { //noautoclicl
                    autoclick=false;
                } else {
                        System.out.println (msgerror);
                        return;
                }
            } else { //en otro caso
                try {
                    md = Integer.parseInt(args[1]); //mododecide
                    if (md<1 || md>5) {
                        System.out.println (msgerror);
                        return;
                    }
                } catch (Exception e) {
                    System.out.println (msgerror);
                    return;
                }
            }
        }

        //iniciar
        Controlador cont = Controlador.getInstance();
        cont.mostrarVentana();
    }
    
}
