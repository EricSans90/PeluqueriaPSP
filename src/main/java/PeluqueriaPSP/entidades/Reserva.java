package PeluqueriaPSP.entidades;

public class Reserva {
    private Long idReserva;
    private Long idUsuario; // Cambiado a Long para simplificar la relación sin JPA
    private Long idTurno; // Cambiado a Long para simplificar la relación sin JPA

    // Constructor vacío
    public Reserva() {}

    // Constructor con parámetros
    public Reserva(Long idReserva, Long idUsuario, Long idTurno) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idTurno = idTurno;
    }

    // Getters y setters

    public Long getIdReserva() { return idReserva; }

    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }

    public Long getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdTurno() { return idTurno; }

    public void setIdTurno(Long idTurno) { this.idTurno = idTurno; }
}