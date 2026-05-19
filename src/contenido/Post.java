package contenido;

import usuarios.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Post extends Contenido {

    public static final int MAX_CARACTERES = 500;

    private List<String> imagenes;
    private boolean reportado;

    public Post(String id, String texto, Usuario autor) {
        super(id, texto, autor);
        this.imagenes = new ArrayList<String>();
        this.reportado = false;
    }

    @Override
    public String getTipo() { return "POST"; }

    @Override
    public boolean esValido() {
        return getTexto() != null &&
               !getTexto().trim().isEmpty() &&
               getTexto().length() <= MAX_CARACTERES;
    }

    public void agregarImagen(String url) {
        if (url != null && !url.isEmpty()) imagenes.add(url);
    }

    public void reportar() { this.reportado = true; }

    @Override
    public String toString() {
        String base = super.toString();
        if (!imagenes.isEmpty()) base += " | Imgs: " + imagenes.size();
        if (reportado)           base += " [REPORTADO]";
        return base;
    }

    public List<String> getImagenes() { return imagenes; }
    public boolean isReportado()      { return reportado; }
}
