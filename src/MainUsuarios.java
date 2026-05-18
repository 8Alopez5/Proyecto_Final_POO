import usuarios.Usuario;
import usuarios.UsuarioEstandar;
import usuarios.UsuarioVerificado;
import usuarios.Moderador;
import gestores.GestorUsuarios;
import excepciones.UsuarioDuplicadoException;
import excepciones.UsuarioNoEncontradoException;
import excepciones.OperacionInvalidaException;

public class MainUsuarios {

    public static void main(String[] args) {

        GestorUsuarios gestor = GestorUsuarios.getInstancia();

        System.out.println("=== MODULO DE USUARIOS - Jacobo Ochoa ===\n");

        // Crear usuarios
        Usuario jacobo = new UsuarioEstandar("u001", "Jacobo Ochoa", "jacobo.ochoa", "jacobo@eia.edu.co");
        Usuario matias = new UsuarioEstandar("u002", "Matias Lopez", "matias.lp", "matias@eia.edu.co");
        Usuario sebas  = new UsuarioEstandar("u003", "Sebastian Rios", "sebas.rios", "sebas@eia.edu.co");
        Usuario artista = new UsuarioVerificado("u004", "Bad Bunny", "badbunny", "bb@label.com", "Musica");
        Usuario mod = new Moderador("u005", "Carlos Admin", "carlos.mod", "mod@threads.com", 2);

        // Registrar usuarios
        System.out.println("-- Registro de usuarios --");
        try {
            gestor.registrarUsuario(jacobo);
            gestor.registrarUsuario(matias);
            gestor.registrarUsuario(sebas);
            gestor.registrarUsuario(artista);
            gestor.registrarUsuario(mod);
        } catch (UsuarioDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Intentar registrar duplicado
        System.out.println("\n-- Intentar registrar usuario duplicado --");
        try {
            gestor.registrarUsuario(new UsuarioEstandar("u001", "Otro", "jacobo.ochoa", "otro@email.com"));
        } catch (UsuarioDuplicadoException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // Seguir usuarios
        System.out.println("\n-- Relaciones --");
        try {
            gestor.seguir(jacobo, artista);
            gestor.seguir(matias, artista);
            gestor.seguir(sebas, artista);
            gestor.seguir(jacobo, matias);
            artista.registrarPublicacion();
            artista.registrarPublicacion();
            jacobo.registrarPublicacion();
        } catch (OperacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Intentar seguirse a si mismo
        System.out.println("\n-- Intentar seguirse a si mismo --");
        try {
            gestor.seguir(jacobo, jacobo);
        } catch (OperacionInvalidaException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // Bloquear
        System.out.println("\n-- Bloquear usuario --");
        try {
            gestor.bloquear(artista, sebas);
            gestor.seguir(sebas, artista);
        } catch (OperacionInvalidaException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // Buscar usuario
        System.out.println("\n-- Buscar usuario --");
        try {
            Usuario encontrado = gestor.buscarPorUsername("badbunny");
            System.out.println("Encontrado: " + encontrado);
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Metricas y listado final
        gestor.mostrarMetricas();
        gestor.mostrarTodos();
    }
}
