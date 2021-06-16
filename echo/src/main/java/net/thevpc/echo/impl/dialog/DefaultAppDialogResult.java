package net.thevpc.echo.impl.dialog;

import net.thevpc.echo.Alert;
import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;

import java.util.function.Supplier;
import net.thevpc.echo.api.AppAlertResult;

public class DefaultAppDialogResult implements AppAlertResult {
    private final String button;
    private final Supplier<?> valueEvaluator;
    private final Application app;
    private final Alert appAlert;

    public DefaultAppDialogResult(String button, Supplier<?> valueEvaluator, Alert appAlert,Application app) {
        this.button = button;
        this.appAlert = appAlert;
        this.valueEvaluator = valueEvaluator;
        this.app = app;
    }

    public Application app() {
        return app;
    }

    @Override
    public String button() {
        return button;
    }

    @Override
    public <T> T value() {
        if (valueEvaluator != null) {
            return (T) valueEvaluator.get();
        }
        throw new IllegalArgumentException("Not an input component");
    }

    @Override
    public boolean isBlankValue() {
        Object o = value();
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).trim().isEmpty();
        }
        if (o instanceof Str) {
            return ((Str) o).value(app.i18n(),appAlert.locale().get()).isEmpty();
        }
        return false;
    }
}
