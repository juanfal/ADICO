/*
 * Puerta.java
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

import java.awt.*;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.Box;
import javax.vecmath.*;

/**
 *
 * @author Manuel Flores Vivas
 */
public class Puerta extends DefaultHandler implements Parseable, Cloneable, Serializable {
    private String foto;
    private String nombre;
    private String tipo;
    private String descripcion;
    private int altura;
    private int anchura;
    private int profundidad;
    private float precio;
    private String dibujo;
    private int distanciaSuelo=0;
    private boolean esPuerta;
    private boolean borde;
    private boolean cotas;
    private Color colorSeleccionado = new Color(255,0,0,180);
    
    //lugar
    private Pared pared;
    private int x, y;
    
    //lectura XML
    static private Writer out;
    private int p = 0;
    
    /** Creates a new instance of Puerta */
    public Puerta() {
    }
    
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        }catch(CloneNotSupportedException ex){
            System.err.println("No se puede clonar el objecto");
        }
        
        return obj;

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
    
    public int getDistanciaSuelo() {
        return distanciaSuelo;
    }
    
    public float getPrecio(){
        return precio;
    }
    
    public boolean esPuerta() {
        return esPuerta;
    }
    
    public boolean esVentana() {
        return !esPuerta;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }    
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setPared(Pared pared) {
        this.pared=pared;
    }

    public void setColorSeleccionado(Color col) {
        colorSeleccionado = col;
    }

    //lectura XML
    public void abrir(String fichero) {
        // Use an instance of ourselves as the SAX event handler
        DefaultHandler handler = this; //new Encimera();

        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Set up output stream
            out = new OutputStreamWriter(System.out, "UTF8");

            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(fichero), handler );

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
        } else if (qName.equals("dibujo")) {
            p=9;
        } else if (qName.equals("distancia")) {
            p=10;
        } else if (qName.equals("puerta")){
            esPuerta=true;
            p=11;
        } else if (qName.equals("ventana")){
            esPuerta=false;
            p=12;
        } else {
            p=13;
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
                dibujo=str; break;
            case 10:
                distanciaSuelo=Integer.parseInt(str); break;
            }
        }
    }
    
    //--------------------------------------------------------------------------
    
    public void pintarPlanta(Graphics2D g2) { //dibuja la puerta en 2D
        g2.setStroke(new BasicStroke(2));
        if (pared.esVertical()){
            g2.setColor(new Color(230,230,230));
            g2.fillRect(x-7, y-anchura/2, 13, anchura);
            g2.setColor(esPuerta()?Color.ORANGE:Color.CYAN);
            g2.drawRect(x-7, y-anchura/2, 13, anchura);
            if (esPuerta()) {
                g2.drawLine(x-7, y-anchura/2+4, x+6, y-anchura/2+4);
                g2.drawLine(x-7, y+anchura/2-4, x+6, y+anchura/2-4);
                
                //pintar puerta abierta
                //g2.drawRect(x+7, y+anchura/2-7, anchura-6, 6);
                //g2.setColor(Color.DARK_GRAY);
                //g2.drawArc(x-anchura+11, y-anchura/2+4, 2*(anchura-5), 2*(anchura-5), 3, 89);
                
                //label
                g2.translate(x+12, y-anchura/2+5);
                g2.rotate(Math.PI/2);
                g2.drawString("puerta", 0,0);
                g2.rotate(-Math.PI/2);
                g2.translate(-x-12, -y+anchura/2-5);
            } else {
                g2.drawRect(x, y-anchura/2, 6, anchura);
                g2.drawLine(x-7, y, x+6, y);
                
                //label
                g2.translate(x+12, y-anchura/2+5);
                g2.rotate(Math.PI/2);
                g2.drawString("ventana", 0,0);
                g2.rotate(-Math.PI/2);
                g2.translate(-x-12, -y+anchura/2-5);
            }
            
            //linea con medida
            Point pa, pb;
            if (pared.getP1().getY()<pared.getP2().getY()) {
                pa=pared.getP1();
                pb=pared.getP2();
            } else {
                pa=pared.getP2();
                pb=pared.getP1();
            }
            
            if (cotas) {
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.BLACK);
                g2.drawLine((int)pa.getX(), (int)pa.getY(), (int)pa.getX()+30, (int)pa.getY()); //medida1
                g2.drawLine(x+6, y-anchura/2,x+30,y-anchura/2);
                g2.drawLine((int)pa.getX()+25, (int)pa.getY()-5, x+25,y-anchura/2+5);
                    g2.drawLine((int)pa.getX()+30, (int)pa.getY()-5, (int)pa.getX()+20, (int)pa.getY()+5);
                    g2.drawLine(x+30,y-anchura/2-5, x+20,y-anchura/2+5);

                    int medida1= y-anchura/2-(int)pa.getY();
                    g2.translate((int)pa.getX()+28, (int)pa.getY()+medida1/2-20);
                    g2.rotate(Math.PI/2);
                    g2.drawString(Integer.toString(medida1)+" cm", 0,0);
                    g2.rotate(-Math.PI/2);
                    g2.translate(-(int)pa.getX()-28, -(int)pa.getY()-medida1/2+20);

                g2.drawLine(x+6, y+anchura/2,x+30,y+anchura/2); //medida2
                g2.drawLine((int)pb.getX(), (int)pb.getY(), (int)pb.getX()+30, (int)pb.getY());
                g2.drawLine(x+25,y+anchura/2-5,(int)pb.getX()+25, (int)pb.getY()+5);
                    g2.drawLine((int)pb.getX()+30, (int)pb.getY()-5,(int)pb.getX()+20, (int)pb.getY()+5);
                    g2.drawLine(x+30,y+anchura/2-5,x+20,y+anchura/2+5);

                    int medida2= (int)pb.getY()-y-anchura/2;
                    g2.translate((int)pa.getX()+28, y+anchura/2+medida2/2-20);
                    g2.rotate(Math.PI/2);
                    g2.drawString(Integer.toString(medida2)+" cm", 0, 0);
                    g2.rotate(-Math.PI/2);
                    g2.translate(-(int)pa.getX()-28, -y-anchura/2-medida2/2+20);
            }
            
        } else { //horizontal
            g2.setColor(new Color(220,220,220));
            g2.fillRect(x-anchura/2, y-7, anchura, 13);
            g2.setColor(esPuerta()?Color.ORANGE:Color.CYAN);
            g2.drawRect(x-anchura/2, y-7, anchura, 13);
            if (esPuerta()) {
                g2.drawLine(x-anchura/2+4, y-7, x-anchura/2+4, y+6);
                g2.drawLine(x+anchura/2-4, y-7, x+anchura/2-4, y+6);
                
                //label
                g2.drawString("puerta", x-anchura/2+5, y-12);
            } else {
                g2.drawRect(x-anchura/2, y, anchura, 6);
                g2.drawLine(x, y-7, x, y+6);
                
                //label
                g2.drawString("ventana", x-anchura/2+5, y-12);
            }
            
            //linea con medida
            Point pa, pb;
            if (pared.getP1().getX()<pared.getP2().getX()) {
                pa=pared.getP1();
                pb=pared.getP2();
            } else {
                pa=pared.getP2();
                pb=pared.getP1();
            }
            
            if (cotas) {
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.BLACK);
                g2.drawLine((int)pa.getX(), (int)pa.getY(), (int)pa.getX(), (int)pa.getY()-30); //medida1
                g2.drawLine(x-anchura/2, y-7,x-anchura/2,y-30);
                g2.drawLine((int)pa.getX()-5, (int)pa.getY()-25, x-anchura/2+5,y-25);
                    g2.drawLine((int)pa.getX()+5, (int)pa.getY()-30,(int)pa.getX()-5, (int)pa.getY()-20);
                    g2.drawLine(x-anchura/2+5,y-30,x-anchura/2-5,y-20);

                    int medida1= x-anchura/2-(int)pa.getX();
                    g2.drawString(Integer.toString(medida1)+" cm", (int)pa.getX()+medida1/2-20, (int)pa.getY()-28);

                g2.drawLine(x+anchura/2, y-7,x+anchura/2,y-30); //medida2
                g2.drawLine((int)pb.getX(), (int)pb.getY(), (int)pb.getX(), (int)pb.getY()-30);
                g2.drawLine(x+anchura/2-5,y-25,(int)pb.getX()+5, (int)pb.getY()-25);
                    g2.drawLine((int)pb.getX()+5, (int)pb.getY()-30,(int)pb.getX()-5, (int)pb.getY()-20);
                    g2.drawLine(x+anchura/2+5,y-30,x+anchura/2-5,y-20);

                    int medida2= (int)pb.getX()-x-anchura/2;
                    g2.drawString(Integer.toString(medida2)+" cm", x+anchura/2+medida2/2-20, (int)pb.getY()-28);
            }
        }
        
        //pintar borde de seleccion
        if (borde) {
            if (pared.esVertical()){
                g2.setColor(colorSeleccionado);
                g2.fillRect(x-9, y-anchura/2-2, 17, anchura+4);
            } else {
                g2.setColor(colorSeleccionado);
                g2.fillRect(x-anchura/2-2, y-9, anchura+4, 17);
            }
        }
    }
    
    public boolean puesta() { //si esta puesta en alguna pared
        return (pared!=null);
    }
    
    public Pared getPared() { //pared asociada
        return pared;
    }
    
    public Rectangle getRectangle () { //rectangulo ligeramente superior
        int cota = 5; //margen
        
        if (pared.esVertical()) {
            return new Rectangle(x-7-cota, y-anchura/2-cota, 14+2*cota, anchura+2*cota);
        } else {
            return new Rectangle(x-anchura/2-cota, y-7-cota, anchura+2*cota, 14+2*cota);
        }
    }
    
    public Rectangle getRectangleUtil() { //rectangulo de la zona util de una puerta o ventana
        int cota = 0; //margen
        int cotaDi = 40; //margen
        
        if (pared.esVertical()) {
            return new Rectangle(x-7-cotaDi, y-anchura/2-cota, 14+2*cotaDi, anchura+2*cota);
        } else {
            return new Rectangle(x-anchura/2-cota, y-7-cotaDi, anchura+2*cota, 14+2*cotaDi);
        }
    }
    
    public void setBorde(boolean borde) { //activar el borde para coloreado de seleccion
        this.borde=borde;
    }
    
    public void setCotas(boolean cotas) { //activar las cotas
        this.cotas=cotas;
    }
    
    public boolean intersects (Puerta puerta) { //si colisiona con otra puerta
        return getRectangle().intersects(puerta.getRectangle());
    }
    
    //añade la pared al BranchGroup
    public void pintar3D(BranchGroup bg) {
        //dibujar puerta
        //establecer apariencia de la caja
        Appearance app = new Appearance();
        Material mat = new Material();
        if (this.esPuerta) {
            mat.setDiffuseColor(new Color3f(1,.1f,0));
            mat.setSpecularColor(new Color3f(1,.1f,0));   
        } else {
            mat.setDiffuseColor(new Color3f(0,.05f,1));
            mat.setSpecularColor(new Color3f(0,.05f,1));
        }
        mat.setShininess(5.0f);
        app.setMaterial(mat);

        Box box = new Box(anchura3D()/2f, altura3D()/2f, 0.12f, app); //caja con la anchura de la pared
        
        //ubicar
        Transform3D translate = new Transform3D();
        translate.setTranslation(new Vector3f(x3D(),altura3D()/2f-1.1f+distanciaSuelo3D(),y3D()));
        
        Transform3D rotate = new Transform3D();
        rotate.rotY(getPared().esVertical()?Math.PI/2:0);
        translate.mul(rotate);

        TransformGroup tg = new TransformGroup(translate); //crear TG
        tg.addChild(box); //añadir la caja al TG
        bg.addChild(tg); //añadirlo al BG
    }
    
    //funciones auxiliares para el dibujo en 3D
    private float anchura3D() {
        return (float)anchura/100;
    }
    
    private float altura3D() {
        return (float)altura/100;
    }
    
    private float distanciaSuelo3D() {
        return (float)distanciaSuelo/100;
    }
    
    private float x3D() {
        return (float)x/100;
    }
    
    private float y3D() {
        return (float)y/100;
    }
    
}
