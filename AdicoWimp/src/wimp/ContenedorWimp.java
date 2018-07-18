/*
 * ContenedorWimp.java
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

package wimp;

import java.awt.*;
import javax.swing.*;
import controlador.*;

/**
 *
 * @author  Manuel Flores Vivas
 */
public class ContenedorWimp extends javax.swing.JFrame {
    //Variables
    private JPanel panelactual; //panel central
    private JPanel panellateral; //panel lateral
    private Controlador controlador; //controlador
    
    /** Creates new form contenedorWimp */
    public ContenedorWimp(Controlador controlador) {
        initComponents();
        this.controlador=controlador;
        
        //cambiar a heavywheight los jmenu para que se puedan ver encima del canvas 3d
        jMenuArchivo.getPopupMenu().setLightWeightPopupEnabled(false);
        jMenuEdicion.getPopupMenu().setLightWeightPopupEnabled(false);
        jMenuGirar.getPopupMenu().setLightWeightPopupEnabled(false);
        jMenuVer.getPopupMenu().setLightWeightPopupEnabled(false);
        jMenuCocina.getPopupMenu().setLightWeightPopupEnabled(false);
        jMenuAyuda.getPopupMenu().setLightWeightPopupEnabled(false);

        //y los tooltips tambien, para que salgan sobre el canvas3d
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

        this.remove(estado);
    }
    
    //cambia el panel central
    public void cambiarContenedor(JPanel nuevo) {
        if (panelactual==null) {
            panelactual=panelPpal;
        }
        this.remove(panelactual); //quitar anterior
        panelactual=nuevo;
        this.add(nuevo, BorderLayout.CENTER); //añadir nuevo
        barraMenu.setFocusable(true); //era true, pero aseguramos que funcionen los atajos de teclado
    }

    //cambia el panel central
    public void cambiarPanelLateral(JPanel nuevo) {
        if (panellateral!=null) {
            this.remove(panellateral); //quitar anterior
        }
        
        panellateral=nuevo;
        if (nuevo!=null) {
            this.add(nuevo, BorderLayout.WEST); //añadir nuevo
        }
    }
    
    //cambiar el texto de la barra de estado
    public void cambiarEstado(String estado) {
        try {
            ((vista.InterfazBarraEstado)panelactual).cambiarEstado(estado);
        } catch (Exception e) {
            //el panel no implementa la interfaz
        }
    }

    public void botonArrastrar(boolean estado) {
        this.jToggleButtonArrastrar.setSelected(estado);
    }

    public void setEnabledCocina(boolean estado) {
        jMenuGuardar.setEnabled(estado);
        jMenuGuardarComo.setEnabled(estado);
        //jMenuBorrarTodos.setEnabled(estado);
        jMenuVer2D.setEnabled(estado);
        jMenuVer3D.setEnabled(estado);
        jMenuPresupuesto.setEnabled(estado);
        jMenuPuertas.setEnabled(estado);
        jMenuMuebles.setEnabled(estado);
        jMenuMateriales.setEnabled(estado);

        jButtonGuardar.setEnabled(estado);
        jButton2D.setEnabled(estado);
        jButton3D.setEnabled(estado);
        jButtonPresupuesto.setEnabled(estado);
        jToggleButtonArrastrar.setEnabled(estado);
        jButtonPuertas.setEnabled(estado);
        jButtonMuebles.setEnabled(estado);
        jButtonMateriales.setEnabled(estado);
    }

    public void setEnabledDeshacer(boolean estado) {
        jMenuDeshacer.setEnabled(estado);
        jMenuDeshacer1.setEnabled(estado);
        jButtonDeshacer.setEnabled(estado);
    }

    public void setEnabledRehacer(boolean estado) {
        jMenuRehacer.setEnabled(estado);
        jMenuRehacer1.setEnabled(estado);
        jButtonRehacer.setEnabled(estado);
    }

