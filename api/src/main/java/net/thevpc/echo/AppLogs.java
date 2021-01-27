/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.props.PList;
import net.thevpc.common.msg.Message;

/**
 *
 * @author thevpc
 */
public interface AppLogs extends PList<Message> {

    void add(Message message);

}
