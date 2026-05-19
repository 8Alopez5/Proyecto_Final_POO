package fabrica;

public class ProveedorFabricas {

    private ProveedorFabricas() {}

    public static FabricaContenido obtener(String tipo) {
        if (tipo == null) throw new IllegalArgumentException("El tipo no puede ser null.");
        switch (tipo.toUpperCase()) {
            case "POST":      return new FabricaPost();
            case "HILO":      return new FabricaHilo();
            case "RESPUESTA": return new FabricaRespuesta();
            case "REPOST":
            case "QUOTE":     return new FabricaRepost();
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        }
    }
}