    public void setEnabledPuertas(boolean estado) {
        jMenuBorrar.setEnabled(estado);
        jMenuGirar90AD.setEnabled(false);
        jMenuGirar90AI.setEnabled(false);
        jMenuGirar180.setEnabled(false);
        jMenuDeseleccionar.setEnabled(estado);
        //emergente
        jMenuBorrar1.setEnabled(estado);
        jMenuGirar90AD1.setEnabled(false);
        jMenuGirar90AI1.setEnabled(false);
        jMenuGirar181.setEnabled(false);
        jMenuDeseleccionar1.setEnabled(estado);
    }

    public void setEnabledMuebles(boolean estado) {
        jMenuBorrar.setEnabled(estado);
        jMenuGirar90AD.setEnabled(estado);
        jMenuGirar90AI.setEnabled(estado);
        jMenuGirar180.setEnabled(estado);
        jMenuDeseleccionar.setEnabled(estado);
        //emergente
        jMenuBorrar1.setEnabled(estado);
        jMenuGirar90AD1.setEnabled(estado);
        jMenuGirar90AI1.setEnabled(estado);
        jMenuGirar181.setEnabled(estado);
        jMenuDeseleccionar1.setEnabled(estado);
    }

    public void setEnabled2D(boolean estado) {
        boolean estado2 =  estado && (controlador.getCocina()!=null) && (controlador.getCocina().numMuebles() > 0);
        jMenuBorrarTodos.setEnabled(estado2);
        jMenuBorrarTodos1.setEnabled(estado2);
    }
    
    public void menuEmergente(Component invoker, int x, int y) {
        jPopupMenuEdicion.show(invoker, x, y);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        softBevelBorder1 = new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED);
        jPopupMenuEdicion = new javax.swing.JPopupMenu();
        jMenuDeshacer1 = new javax.swing.JMenuItem();
        jMenuRehacer1 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        jMenuGirar1 = new javax.swing.JMenu();
        jMenuGirar90AD1 = new javax.swing.JMenuItem();
        jMenuGirar90AI1 = new javax.swing.JMenuItem();
        jMenuGirar181 = new javax.swing.JMenuItem();
        jMenuBorrar1 = new javax.swing.JMenuItem();
        jMenuBorrarTodos1 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        jMenuDeseleccionar1 = new javax.swing.JMenuItem();
        barraHerramientas = new javax.swing.JToolBar();
        jButtonNuevo = new javax.swing.JButton();
        jButtonAbrir = new javax.swing.JButton();
        jButtonGuardar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButtonDeshacer = new javax.swing.JButton();
        jButtonRehacer = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton2D = new javax.swing.JButton();
        jButton3D = new javax.swing.JButton();
        jButtonPresupuesto = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jToggleButtonArrastrar = new javax.swing.JToggleButton();
        jButtonPuertas = new javax.swing.JButton();
        jButtonMuebles = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButtonMateriales = new javax.swing.JButton();
        estado = new javax.swing.JLabel();
        panelPpal = new javax.swing.JPanel();
        barraMenu = new javax.swing.JMenuBar();
        jMenuArchivo = new javax.swing.JMenu();
        jMenuNuevo = new javax.swing.JMenuItem();
        jMenuAbrir = new javax.swing.JMenuItem();
        jMenuGuardar = new javax.swing.JMenuItem();
        jMenuGuardarComo = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        jMenuCerrar = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenuItem();
        jMenuEdicion = new javax.swing.JMenu();
        jMenuDeshacer = new javax.swing.JMenuItem();
        jMenuRehacer = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuGirar = new javax.swing.JMenu();
        jMenuGirar90AD = new javax.swing.JMenuItem();
        jMenuGirar90AI = new javax.swing.JMenuItem();
        jMenuGirar180 = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenuItem();
        jMenuBorrarTodos = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuDeseleccionar = new javax.swing.JMenuItem();
        jMenuVer = new javax.swing.JMenu();
        jMenuVer2D = new javax.swing.JMenuItem();
        jMenuVer3D = new javax.swing.JMenuItem();
        jMenuPresupuesto = new javax.swing.JMenuItem();
        jMenuCocina = new javax.swing.JMenu();
        jMenuPuertas = new javax.swing.JMenuItem();
        jMenuMuebles = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        jMenuMateriales = new javax.swing.JMenuItem();
        jMenuAyuda = new javax.swing.JMenu();
        jMenuAcercaDe = new javax.swing.JMenuItem();

