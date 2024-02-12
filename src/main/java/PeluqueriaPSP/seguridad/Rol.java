package PeluqueriaPSP.seguridad;

public class Rol {
    private Integer idRol;
    private String nombreRol; // Cambiado a String para simplificar el manejo sin JPA

    // Constructor vacío
    public Rol() {}

    // Constructor con parámetros
    public Rol(Integer idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    // Getters y setters


    public Integer getIdRol() { return idRol; }

    public void setIdRol(Integer idRol) { this.idRol = idRol; }

    public String getNombreRol() { return nombreRol; }

    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }

    // Tipos de rol
    public enum NombreRol { ADMINISTRADOR, PELUQUERO, CLIENTE }
}
