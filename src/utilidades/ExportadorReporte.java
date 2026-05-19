package utilidades;

import usuarios.Usuario;
import contenido.Contenido;
import notificaciones.Notificacion;
import gestores.GestorUsuarios;
import gestores.GestorContenido;
import notificaciones.GestorNotificaciones;

import java.util.List;

public final class ExportadorReporte {

    private ExportadorReporte() {}

    public static void reporteCompleto(GestorUsuarios gestorUsuarios,
                                        GestorContenido gestorContenido,
                                        GestorNotificaciones gestorNotif) {

        System.out.println("\n===================================================");
        System.out.println("       REPORTE DE ACTIVIDAD — THREADS");
        System.out.println("===================================================");

        System.out.println("\n[USUARIOS]");
        System.out.println("  Total registrados: " + gestorUsuarios.getTotalUsuarios());
        System.out.println("  Top 3 por seguidores:");
        List<Usuario> top3 = gestorUsuarios.listarPorSeguidores();
        int max3 = Math.min(3, top3.size());
        for (int i = 0; i < max3; i++) {
            Usuario u = top3.get(i);
            System.out.println("    " + (i + 1) + ". @" + u.getUsername() + " (" + u.getSeguidores() + " seguidores)");
        }

        System.out.println("\n[CONTENIDO]");
        System.out.println("  Total publicaciones: " + gestorContenido.getTotalContenido());
        System.out.println("  Top 3 por popularidad:");
        List<Contenido> topContenido = gestorContenido.topContenidos(3);
        for (int i = 0; i < topContenido.size(); i++) {
            Contenido c = topContenido.get(i);
            System.out.println("    " + (i + 1) + ". [" + c.getId() + "] @" + c.getAutor().getUsername() + " | " + c.getLikes() + " likes");
        }

        System.out.println("\n[NOTIFICACIONES]");
        gestorNotif.mostrarMetricas();

        System.out.println("\n[HISTORIAL DE OPERACIONES]");
        HistorialOperaciones hist = HistorialOperaciones.getInstancia();
        System.out.println("  Total operaciones registradas: " + hist.getTotalOperaciones());

        System.out.println("\n===================================================");
        System.out.println("  Fin del reporte.");
        System.out.println("===================================================");
    }

    public static void reporteBandeja(String username, List<Notificacion> bandeja) {
        System.out.println("\n--- Bandeja de @" + username + " ---");
        if (bandeja.isEmpty()) {
            System.out.println("  (sin notificaciones)");
            return;
        }
        for (Notificacion n : bandeja) {
            System.out.println("  " + n);
        }
    }
}