        jMenuDeshacer1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuDeshacer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/edit-undo.png"))); // NOI18N
        jMenuDeshacer1.setMnemonic('D');
        jMenuDeshacer1.setText("Deshacer");
        jMenuDeshacer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDeshacer1ActionPerformed(evt);
            }
        });
        jPopupMenuEdicion.add(jMenuDeshacer1);

        jMenuRehacer1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuRehacer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/edit-redo.png"))); // NOI18N
        jMenuRehacer1.setMnemonic('R');
        jMenuRehacer1.setText("Rehacer");
        jMenuRehacer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRehacer1ActionPerformed(evt);
            }
        });
        jPopupMenuEdicion.add(jMenuRehacer1);
        jPopupMenuEdicion.add(jSeparator9);

        jMenuGirar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/rotate_cw.png"))); // NOI18N
        jMenuGirar1.setMnemonic('G');
        jMenuGirar1.setText("Girar");

        jMenuGirar90AD1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuGirar90AD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-rotate-right.png"))); // NOI18N
        jMenuGirar90AD1.setMnemonic('d');
        jMenuGirar90AD1.setText("Girar 90º a la derecha");
        jMenuGirar90AD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar90AD1ActionPerformed(evt);
            }
        });
        jMenuGirar1.add(jMenuGirar90AD1);

        jMenuGirar90AI1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-rotate-left.png"))); // NOI18N
        jMenuGirar90AI1.setMnemonic('i');
        jMenuGirar90AI1.setText("Girar 90º a la izquierda");
        jMenuGirar90AI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar90AI1ActionPerformed(evt);
            }
        });
        jMenuGirar1.add(jMenuGirar90AI1);

        jMenuGirar181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-flip-horizontal.png"))); // NOI18N
        jMenuGirar181.setMnemonic('r');
        jMenuGirar181.setText("Girar 180º");
        jMenuGirar181.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar181ActionPerformed(evt);
            }
        });
        jMenuGirar1.add(jMenuGirar181);

        jPopupMenuEdicion.add(jMenuGirar1);

        jMenuBorrar1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuBorrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/editdelete.png"))); // NOI18N
        jMenuBorrar1.setMnemonic('B');
        jMenuBorrar1.setText("Borrar");
        jMenuBorrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrar1ActionPerformed(evt);
            }
        });
        jPopupMenuEdicion.add(jMenuBorrar1);

        jMenuBorrarTodos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/cnrdelete-all.png"))); // NOI18N
        jMenuBorrarTodos1.setMnemonic('t');
        jMenuBorrarTodos1.setText("Borrar todos los muebles");
        jMenuBorrarTodos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrarTodos1ActionPerformed(evt);
            }
        });
        jPopupMenuEdicion.add(jMenuBorrarTodos1);
        jPopupMenuEdicion.add(jSeparator10);

        jMenuDeseleccionar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/ink_selection_deselect.png"))); // NOI18N
        jMenuDeseleccionar1.setMnemonic('e');
        jMenuDeseleccionar1.setText("Deseleccionar");
        jMenuDeseleccionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDeseleccionar1ActionPerformed(evt);
            }
        });
        jPopupMenuEdicion.add(jMenuDeseleccionar1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        barraHerramientas.setFloatable(false);
        barraHerramientas.setRollover(true);
        barraHerramientas.setMaximumSize(new java.awt.Dimension(829, 44));
        barraHerramientas.setMinimumSize(new java.awt.Dimension(715, 44));
        barraHerramientas.setPreferredSize(new java.awt.Dimension(715, 44));

        jButtonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/document-new.png"))); // NOI18N
        jButtonNuevo.setToolTipText("Nueva cocina");
        jButtonNuevo.setFocusable(false);
        jButtonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNuevo.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonNuevo.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonNuevo.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonNuevo);

        jButtonAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/document-open.png"))); // NOI18N
        jButtonAbrir.setToolTipText("Abrir cocina");
        jButtonAbrir.setFocusable(false);
        jButtonAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAbrir.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonAbrir.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonAbrir.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbrirActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonAbrir);

        jButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/document-save.png"))); // NOI18N
        jButtonGuardar.setToolTipText("Guardar cocina");
        jButtonGuardar.setFocusable(false);
        jButtonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGuardar.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonGuardar.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonGuardar.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonGuardar);
        barraHerramientas.add(jSeparator2);

        jButtonDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/edit-undo.png"))); // NOI18N
        jButtonDeshacer.setToolTipText("Deshacer cambios");
        jButtonDeshacer.setFocusable(false);
        jButtonDeshacer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeshacer.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonDeshacer.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonDeshacer.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonDeshacer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeshacerActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonDeshacer);

        jButtonRehacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/edit-redo.png"))); // NOI18N
        jButtonRehacer.setToolTipText("Rehacer cambios");
        jButtonRehacer.setFocusable(false);
        jButtonRehacer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRehacer.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonRehacer.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonRehacer.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonRehacer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRehacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRehacerActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonRehacer);
        barraHerramientas.add(jSeparator8);

        jButton2D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/2d24.png"))); // NOI18N
        jButton2D.setToolTipText("Ver cocina en 2D");
        jButton2D.setFocusable(false);
        jButton2D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2D.setMaximumSize(new java.awt.Dimension(42, 40));
        jButton2D.setMinimumSize(new java.awt.Dimension(42, 40));
        jButton2D.setPreferredSize(new java.awt.Dimension(42, 40));
        jButton2D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2DActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButton2D);

        jButton3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/3d24.png"))); // NOI18N
        jButton3D.setToolTipText("Ver cocina en 3D");
        jButton3D.setFocusable(false);
        jButton3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3D.setMaximumSize(new java.awt.Dimension(42, 40));
        jButton3D.setMinimumSize(new java.awt.Dimension(42, 40));
        jButton3D.setPreferredSize(new java.awt.Dimension(42, 40));
        jButton3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3DActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButton3D);

        jButtonPresupuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/kspread_ksp.png"))); // NOI18N
        jButtonPresupuesto.setToolTipText("Ver presupuesto");
        jButtonPresupuesto.setFocusable(false);
        jButtonPresupuesto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonPresupuesto.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonPresupuesto.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonPresupuesto.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonPresupuesto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPresupuestoActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonPresupuesto);
        barraHerramientas.add(jSeparator3);

        jToggleButtonArrastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/ink_draw_select.png"))); // NOI18N
        jToggleButtonArrastrar.setSelected(true);
        jToggleButtonArrastrar.setToolTipText("Seleccionar y arrastrar objetos");
        jToggleButtonArrastrar.setFocusable(false);
        jToggleButtonArrastrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButtonArrastrar.setMaximumSize(new java.awt.Dimension(42, 40));
        jToggleButtonArrastrar.setMinimumSize(new java.awt.Dimension(42, 40));
        jToggleButtonArrastrar.setPreferredSize(new java.awt.Dimension(42, 40));
        jToggleButtonArrastrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonArrastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonArrastrarActionPerformed(evt);
            }
        });
        barraHerramientas.add(jToggleButtonArrastrar);

        jButtonPuertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/puerta24.png"))); // NOI18N
        jButtonPuertas.setToolTipText("Poner puertas o ventanas");
        jButtonPuertas.setFocusable(false);
        jButtonPuertas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonPuertas.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonPuertas.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonPuertas.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonPuertas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonPuertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPuertasActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonPuertas);

        jButtonMuebles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/mueble24.png"))); // NOI18N
        jButtonMuebles.setToolTipText("Poner muebles o electrodomesticos");
        jButtonMuebles.setFocusable(false);
        jButtonMuebles.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMuebles.setMaximumSize(new java.awt.Dimension(42, 40));
        jButtonMuebles.setMinimumSize(new java.awt.Dimension(42, 40));
        jButtonMuebles.setPreferredSize(new java.awt.Dimension(42, 40));
        jButtonMuebles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMuebles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMueblesActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonMuebles);
        barraHerramientas.add(jSeparator4);

        jButtonMateriales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/24x24/materiales.png"))); // NOI18N
        jButtonMateriales.setToolTipText("Elegir materiales");
        jButtonMateriales.setFocusable(false);
        jButtonMateriales.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMateriales.setMaximumSize(new java.awt.Dimension(40, 40));
        jButtonMateriales.setMinimumSize(new java.awt.Dimension(40, 40));
        jButtonMateriales.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonMateriales.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaterialesActionPerformed(evt);
            }
        });
        barraHerramientas.add(jButtonMateriales);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        estado.setText("Listo");
        estado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(estado, java.awt.BorderLayout.PAGE_END);

        panelPpal.setBackground(new java.awt.Color(255, 255, 255));
        panelPpal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelPpal.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelPpal, java.awt.BorderLayout.CENTER);

        jMenuArchivo.setMnemonic('A');
        jMenuArchivo.setText("Archivo");

        jMenuNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/document-new.png"))); // NOI18N
        jMenuNuevo.setMnemonic('N');
        jMenuNuevo.setText("Nueva cocina");
        jMenuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNuevoActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuNuevo);

        jMenuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/document-open.png"))); // NOI18N
        jMenuAbrir.setMnemonic('A');
        jMenuAbrir.setText("Abrir cocina");
        jMenuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAbrirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuAbrir);

        jMenuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/document-save.png"))); // NOI18N
        jMenuGuardar.setMnemonic('G');
        jMenuGuardar.setText("Guardar");
        jMenuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGuardarActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuGuardar);

        jMenuGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/document-save-as.png"))); // NOI18N
        jMenuGuardarComo.setMnemonic('u');
        jMenuGuardarComo.setText("Guardar como");
        jMenuGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGuardarComoActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuGuardarComo);
        jMenuArchivo.add(jSeparator7);

        jMenuCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/gnome-session-suspend.png"))); // NOI18N
        jMenuCerrar.setMnemonic('C');
        jMenuCerrar.setText("Cerrar");
        jMenuCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCerrarActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuCerrar);

        jMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/gnome-session-halt.png"))); // NOI18N
        jMenuSalir.setMnemonic('S');
        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuSalir);

        barraMenu.add(jMenuArchivo);

        jMenuEdicion.setMnemonic('E');
        jMenuEdicion.setText("Edición");

        jMenuDeshacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/edit-undo.png"))); // NOI18N
        jMenuDeshacer.setMnemonic('D');
        jMenuDeshacer.setText("Deshacer");
        jMenuDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDeshacerActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuDeshacer);

        jMenuRehacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuRehacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/edit-redo.png"))); // NOI18N
        jMenuRehacer.setMnemonic('R');
        jMenuRehacer.setText("Rehacer");
        jMenuRehacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRehacerActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuRehacer);
        jMenuEdicion.add(jSeparator1);

        jMenuGirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/rotate_cw.png"))); // NOI18N
        jMenuGirar.setMnemonic('G');
        jMenuGirar.setText("Girar");

        jMenuGirar90AD.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuGirar90AD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-rotate-right.png"))); // NOI18N
        jMenuGirar90AD.setMnemonic('d');
        jMenuGirar90AD.setText("Girar 90º a la derecha");
        jMenuGirar90AD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar90ADActionPerformed(evt);
            }
        });
        jMenuGirar.add(jMenuGirar90AD);

        jMenuGirar90AI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-rotate-left.png"))); // NOI18N
        jMenuGirar90AI.setMnemonic('i');
        jMenuGirar90AI.setText("Girar 90º a la izquierda");
        jMenuGirar90AI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar90AIActionPerformed(evt);
            }
        });
        jMenuGirar.add(jMenuGirar90AI);

        jMenuGirar180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/object-flip-horizontal.png"))); // NOI18N
        jMenuGirar180.setMnemonic('r');
        jMenuGirar180.setText("Girar 180º");
        jMenuGirar180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGirar180ActionPerformed(evt);
            }
        });
        jMenuGirar.add(jMenuGirar180);

        jMenuEdicion.add(jMenuGirar);

        jMenuBorrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/editdelete.png"))); // NOI18N
        jMenuBorrar.setMnemonic('B');
        jMenuBorrar.setText("Borrar");
        jMenuBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrarActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuBorrar);

        jMenuBorrarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/cnrdelete-all.png"))); // NOI18N
        jMenuBorrarTodos.setMnemonic('t');
        jMenuBorrarTodos.setText("Borrar todos los muebles");
        jMenuBorrarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrarTodosActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuBorrarTodos);
        jMenuEdicion.add(jSeparator5);

        jMenuDeseleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/ink_selection_deselect.png"))); // NOI18N
        jMenuDeseleccionar.setMnemonic('l');
        jMenuDeseleccionar.setText("Deseleccionar");
        jMenuDeseleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDeseleccionarActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuDeseleccionar);

        barraMenu.add(jMenuEdicion);

        jMenuVer.setMnemonic('V');
        jMenuVer.setText("Ver");

        jMenuVer2D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/2d16.png"))); // NOI18N
        jMenuVer2D.setMnemonic('2');
        jMenuVer2D.setText("Ver cocina en 2D");
        jMenuVer2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuVer2DActionPerformed(evt);
            }
        });
        jMenuVer.add(jMenuVer2D);

        jMenuVer3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/3d16.png"))); // NOI18N
        jMenuVer3D.setMnemonic('3');
        jMenuVer3D.setText("Ver cocina en 3D");
        jMenuVer3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuVer3DActionPerformed(evt);
            }
        });
        jMenuVer.add(jMenuVer3D);

        jMenuPresupuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/kspread_ksp.png"))); // NOI18N
        jMenuPresupuesto.setMnemonic('p');
        jMenuPresupuesto.setText("Ver presupuesto");
        jMenuPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPresupuestoActionPerformed(evt);
            }
        });
        jMenuVer.add(jMenuPresupuesto);

        barraMenu.add(jMenuVer);

        jMenuCocina.setMnemonic('C');
        jMenuCocina.setText("Cocina");

        jMenuPuertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/puerta16.png"))); // NOI18N
        jMenuPuertas.setMnemonic('p');
        jMenuPuertas.setText("Poner puertas o ventanas");
        jMenuPuertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPuertasActionPerformed(evt);
            }
        });
        jMenuCocina.add(jMenuPuertas);

        jMenuMuebles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/mueble16.png"))); // NOI18N
        jMenuMuebles.setMnemonic('m');
        jMenuMuebles.setText("Poner muebles o electrodomesticos");
        jMenuMuebles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMueblesActionPerformed(evt);
            }
        });
        jMenuCocina.add(jMenuMuebles);
        jMenuCocina.add(jSeparator6);

        jMenuMateriales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/materiales.png"))); // NOI18N
        jMenuMateriales.setMnemonic('a');
        jMenuMateriales.setText("Elegir materiales");
        jMenuMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMaterialesActionPerformed(evt);
            }
        });
        jMenuCocina.add(jMenuMateriales);

        barraMenu.add(jMenuCocina);

        jMenuAyuda.setMnemonic('y');
        jMenuAyuda.setText("Ayuda");

        jMenuAcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wimp/iconos/16x16/help-about.png"))); // NOI18N
        jMenuAcercaDe.setMnemonic('A');
        jMenuAcercaDe.setText("Acerca de");
        jMenuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAcercaDeActionPerformed(evt);
            }
        });
        jMenuAyuda.add(jMenuAcercaDe);

        barraMenu.add(jMenuAyuda);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2DActionPerformed
    controlador.mostrarCocina2D();
}//GEN-LAST:event_jButton2DActionPerformed

    private void jButtonPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPresupuestoActionPerformed
    controlador.mostrarPresupuesto();
}//GEN-LAST:event_jButtonPresupuestoActionPerformed

    private void jButton3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3DActionPerformed
    controlador.mostrarCocina3D();
}//GEN-LAST:event_jButton3DActionPerformed

