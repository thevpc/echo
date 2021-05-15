package net.thevpc.echo.api;

import net.thevpc.common.i18n.I18n;
import net.thevpc.echo.Application;

class InternalRawStr implements Str {
    private String value;

    InternalRawStr(String value) {
        this.value = value == null ? "" : value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean is18n() {
        return false;
    }

    @Override
    public String getValue(I18n n) {
        return getValue();
    }

    @Override
    public String getValue(Application app) {
        return getValue();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
