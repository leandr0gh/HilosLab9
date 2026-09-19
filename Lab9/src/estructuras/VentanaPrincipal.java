
package estructuras;

/**
 *
 * @author Leandro
 */

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    
    private final CentroLogistico centro = new CentroLogistico();
    private ControlSimulacion control = new ControlSimulacion();
    
    private RecepcionThread hiloRecepcion;
    private ClasificadorThread[] clasificadores;
    private EmpaquetadorThread[] empaquetadores;
    private RepartidorThread[] repartidores;
    
    private JTextArea areaRecepcion, areaAlmacen, areaClasificacion, areaEmpaquetado, areaExpedicion, areaLog;
    private JProgressBar barAlmacen, barEmpaquetado;
    private JLabel[] lblRepartidores;
    private JButton btnIniciar, btnPausar, btnReanudar, btnDetener, btnReiniciar, btnStats;
    
    private Timer timerActualizacion;
    
    public VentanaPrincipal(){
        setTitle("Sistema de Simulacion de Centro Logistico");
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        inicializarComponentes();

    }
    
    private void inicializarComponentes(){
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnReanudar = new JButton("Reanudar");
        btnDetener = new JButton("Detener");
        btnReiniciar = new JButton("Reiniciar");
        btnStats = new JButton("Estadisticas");
        
        btnPausar.setEnabled(false);
        btnReanudar.setEnabled(false);
        btnDetener.setEnabled(false);
        
        panelControles.add(btnIniciar);
        panelControles.add(btnPausar);
        panelControles.add(btnReanudar);
        panelControles.add(btnDetener);
        panelControles.add(btnReiniciar);
        panelControles.add(btnStats);
        
        add(panelControles, BorderLayout.NORTH);
        
        JPanel panelZonas = new JPanel(new GridLayout(3, 1, 10, 10));
        
        JPanel fila1 = new JPanel(new GridLayout(1, 3, 10, 10));
        areaRecepcion = crearAreaTexto("Recepcion (Cap: 10", fila1);
        
        JPanel panelAlmacenContainer = new JPanel(new BorderLayout());
        areaAlmacen = crearAreaTexto("Almacen (Cap: 20)", panelAlmacenContainer);
        barAlmacen = new JProgressBar(0, 20);
        barAlmacen.setStringPainted(true);
        panelAlmacenContainer.add(barAlmacen, BorderLayout.SOUTH);
        fila1.add(panelAlmacenContainer);
        
        areaClasificacion = crearAreaTexto("Clasificacion (Cap: 10)", fila1);
        panelZonas.add(fila1);
        
        JPanel fila2 = new JPanel(new GridLayout(1, 2, 10, 10));
        
        JPanel panelEmpaqContainer = new JPanel(new BorderLayout());
        areaEmpaquetado = crearAreaTexto("Empaquetado (Cap: 8)", panelEmpaqContainer);
        barEmpaquetado = new JProgressBar(0, 8);
        barEmpaquetado.setStringPainted(true);
        panelEmpaqContainer.add(barEmpaquetado, BorderLayout.SOUTH);
        fila2.add(panelEmpaqContainer);
        
        areaExpedicion = crearAreaTexto("Expedicion por rutas (Cap: 15)", fila2);
        panelZonas.add(fila2);
        
        JPanel panelRepartidores = new JPanel(new GridLayout(1, 4, 10, 10));
        panelRepartidores.setBorder(BorderFactory.createTitledBorder("ESTADO DE REPARTIDORES"));
        lblRepartidores = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            lblRepartidores[i] = new JLabel("<html><b>🚚 Repartidor " + (i + 1) + "</b><br>Estado: INACTIVO<br>Paquetes: 0</html>", SwingConstants.CENTER);
            lblRepartidores[i].setBorder(BorderFactory.createEtchedBorder());
            panelRepartidores.add(lblRepartidores[i]);
        }
        panelZonas.add(panelRepartidores);

        add(panelZonas, BorderLayout.CENTER);
        
        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBorder(BorderFactory.createTitledBorder("REGISTRO DEL SISTEMA (LOG)"));
        areaLog = new JTextArea(8, 50);
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        panelLog.add(new JScrollPane(areaLog), BorderLayout.CENTER);

        add(panelLog, BorderLayout.SOUTH);

        btnIniciar.addActionListener(e -> iniciarSimulacion());
        btnPausar.addActionListener(e -> pausarSimulacion());
        btnReanudar.addActionListener(e -> reanudarSimulacion());
        btnDetener.addActionListener(e -> detenerSimulacion());
        btnReiniciar.addActionListener(e -> reiniciarSimulacion());
        btnStats.addActionListener(e -> abrirEstadisticas());
                
    }
    
    private void iniciarSimulacion() {
        control = new ControlSimulacion();

        hiloRecepcion = new RecepcionThread(centro, control);

        clasificadores = new ClasificadorThread[]{
            new ClasificadorThread("Clasificador-1", centro, control),
            new ClasificadorThread("Clasificador-2", centro, control),
            new ClasificadorThread("Clasificador-3", centro, control)
        };

        empaquetadores = new EmpaquetadorThread[]{
            new EmpaquetadorThread("Empaquetador-1", centro, control),
            new EmpaquetadorThread("Empaquetador-2", centro, control)
        };

        repartidores = new RepartidorThread[]{
            new RepartidorThread("Repartidor 1", "Ruta 1", 5, centro, control),
            new RepartidorThread("Repartidor 2", "Ruta 2", 4, centro, control),
            new RepartidorThread("Repartidor 3", "Ruta 3", 6, centro, control),
            new RepartidorThread("Repartidor 4", "Ruta 4", 5, centro, control)
        };

        hiloRecepcion.start();
        for (ClasificadorThread c : clasificadores) c.start();
        for (EmpaquetadorThread em : empaquetadores) em.start();
        for (RepartidorThread r : repartidores) r.start();

        timerActualizacion.start();

        btnIniciar.setEnabled(false);
        btnPausar.setEnabled(true);
        btnDetener.setEnabled(true);
        centro.registrarEvento(">>> SIMULACIÓN INICIADA <<<");
    }
    
    private void pausarSimulacion() {
        control.pausar();
        btnPausar.setEnabled(false);
        btnReanudar.setEnabled(true);
        centro.registrarEvento(">>> SIMULACIÓN PAUSADA <<<");
    }
    
    private void reanudarSimulacion() {
        control.reanudar();
        btnReanudar.setEnabled(false);
        btnPausar.setEnabled(true);
        centro.registrarEvento(">>> SIMULACIÓN REANUDADA <<<");
    }
    
    private void detenerSimulacion() {
        control.detener();
        if (hiloRecepcion != null) hiloRecepcion.interrupt();
        if (clasificadores != null) for (Thread t : clasificadores) t.interrupt();
        if (empaquetadores != null) for (Thread t : empaquetadores) t.interrupt();
        if (repartidores != null) for (Thread t : repartidores) t.interrupt();

        btnPausar.setEnabled(false);
        btnReanudar.setEnabled(false);
        btnDetener.setEnabled(false);
        btnIniciar.setEnabled(true);
        centro.registrarEvento(">>> SIMULACIÓN DETENIDA <<<");
    }
    
    private void reiniciarSimulacion() {
        detenerSimulacion();
        centro.recepcion.vaciar();
        centro.almacen.vaciar();
        centro.clasificacion.vaciar();
        centro.empaquetado.vaciar();
        centro.expedicion.vaciar();
        centro.entregados.vaciar();
        centro.devueltos.vaciar();
        centro.getLogEventos().vaciar();

        actualizarGUI();
        areaLog.setText("");
        centro.registrarEvento(">>> SISTEMA REINICIADO <<<");
    }
    
    private void abrirEstadisticas() {
        if (repartidores == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar la simulación para ver estadísticas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        VentanaEstadisticas stats = new VentanaEstadisticas(this, centro, repartidores);
        stats.setVisible(true);
    }
    
    private void configurarTimerRefresco() {
        timerActualizacion = new Timer(200, e -> actualizarGUI());
    }
    
    private void actualizarGUI() {
        areaRecepcion.setText(formatearLista(centro.recepcion.getLista()));
        areaAlmacen.setText(formatearLista(centro.almacen.getLista()));
        areaClasificacion.setText(formatearLista(centro.clasificacion.getLista()));
        areaEmpaquetado.setText(formatearLista(centro.empaquetado.getLista()));
        areaExpedicion.setText(formatearLista(centro.expedicion.getLista()));

        barAlmacen.setValue(centro.almacen.getOcupacion());
        barAlmacen.setString(centro.almacen.getOcupacion() + " / 20 paquetes");

        barEmpaquetado.setValue(centro.empaquetado.getOcupacion());
        barEmpaquetado.setString(centro.empaquetado.getOcupacion() + " / 8 paquetes");

        if (repartidores != null) {
            for (int i = 0; i < repartidores.length; i++) {
                RepartidorThread rep = repartidores[i];
                lblRepartidores[i].setText(String.format(
                    "<html><b>🚚 %s</b> (%s)<br>Estado: <b>%s</b><br>Carga: %d/%d</html>",
                    rep.getIdRepartidor(), rep.getRutaAsignada(), rep.getEstadoRepartidor(),
                    rep.getPaquetesActuales(), rep.getCapacidadMaxima()
                ));
            }
        }

        StringBuilder logText = new StringBuilder();
        ListaEnlazada<String> log = centro.getLogEventos();
        for (int i = 0; i < log.getTamaño(); i++) {
            logText.append(log.obtener(i)).append("\n");
        }
        areaLog.setText(logText.toString());
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private String formatearLista(ListaEnlazada<Paquete> lista) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.getTamaño(); i++) {
            Paquete p = lista.obtener(i);
            sb.append(String.format("%s | %s | %s\n", p.getCodigo(), p.getPrioridad(), p.getEstado()));
        }
        return sb.toString();
    }
    
    
    private JTextArea crearAreaTexto(String titulo, JPanel padre) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createTitledBorder(titulo));
        padre.add(scroll);
        return area;
    }
}
