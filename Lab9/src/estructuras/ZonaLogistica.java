
package estructuras;

/**
 *
 * @author Leandro
 */
public class ZonaLogistica {
    private String nombre;
    private ListaEnlazada<Paquete> lista;
    private int capacidadMaxima;

    public ZonaLogistica(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.lista = new ListaEnlazada<>();
    }

    public synchronized void recibir(Paquete paquete) throws InterruptedException {
        while (lista.getTamaño() >= capacidadMaxima) {
            wait();
        }
        lista.agregar(paquete);
        notifyAll();
    }

    public synchronized Paquete procesar() throws InterruptedException {
        while (lista.estaVacia()) {
            wait();
        }

        Paquete paquetePrioritario = lista.obtener(0);
        for (int i = 1; i < lista.getTamaño(); i++) {
            Paquete actual = lista.obtener(i);
            if (actual.getPrioridad().getNivel() > paquetePrioritario.getPrioridad().getNivel()) {
                paquetePrioritario = actual;
            }
        }

        lista.eliminar(paquetePrioritario);
        notifyAll();
        return paquetePrioritario;
    }
    
    public ListaEnlazada<Paquete> getLista() { return lista; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public int getOcupacion() { return lista.getTamaño(); }
    public String getNombre() { return nombre; }
    
    public synchronized void vaciar() {
        lista.vaciar();
    }
}
