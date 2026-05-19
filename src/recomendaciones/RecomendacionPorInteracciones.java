package recomendaciones;

import usuarios.Usuario;
import contenido.Contenido;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionPorInteracciones implements AlgoritmoRecomendacion {

    @Override
    public List<Contenido> recomendarContenido(Usuario usuario,
                                                List<Contenido> todoElContenido,
                                                int limite) {
        return todoElContenido.stream()
                .filter(c -> !c.getAutor().getId().equals(usuario.getId()))

                .sorted(Comparator.comparingInt(this::calcularScore).reversed())
                .limit(limite)
                .collect(Collectors.toList());
    }

    private int calcularScore(Contenido c) {
        return c.getLikes() * 2
                + c.getCantidadReposts() * 3
                + c.getCantidadRespuestas();
    }

    @Override
    public List<Usuario> recomendarUsuarios(Usuario usuario,
                                             List<Usuario> todosLosUsuarios,
                                             int limite) {

        List<String> yaSigneIds = usuario.getSiguiendo().stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());


        List<String> redDirecta = usuario.getSeguidores().stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());

        return todosLosUsuarios.stream()
                .filter(u -> !u.getId().equals(usuario.getId()))
                .filter(u -> !yaSigneIds.contains(u.getId()))

                .sorted(Comparator.comparingInt(
                    (Usuario u) -> (int) u.getSeguidores().stream()
                        .filter(s -> redDirecta.contains(s.getId()))
                        .count()
                ).reversed())
                .limit(limite)
                .collect(Collectors.toList());
    }

    @Override
    public String getNombre() { return "Recomendación por Interacciones"; }
}
