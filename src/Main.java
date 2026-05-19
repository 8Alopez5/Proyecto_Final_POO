import usuarios.Usuario;
import usuarios.UsuarioEstandar;
import usuarios.UsuarioVerificado;
import usuarios.Moderador;
import gestores.GestorUsuarios;
import gestores.GestorContenido;
import contenido.Contenido;
import contenido.Hilo;
import contenido.Post;
import excepciones.UsuarioDuplicadoException;
import excepciones.UsuarioNoEncontradoException;
import excepciones.OperacionInvalidaException;
import excepciones.ContenidoInvalidoException;
import excepciones.ContenidoNoEncontradoException;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   THREADS - PROYECTO FINAL POO");
        System.out.println("=========================================");





        System.out.println("\n=== MODULO 1: USUARIOS Y RELACIONES ===");
        System.out.println("=== Jacobo Ochoa                     ===\n");

        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstancia();


        System.out.println("-- Registro de usuarios --");

        Usuario jacobo  = new UsuarioEstandar("u001", "Jacobo Ochoa",   "jacobo.ochoa", "jacobo@eia.edu.co");
        Usuario matias  = new UsuarioEstandar("u002", "Matias Battistolo",   "mbattistolo",    "matias@eia.edu.co");
        Usuario sebas   = new UsuarioEstandar("u003", "Sebastian Ramirez", "sebas.rmz",   "sebas@eia.edu.co");
        Usuario artista = new UsuarioVerificado("u004", "Bad Bunny",    "badbunny",     "bb@label.com", "Musica");
        Usuario mod     = new Moderador("u005", "Carlos Perez",         "carlos.mod",   "mod@threads.com", 2);

        try {
            gestorUsuarios.registrarUsuario(jacobo);
            gestorUsuarios.registrarUsuario(matias);
            gestorUsuarios.registrarUsuario(sebas);
            gestorUsuarios.registrarUsuario(artista);
            gestorUsuarios.registrarUsuario(mod);
        } catch (UsuarioDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("\n-- Intentar registrar usuario duplicado --");
        try {
            gestorUsuarios.registrarUsuario(new UsuarioEstandar("u001", "Otro", "jacobo.ochoa", "otro@email.com"));
        } catch (UsuarioDuplicadoException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }


        System.out.println("\n-- Relaciones --");
        try {
            gestorUsuarios.seguir(jacobo, artista);
            gestorUsuarios.seguir(matias, artista);
            gestorUsuarios.seguir(sebas, artista);
            gestorUsuarios.seguir(jacobo, matias);
        } catch (OperacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("\n-- Intentar seguirse a si mismo --");
        try {
            gestorUsuarios.seguir(jacobo, jacobo);
        } catch (OperacionInvalidaException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }


        System.out.println("\n-- Bloquear usuario --");
        try {
            gestorUsuarios.bloquear(artista, sebas);
            gestorUsuarios.seguir(sebas, artista);
        } catch (OperacionInvalidaException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }


        System.out.println("\n-- Buscar usuario --");
        try {
            System.out.println("Encontrado: " + gestorUsuarios.buscarPorUsername("badbunny"));
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            gestorUsuarios.buscarPorUsername("fantasma");
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }


        System.out.println("\n-- Listado por nombre --");
        for (Usuario u : gestorUsuarios.listarPorNombre()) System.out.println("  " + u);

        System.out.println("\n-- Listado por seguidores --");
        for (Usuario u : gestorUsuarios.listarPorSeguidores()) System.out.println("  " + u);

        gestorUsuarios.mostrarMetricas();








        System.out.println("=== MODULO 2: CONTENIDO E INTERACCIONES ===");
        System.out.println("=== Matias Battistolo                   ===\n");

        GestorContenido gestorContenido = new GestorContenido();


        System.out.println("-- Publicar contenido --");

        Post postArtista = null;
        Post postJacobo  = null;
        Contenido hilo   = null;

        try {
            postArtista = gestorContenido.publicarPost(
                artista, "Nuevo album en camino. No les voy a decir mas nada.");

            postJacobo = gestorContenido.publicarPost(
                jacobo, "Termine el modulo 1 del proyecto final. " +
                        "Singleton + colecciones + relaciones. Todo funciona!");

            gestorContenido.publicarPost(
                matias, "Trabajando en el Factory Method. Que patron tan limpio.");

            hilo = gestorContenido.publicarHilo(
                artista, "Hilo sobre mi proceso creativo. Abran:", "Proceso creativo");

            Hilo hiloConcreto = (Hilo) hilo;
            hiloConcreto.agregarParte("1/ Todo empieza con una melodia que no me puedo sacar de la cabeza.");
            hiloConcreto.agregarParte("2/ Luego vienen las letras. A veces tardo dias en una sola linea.");
            hiloConcreto.agregarParte("3/ La produccion es lo ultimo. FIN.");
            System.out.println("Hilo con " + hiloConcreto.getCantidadPartes() + " partes.");

        } catch (ContenidoInvalidoException e) {
            System.out.println("Error al publicar: " + e.getMessage());
        }


        System.out.println("\n-- Responder y Repostear --");
        try {
            gestorContenido.responder(jacobo, "FUEGO! Ya estamos listos.", postArtista.getId());
            gestorContenido.responder(sebas,  "El punto 2 es exactamente lo que siento al programar.", hilo.getId());
            gestorContenido.repostear(matias,  postArtista.getId());
            gestorContenido.quoteRepost(sebas, "Esto lo tiene que leer todo el mundo.", postArtista.getId());
            gestorContenido.responder(mod,     "Buen trabajo, Jacobo. Documenten el patron.", postJacobo.getId());
        } catch (ContenidoInvalidoException | ContenidoNoEncontradoException | OperacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("\n-- Interacciones --");
        try {
            gestorContenido.darLike(jacobo,  postArtista.getId());
            gestorContenido.darLike(matias,  postArtista.getId());
            gestorContenido.darLike(sebas,   postArtista.getId());
            gestorContenido.darLike(mod,     postArtista.getId());
            gestorContenido.darLike(artista, postJacobo.getId());
            gestorContenido.darLike(jacobo,  hilo.getId());
            gestorContenido.darLike(sebas,   hilo.getId());
            gestorContenido.guardar(jacobo,  hilo.getId());
            gestorContenido.guardar(matias,  postArtista.getId());
            gestorContenido.quitarLike(jacobo, postArtista.getId());
        } catch (ContenidoNoEncontradoException | OperacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("\n-- Errores controlados de contenido --");

        System.out.println("[TEST] Post vacio:");
        try { gestorContenido.publicarPost(jacobo, ""); }
        catch (ContenidoInvalidoException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("[TEST] Post > 500 caracteres:");
        try { gestorContenido.publicarPost(jacobo, "A".repeat(501)); }
        catch (ContenidoInvalidoException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("[TEST] Like duplicado:");
        try { gestorContenido.darLike(matias, postArtista.getId()); }
        catch (ContenidoNoEncontradoException | OperacionInvalidaException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("[TEST] Repostear propio contenido:");
        try { gestorContenido.repostear(artista, postArtista.getId()); }
        catch (ContenidoInvalidoException | ContenidoNoEncontradoException | OperacionInvalidaException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("[TEST] Contenido inexistente:");
        try { gestorContenido.buscarPorId("X9999"); }
        catch (ContenidoNoEncontradoException e) { System.out.println("  " + e.getMessage()); }


        System.out.println("\n-- Filtrado y Ordenamiento --");

        List<Contenido> soloPosts = gestorContenido.filtrarPorTipo("POST");
        System.out.println("Posts en el sistema: " + soloPosts.size());

        System.out.println("\nTop 3 por popularidad:");
        List<Contenido> top3 = gestorContenido.topContenidos(3);
        for (int i = 0; i < top3.size(); i++)
            System.out.println("  " + (i + 1) + ". " + top3.get(i));

        System.out.println("\nContenido de @badbunny:");
        for (Contenido c : gestorContenido.contenidoDeUsuario(artista))
            System.out.println("  " + c);


        System.out.println("\n-- Historial de interacciones --");
        try { gestorContenido.mostrarHistorial(postArtista.getId()); }
        catch (ContenidoNoEncontradoException e) { System.out.println("Error: " + e.getMessage()); }


        System.out.println("-- Metricas finales --");
        gestorContenido.mostrarMetricas();
        gestorUsuarios.mostrarMetricas();


        System.out.println("=========================================");
        System.out.println("  DEMO COMPLETA");
        System.out.println("  Modulo 1 | Singleton  | Jacobo Ochoa");
        System.out.println("  Modulo 2 | Factory Method | Matias Battistolo");
        System.out.println("  Usuarios:  " + gestorUsuarios.getTotalUsuarios());
        System.out.println("  Contenido: " + gestorContenido.getTotalContenido());
        System.out.println("=========================================");
    }
}
