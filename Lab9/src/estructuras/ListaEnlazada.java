package estructuras;

/**
 *
 * @author Leandro
 */
public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamaño++;
    }

    public T eliminarPrimero() {
        if (estaVacia()) return null;
        
        T dato = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamaño--;
        
        if (estaVacia()) {
            cola = null;
        }
        return dato;
    }

    public boolean eliminar(T dato) {
        if (estaVacia()) return false;

        if (cabeza.dato.equals(dato)) {
            eliminarPrimero();
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(dato)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            if (actual.siguiente == cola) {
                cola = actual;
            }
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            return true;
        }
        return false;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) return null;
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public int getTamaño() { return tamaño; }
    public boolean estaVacia() { return tamaño == 0; }
    
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
}