package contenido;

import usuarios.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Hilo extends Contenido {

    public static final int MAX_PARTES = 25;
    public static final int MAX_CHARS_POR_PARTE = 500;

    private List<String> partes;
    private String titulo;

    public Hilo(String id, String textoPrincipal, Usuario autor, String titulo) {
        super(id, textoPrincipal, autor);
        this.partes = new ArrayList<String>();
        this.partes.add(textoPrincipal);
        this.titulo = titulo;
    }

    @Override
    public String getTipo() { return "HILO"; }

    @Override
    public boolean esValido() {
        if (partes.isEmpty()) return false;
        for (String p : partes) {
            if (p == null || p.trim().isEmpty() || p.length() > MAX_CHARS_POR_PARTE)
                return false;
        }
        return true;
    }

    public void agregarParte(String texto) {
        if (partes.size() >= MAX_PARTES)
            throw new IllegalStateException("Un hilo no puede tener mas de " + MAX_PARTES + " partes.");
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException("La parte no puede estar vacia.");
        partes.add(texto);
    }

    @Override
    public String toString() {
        return super.toString() + " | Partes: " + partes.size() +
               (titulo != null ? " | \"" + titulo + "\"" : "");
    }

    public List<String> getPartes() { return partes; }
    public int getCantidadPartes()  { return partes.size(); }
    public String getTitulo()       { return titulo; }
}
