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



package net.thevpc.echo.impl;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.impl.ReadOnlyList;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppMessageProducer;
import net.thevpc.echo.api.AppMessages;

/**
 *
 * @author thevpc
 */
public class DefaultAppMessages extends ReadOnlyList<Message> implements AppMessages {

    protected WritableList<AppMessageProducer> messageProducers = Props.of("producers").listOf(AppMessageProducer.class);
    private int maxMessageEntries = 1000;
    private boolean _updateMessages;
    private Application app;

    public DefaultAppMessages(Application app) {
        super(Props.of("messages").linkedListOf(Message.class));
        this.app = app;
        Props.configureMaxEntries(messageProducers, maxMessageEntries);
    }

    @Override
    public WritableList<AppMessageProducer> producers() {
        return messageProducers;
    }

    private WritableList<Message> base() {
        return (WritableList<Message>) base;
    }

    @Override
    public void update() {
        if (_updateMessages) {
            return;
        }
        try {
            _updateMessages = true;
            base().clear();
            for (AppMessageProducer messageProducer : messageProducers) {
                messageProducer.produceMessages(app, base());
            }
        } finally {
            _updateMessages = false;
        }
    }

}