private void jButtonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbrirActionPerformed
    controlador.abrir();
}//GEN-LAST:event_jButtonAbrirActionPerformed

private void jMenuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarActionPerformed
    controlador.borrar();
}//GEN-LAST:event_jMenuBorrarActionPerformed

private void jMenuGirar90ADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar90ADActionPerformed
    controlador.girar(90);
}//GEN-LAST:event_jMenuGirar90ADActionPerformed

private void jMenuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAbrirActionPerformed
    controlador.abrir();
}//GEN-LAST:event_jMenuAbrirActionPerformed

private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
    controlador.salir();
}//GEN-LAST:event_jMenuSalirActionPerformed

private void jMenuVer2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuVer2DActionPerformed
    controlador.mostrarCocina2D();
}//GEN-LAST:event_jMenuVer2DActionPerformed

private void jMenuVer3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuVer3DActionPerformed
    controlador.mostrarCocina3D();
}//GEN-LAST:event_jMenuVer3DActionPerformed

private void jMenuPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPresupuestoActionPerformed
    controlador.mostrarPresupuesto();
}//GEN-LAST:event_jMenuPresupuestoActionPerformed

private void jMenuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGuardarActionPerformed
    controlador.guardar();
}//GEN-LAST:event_jMenuGuardarActionPerformed

