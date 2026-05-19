package interacciones;

import contenido.Contenido;
import usuarios.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Interaccion {

    public enum Tipo {
        LIKE, QUITAR_LIKE, GUARDAR, REPORTAR
    }

    private Usuario usuario;
    private Contenido contenido;
    private Tipo tipo;
    private Date fecha;
    private String detalle;

    public Interaccion(Usuario usuario, Contenido contenido, Tipo tipo) {
        this.usuario   = usuario;
        this.contenido = contenido;
        this.tipo      = tipo;
        this.fecha     = new Date();
    }

    public Interaccion(Usuario usuario, Contenido contenido, Tipo tipo, String detalle) {
        this(usuario, contenido, tipo);
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        String base = "@" + usuario.getUsername() + " -> " + tipo +
                      " -> #" + contenido.getId();
        return detalle != null ? base + " (\"" + detalle + "\")" : base;
    }

    public Usuario getUsuario()   { return usuario; }
    public Contenido getContenido() { return contenido; }
    public Tipo getTipo()         { return tipo; }
    public Date getFecha()        { return fecha; }
    public String getDetalle()    { return detalle; }
}
