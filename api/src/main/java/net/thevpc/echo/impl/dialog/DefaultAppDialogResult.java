package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.api.AppDialogResult;
import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;

import java.util.function.Supplier;

public class DefaultAppDialogResult implements AppDialogResult {
    private final String a;
    private final Supplier<?> valueEvaluator;
    private final Application app;

    public DefaultAppDialogResult(String a, Supplier<?> valueEvaluator, Application app) {
        this.a = a;
        this.valueEvaluator = valueEvaluator;
        this.app = app;
    }

    public Application app() {
        return app;
    }

    @Override
    public String buttonId() {
        return a;
    }

    @Override
    public <T> T value() {
        if (valueEvaluator != null) {
            return (T) valueEvaluator.get();
        }
        throw new IllegalArgumentException("Not an input component");
    }

    public boolean isBlankValue() {
        Object o = value();
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).trim().isEmpty();
        }
        if (o instanceof Str) {
            return ((Str) o).value(app.i18n()).isEmpty();
        }
        return false;
    }
}
