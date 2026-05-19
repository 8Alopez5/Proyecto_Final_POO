package recomendaciones;

import usuarios.Usuario;
import contenido.Contenido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecomendacionPorPopularidad implements AlgoritmoRecomendacion {

    @Override
    public List<Contenido> recomendarContenido(Usuario usuario,
                                                List<Contenido> todoElContenido,
                                                int limite) {
        List<Contenido> filtrado = new ArrayList<Contenido>();
        for (Contenido c : todoElContenido) {
            if (!c.getAutor().getId().equals(usuario.getId())) {
                filtrado.add(c);
            }
        }
        Collections.sort(filtrado, new Comparator<Contenido>() {
            public int compare(Contenido a, Contenido b) {
                return b.getLikes() - a.getLikes();
            }
        });
        if (filtrado.size() > limite) return filtrado.subList(0, limite);
        return filtrado;
    }

    @Override
    public List<Usuario> recomendarUsuarios(Usuario usuario,
                                             List<Usuario> todosLosUsuarios,
                                             int limite) {
        List<Usuario> filtrado = new ArrayList<Usuario>();
        for (Usuario u : todosLosUsuarios) {
            if (!u.getId().equals(usuario.getId())) {
                filtrado.add(u);
            }
        }
        Collections.sort(filtrado, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return b.getSeguidores() - a.getSeguidores();
            }
        });
        if (filtrado.size() > limite) return filtrado.subList(0, limite);
        return filtrado;
    }

    @Override
    public String getNombre() { return "Recomendacion por Popularidad"; }
}
