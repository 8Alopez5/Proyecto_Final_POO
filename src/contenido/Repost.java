package contenido;

import usuarios.Usuario;

public class Repost extends Contenido {

    private Contenido original;
    private boolean esQuote;


    public Repost(String id, Usuario autor, Contenido original) {
        super(id, "Repost de @" + original.getAutor().getUsername(), autor);
        this.original = original;
        this.esQuote  = false;
        original.registrarRepost();
    }


    public Repost(String id, String comentario, Usuario autor, Contenido original) {
        super(id, comentario, autor);
        this.original = original;
        this.esQuote  = true;
        original.registrarRepost();
    }

    @Override
    public String getTipo() { return esQuote ? "QUOTE" : "REPOST"; }

    @Override
    public boolean esValido() {
        return original != null && original.isActivo();
    }

    @Override
    public String toString() {
        return super.toString() +
               " | Original: [" + original.getTipo() + "] @" + original.getAutor().getUsername();
    }

    public Contenido getOriginal() { return original; }
    public boolean isEsQuote()     { return esQuote; }
}
