package net.thevpc.echo.api;

import net.thevpc.common.i18n.I18n;
import net.thevpc.echo.Application;

public interface Str {
    static Str of(int s) {
        return of(String.valueOf(s));
    }
    static Str of(float s) {
        return of(String.valueOf(s));
    }
    static Str of(long s) {
        return of(String.valueOf(s));
    }
    static Str of(double s) {
        return of(String.valueOf(s));
    }
    static Str of(char s) {
        return of(String.valueOf(s));
    }
    static Str of(boolean s) {
        return of(String.valueOf(s));
    }
    static Str of(byte s) {
        return of(String.valueOf(s));
    }
    static Str of(short s) {
        return of(String.valueOf(s));
    }
    static Str of(String s) {
        return new InternalRawStr(s);
    }

    static Str i18n(String s) {
        return new InternalI18nStr(s);
    }

    String getValue();
    String getValue(I18n n);
    String getValue(Application app);

    boolean is18n();

    default boolean isEmpty(){
        return getValue().isEmpty();
    }

}
