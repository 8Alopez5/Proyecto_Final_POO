package usuarios;

public class UsuarioEstandar extends Usuario {

    public UsuarioEstandar(String id, String nombre, String username, String email) {
        super(id, nombre, username, email);
    }

    @Override
    public String getRol() { return "ESTANDAR"; }

    @Override
    public boolean puedePublicar() { return true; }
}
