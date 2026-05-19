package notificaciones;

public class NotifNuevoPost extends Notificacion {

    private final String autorUsername;
    private final String tipoContenido;
    private final String contenidoId;

    public NotifNuevoPost(String destinatarioId, String autorUsername,
                          String tipoContenido, String contenidoId) {
        super(destinatarioId);
        this.autorUsername  = autorUsername;
        this.tipoContenido  = tipoContenido;
        this.contenidoId    = contenidoId;
    }

    @Override
    public String getMensaje() {
        return "@" + autorUsername + " publicó un nuevo " + tipoContenido
                + " [" + contenidoId + "]";
    }

    @Override
    public String getTipo() { return "NUEVO_POST"; }
}
