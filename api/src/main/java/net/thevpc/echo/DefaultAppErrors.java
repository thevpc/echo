/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.msg.ExceptionMessage;
import java.util.logging.Level;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.DelegateProperty;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.ObservableDispatcher;
import net.thevpc.common.props.WritableDispatcher;

/**
 *
 * @author thevpc
 */
public class DefaultAppErrors extends DelegateProperty<Message> implements AppErrors {

    private final Application app;

    public DefaultAppErrors(Application app) {
        super(Props.of("errors").dispatcherOf(Message.class));
        this.app = app;
    }

    @Override
    public void add(String item) {
        if (item != null) {
            add(new RuntimeException(item));
        }
    }

    @Override
    public void add(Throwable item) {
        if (item != null) {
            add(new ExceptionMessage(Level.SEVERE, null, item));
        }
    }

    @Override
    public void add(Message item) {
        if (item != null) {
            base().add(item);
        }
    }

    @Override
    public ObservableDispatcher<Message> readOnly() {
        return base().readOnly();
    }

    private WritableDispatcher<Message> base() {
        return (WritableDispatcher<Message>) base;
    }
}
