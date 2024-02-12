package PeluqueriaPSP.entidades;

import java.util.Date;

public class Peluquero {
    private Long idPeluquero;
    private Long idUsuario;
    private String dni;
    private String nombre;
    private Date fechaNacimiento;

    // Constructor vacío
    public Peluquero() {}

    // Constructor con parámetros
    public Peluquero(Long idPeluquero, Long idUsuario, String dni, String nombre, Date fechaNacimiento) {
        this.idPeluquero = idPeluquero;
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y setters
    public Long getIdPeluquero() { return idPeluquero; }

    public void setIdPeluquero(Long idPeluquero) { this.idPeluquero = idPeluquero; }

    public Long getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getDni() { return dni;}

    public void setDni(String dni) {this.dni = dni;}

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaNacimiento() { return fechaNacimiento;}

    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento;}
}