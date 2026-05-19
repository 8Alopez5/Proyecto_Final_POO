package notificaciones;

import java.util.EnumSet;
import java.util.Set;

public class PreferenciasNotificacion {

    public enum TipoNotif {
        NUEVO_POST, LIKE, RESPUESTA, MENCION, REPOST
    }


    private final Set<TipoNotif> tiposActivos;

    public PreferenciasNotificacion() {
        this.tiposActivos = EnumSet.allOf(TipoNotif.class);
    }

    public void activar(TipoNotif tipo)    { tiposActivos.add(tipo); }
    public void desactivar(TipoNotif tipo) { tiposActivos.remove(tipo); }

    public boolean acepta(String tipoString) {
        try {
            TipoNotif tipo = TipoNotif.valueOf(tipoString);
            return tiposActivos.contains(tipo);
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Preferencias activas: " + tiposActivos;
    }
}
