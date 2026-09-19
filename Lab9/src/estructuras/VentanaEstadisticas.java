
package estructuras;

/**
 *
 * @author Leandro
 */
import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas extends JDialog {
    
    public VentanaEstadisticas(JFrame padre, CentroLogistico centro, RepartidorThread[] repartidores) {
        super(padre, "Estadísticas del Centro Logístico", true);
        setSize(400, 350);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        JTextArea txtStats = new JTextArea();
        txtStats.setEditable(false);
        txtStats.setFont(new Font("Monospaced", Font.PLAIN, 12));

        int entregados = centro.entregados.getTamaño();
        int devueltos = centro.devueltos.getTamaño();
        int enRecepcion = centro.recepcion.getOcupacion();
        int enAlmacen = centro.almacen.getOcupacion();
        int enClasif = centro.clasificacion.getOcupacion();
        int enEmpaq = centro.empaquetado.getOcupacion();
        int enExped = centro.expedicion.getOcupacion();
        
        int enProceso = enRecepcion + enAlmacen + enClasif + enEmpaq + enExped;
        int totalGenerados = entregados + devueltos + enProceso;

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("         ESTADÍSTICAS DEL SISTEMA        \n");
        sb.append("=========================================\n\n");
        sb.append(String.format(" Paquetes Generados : %d\n", totalGenerados));
        sb.append(String.format(" Entregados         : %d\n", entregados));
        sb.append(String.format(" Devueltos          : %d\n", devueltos));
        sb.append(String.format(" En Proceso         : %d\n\n", enProceso));
        sb.append("-----------------------------------------\n");
        sb.append(" RENDIMIENTO POR REPARTIDOR\n");
        sb.append("-----------------------------------------\n");

        for (RepartidorThread rep : repartidores) {
            sb.append(String.format(" %-15s : %d entregados\n", rep.getIdRepartidor(), rep.getTotalEntregados()));
        }

        txtStats.setText(sb.toString());
        add(new JScrollPane(txtStats), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }
}
