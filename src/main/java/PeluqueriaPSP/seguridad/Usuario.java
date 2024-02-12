package PeluqueriaPSP.seguridad;

public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String contrasenya;
    private Integer idRol;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(Long idUsuario, String nombre, String email, String contrasenya, Integer idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasenya = contrasenya;
        this.idRol = idRol;
    }

    // Getters y setters

    public Long getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getContrasenya() { return contrasenya; }

    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

    public Integer getIdRol() { return idRol; }

    public void setIdRol(Integer idRol) { this.idRol = idRol; }
}