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



package net.thevpc.echo;

import net.thevpc.common.msg.ExceptionMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.DelegateWritableProperty;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.ObservableDispatcher;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableDispatcher;

/**
 *
 * @author thevpc
 */
public class DefaultAppErrors extends DelegateWritableProperty implements AppErrors {

    private final WritableBoolean enableErrorStackTrace = Props.of("enableErrorStackTrace").booleanOf(true);
    private final Application app;
    private static Logger LOGGER = Logger.getLogger(DefaultAppErrors.class.getName());

    public DefaultAppErrors(Application app) {
        super(Props.of("errors").dispatcherOf(Message.class));
        this.app = app;
        listeners().add(x -> {
            if (enableErrorStackTrace.get()) {
                ExceptionMessage e = x.getNewValue();
                if (e.getError() instanceof Throwable) {
                    LOGGER.log(Level.SEVERE, e.getText(), e.getError());
                    //e.getError().printStackTrace();
                } else {
                    LOGGER.log(Level.SEVERE, e.getText());
                    //System.err.println(e);
                }
            }
        });
    }

    @Override
    public WritableBoolean enableErrorStackTrace() {
        return enableErrorStackTrace;
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
