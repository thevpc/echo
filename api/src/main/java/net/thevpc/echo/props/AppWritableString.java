/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.props;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.Application;

/**
 *
 * @author vpc
 */
public class AppWritableString extends WritableValueImpl<String> {

    private String id;
    private Application app;

    public AppWritableString(String name, Application app) {
        super(name, PropertyType.of(String.class), null);
        this.app = app;
        app.i18n().locale().listeners().add(e -> {
            reevalValue();
        });
    }

    protected void reevalValue() {
        if (id != null) {
            superSet(app.i18n().getString(id));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if (id != null) {
            reevalValue();
        }
    }

    private void superSet(String str) {
        super.set(str);
    }

    @Override
    public void set(String value) {
        if (id == null) {
            superSet(value);
        }
    }

}
