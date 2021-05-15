package net.thevpc.echo.api;

import net.thevpc.common.i18n.I18n;
import net.thevpc.echo.Application;

class InternalI18nStr implements Str {
    private String value;

    InternalI18nStr(String value) {
        this.value = value == null ? "" : value.trim();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean is18n() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf("<i18n>:" + value);
    }

    @Override
    public String getValue(I18n n) {
        return n.getString(getValue());
    }

    @Override
    public String getValue(Application app) {
        return getValue(app.i18n());
    }
}
