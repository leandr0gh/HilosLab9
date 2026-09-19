
package estructuras;

/**
 *
 * @author Leandro
 * basicamente chele esta onda es para que controle como se mueve la vaina
 */
public class ControlSimulacion {
    private volatile boolean corriendo = true;
    private volatile boolean pausado = false;

    public synchronized void pausar() {
        pausado = true;
    }

    public synchronized void reanudar() {
        pausado = false;
        notifyAll();
    }

    public void detener() {
        corriendo = false;
    }

    public synchronized void verificarPausa() throws InterruptedException {
        while (pausado) {
            wait();
        }
    }

    public boolean isCorriendo() { return corriendo; }
    public boolean isPausado() { return pausado; }
}