private void jMenuGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGuardarComoActionPerformed
    controlador.guardarComo();
}//GEN-LAST:event_jMenuGuardarComoActionPerformed

private void jMenuBorrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarTodosActionPerformed
    controlador.borrarTodosLosMuebles();
}//GEN-LAST:event_jMenuBorrarTodosActionPerformed

private void jButtonPuertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPuertasActionPerformed
    controlador.puerta();
}//GEN-LAST:event_jButtonPuertasActionPerformed

private void jButtonMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaterialesActionPerformed
    controlador.materiales();
}//GEN-LAST:event_jButtonMaterialesActionPerformed

private void jButtonMueblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMueblesActionPerformed
    controlador.mueble();
}//GEN-LAST:event_jButtonMueblesActionPerformed

private void jMenuCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCerrarActionPerformed
    controlador.cerrar();
}//GEN-LAST:event_jMenuCerrarActionPerformed

private void jMenuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNuevoActionPerformed
    controlador.nuevo();
}//GEN-LAST:event_jMenuNuevoActionPerformed

private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
    controlador.nuevo();
}//GEN-LAST:event_jButtonNuevoActionPerformed

private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
    controlador.guardar();
}//GEN-LAST:event_jButtonGuardarActionPerformed

private void jToggleButtonArrastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonArrastrarActionPerformed
    controlador.cancelarPoner();
    controlador.catalogoDeseleccionar();
}//GEN-LAST:event_jToggleButtonArrastrarActionPerformed

