/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import java.util.function.Supplier;
import java.util.logging.Level;

import net.thevpc.echo.AppEvent;
import net.thevpc.echo.UndoableAction;
import net.thevpc.common.msg.JFormattedMessage;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author thevpc
 */
public class PropUndoableAction<T> implements UndoableAction {

    private WritableValue<T> prop;
    private Supplier<WritableValue<T>> propSupp;
    private Supplier<T> newValue;
    private T oldValue;
    private String messageTemplate;

    public PropUndoableAction(Supplier<T> newValue, Supplier<WritableValue<T>> propSupp, String messageTemplate) {
        this.propSupp = propSupp;
        this.newValue = newValue;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public Message doAction(AppEvent event) {
        prop = propSupp.get();
        if (prop == null) {
            return null;
        }
        oldValue = prop.get();
        prop.set(newValue.get());
        postDo();
        return new JFormattedMessage(Level.INFO, messageTemplate, new Object[]{prop.get()});
    }

    protected void postDo() {

    }

    protected void postUndo() {

    }

    @Override
    public void undoAction(AppEvent event) {
        prop.set(oldValue);
        postUndo();
    }

    @Override
    public void redoAction(AppEvent event) {
        prop.set(newValue.get());
        postDo();
    }

}
