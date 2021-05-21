package net.thevpc.echo.impl;

import net.thevpc.echo.api.AppPropertiesNodeItem;
import net.thevpc.echo.Application;

public class PValueViewPropertyReadOnly extends AbstractPropertiesNode implements AppPropertiesNodeItem {

    final Application application;
    final String name;
    final Object wv;
    final Object[] values;

    public PValueViewPropertyReadOnly(String type,Application application, String name, Object wv) {
        super(type);
        this.name = name;
        this.wv = wv;
        this.application = application;
        this.values = null;
    }

    public PValueViewPropertyReadOnly(String type,Application application, String name, Object wv, Object[] values) {
        super(type);
        this.name = name;
        this.wv = wv;
        this.values = values;
        this.application = application;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object object() {
        return wv;
    }

    @Override
    public Object[] getValues() {
        return values;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Class getType() {
        return wv.getClass();
    }

    @Override
    public void setValue(Object aValue) {

    }

    @Override
    public Object getEvaluatedValue() {
        return wv;
    }

    @Override
    public String toString() {
        return String.valueOf(name());
    }
}