private void jMenuDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDeshacerActionPerformed
    controlador.deshacer();
}//GEN-LAST:event_jMenuDeshacerActionPerformed

private void jMenuRehacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRehacerActionPerformed
    controlador.rehacer();
}//GEN-LAST:event_jMenuRehacerActionPerformed

private void jMenuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAcercaDeActionPerformed
    controlador.acercade();
}//GEN-LAST:event_jMenuAcercaDeActionPerformed

private void jMenuPuertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPuertasActionPerformed
    controlador.puerta();
}//GEN-LAST:event_jMenuPuertasActionPerformed

private void jMenuMueblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMueblesActionPerformed
    controlador.mueble();
}//GEN-LAST:event_jMenuMueblesActionPerformed

private void jMenuMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMaterialesActionPerformed
    controlador.materiales();
}//GEN-LAST:event_jMenuMaterialesActionPerformed

private void jMenuDeseleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDeseleccionarActionPerformed
    controlador.deseleccionar();
}//GEN-LAST:event_jMenuDeseleccionarActionPerformed

private void jButtonRehacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRehacerActionPerformed
    controlador.rehacer();
}//GEN-LAST:event_jButtonRehacerActionPerformed

private void jButtonDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeshacerActionPerformed
    controlador.deshacer();
}//GEN-LAST:event_jButtonDeshacerActionPerformed

