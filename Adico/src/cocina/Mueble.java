/*
 * Mueble.java
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
import java.awt.image.*;


/**
 *
 * @author Manuel Flores Vivas
 */
public class Mueble extends DefaultHandler implements Parseable, Cloneable, Serializable, Comparable<Mueble> {
    private Cocina cocina;
    private int codigo;
    private String ruta;
    private String foto;
    private String nombre;
    private String tipo;
    private String descripcion;
    private int altura;
    private int anchura;
    private int profundidad;
    private int profundidadRinconera;
    private float precio;
    private String dibujo;
    private int distanciaSuelo = 0;
    private boolean borde;
    private boolean girable = true; //auto-giro
    private boolean necesitaEncimera = false;
    private int numPuertas = 0;
    private int numTiradores = 0;
    private boolean esRinconera = false;
    private Color colorFondo = Color.YELLOW;
    private Color colorBorde = Color.ORANGE;
    private Color colorSeleccionado = new Color(255,0,0,180);
    private boolean dibujarPuerta = false;
    
    //lugar
    private int x, y;
    private int angulo;
    
    //lectura XML
    static private Writer out;
    private int p = 0;

    public int compareTo(Mueble m2) {
            if (getCodigo()==m2.getCodigo()) {
                return 0;
            } else if (getCodigo()<m2.getCodigo()) {
                return -1;
            } else {
                return 1;
            }
        }

    /** Creates a new instance of Mueble */
    public Mueble() {
    }
    
    public void setCocina(Cocina cocina) {
        this.cocina = cocina;
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
        return (angulo==0 || angulo==180)?anchura:profundidad;
    }
    
    public int getProfundidad(){
        return (angulo==0 || angulo==180)?profundidad:anchura;
    }
    
    public int getDistanciaSuelo() {
        return distanciaSuelo;
    }
    
