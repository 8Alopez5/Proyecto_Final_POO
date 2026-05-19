package notificaciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Notificacion {

    private static int contadorId = 1;

    private final String id;
    private final String destinatarioId;
    private final LocalDateTime fechaCreacion;
    private boolean leida;

    public Notificacion(String destinatarioId) {
        this.id = "N" + String.format("%04d", contadorId++);
        this.destinatarioId = destinatarioId;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }


    public abstract String getMensaje();


    public abstract String getTipo();

    public void marcarLeida() {
        this.leida = true;
    }

    public String getId()              { return id; }
    public String getDestinatarioId()  { return destinatarioId; }
    public LocalDateTime getFecha()    { return fechaCreacion; }
    public boolean isLeida()           { return leida; }

    @Override
    public String toString() {
        String estado = leida ? "[leída]" : "[nueva]";
        String fecha  = fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
        return String.format("%s %s [%s] %s — %s", id, estado, fecha, getTipo(), getMensaje());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notificacion)) return false;
        return id.equals(((Notificacion) o).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}
