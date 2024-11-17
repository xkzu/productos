package cl.duoc.app.util;

public class UtilProducto {

    private UtilProducto() {
    }

    public static boolean isEmptyOrNull(String string) {
        return null == string || string.isEmpty();

    }
}
