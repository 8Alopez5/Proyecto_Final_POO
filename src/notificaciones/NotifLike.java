package notificaciones;

public class NotifLike extends Notificacion {

    private final String remitenteUsername;
    private final String contenidoId;

    public NotifLike(String destinatarioId, String remitenteUsername, String contenidoId) {
        super(destinatarioId);
        this.remitenteUsername = remitenteUsername;
        this.contenidoId       = contenidoId;
    }

    @Override
    public String getMensaje() {
        return "@" + remitenteUsername + " le dio like a tu publicación [" + contenidoId + "]";
    }

    @Override
    public String getTipo() { return "LIKE"; }
}
