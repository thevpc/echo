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

import net.thevpc.common.props.*;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppLogs;

import java.util.LinkedList;

/**
 *
 * @author thevpc
 */
public class DefaultAppLogs extends WritableListImpl<Message> implements AppLogs {

    private int maxMessageEntries = 1000;
    private Application app;

    public DefaultAppLogs(Application app) {
        super("logs", PropertyType.of(Message.class),new LinkedList<Message>());
        this.app = app;
        Props.configureMaxEntries(this,maxMessageEntries);
    }
}