private void jMenuDeshacer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDeshacer1ActionPerformed
    controlador.deshacer();
}//GEN-LAST:event_jMenuDeshacer1ActionPerformed

private void jMenuRehacer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRehacer1ActionPerformed
    controlador.rehacer();
}//GEN-LAST:event_jMenuRehacer1ActionPerformed

private void jMenuGirar90AD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar90AD1ActionPerformed
    controlador.girar(90);
}//GEN-LAST:event_jMenuGirar90AD1ActionPerformed

private void jMenuBorrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrar1ActionPerformed
    controlador.borrar();
}//GEN-LAST:event_jMenuBorrar1ActionPerformed

private void jMenuBorrarTodos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarTodos1ActionPerformed
    controlador.borrarTodosLosMuebles();
}//GEN-LAST:event_jMenuBorrarTodos1ActionPerformed

private void jMenuDeseleccionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDeseleccionar1ActionPerformed
    controlador.deseleccionar();
}//GEN-LAST:event_jMenuDeseleccionar1ActionPerformed

private void jMenuGirar90AIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar90AIActionPerformed
    controlador.girar(270);
}//GEN-LAST:event_jMenuGirar90AIActionPerformed

private void jMenuGirar180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar180ActionPerformed
    controlador.girar(180);
}//GEN-LAST:event_jMenuGirar180ActionPerformed

