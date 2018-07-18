/*
 * Mostrable.java
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

package cocina;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

/**
 *
 * @author Manuel Flores Vivas
 */

public class Mostrable extends DefaultHandler implements Parseable, Serializable {
    private int codigo;
    private String fichero;
    private String foto;
    private String nombre;
    private String tipo;
    private String descripcion;
    private int altura;
    private int anchura;
    private int profundidad;
    private float precio;

    //lectura XML
    static private Writer out;
    private int p = 0;
    
    /** Creates a new instance of Encimera */
    public Mostrable() {
    }
    
    public String getFichero(){
        return fichero;
    }
    
    public String getFoto(){
        return foto;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public int getAltura(){
        return altura;
    }
    
    public int getAnchura(){
        return anchura;
    }
    
    public int getProfundidad(){
        return profundidad;
    }
    
    public float getPrecio(){
        return precio;
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    //lectura XML
    public void abrir(String fichero) {
        // Use an instance of ourselves as the SAX event handler
        DefaultHandler handler = this;

        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Set up output stream
            out = new OutputStreamWriter(System.out, "UTF8");

            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(fichero), handler );

            this.fichero=fichero;

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("foto")) {
            p=1;
        } else if (qName.equals("nombre")) {
            p=2;
        } else if (qName.equals("tipo")) {
            p=3;
        } else if (qName.equals("descripcion")) {
            p=4;
        } else if (qName.equals("altura")) {
            p=5;
        } else if (qName.equals("anchura")) {
            p=6;
        } else if (qName.equals("profundidad")) {
            p=7;
        } else if (qName.equals("precio")) {
            p=8;
        } else if (qName.equals("codigo")){
            p=9;
        } else {
            p=10;
        }
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String str = new String(buf, offset, len);
        if (!str.trim().equals("")) {
            switch (p) {
            case 1:
                foto=str; break;
            case 2:
                nombre=str; break;
            case 3:
                tipo=str; break;
            case 4:
                descripcion=str; break;
            case 5:
                altura=Integer.parseInt(str); break;
            case 6:
                anchura=Integer.parseInt(str); break;
            case 7:
                profundidad=Integer.parseInt(str); break;
            case 8:
                precio=Float.parseFloat(str); break;
            case 9:
                codigo=Integer.parseInt(str); break;
            
            }
        }
    }
    
}