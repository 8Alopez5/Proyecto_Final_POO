package gestores;

import usuarios.Usuario;
import relaciones.Relacion;
import relaciones.Relacion.TipoRelacion;
import excepciones.UsuarioDuplicadoException;
import excepciones.UsuarioNoEncontradoException;
import excepciones.OperacionInvalidaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorUsuarios {

    private static GestorUsuarios instancia;

    private Map<String, Usuario> usuariosPorId;
    private Map<String, Usuario> usuariosPorUsername;
    private List<Relacion> relaciones;

    private GestorUsuarios() {
        usuariosPorId = new HashMap<String, Usuario>();
        usuariosPorUsername = new HashMap<String, Usuario>();
        relaciones = new ArrayList<Relacion>();
    }

    public static GestorUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new GestorUsuarios();
        }
        return instancia;
    }

    public void registrarUsuario(Usuario usuario) throws UsuarioDuplicadoException {
        if (usuariosPorId.containsKey(usuario.getId())) {
            throw new UsuarioDuplicadoException("Ya existe un usuario con el id: " + usuario.getId());
        }
        if (usuariosPorUsername.containsKey(usuario.getUsername())) {
            throw new UsuarioDuplicadoException("El username @" + usuario.getUsername() + " ya esta en uso.");
        }
        usuariosPorId.put(usuario.getId(), usuario);
        usuariosPorUsername.put(usuario.getUsername(), usuario);
        System.out.println("Usuario registrado: @" + usuario.getUsername());
    }

    public Usuario buscarPorUsername(String username) throws UsuarioNoEncontradoException {
        Usuario usuario = usuariosPorUsername.get(username);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("No se encontro: @" + username);
        }
        return usuario;
    }

    public void seguir(Usuario seguidor, Usuario objetivo) throws OperacionInvalidaException {
        if (seguidor.equals(objetivo)) {
            throw new OperacionInvalidaException("No puedes seguirte a ti mismo.");
        }
        if (existeRelacion(objetivo, seguidor, TipoRelacion.BLOQUEAR)) {
            throw new OperacionInvalidaException("@" + objetivo.getUsername() + " te ha bloqueado.");
        }
        if (existeRelacion(seguidor, objetivo, TipoRelacion.SEGUIR)) {
            throw new OperacionInvalidaException("Ya sigues a @" + objetivo.getUsername());
        }
        relaciones.add(new Relacion(seguidor, objetivo, TipoRelacion.SEGUIR));
        seguidor.agregarSeguido();
        objetivo.agregarSeguidor();
        System.out.println("@" + seguidor.getUsername() + " ahora sigue a @" + objetivo.getUsername());
    }

    public void dejarDeSeguir(Usuario seguidor, Usuario objetivo) throws OperacionInvalidaException {
        if (!existeRelacion(seguidor, objetivo, TipoRelacion.SEGUIR)) {
            throw new OperacionInvalidaException("No sigues a @" + objetivo.getUsername());
        }
        eliminarRelacion(seguidor, objetivo, TipoRelacion.SEGUIR);
        seguidor.quitarSeguido();
        objetivo.quitarSeguidor();
        System.out.println("@" + seguidor.getUsername() + " dejo de seguir a @" + objetivo.getUsername());
    }

    public void bloquear(Usuario bloqueador, Usuario bloqueado) throws OperacionInvalidaException {
        if (bloqueador.equals(bloqueado)) {
            throw new OperacionInvalidaException("No puedes bloquearte a ti mismo.");
        }
        if (existeRelacion(bloqueador, bloqueado, TipoRelacion.BLOQUEAR)) {
            throw new OperacionInvalidaException("Ya bloqueaste a @" + bloqueado.getUsername());
        }
        if (existeRelacion(bloqueador, bloqueado, TipoRelacion.SEGUIR)) {
            eliminarRelacion(bloqueador, bloqueado, TipoRelacion.SEGUIR);
            bloqueador.quitarSeguido();
            bloqueado.quitarSeguidor();
        }
        if (existeRelacion(bloqueado, bloqueador, TipoRelacion.SEGUIR)) {
            eliminarRelacion(bloqueado, bloqueador, TipoRelacion.SEGUIR);
            bloqueado.quitarSeguido();
            bloqueador.quitarSeguidor();
        }
        relaciones.add(new Relacion(bloqueador, bloqueado, TipoRelacion.BLOQUEAR));
        System.out.println("@" + bloqueador.getUsername() + " bloqueo a @" + bloqueado.getUsername());
    }

    public List<Usuario> listarPorNombre() {
        List<Usuario> lista = new ArrayList<Usuario>(usuariosPorId.values());
        Collections.sort(lista, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return a.getNombre().compareTo(b.getNombre());
            }
        });
        return lista;
    }

    public List<Usuario> listarPorSeguidores() {
        List<Usuario> lista = new ArrayList<Usuario>(usuariosPorId.values());
        Collections.sort(lista, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return b.getSeguidores() - a.getSeguidores();
            }
        });
        return lista;
    }

    public void mostrarTodos() {
        System.out.println("\n===== USUARIOS =====");
        for (Usuario u : listarPorNombre()) {
            System.out.println(u);
        }
        System.out.println("====================\n");
    }

    public void mostrarMetricas() {
        System.out.println("\n===== METRICAS =====");
        System.out.println("Total usuarios: " + usuariosPorId.size());
        System.out.println("Total relaciones: " + relaciones.size());
        List<Usuario> porSeguidores = listarPorSeguidores();
        if (!porSeguidores.isEmpty()) {
            System.out.println("Mas influyente: " + porSeguidores.get(0));
        }
        System.out.println("====================\n");
    }

    public int getTotalUsuarios() { return usuariosPorId.size(); }

    private boolean existeRelacion(Usuario origen, Usuario destino, TipoRelacion tipo) {
        for (Relacion r : relaciones) {
            if (r.getOrigen().equals(origen) && r.getDestino().equals(destino) && r.getTipo() == tipo) {
                return true;
            }
        }
        return false;
    }

    private void eliminarRelacion(Usuario origen, Usuario destino, TipoRelacion tipo) {
        Relacion aEliminar = null;
        for (Relacion r : relaciones) {
            if (r.getOrigen().equals(origen) && r.getDestino().equals(destino) && r.getTipo() == tipo) {
                aEliminar = r;
                break;
            }
        }
        if (aEliminar != null) relaciones.remove(aEliminar);
    }
}
