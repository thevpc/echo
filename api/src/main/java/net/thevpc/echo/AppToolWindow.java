/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.echo.props.AppWritableIcon;
import net.thevpc.echo.props.AppWritableString;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author thevpc
 */
public interface AppToolWindow {

    String id();

    WritableValue<Boolean> active();

    AppWritableString title();

    AppWritableIcon icon();

}
