package contenido;

import usuarios.Usuario;
import java.util.Date;

public abstract class Contenido implements Comparable<Contenido> {

    private String id;
    private String texto;
    private Usuario autor;
    private Date fechaPublicacion;
    private int likes;
    private int reposts;
    private int respuestas;
    private boolean activo;

    public Contenido(String id, String texto, Usuario autor) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.fechaPublicacion = new Date();
        this.likes = 0;
        this.reposts = 0;
        this.respuestas = 0;
        this.activo = true;
    }


    public abstract String getTipo();


    public abstract boolean esValido();


    public double calcularPopularidad() {
        return likes + (reposts * 2.0) + (respuestas * 1.5);
    }

    public void darLike()         { likes++; }
    public void quitarLike()      { if (likes > 0) likes--; }
    public void registrarRepost() { reposts++; }
    public void registrarRespuesta() { respuestas++; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contenido)) return false;
        return this.id.equals(((Contenido) o).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        String preview = texto.length() > 50 ? texto.substring(0, 50) + "..." : texto;
        return "[" + getTipo() + "] @" + autor.getUsername() +
               " | \"" + preview + "\"" +
               " | Likes: " + likes +
               " | Reposts: " + reposts +
               " | Respuestas: " + respuestas +
               " | Pop: " + String.format("%.1f", calcularPopularidad());
    }


    @Override
    public int compareTo(Contenido otro) {
        return otro.fechaPublicacion.compareTo(this.fechaPublicacion);
    }


    public String getId()               { return id; }
    public String getTexto()            { return texto; }
    public Usuario getAutor()           { return autor; }
    public Date getFechaPublicacion()   { return fechaPublicacion; }
    public int getLikes()               { return likes; }
    public int getReposts()             { return reposts; }
    public int getRespuestas()          { return respuestas; }
    public boolean isActivo()           { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }
}
