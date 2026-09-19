
package estructuras;

/**
 *
 * @author Leandro
 */
import java.util.Random;

public class RecepcionThread extends Thread {
    private final CentroLogistico centro;
    private final ControlSimulacion control;
    private final Random random = new Random();
    private int contadorPaquetes = 1;

    private final String[] clientes = {"Alex Enamorado", "Ana Sanchez", "Leandro Sandoval", "Valeria Torres", "Tatiana Ruiz"};
    private final String[] ciudades = {"Naples", "San Pedro Sula", "Tegucigalpa", "Sant Martí", "Badalona"};

    public RecepcionThread(CentroLogistico centro, ControlSimulacion control) {
        this.centro = centro;
        this.control = control;
    }

    @Override
    public void run() {
        while (control.isCorriendo()) {
            try {
                control.verificarPausa();

                String codigo = String.format("PKG-%04d", contadorPaquetes++);
                String cliente = clientes[random.nextInt(clientes.length)];
                String ciudad = ciudades[random.nextInt(ciudades.length)];
                double peso = Math.round((1.0 + random.nextDouble() * 9.0) * 10.0) / 10.0;
                Prioridad prioridad = Prioridad.values()[random.nextInt(Prioridad.values().length)];

                Paquete paquete = new Paquete(codigo, cliente, "Calle " + random.nextInt(100), ciudad, peso, prioridad);

                centro.recepcion.recibir(paquete);
                centro.registrarEvento(paquete.getCodigo() + " recibido en Centro Logístico (" + prioridad + ")");

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
