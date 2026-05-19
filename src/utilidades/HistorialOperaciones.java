package utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class HistorialOperaciones {

    private static final HistorialOperaciones INSTANCIA = new HistorialOperaciones();

    private final List<Entrada> entradas;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private HistorialOperaciones() {
        this.entradas = new ArrayList<>();
    }

    public static HistorialOperaciones getInstancia() { return INSTANCIA; }



    public void registrar(String modulo, String operacion, String detalle) {
        entradas.add(new Entrada(modulo, operacion, detalle));
    }

    public void registrar(String modulo, String operacion) {
        registrar(modulo, operacion, "");
    }



    public List<Entrada> getEntradas() { return Collections.unmodifiableList(entradas); }

    public List<Entrada> filtrarPorModulo(String modulo) {
        return entradas.stream()
                .filter(e -> e.getModulo().equalsIgnoreCase(modulo))
                .collect(Collectors.toList());
    }

    public int getTotalOperaciones() { return entradas.size(); }



    public void imprimirResumen() {
        System.out.println("\n--- Historial de Operaciones (" + entradas.size() + " registros) ---");
        entradas.forEach(e -> System.out.println("  " + e));
    }

    public void imprimirUltimas(int n) {
        System.out.println("\n--- Últimas " + n + " operaciones ---");
        int inicio = Math.max(0, entradas.size() - n);
        entradas.subList(inicio, entradas.size())
                .forEach(e -> System.out.println("  " + e));
    }



    public static class Entrada {
        private final LocalDateTime timestamp;
        private final String modulo;
        private final String operacion;
        private final String detalle;

        public Entrada(String modulo, String operacion, String detalle) {
            this.timestamp = LocalDateTime.now();
            this.modulo    = modulo;
            this.operacion = operacion;
            this.detalle   = detalle;
        }

        public String getModulo()    { return modulo; }
        public String getOperacion() { return operacion; }

        @Override
        public String toString() {
            String base = String.format("[%s] [%s] %s",
                    timestamp.format(FMT), modulo, operacion);
            return detalle.isEmpty() ? base : base + " — " + detalle;
        }
    }
}
