package notificaciones;

public interface ISujetoNotificacion {

    void suscribir(String usuarioId, IObservadorNotificacion observador);

    void desuscribir(String usuarioId);

    void notificar(Notificacion notificacion);
}
