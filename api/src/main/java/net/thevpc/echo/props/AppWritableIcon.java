/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.props;

import javax.swing.Icon;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.Application;

/**
 *
 * @author vpc
 */
public class AppWritableIcon extends WritableValueImpl<Icon> {

    private String id;
    private Application app;

    public AppWritableIcon(String name, Application app) {
        super(name, PropertyType.of(String.class), null);
        this.app = app;
        app.iconSets().listeners().add(e -> {
            reevalValue();
        });
    }

    public void reevalValue() {
        if (id != null) {
            String iconId = this.id;
            if (iconId.startsWith("$")) {
                String id2 = app.i18n().getString(iconId.substring(1), x -> null);
                if (id2 != null) {
                    iconId = id2.trim();
                    if (iconId.isEmpty()) {
                        superSet(null);
                        return;
                    }
                } else {
                    superSet(null);
                    return;
                }
            }
            superSet(app.iconSets().icon(iconId).get());
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

    private void superSet(Icon str) {
        super.set(str);
    }

    @Override
    public void set(Icon value) {
        if (id == null) {
            superSet(value);
        }
    }

}
