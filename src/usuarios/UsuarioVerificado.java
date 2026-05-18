package usuarios;

public class UsuarioVerificado extends Usuario {

    private String categoria;

    public UsuarioVerificado(String id, String nombre, String username, String email, String categoria) {
        super(id, nombre, username, email);
        this.categoria = categoria;
    }

    @Override
    public String getRol() { return "VERIFICADO"; }

    @Override
    public boolean puedePublicar() { return true; }

    @Override
    public String toString() {
        return super.toString() + " checkmark [" + categoria + "]";
    }

    public String getCategoria() { return categoria; }
}
