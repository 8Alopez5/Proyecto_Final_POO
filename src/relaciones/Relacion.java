package relaciones;

import usuarios.Usuario;
import java.util.Date;

public class Relacion {

    public enum TipoRelacion {
        SEGUIR, BLOQUEAR, CONTACTO
    }

    private Usuario origen;
    private Usuario destino;
    private TipoRelacion tipo;
    private Date fecha;

    public Relacion(Usuario origen, Usuario destino, TipoRelacion tipo) {
        this.origen = origen;
        this.destino = destino;
        this.tipo = tipo;
        this.fecha = new Date();
    }

    @Override
    public String toString() {
        return "@" + origen.getUsername() + " -> " + tipo + " -> @" + destino.getUsername();
    }

    public Usuario getOrigen() { return origen; }
    public Usuario getDestino() { return destino; }
    public TipoRelacion getTipo() { return tipo; }
    public Date getFecha() { return fecha; }
}
