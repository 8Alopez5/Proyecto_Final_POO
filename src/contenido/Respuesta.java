package contenido;

import usuarios.Usuario;

public class Respuesta extends Contenido {

    public static final int MAX_CARACTERES = 500;

    private Contenido padre;
    private String mencion;

    public Respuesta(String id, String texto, Usuario autor, Contenido padre) {
        super(id, texto, autor);
        this.padre = padre;
        if (padre != null) padre.registrarRespuesta();
    }

    public Respuesta(String id, String texto, Usuario autor, Contenido padre, String mencion) {
        this(id, texto, autor, padre);
        this.mencion = mencion;
    }

    @Override
    public String getTipo() { return "RESPUESTA"; }

    @Override
    public boolean esValido() {
        return getTexto() != null &&
               !getTexto().trim().isEmpty() &&
               getTexto().length() <= MAX_CARACTERES &&
               padre != null;
    }

    @Override
    public String toString() {
        String base = super.toString() +
               " | En: [" + padre.getTipo() + "] @" + padre.getAutor().getUsername();
        if (mencion != null) base += " | @" + mencion;
        return base;
    }

    public Contenido getPadre() { return padre; }
    public String getMencion()  { return mencion; }
}
