/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableDispatcher;

/**
 *
 * @author thevpc
 */
public interface AppErrors extends WritableDispatcher<Message> {

    void add(Throwable item);

    void add(String item);

}
