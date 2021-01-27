/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

/**
 *
 * @author thevpc
 */
public interface AppToolWindow {

    String id();

    WritablePValue<Boolean> active();

}
