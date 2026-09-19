
package estructuras;

/**
 *
 * @author Leandro
 */
public class EmpaquetadorThread extends Thread {
    private final String nombreEmpaquetador;
    private final CentroLogistico centro;
    private final ControlSimulacion control;

    public EmpaquetadorThread(String nombre, CentroLogistico centro, ControlSimulacion control) {
        this.nombreEmpaquetador = nombre;
        this.centro = centro;
        this.control = control;
    }

    @Override
    public void run() {
        while (control.isCorriendo()) {
            try {
                control.verificarPausa();

                Paquete paquete = centro.empaquetado.procesar();
                paquete.setEstado(EstadoPaquete.EMPAQUETANDO);

                int tiempoMs;
                if (paquete.getPeso() <= 2.0) {
                    tiempoMs = 1000;
                } else if (paquete.getPeso() <= 5.0) {
                    tiempoMs = 2000;
                } else {
                    tiempoMs = 3000;
                }

                centro.registrarEvento(paquete.getCodigo() + " empaquetando por " + nombreEmpaquetador + " (" + paquete.getPeso() + "kg)");
                Thread.sleep(tiempoMs);

                paquete.setEstado(EstadoPaquete.EMPAQUETADO);
                centro.registrarEvento(paquete.getCodigo() + " empaquetado listo");

                paquete.setEstado(EstadoPaquete.EN_EXPEDICION);
                centro.expedicion.recibir(paquete);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
