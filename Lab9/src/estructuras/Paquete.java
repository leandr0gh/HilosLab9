package estructuras;

import estructuras.EstadoPaquete;

public class Paquete {

    private String codigo;
    private String cliente;
    private String direccion;
    private String ciudad;
    private double peso;

    private Prioridad prioridad;
    private EstadoPaquete estado;

    private String ruta;
    private int intentos;

    public Paquete(String codigo, String cliente, String direccion,
                   String ciudad, double peso, Prioridad prioridad) {

        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.peso = peso;
        this.prioridad = prioridad;

        this.estado = EstadoPaquete.RECIBIDO;
        this.ruta = null;
        this.intentos = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getPeso() {
        return peso;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public String getRuta() {
        return ruta;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void aumentarIntentos() {
        intentos++;
    }

    @Override
    public String toString() {
        return codigo + " | "
                + cliente + " | "
                + ciudad + " | "
                + peso + " kg | "
                + prioridad + " | "
                + estado;
    }
}