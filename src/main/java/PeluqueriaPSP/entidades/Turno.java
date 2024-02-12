package PeluqueriaPSP.entidades;

import java.sql.Time;
import java.util.Date;

public class Turno {
    private Long idTurno;
    private Long idPeluquero; // Cambiado a Long para simplificar la relación sin JPA
    private String nombre;
    private Date fecha;
    private Time hora;
    private Integer duracion;
    private Boolean reservado;

    // Constructor vacío
    public Turno() {}

    // Constructor con parámetros
    public Turno(Long idTurno, Long idPeluquero, String nombre, Date fecha, Time hora, Integer duracion, Boolean reservado) {
        this.idTurno = idTurno;
        this.idPeluquero = idPeluquero;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.reservado = reservado;
    }

    // Getters y setters

    public Long getIdTurno() { return idTurno; }

    public void setIdTurno(Long idTurno) { this.idTurno = idTurno; }

    public Long getIdPeluquero() { return idPeluquero; }

    public void setIdPeluquero(Long idPeluquero) { this.idPeluquero = idPeluquero; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFecha() { return fecha; }

    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Time getHora() { return hora; }

    public void setHora(Time hora) { this.hora = hora; }

    public Integer getDuracion() { return duracion; }

    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public Boolean getReservado() { return reservado; }

    public void setReservado(Boolean reservado) { this.reservado = reservado; }
}