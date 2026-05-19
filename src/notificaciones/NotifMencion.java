package notificaciones;

public class NotifMencion extends Notificacion {

    private final String remitenteUsername;
    private final String contenidoId;

    public NotifMencion(String destinatarioId, String remitenteUsername, String contenidoId) {
        super(destinatarioId);
        this.remitenteUsername = remitenteUsername;
        this.contenidoId       = contenidoId;
    }

    @Override
    public String getMensaje() {
        return "@" + remitenteUsername + " te mencionó en [" + contenidoId + "]";
    }

    @Override
    public String getTipo() { return "MENCION"; }
}
