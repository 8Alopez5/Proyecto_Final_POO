package recomendaciones;

import usuarios.Usuario;
import contenido.Contenido;
import java.util.List;

public interface AlgoritmoRecomendacion {


    List<Contenido> recomendarContenido(Usuario usuario,
                                         List<Contenido> todoElContenido,
                                         int limite);

    List<Usuario> recomendarUsuarios(Usuario usuario,
                                      List<Usuario> todosLosUsuarios,
                                      int limite);

    String getNombre();
}
