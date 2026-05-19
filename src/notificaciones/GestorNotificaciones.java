package notificaciones;

import java.util.*;
import java.util.stream.Collectors;

public class GestorNotificaciones implements ISujetoNotificacion {


    private final Map<String, IObservadorNotificacion> observadores;


    private final Map<String, PreferenciasNotificacion> preferencias;


    private final List<Notificacion> historial;


    private final Map<String, List<Notificacion>> bandejas;

    public GestorNotificaciones() {
        this.observadores = new HashMap<>();
        this.preferencias = new HashMap<>();
        this.historial    = new ArrayList<>();
        this.bandejas     = new HashMap<>();
    }



    @Override
    public void suscribir(String usuarioId, IObservadorNotificacion observador) {
        observadores.put(usuarioId, observador);
        preferencias.putIfAbsent(usuarioId, new PreferenciasNotificacion());
        bandejas.putIfAbsent(usuarioId, new ArrayList<>());
        System.out.println("  [Observer] @" + usuarioId + " suscrito al sistema de notificaciones.");
    }

    @Override
    public void desuscribir(String usuarioId) {
        observadores.remove(usuarioId);
        System.out.println("  [Observer] @" + usuarioId + " desuscrito.");
    }


    @Override
    public void notificar(Notificacion notificacion) {
        String destId = notificacion.getDestinatarioId();
        historial.add(notificacion);

        IObservadorNotificacion obs = observadores.get(destId);
        if (obs == null) return;

        PreferenciasNotificacion prefs = preferencias.getOrDefault(destId, new PreferenciasNotificacion());
        if (!prefs.acepta(notificacion.getTipo())) {
            System.out.println("  [Notif bloqueada por preferencias] " + notificacion.getMensaje());
            return;
        }

        bandejas.computeIfAbsent(destId, k -> new ArrayList<>()).add(notificacion);
        obs.actualizar(notificacion);
    }




    public void eventoNuevoPost(String autorId, String autorUsername,
                                 String tipoContenido, String contenidoId,
                                 List<String> seguidoresIds) {
        seguidoresIds.forEach(segId ->
            notificar(new NotifNuevoPost(segId, autorUsername, tipoContenido, contenidoId))
        );
    }


    public void eventoLike(String autorContenidoId, String remitenteUsername, String contenidoId) {
        notificar(new NotifLike(autorContenidoId, remitenteUsername, contenidoId));
    }


    public void eventoRespuesta(String autorContenidoId, String remitenteUsername, String contenidoId) {
        notificar(new NotifRespuesta(autorContenidoId, remitenteUsername, contenidoId));
    }


    public void eventoMencion(String mencionadoId, String remitenteUsername, String contenidoId) {
        notificar(new NotifMencion(mencionadoId, remitenteUsername, contenidoId));
    }


    public void eventoRepost(String autorContenidoId, String remitenteUsername, String contenidoId) {
        notificar(new NotifRepost(autorContenidoId, remitenteUsername, contenidoId));
    }




    public List<Notificacion> getBandeja(String usuarioId) {
        return bandejas.getOrDefault(usuarioId, Collections.emptyList());
    }


    public List<Notificacion> getNoLeidas(String usuarioId) {
        return getBandeja(usuarioId).stream()
                .filter(n -> !n.isLeida())
                .collect(Collectors.toList());
    }


    public void marcarTodasLeidas(String usuarioId) {
        getBandeja(usuarioId).forEach(Notificacion::marcarLeida);
    }


    public PreferenciasNotificacion getPreferencias(String usuarioId) {
        return preferencias.computeIfAbsent(usuarioId, k -> new PreferenciasNotificacion());
    }


    public void mostrarMetricas() {
        System.out.println("\n--- Métricas de Notificaciones ---");
        System.out.println("  Total notificaciones generadas : " + historial.size());
        System.out.println("  Usuarios suscritos             : " + observadores.size());


        Map<String, Long> porTipo = historial.stream()
                .collect(Collectors.groupingBy(Notificacion::getTipo, Collectors.counting()));
        System.out.println("  Por tipo:");
        porTipo.forEach((tipo, count) -> System.out.println("    " + tipo + ": " + count));

        long noLeidas = historial.stream().filter(n -> !n.isLeida()).count();
        System.out.println("  Sin leer en el sistema         : " + noLeidas);
    }
}
