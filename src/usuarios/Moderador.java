package usuarios;

public class Moderador extends Usuario {

    private int nivel;

    public Moderador(String id, String nombre, String username, String email, int nivel) {
        super(id, nombre, username, email);
        this.nivel = nivel;
    }

    @Override
    public String getRol() { return "MODERADOR"; }

    @Override
    public boolean puedePublicar() { return true; }

    public boolean puedeBanear() { return nivel >= 2; }

    @Override
    public String toString() {
        return super.toString() + " | Nivel: " + nivel;
    }

    public int getNivel() { return nivel; }
}
