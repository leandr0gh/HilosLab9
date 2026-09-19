
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
    
    private JTextArea areaRecepcion, areaAlmacen, areaClasificacion, areaEmpaquetado;
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
        
        
                
    }
}
