package utilidades;

import usuarios.Usuario;
import contenido.Contenido;
import notificaciones.Notificacion;
import gestores.GestorUsuarios;
import gestores.GestorContenido;
import notificaciones.GestorNotificaciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class ExportadorReporte {

    private ExportadorReporte() {}

    private static final String LINEA = "─".repeat(55);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static void reporteCompleto(GestorUsuarios gestorUsuarios,
                                        GestorContenido gestorContenido,
                                        GestorNotificaciones gestorNotif) {
        String fechaReporte = LocalDateTime.now().format(FMT);

        System.out.println("\n" + LINEA);
        System.out.println("       REPORTE DE ACTIVIDAD — THREADS");
        System.out.println("       Generado: " + fechaReporte);
        System.out.println(LINEA);


        System.out.println("\n[USUARIOS]");
        System.out.printf("  Total registrados : %d%n", gestorUsuarios.getTotalUsuarios());
        System.out.println("  Top 3 por seguidores:");
        List<Usuario> top3 = gestorUsuarios.listarPorSeguidores();
        int max3 = Math.min(3, top3.size());
        for (int i = 0; i < max3; i++) {
            Usuario u = top3.get(i);
            System.out.printf("    %d. %-20s (%d seguidores)%n",
                    i + 1, u.getUsername(), u.getSeguidores().size());
        }


        System.out.println("\n[CONTENIDO]");
        System.out.printf("  Total publicaciones : %d%n", gestorContenido.getTotalContenido());
        System.out.println("  Top 3 por popularidad:");
        List<Contenido> topContenido = gestorContenido.topContenidos(3);
        for (int i = 0; i < topContenido.size(); i++) {
            Contenido c = topContenido.get(i);
            System.out.printf("    %d. [%s] %-15s | %d likes%n",
                    i + 1, c.getId(), c.getAutor().getUsername(), c.getLikes());
        }


        System.out.println("\n[NOTIFICACIONES]");
        gestorNotif.mostrarMetricas();


        System.out.println("\n[HISTORIAL DE OPERACIONES]");
        HistorialOperaciones hist = HistorialOperaciones.getInstancia();
        System.out.printf("  Total operaciones registradas: %d%n", hist.getTotalOperaciones());

        System.out.println("\n" + LINEA);
        System.out.println("  Fin del reporte.");
        System.out.println(LINEA);
    }


    public static void reporteBandeja(String username, List<Notificacion> bandeja) {
        System.out.println("\n--- Bandeja de @" + username + " ---");
        if (bandeja.isEmpty()) {
            System.out.println("  (sin notificaciones)");
            return;
        }
        bandeja.forEach(n -> System.out.println("  " + n));
    }
}
