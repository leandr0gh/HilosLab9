package estructuras;

/**
 *
 * @author Leandro
 */
public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamano;

    public ListaEnlazada() {
        cabeza = null;
        tamano = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        tamano++;
    }

    public int tamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public T obtener(int indice) {

        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException(
                    "Índice fuera de rango: " + indice
            );
        }

        Nodo<T> actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    public boolean eliminar(T dato) {

        if (cabeza == null) {
            return false;
        }


        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamano--;
            return true;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {

            if (actual.getSiguiente().getDato().equals(dato)) {

                actual.setSiguiente(
                        actual.getSiguiente().getSiguiente()
                );

                tamano--;
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    public T buscar(T dato) {

        Nodo<T> actual = cabeza;

        while (actual != null) {

            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public void limpiar() {
        cabeza = null;
        tamano = 0;
    }

    public void recorrer() {

        Nodo<T> actual = cabeza;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
