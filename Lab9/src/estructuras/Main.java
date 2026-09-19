
package estructuras;

import javax.swing.SwingUtilities;

/**
 *
 * @author Leandro
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        VentanaPrincipal app = new VentanaPrincipal();
        app.setVisible(true);
        });
    }
}
