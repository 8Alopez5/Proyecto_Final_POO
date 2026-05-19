package gestores;

import contenido.Contenido;
import contenido.Hilo;
import contenido.Post;
import fabrica.FabricaContenido;
import fabrica.ProveedorFabricas;
import interacciones.HistorialInteracciones;
import interacciones.Interaccion;
import usuarios.Usuario;
import excepciones.ContenidoInvalidoException;
import excepciones.ContenidoNoEncontradoException;
import excepciones.OperacionInvalidaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorContenido {

    private Map<String, Contenido> contenidos;
    private Map<String, HistorialInteracciones> historiales;
    private int contador;

    public GestorContenido() {
        this.contenidos  = new HashMap<String, Contenido>();
        this.historiales = new HashMap<String, HistorialInteracciones>();
        this.contador    = 1;
    }





    public Post publicarPost(Usuario autor, String texto)
            throws ContenidoInvalidoException {
        validarAutor(autor);
        Post post = (Post) ProveedorFabricas.obtener("POST").crear(nextId(), texto, autor);
        if (!post.esValido())
            throw new ContenidoInvalidoException(
                "Post invalido: texto vacio o supera " + Post.MAX_CARACTERES + " caracteres.");
        guardar(post);
        autor.registrarPublicacion();
        System.out.println("Post publicado: #" + post.getId() + " por @" + autor.getUsername());
        return post;
    }

    public Hilo publicarHilo(Usuario autor, String texto, String titulo)
            throws ContenidoInvalidoException {
        validarAutor(autor);
        Hilo hilo = (Hilo) ProveedorFabricas.obtener("HILO").crear(nextId(), texto, autor, titulo);
        if (!hilo.esValido())
            throw new ContenidoInvalidoException("Hilo invalido: texto vacio.");
        guardar(hilo);
        autor.registrarPublicacion();
        System.out.println("Hilo publicado: #" + hilo.getId() + " por @" + autor.getUsername());
        return hilo;
    }

    public Contenido responder(Usuario autor, String texto, String idPadre)
            throws ContenidoInvalidoException, ContenidoNoEncontradoException {
        validarAutor(autor);
        Contenido padre = buscarPorId(idPadre);
        Contenido resp  = ProveedorFabricas.obtener("RESPUESTA").crear(nextId(), texto, autor, padre);
        if (!resp.esValido())
            throw new ContenidoInvalidoException("Respuesta invalida.");
        guardar(resp);
        autor.registrarPublicacion();
        System.out.println("@" + autor.getUsername() + " respondio a #" + idPadre);
        return resp;
    }

    public Contenido repostear(Usuario autor, String idOriginal)
            throws ContenidoInvalidoException, ContenidoNoEncontradoException,
                   OperacionInvalidaException {
        Contenido original = buscarPorId(idOriginal);
        if (original.getAutor().equals(autor))
            throw new OperacionInvalidaException("No puedes repostear tu propio contenido.");
        Contenido repost = ProveedorFabricas.obtener("REPOST").crear(nextId(), null, autor, original);
        guardar(repost);
        autor.registrarPublicacion();
        System.out.println("@" + autor.getUsername() + " reposteo #" + idOriginal);
        return repost;
    }

    public Contenido quoteRepost(Usuario autor, String comentario, String idOriginal)
            throws ContenidoInvalidoException, ContenidoNoEncontradoException {
        Contenido original = buscarPorId(idOriginal);
        Contenido quote = ProveedorFabricas.obtener("QUOTE").crear(nextId(), comentario, autor, original);
        guardar(quote);
        autor.registrarPublicacion();
        System.out.println("@" + autor.getUsername() + " hizo quote de #" + idOriginal);
        return quote;
    }





    public void darLike(Usuario usuario, String idContenido)
            throws ContenidoNoEncontradoException, OperacionInvalidaException {
        Contenido c = buscarPorId(idContenido);
        HistorialInteracciones h = historiales.get(idContenido);
        if (h.usuarioYaHizo(usuario, Interaccion.Tipo.LIKE))
            throw new OperacionInvalidaException("@" + usuario.getUsername() + " ya dio like a #" + idContenido);
        c.darLike();
        h.registrar(new Interaccion(usuario, c, Interaccion.Tipo.LIKE));
        System.out.println("@" + usuario.getUsername() + " dio like a #" + idContenido);
    }

    public void quitarLike(Usuario usuario, String idContenido)
            throws ContenidoNoEncontradoException, OperacionInvalidaException {
        Contenido c = buscarPorId(idContenido);
        HistorialInteracciones h = historiales.get(idContenido);
        if (!h.usuarioYaHizo(usuario, Interaccion.Tipo.LIKE))
            throw new OperacionInvalidaException("@" + usuario.getUsername() + " no habia dado like a #" + idContenido);
        c.quitarLike();
        h.registrar(new Interaccion(usuario, c, Interaccion.Tipo.QUITAR_LIKE));
        System.out.println("@" + usuario.getUsername() + " quito like de #" + idContenido);
    }

    public void guardar(Usuario usuario, String idContenido)
            throws ContenidoNoEncontradoException, OperacionInvalidaException {
        Contenido c = buscarPorId(idContenido);
        HistorialInteracciones h = historiales.get(idContenido);
        if (h.usuarioYaHizo(usuario, Interaccion.Tipo.GUARDAR))
            throw new OperacionInvalidaException("@" + usuario.getUsername() + " ya guardo #" + idContenido);
        h.registrar(new Interaccion(usuario, c, Interaccion.Tipo.GUARDAR));
        System.out.println("@" + usuario.getUsername() + " guardo #" + idContenido);
    }

    public void reportar(Usuario usuario, String idContenido, String razon)
            throws ContenidoNoEncontradoException {
        Contenido c = buscarPorId(idContenido);
        historiales.get(idContenido).registrar(new Interaccion(usuario, c, Interaccion.Tipo.REPORTAR, razon));
        if (c instanceof Post) ((Post) c).reportar();
        System.out.println("@" + usuario.getUsername() + " reporto #" + idContenido + ": " + razon);
    }





    public Contenido buscarPorId(String id) throws ContenidoNoEncontradoException {
        Contenido c = contenidos.get(id);
        if (c == null || !c.isActivo())
            throw new ContenidoNoEncontradoException("No existe contenido activo con id: " + id);
        return c;
    }

    public List<Contenido> filtrarPorTipo(String tipo) {
        List<Contenido> res = new ArrayList<Contenido>();
        for (Contenido c : contenidos.values())
            if (c.isActivo() && c.getTipo().equalsIgnoreCase(tipo)) res.add(c);
        return res;
    }

    public List<Contenido> contenidoDeUsuario(Usuario usuario) {
        List<Contenido> res = new ArrayList<Contenido>();
        for (Contenido c : contenidos.values())
            if (c.isActivo() && c.getAutor().equals(usuario)) res.add(c);
        Collections.sort(res);
        return res;
    }





    public List<Contenido> ordenarPorPopularidad() {
        List<Contenido> lista = activos();
        Collections.sort(lista, new Comparator<Contenido>() {
            public int compare(Contenido a, Contenido b) {
                double d = b.calcularPopularidad() - a.calcularPopularidad();
                return d > 0 ? 1 : d < 0 ? -1 : 0;
            }
        });
        return lista;
    }

    public List<Contenido> ordenarPorFecha() {
        List<Contenido> lista = activos();
        Collections.sort(lista);
        return lista;
    }

    public List<Contenido> topContenidos(int n) {
        List<Contenido> lista = ordenarPorPopularidad();
        return lista.subList(0, Math.min(n, lista.size()));
    }





    public void mostrarHistorial(String idContenido) throws ContenidoNoEncontradoException {
        buscarPorId(idContenido);
        historiales.get(idContenido).mostrar();
    }





    public void mostrarMetricas() {
        int total = 0, posts = 0, hilos = 0, respuestas = 0, reposts = 0;
        for (Contenido c : contenidos.values()) {
            if (!c.isActivo()) continue;
            total++;
            switch (c.getTipo()) {
                case "POST":      posts++;      break;
                case "HILO":      hilos++;      break;
                case "RESPUESTA": respuestas++; break;
                case "REPOST": case "QUOTE": reposts++; break;
            }
        }
        System.out.println("\n===== METRICAS DE CONTENIDO =====");
        System.out.println("Total:      " + total);
        System.out.println("Posts:      " + posts);
        System.out.println("Hilos:      " + hilos);
        System.out.println("Respuestas: " + respuestas);
        System.out.println("Reposts:    " + reposts);
        List<Contenido> top = topContenidos(1);
        if (!top.isEmpty()) System.out.println("Mas popular: " + top.get(0));
        System.out.println("==================================\n");
    }

    public void eliminar(String id) throws ContenidoNoEncontradoException {
        Contenido c = contenidos.get(id);
        if (c == null) throw new ContenidoNoEncontradoException("No existe #" + id);
        c.setActivo(false);
        System.out.println("Contenido #" + id + " eliminado.");
    }

    public int getTotalContenido() { return contenidos.size(); }





    private void guardar(Contenido c) {
        contenidos.put(c.getId(), c);
        historiales.put(c.getId(), new HistorialInteracciones(c));
    }

    private void validarAutor(Usuario autor) throws ContenidoInvalidoException {
        if (autor == null)
            throw new ContenidoInvalidoException("El autor no puede ser null.");
        if (!autor.puedePublicar())
            throw new ContenidoInvalidoException("@" + autor.getUsername() + " no puede publicar.");
    }

    private String nextId() {
        return "C" + String.format("%04d", contador++);
    }

    private List<Contenido> activos() {
        List<Contenido> res = new ArrayList<Contenido>();
        for (Contenido c : contenidos.values()) if (c.isActivo()) res.add(c);
        return res;
    }
}
