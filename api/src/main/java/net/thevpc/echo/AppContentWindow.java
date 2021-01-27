/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import javax.swing.JComponent;
import net.thevpc.common.props.WritablePValue;

/**
 *
 * @author thevpc
 */
public interface AppContentWindow {

    String id();

    WritablePValue<Boolean> active();

    WritablePValue<String> title();

    WritablePValue<String> icon();

    WritablePValue<Boolean> closable();
    
    WritablePValue<JComponent> component();

    WritablePValue<AppWindowStateSet> state();
}
