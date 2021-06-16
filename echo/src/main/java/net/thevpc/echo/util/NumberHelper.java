package net.thevpc.echo.util;

public class NumberHelper {
    public static <T extends Number> T valueOf(Number n, Class<T> t) {
        switch (t.getName()) {
            case "int":
                return (T) (Number) (n == null ? 0 : n.intValue());
            case "java.lang.Integer":
                return (T) (Number) n.intValue();
            case "long":
                return (T) (Number) (n == null ? 0L : n.longValue());
            case "java.lang.Long":
                return (T) (Number) n.longValue();
            case "float":
                return (T) (Number) (n == null ? (float) 0 : n.floatValue());
            case "java.lang.Float":
                return (T) (Number) n.floatValue();
            case "double":
                return (T) (Number) (n == null ? (float) 0 : n.doubleValue());
            case "java.lang.Double":
                return (T) (Number) n.doubleValue();
        }
        throw new IllegalArgumentException("unsupported number type " + t);
    }
}
