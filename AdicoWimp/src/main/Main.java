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
public class Main {
    public static void main(String [] args) {
        //mostrar ventana wimp
        controlador.Controlador cont = Controlador.getInstance();
        cont.mostrarVentana();
    }
}
