/*
 * InterfazControlador.java
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

/**
 *
 * @author Manuel Flores Vivas
 */

package controlador;

import vista.*;
import cocina.*;

public interface InterfazControlador {
    public ContenedorCocinaPlanta getContCocinaPlanta();
    
    public void cambiarEstado(String estado); //cambiar el texto de la barra de estado
    
    public boolean isIGO(); //devuelve true si es el contenedor del proyecto IGO (falso si es el contenedor de WIMP)
    
    public void paredClick(); //recibe click en la pared
    
    public void puertaClick(Puerta puerta); //recibe la puerta seleccionada
    
    public void muebleClick(); //mueble puesto
    
    public void muebleClick(Mueble mueble); //recibe el mueble seleccionado
    
    public void setMueblePuesto(boolean puesto);

    public void cambio();

    public void menuEmergente(int x, int y);
}
