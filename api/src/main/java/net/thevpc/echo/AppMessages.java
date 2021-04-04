/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.ObservableList;

/**
 *
 * @author thevpc
 */
public interface AppMessages extends ObservableList<Message> {

    WritableList<AppMessageProducer> producers();

    void update();

}
