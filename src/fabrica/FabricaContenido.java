package fabrica;

import contenido.Contenido;
import contenido.Post;
import contenido.Hilo;
import contenido.Respuesta;
import contenido.Repost;
import usuarios.Usuario;
import java.util.List;

public interface FabricaContenido {

    Contenido crear(String id, String texto, Usuario autor, Object... extras);
}

class FabricaPost implements FabricaContenido {
    @Override
    public Contenido crear(String id, String texto, Usuario autor, Object... extras) {
        Post p = new Post(id, texto, autor);
        if (extras != null && extras.length > 0 && extras[0] instanceof List) {
            for (Object url : (List<?>) extras[0]) {
                p.agregarImagen(url.toString());
            }
        }
        return p;
    }
}

class FabricaHilo implements FabricaContenido {
    @Override
    public Contenido crear(String id, String texto, Usuario autor, Object... extras) {
        String titulo = (extras != null && extras.length > 0 && extras[0] instanceof String)
                        ? (String) extras[0] : null;
        return new Hilo(id, texto, autor, titulo);
    }
}

class FabricaRespuesta implements FabricaContenido {
    @Override
    public Contenido crear(String id, String texto, Usuario autor, Object... extras) {
        if (extras == null || extras.length == 0 || !(extras[0] instanceof Contenido))
            throw new IllegalArgumentException("FabricaRespuesta requiere el contenido padre.");
        Contenido padre = (Contenido) extras[0];
        if (extras.length > 1 && extras[1] instanceof String)
            return new Respuesta(id, texto, autor, padre, (String) extras[1]);
        return new Respuesta(id, texto, autor, padre);
    }
}

class FabricaRepost implements FabricaContenido {
    @Override
    public Contenido crear(String id, String texto, Usuario autor, Object... extras) {
        if (extras == null || extras.length == 0 || !(extras[0] instanceof Contenido))
            throw new IllegalArgumentException("FabricaRepost requiere el contenido original.");
        Contenido original = (Contenido) extras[0];
        if (texto != null && !texto.trim().isEmpty())
            return new Repost(id, texto, autor, original);
        return new Repost(id, autor, original);
    }
}
