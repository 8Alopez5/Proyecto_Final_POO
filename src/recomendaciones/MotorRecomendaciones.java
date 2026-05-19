package recomendaciones;

import usuarios.Usuario;
import contenido.Contenido;

import java.util.List;

public class MotorRecomendaciones {

    private AlgoritmoRecomendacion estrategia;

    public MotorRecomendaciones(AlgoritmoRecomendacion estrategia) {
        this.estrategia = estrategia;
    }


    public void setEstrategia(AlgoritmoRecomendacion estrategia) {
        this.estrategia = estrategia;
        System.out.println("  [Strategy] Estrategia cambiada a: " + estrategia.getNombre());
    }

    public AlgoritmoRecomendacion getEstrategia() { return estrategia; }


    public List<Contenido> recomendarContenido(Usuario usuario,
                                                List<Contenido> todoElContenido,
                                                int limite) {
        return estrategia.recomendarContenido(usuario, todoElContenido, limite);
    }


    public List<Usuario> recomendarUsuarios(Usuario usuario,
                                             List<Usuario> todosLosUsuarios,
                                             int limite) {
        return estrategia.recomendarUsuarios(usuario, todosLosUsuarios, limite);
    }
}
