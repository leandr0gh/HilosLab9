
package estructuras;

/**
 *
 * @author Leandro
 */
import java.util.Random;

public class RepartidorThread extends Thread {
    private final String idRepartidor;
    private final String rutaAsignada;
    private final int capacidadMaxima;
    private final CentroLogistico centro;
    private final ControlSimulacion control;
    private final Random random = new Random();

    private String estado = "DISPONIBLE";
    private final ListaEnlazada<Paquete> paquetesCargados = new ListaEnlazada<>();
    private int totalEntregados = 0;

    public RepartidorThread(String id, String ruta, int capacidad, CentroLogistico centro, ControlSimulacion control) {
        this.idRepartidor = id;
        this.rutaAsignada = ruta;
        this.capacidadMaxima = capacidad;
        this.centro = centro;
        this.control = control;
    }

    @Override
    public void run() {
        while (control.isCorriendo()) {
            try {
                control.verificarPausa();

                estado = "CARGANDO";
                cargarPaquetesDeExpedicion();

                if (paquetesCargados.estaVacia()) {
                    estado = "DISPONIBLE";
                    Thread.sleep(1500);
                    continue;
                }

                estado = "EN RUTA";
                centro.registrarEvento("🚚 " + idRepartidor + " inicia " + rutaAsignada + " con " + paquetesCargados.getTamaño() + " paquetes");
                Thread.sleep(2000);

                estado = "ENTREGANDO";
                while (!paquetesCargados.estaVacia()) {
                    control.verificarPausa();
                    Paquete paquete = paquetesCargados.eliminarPrimero();
                    paquete.setEstado(EstadoPaquete.EN_REPARTO);

                    Thread.sleep(1500);

                    boolean entregaExitosa = random.nextDouble() < 0.80;

                    if (entregaExitosa) {
                        paquete.setEstado(EstadoPaquete.ENTREGADO);
                        centro.entregados.agregar(paquete);
                        totalEntregados++;
                        centro.registrarEvento(paquete.getCodigo() + " entregado con éxito por " + idRepartidor);
                    } else {
                        paquete.registrarIntentoFallido();
                        if (paquete.getIntentos() >= 3) {
                            paquete.setEstado(EstadoPaquete.DEVUELTO);
                            centro.devueltos.agregar(paquete);
                            centro.registrarEvento("❌ " + paquete.getCodigo() + " DEVUELTO (3 intentos fallidos)");
                        } else {
                            paquete.setEstado(EstadoPaquete.NUEVO_INTENTO);
                            centro.registrarEvento("⚠️ " + paquete.getCodigo() + " - Cliente ausente (Intento " + paquete.getIntentos() + "/3)");
                            centro.recepcion.recibir(paquete);
                        }
                    }
                }

                estado = "REGRESANDO";
                Thread.sleep(2000);
                estado = "DISPONIBLE";

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void cargarPaquetesDeExpedicion() throws InterruptedException {
        synchronized (centro.expedicion) {
            ListaEnlazada<Paquete> listaExpedicion = centro.expedicion.getLista();
            int i = 0;
            while (i < listaExpedicion.getTamaño() && paquetesCargados.getTamaño() < capacidadMaxima) {
                Paquete p = listaExpedicion.obtener(i);
                if (p != null && p.getRutaAsignada().equals(this.rutaAsignada)) {
                    listaExpedicion.eliminar(p);
                    paquetesCargados.agregar(p);
                } else {
                    i++;
                }
            }
        }
    }

    public String getIdRepartidor() { return idRepartidor; }
    public String getEstadoRepartidor() { return estado; }
    public String getRutaAsignada() { return rutaAsignada; }
    public int getPaquetesActuales() { return paquetesCargados.getTamaño(); }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public int getTotalEntregados() { return totalEntregados; }
}