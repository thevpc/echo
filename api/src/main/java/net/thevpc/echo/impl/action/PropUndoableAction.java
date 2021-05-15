/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 *
 * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <br> ====================================================================
 */



package net.thevpc.echo.impl.action;

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
