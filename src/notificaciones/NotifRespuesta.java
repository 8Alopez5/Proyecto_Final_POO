package notificaciones;

public class NotifRespuesta extends Notificacion {

    private final String remitenteUsername;
    private final String contenidoId;

    public NotifRespuesta(String destinatarioId, String remitenteUsername, String contenidoId) {
        super(destinatarioId);
        this.remitenteUsername = remitenteUsername;
        this.contenidoId       = contenidoId;
    }

    @Override
    public String getMensaje() {
        return "@" + remitenteUsername + " respondió tu publicación [" + contenidoId + "]";
    }

    @Override
    public String getTipo() { return "RESPUESTA"; }
}
