package utilidades;

public final class FormateadorConsola {


    private FormateadorConsola() {}

    private static final int ANCHO = 55;
    private static final String SEP = "=".repeat(ANCHO);
    private static final String SEP_MENOR = "-".repeat(ANCHO);


    public static void cabecera(String titulo) {
        System.out.println("\n" + SEP);
        System.out.println(centrar(titulo, ANCHO));
        System.out.println(SEP);
    }


    public static void subseccion(String titulo) {
        System.out.println("\n" + SEP_MENOR);
        System.out.println("  >> " + titulo);
        System.out.println(SEP_MENOR);
    }


    public static void item(int numero, String texto) {
        System.out.printf("  %2d. %s%n", numero, texto);
    }


    public static void linea(String texto) {
        System.out.println("  " + texto);
    }


    public static void testOk(String descripcion) {
        System.out.println("  [OK]  " + descripcion);
    }


    public static void testError(String descripcion, String mensaje) {
        System.out.println("  [ERR] " + descripcion + ": " + mensaje);
    }


    public static String centrar(String texto, int ancho) {
        if (texto.length() >= ancho) return texto;
        int padding = (ancho - texto.length()) / 2;
        return " ".repeat(padding) + texto;
    }


    public static String formatearNumero(long numero) {
        return String.format("%,d", numero);
    }


    public static void fila(String clave, String valor) {
        System.out.printf("  %-35s %s%n", clave, valor);
    }


    public static void pie() {
        System.out.println("\n" + SEP);
        System.out.println(centrar("FIN DE LA DEMO", ANCHO));
        System.out.println(SEP + "\n");
    }
}
