package interacciones;

import contenido.Contenido;
import usuarios.Usuario;
import java.util.ArrayList;
import java.util.List;

public class HistorialInteracciones {

    private Contenido contenido;
    private List<Interaccion> lista;

    public HistorialInteracciones(Contenido contenido) {
        this.contenido = contenido;
        this.lista     = new ArrayList<Interaccion>();
    }

    public void registrar(Interaccion i) {
        if (i != null) lista.add(i);
    }

    public boolean usuarioYaHizo(Usuario usuario, Interaccion.Tipo tipo) {
        for (Interaccion i : lista)
            if (i.getUsuario().equals(usuario) && i.getTipo() == tipo) return true;
        return false;
    }

    public List<Interaccion> filtrarPorTipo(Interaccion.Tipo tipo) {
        List<Interaccion> res = new ArrayList<Interaccion>();
        for (Interaccion i : lista)
            if (i.getTipo() == tipo) res.add(i);
        return res;
    }

    public void mostrar() {
        System.out.println("\n--- Historial [" + contenido.getTipo() + "] #" + contenido.getId() + " ---");
        if (lista.isEmpty()) {
            System.out.println("  Sin interacciones.");
        } else {
            for (Interaccion i : lista) System.out.println("  " + i);
        }
        System.out.println("  Total: " + lista.size());
        System.out.println("-------------------------------------------");
    }

    public List<Interaccion> getLista() { return lista; }
}
