/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import javax.swing.Icon;
import javax.swing.JComponent;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author thevpc
 */
public interface AppContentWindow {

    String id();

    WritableValue<Boolean> active();

    WritableValue<String> title();

    WritableValue<Icon> icon();

    WritableValue<Boolean> closable();
    
    WritableValue<JComponent> component();

    WritableValue<AppWindowStateSet> state();
}
