package notificaciones;

public class NotifRepost extends Notificacion {

    private final String remitenteUsername;
    private final String contenidoId;

    public NotifRepost(String destinatarioId, String remitenteUsername, String contenidoId) {
        super(destinatarioId);
        this.remitenteUsername = remitenteUsername;
        this.contenidoId       = contenidoId;
    }

    @Override
    public String getMensaje() {
        return "@" + remitenteUsername + " reposteó tu publicación [" + contenidoId + "]";
    }

    @Override
    public String getTipo() { return "REPOST"; }
}
