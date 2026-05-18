package usuarios;

import java.util.Date;

public abstract class Usuario implements Comparable<Usuario> {

    private String id;
    private String nombre;
    private String username;
    private String email;
    private Date fechaRegistro;
    private int seguidores;
    private int seguidos;
    private int publicaciones;

    public Usuario(String id, String nombre, String username, String email) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.fechaRegistro = new Date();
        this.seguidores = 0;
        this.seguidos = 0;
        this.publicaciones = 0;
    }

    public abstract String getRol();
    public abstract boolean puedePublicar();

    public double calcularTasaInteraccion() {
        if (seguidores == 0) return 0.0;
        return (double) publicaciones / seguidores * 100;
    }

    public void agregarSeguidor() { seguidores = seguidores + 1; }
    public void quitarSeguidor() { if (seguidores > 0) seguidores = seguidores - 1; }
    public void agregarSeguido() { seguidos = seguidos + 1; }
    public void quitarSeguido() { if (seguidos > 0) seguidos = seguidos - 1; }
    public void registrarPublicacion() { publicaciones = publicaciones + 1; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario otro = (Usuario) o;
        return this.id.equals(otro.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return "[" + getRol() + "] @" + username + " | " + nombre +
               " | Seguidores: " + seguidores +
               " | Posts: " + publicaciones;
    }

    @Override
    public int compareTo(Usuario otro) {
        return this.username.compareTo(otro.username);
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public int getSeguidores() { return seguidores; }
    public int getSeguidos() { return seguidos; }
    public int getPublicaciones() { return publicaciones; }
}