    public float getPrecio(){
        return precio;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getCodigo() {
        return codigo;
    }
            
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int centimetrosCuadradosDeEncimera() {
        return (necesitaEncimera)?(anchura*profundidad):0;
    }
    
    public int numPuertas() {
        return numPuertas;
    }
    
    public int numTiradores() {
        return numTiradores;
    }
    
    public boolean esRinconera() {
        return esRinconera;
    }

    public void setColorSeleccionado(Color col) {
        colorSeleccionado = col;
    }
    
    //lectura XML
    public void abrir(String fichero) {
        // Use an instance of ourselves as the SAX event handler
        DefaultHandler handler = this;
        ruta=fichero.substring(0, fichero.lastIndexOf(File.separator)+1);

        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Set up output stream
            out = new OutputStreamWriter(System.out, "UTF8");

            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(fichero), handler );
            
            if (esRinconera) {
                profundidadRinconera=profundidad;
                profundidad=anchura;
            }

            //transparencia para ver si hay un objeto debajo
            if (distanciaSuelo>0) {
                colorFondo = new Color(colorFondo.getRed(),colorFondo.getGreen(),colorFondo.getBlue(),243);
            }

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
        } else if (qName.equals("codigo")) {
            p=11;
        } else if (qName.equals("necesitaencimera")) {
            p=12;
            this.necesitaEncimera = true;
        } else if (qName.equals("puertas")) {
            p=13;
        } else if (qName.equals("tiradores")) {
            p=14;
        } else if (qName.equals("rinconera")) {
            p=15;
            this.esRinconera = true;
        } else if (qName.equals("colorborde")) {
            p=16;
        } else if (qName.equals("colorfondo")) {
            p=17;
        } else if (qName.equals("dibujarpuerta")) {
            p=18;
            this.dibujarPuerta = true;
        } else {
            p=19;
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
                anchura=Integer.parseInt(str)-2; break;
            case 7:
                profundidad=Integer.parseInt(str)-2; break;
            case 8:
                precio=Float.parseFloat(str); break;
            case 9:
                dibujo=str; break;
            case 10:
                distanciaSuelo=Integer.parseInt(str); break;
            case 11:
                codigo=Integer.parseInt(str); break;
            case 13:
                numPuertas=Integer.parseInt(str); break;
            case 14:
                numTiradores=Integer.parseInt(str); break;
            case 16:
                colorBorde=Color.decode(str); break;
            case 17:
                colorFondo=Color.decode(str);
                break;
            }
        }
    }
    
    //--------------------------------------------------------------------------
    
    public void pintarPlanta(Graphics2D g2, ImageObserver obs) { //dibuja el mueble en 2D
        if (esRinconera) {
            pintarPlantaParaRinconeras(g2, obs);
        } else {
            pintarPlantaParaMuebles(g2, obs);
        }
    }
    
    private void pintarPlantaParaMuebles(Graphics2D g2, ImageObserver obs) { //dibuja el mueble en 2D
        g2.translate(x, y);
        g2.rotate(angulo*Math.PI/180);
        g2.setStroke(new BasicStroke(2));
        
        g2.setColor(colorFondo);
        g2.fillRect(-anchura/2, -profundidad/2, anchura, profundidad);
        
        g2.setColor(colorBorde);
        g2.drawRect(-anchura/2, -profundidad/2, anchura, profundidad);           
        if (this.dibujarPuerta) { //dibujamos un segundo filo
            g2.drawRect(-anchura/2, +profundidad/2-6, anchura, 6);
        }
        
        //cargar grafico asociado
        if (this.dibujo != null) {
            Image img = Toolkit.getDefaultToolkit().getImage(this.ruta+this.dibujo);
            g2.drawImage(img, -anchura/2,-profundidad/2,anchura, profundidad,obs);
        }
                
        //pintar borde de seleccion
        if (borde) {
                g2.setColor(colorSeleccionado);
                g2.fillRect(-anchura/2-2, -profundidad/2-2, anchura+2, profundidad+2);
        }
        g2.rotate(-angulo*Math.PI/180);
        g2.translate(-x, -y);
    }

    private void pintarPlantaParaRinconeras(Graphics2D g2, ImageObserver obs) { //dibuja la rinconera en 2D
        g2.translate(x, y);
        g2.rotate(angulo*Math.PI/180);
        g2.setStroke(new BasicStroke(2));
        
        g2.setColor(colorFondo);
        g2.fillRect(-anchura/2, -profundidad/2, anchura, profundidadRinconera);
        g2.fillRect(-anchura/2, -profundidad/2 + profundidadRinconera, profundidadRinconera, profundidad - profundidadRinconera);
        
        g2.setColor(colorBorde);
                
        g2.drawLine(-anchura/2, -profundidad/2, -anchura/2, profundidad/2);
        g2.drawLine(-anchura/2, profundidad/2, -anchura/2+profundidadRinconera, profundidad/2);
        g2.drawLine(-anchura/2+profundidadRinconera, profundidad/2, -anchura/2+profundidadRinconera, -profundidad/2+profundidadRinconera);
        g2.drawLine(-anchura/2+profundidadRinconera, -profundidad/2+profundidadRinconera, anchura/2, -profundidad/2+profundidadRinconera);
        g2.drawLine(anchura/2, -profundidad/2+profundidadRinconera, anchura/2, -profundidad/2);
        g2.drawLine(-anchura/2, -profundidad/2, anchura/2, -profundidad/2);
        
        if (this.dibujarPuerta) { //dibujamos un segundo filo
            g2.drawLine(-anchura/2+profundidadRinconera-6, profundidad/2, -anchura/2+profundidadRinconera-6, -profundidad/2+profundidadRinconera-6);
            g2.drawLine(-anchura/2+profundidadRinconera-6, -profundidad/2+profundidadRinconera-6, anchura/2, -profundidad/2+profundidadRinconera-6);
        }
        
        //cargar grafico asociado
        if (this.dibujo != null) {
            Image img = Toolkit.getDefaultToolkit().getImage(this.ruta+this.dibujo);
            g2.drawImage(img, -anchura/2,-profundidad/2,anchura, profundidad,obs);
        }
                
        //pintar borde de seleccion
        if (borde) {
                g2.setColor(colorSeleccionado);
                g2.fillRect(-anchura/2-2, -profundidad/2-2, anchura+2, profundidadRinconera+2);
                g2.fillRect(-anchura/2-2, -profundidad/2+profundidadRinconera, profundidadRinconera+2, profundidad-profundidadRinconera+1);
        }
        g2.rotate(-angulo*Math.PI/180);
        g2.translate(-x, -y);
        
    }

    
    public Rectangle getRectangle () { //rectangulo ligeramente superior
        return getRectangle(6);
    }
    
    public Rectangle getRectangle (int cota) { //rectangulo con un borde extra
        int anchura = this.anchura;
        int profundidad = this.profundidad;
        if (angulo==90 || angulo==270) { //rotar
            anchura=this.profundidad;
            profundidad=this.anchura;
        }
        
        return new Rectangle(x-anchura/2-cota, y-profundidad/2-cota, anchura+2*cota, profundidad+2*cota);
    }
    
    public void setBorde(boolean borde) { //activar el borde para coloreado de seleccion
        this.borde=borde;
    }
    
    public boolean intersects (Puerta puerta) { //si colisiona con una puerta
        if ((getDistanciaSuelo()==0)&&(puerta.esVentana())) { //dejamos poner muebles que van en el suelo delante de ventanas, pero no muebles altos
            return false;
        } else { //resto de los casos (de muebles y para puertas)
            if (puerta.esVentana()) {
                return getRectangle().intersects(puerta.getRectangle()) &&  distanciaSuelo < puerta.getDistanciaSuelo()+puerta.getAltura() && distanciaSuelo+altura > puerta.getDistanciaSuelo() ;
            } else {
                return getRectangle().intersects(puerta.getRectangleUtil()) &&  distanciaSuelo < puerta.getDistanciaSuelo()+puerta.getAltura() && distanciaSuelo+altura > puerta.getDistanciaSuelo() ;
            }
        }        
    }
    
    public boolean intersects (Mueble mueble) { //si colisiona con otro mueble
        return getRectangle().intersects(mueble.getRectangle(-4)) && distanciaSuelo < mueble.getDistanciaSuelo()+mueble.getAltura() && distanciaSuelo+altura > mueble.getDistanciaSuelo();
    }
    
    protected boolean iman(Mueble mueble) {
        int anchura = this.anchura;
        int profundidad = this.profundidad;
        boolean resultado = false;
        
        if (angulo==90 || angulo==270) { //rotar
            anchura=this.profundidad;
            profundidad=this.anchura;
        }
        
        int cota=40;

        //solamente si se van a tocar (si estan a diferente altura no)
        if (distanciaSuelo < mueble.getDistanciaSuelo()+mueble.getAltura() && distanciaSuelo+altura > mueble.getDistanciaSuelo()) {
            
            //traerselo de arriba hacia abajo
            if ((mueble.getY()+ mueble.getProfundidad()/2 < y-profundidad/2+cota) && (mueble.getY()+ mueble.getProfundidad()/2 + cota > y-profundidad/2)) {
                if ((mueble.getX() + mueble.getAnchura()/2 > x - anchura/2) && (mueble.getX() - mueble.getAnchura()/2 < x + anchura/2)) {
                    mueble.setY((int)Math.ceil(y-(double)profundidad/2-(double)mueble.getProfundidad()/2-2));
                    resultado=true;
                }
            }

            //traerselo de abajo hacia arriba
            if ((mueble.getY()- mueble.getProfundidad()/2 > y+profundidad/2-cota) && (mueble.getY()- mueble.getProfundidad()/2 - cota < y+profundidad/2)) {
                if ((mueble.getX() + mueble.getAnchura()/2 > x - anchura/2) && (mueble.getX() - mueble.getAnchura()/2 < x + anchura/2)) {
                    mueble.setY((int)Math.ceil(y+(double)profundidad/2+(double)mueble.getProfundidad()/2+2));
                    resultado=true;
                }
            }

            //traerselo de izquierda a derecha
            if ((mueble.getX()+ mueble.getAnchura()/2 < x-anchura/2+cota) && (mueble.getX()+ mueble.getAnchura()/2 + cota > x-anchura/2)) {
                if ((mueble.getY() + mueble.getProfundidad()/2 > y - profundidad/2) && (mueble.getY() - mueble.getProfundidad()/2 < y + profundidad/2)) {
                    mueble.setX((int)Math.ceil(x-(double)anchura/2-(double)mueble.getAnchura()/2-2));
                    resultado=true;
                }
            }

            //traerselo de derecha a izquierda
            if ((mueble.getX()- mueble.getAnchura()/2 > x+anchura/2-cota) && (mueble.getX()- mueble.getAnchura()/2 - cota < x+anchura/2)) {
                if ((mueble.getY() + mueble.getProfundidad()/2 > y - profundidad/2) && (mueble.getY() - mueble.getProfundidad()/2 < y + profundidad/2)) {
                    mueble.setX((int)Math.ceil(x+(double)anchura/2+(double)mueble.getAnchura()/2+2));
                    resultado=true;
                }
            }
        }

        return resultado;
    }
    
    public void setAngulo(int angulo) {
        if (angulo==0 || angulo ==90 || angulo ==180 || angulo == 270) {
            this.angulo = angulo;
        }
    }

    public int getAngulo() {
        return angulo;
    }
    public void girar(int angulo) {
        if (angulo==0 || angulo ==90 || angulo ==180 || angulo == 270) {
            this.angulo = (this.angulo+angulo)%360;
        }
    }
    
    public void setAutoGirable(boolean girable) {
        this.girable = girable;
    }
    
    public boolean esGirable() {
        return this.girable;
    }
    
    public boolean contains(int xx, int yy) {
        if (esRinconera) {
            int cota=6;
            Rectangle subrect1 = new Rectangle();
            int distancia = profundidad-profundidadRinconera+2*cota;
            int x0 = x-anchura/2-cota;
            int y0 = y-profundidad/2-cota;
            int x1= x-anchura/2+profundidadRinconera+cota;
            int y1 = y-profundidad/2+profundidadRinconera+cota;
            //creamos un rectangulo con la zona que no ocupa fisicamente en funcion del angulo
            switch(angulo) {
                case 0:
                    subrect1 = new Rectangle(x1, y1, distancia, distancia);
                    break;
                case 90:
                    subrect1 = new Rectangle(x0, y1, distancia, distancia);
                    break;                    
                case 180:
                    subrect1 = new Rectangle(x0, y0, distancia, distancia);
                    break;
                case 270:
                    subrect1 = new Rectangle(x1, y0, distancia, distancia);
                    break;                    
                    
            }
            
            //si el punto esta dentro del rectangulo ppal pero no del subrectangulo, estara en la zona que ocupa fisicamente
            return (getRectangle().contains(xx,yy) && !subrect1.contains(xx,yy));
        } else {
            //para los muebles rectangulares usamos directamente el metodo getRectangle
            return getRectangle().contains(xx, yy);
        }
    }
    
    //añade la pared al BranchGroup
    public void pintar3D(BranchGroup bg) {
        //dibujar puerta
        //establecer apariencia de la caja
        Appearance app = new Appearance();
        Material mat = new Material();
        
        mat.setDiffuseColor(new Color3f((float)colorFondo.getRed()/255,(float)colorFondo.getGreen()/255,(float)colorFondo.getBlue()/255));
        mat.setSpecularColor(new Color3f((float)colorFondo.getRed()/255,(float)colorFondo.getGreen()/255,(float)colorFondo.getBlue()/255));   

        mat.setShininess(5.0f);
        app.setMaterial(mat);

        if (!esRinconera) { //Muebles rectangulares, se modelan como una caja

            Box box = new Box(anchura3D()/2f, altura3D()/2f, profundidad3D()/2f, app); //caja con la anchura de la pared

            //ubicar
            Transform3D translate = new Transform3D();
            translate.setTranslation(new Vector3f(x3D(),altura3D()/2f-1.1f+distanciaSuelo3D(),y3D()));

            Transform3D rotate = new Transform3D();
            rotate.rotY((angulo==90 || angulo==270)?Math.PI/2:0);
            translate.mul(rotate);

            TransformGroup tg = new TransformGroup(translate); //crear TG
            tg.addChild(box); //añadir la caja al TG
            bg.addChild(tg); //añadirlo al BG

        } else { //Rinconeras, se modelan como dos cajas superpuestas

            Box box1 = new Box(anchura3D()/2f, altura3D()/2f, profundidadRinconera3D()/2f, app); //caja con una parte de la rinconera
            Box box2 = new Box(profundidadRinconera3D()/2f, altura3D()/2f, profundidad3D()/2f, app); //caja con la otra parte de la rinconera

            //ubicar en x,y, altura
            Transform3D translate1 = new Transform3D();
            translate1.setTranslation(new Vector3f(x3D(),altura3D()/2f-1.1f+distanciaSuelo3D(),y3D()));

            Transform3D translate2 = new Transform3D();
            translate2.setTranslation(new Vector3f(x3D(),altura3D()/2f-1.1f+distanciaSuelo3D(),y3D()));

            //girar
            Transform3D rotate = new Transform3D();
            rotate.rotY(-(angulo/90)*Math.PI/2);

            translate1.mul(rotate);
            translate2.mul(rotate);

            //desplazar las dos cajas desde el centro (x,y) para conseguir la forma de la rinconera
            Transform3D translate1_2 = new Transform3D();
            translate1_2.setTranslation(new Vector3f(0,0,-(anchura3D()-profundidadRinconera3D())/2f));

            Transform3D translate2_2 = new Transform3D();
            translate2_2.setTranslation(new Vector3f(-(anchura3D()-profundidadRinconera3D())/2f,0,0));

            translate1.mul(translate1_2);
            translate2.mul(translate2_2);

            TransformGroup tg1 = new TransformGroup(translate1); //crear TG
            TransformGroup tg2 = new TransformGroup(translate2); //crear TG

            tg1.addChild(box1); //añadir la caja al TG
            tg2.addChild(box2); //añadir la caja al TG
            bg.addChild(tg1); //añadirlo al BG
            bg.addChild(tg2); //añadirlo al BG

        }
    }
    
    //funciones auxiliares para el dibujo en 3D
    private float anchura3D() {
        return (float)(anchura+1)/100;
    }
    
    private float altura3D() {
        return (float)altura/100;
    }
    
    private float profundidad3D() {
        return (float)(profundidad+1)/100;
    }
    
    private float distanciaSuelo3D() {
        return (float)distanciaSuelo/100;
    }

    private float profundidadRinconera3D() {
        return (float)(profundidadRinconera+1)/100;
    }
    
    private float x3D() {
        return (float)x/100;
    }
    
    private float y3D() {
        return (float)y/100;
    }
}
