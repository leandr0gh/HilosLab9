
package estructuras;

/**
 *
 * @author Leandro
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CentroLogistico {
    public final ZonaLogistica recepcion = new ZonaLogistica("Recepción", 10);
    public final ZonaLogistica almacen = new ZonaLogistica("Almacén", 20);
    public final ZonaLogistica clasificacion = new ZonaLogistica("Clasificación", 10);
    public final ZonaLogistica empaquetado = new ZonaLogistica("Empaquetado", 8);
    public final ZonaLogistica expedicion = new ZonaLogistica("Expedición", 15);
    
    public final ListaEnlazada<Paquete> entregados = new ListaEnlazada<>();
    public final ListaEnlazada<Paquete> devueltos = new ListaEnlazada<>();
    
    private final ListaEnlazada<String> logEventos = new ListaEnlazada<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public synchronized void registrarEvento(String mensaje) {
        String tiempo = LocalTime.now().format(formatter);
        String registro = tiempo + " - " + mensaje;
        logEventos.agregar(registro);
        System.out.println(registro);
    }
    
    public ListaEnlazada<String> getLogEventos() {
        return logEventos;
    }
}