private void jMenuGirar90AI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar90AI1ActionPerformed
    controlador.girar(270);
}//GEN-LAST:event_jMenuGirar90AI1ActionPerformed

private void jMenuGirar181ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGirar181ActionPerformed
    controlador.girar(180);
}//GEN-LAST:event_jMenuGirar181ActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    controlador.salir(); //capturar el boton de cerrar la ventana
}//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JLabel estado;
    private javax.swing.JButton jButton2D;
    private javax.swing.JButton jButton3D;
    private javax.swing.JButton jButtonAbrir;
    private javax.swing.JButton jButtonDeshacer;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonMateriales;
    private javax.swing.JButton jButtonMuebles;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JButton jButtonPresupuesto;
    private javax.swing.JButton jButtonPuertas;
    private javax.swing.JButton jButtonRehacer;
    private javax.swing.JMenuItem jMenuAbrir;
    private javax.swing.JMenuItem jMenuAcercaDe;
    private javax.swing.JMenu jMenuArchivo;
    private javax.swing.JMenu jMenuAyuda;
    private javax.swing.JMenuItem jMenuBorrar;
    private javax.swing.JMenuItem jMenuBorrar1;
    private javax.swing.JMenuItem jMenuBorrarTodos;
    private javax.swing.JMenuItem jMenuBorrarTodos1;
    private javax.swing.JMenuItem jMenuCerrar;
    private javax.swing.JMenu jMenuCocina;
    private javax.swing.JMenuItem jMenuDeseleccionar;
    private javax.swing.JMenuItem jMenuDeseleccionar1;
    private javax.swing.JMenuItem jMenuDeshacer;
    private javax.swing.JMenuItem jMenuDeshacer1;
    private javax.swing.JMenu jMenuEdicion;
    private javax.swing.JMenu jMenuGirar;
    private javax.swing.JMenu jMenuGirar1;
    private javax.swing.JMenuItem jMenuGirar180;
    private javax.swing.JMenuItem jMenuGirar181;
    private javax.swing.JMenuItem jMenuGirar90AD;
    private javax.swing.JMenuItem jMenuGirar90AD1;
    private javax.swing.JMenuItem jMenuGirar90AI;
    private javax.swing.JMenuItem jMenuGirar90AI1;
    private javax.swing.JMenuItem jMenuGuardar;
    private javax.swing.JMenuItem jMenuGuardarComo;
    private javax.swing.JMenuItem jMenuMateriales;
    private javax.swing.JMenuItem jMenuMuebles;
    private javax.swing.JMenuItem jMenuNuevo;
    private javax.swing.JMenuItem jMenuPresupuesto;
    private javax.swing.JMenuItem jMenuPuertas;
    private javax.swing.JMenuItem jMenuRehacer;
    private javax.swing.JMenuItem jMenuRehacer1;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JMenu jMenuVer;
    private javax.swing.JMenuItem jMenuVer2D;
    private javax.swing.JMenuItem jMenuVer3D;
    private javax.swing.JPopupMenu jPopupMenuEdicion;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JToggleButton jToggleButtonArrastrar;
    private javax.swing.JPanel panelPpal;
    private javax.swing.border.SoftBevelBorder softBevelBorder1;
    // End of variables declaration//GEN-END:variables

}
