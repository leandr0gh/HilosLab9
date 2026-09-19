
package estructuras;

/**
 *
 * @author Leandro
 */
public class ClasificadorThread extends Thread {
    private final String nombreClasificador;
    private final CentroLogistico centro;
    private final ControlSimulacion control;

    public ClasificadorThread(String nombre, CentroLogistico centro, ControlSimulacion control) {
        this.nombreClasificador = nombre;
        this.centro = centro;
        this.control = control;
    }

    @Override
    public void run() {
        while (control.isCorriendo()) {
            try {
                control.verificarPausa();

                Paquete paquete = centro.recepcion.procesar();
                paquete.setEstado(EstadoPaquete.CLASIFICANDO);
                centro.registrarEvento(paquete.getCodigo() + " tomado por " + nombreClasificador);

                String ruta = determinarRuta(paquete.getCiudad());
                paquete.setRutaAsignada(ruta);

                Thread.sleep(1200);

                paquete.setEstado(EstadoPaquete.CLASIFICADO);
                centro.registrarEvento(paquete.getCodigo() + " clasificado → " + ruta);

                centro.empaquetado.recibir(paquete);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private String determinarRuta(String ciudad) {
        switch (ciudad) {
            case "Naples":
            case "San Pedro Sula":
                return "Ruta 1";
            case "Tegucigalpa":
                return "Ruta 2";
            case "Sant Martí":
                return "Ruta 3";
            case "Badalona":
                return "Ruta 4";
            default:
                return "Ruta 1";
        }
    }
}
