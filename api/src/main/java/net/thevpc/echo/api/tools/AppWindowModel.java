/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.AppWindowStateSetValue;
import net.thevpc.echo.api.components.AppComponent;

/**
 * @author thevpc
 */
public interface AppWindowModel extends AppContainerModel {

    WritableBoolean active();

    WritableValue<AppWindowAnchor> anchor();

    WritableBoolean closable();

    WritableBoolean iconifiable();

    WritableValue<AppComponent> component();

    AppWindowStateSetValue state();
    void close();
}
