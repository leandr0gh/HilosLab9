package estructuras;


public class Paquete {
    private String codigo;
    private String cliente;
    private String direccion;
    private String ciudad;
    private double peso;
    private Prioridad prioridad;
    private EstadoPaquete estado;
    private String rutaAsignada;
    private int intentos;

    public Paquete(String codigo, String cliente, String direccion, String ciudad, double peso, Prioridad prioridad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.peso = peso;
        this.prioridad = prioridad;
        this.estado = EstadoPaquete.RECIBIDO;
        this.rutaAsignada = "PENDIENTE";
        this.intentos = 0;
    }

    public String getCodigo() { return codigo; }
    public Prioridad getPrioridad() { return prioridad; }
    public EstadoPaquete getEstado() { return estado; }
    public void setEstado(EstadoPaquete estado) { this.estado = estado; }
    public String getRutaAsignada() { return rutaAsignada; }
    public void setRutaAsignada(String rutaAsignada) { this.rutaAsignada = rutaAsignada; }
    public int getIntentos() { return intentos; }
    public void registrarIntentoFallido() { this.intentos++; }
    public double getPeso() { return peso; }
    public String getCiudad() { return ciudad; }

    @Override
    public String toString() {
        return codigo + " [" + prioridad + "]";
    }
}