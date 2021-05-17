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



package net.thevpc.echo.api.components;

import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.Path;
import net.thevpc.common.props.Property;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.impl.components.AppComponentConstraints;
import net.thevpc.echo.api.peers.AppComponentPeer;

/**
 * AppComponent inherits all AppComponentModel Property implementation
 * @author vpc
 */
public interface AppComponent extends Property {
    AppComponentConstraints constraints();

    AppComponent setOptions(AppComponentOptions options);

    AppComponent parent();

    AppComponentModel model();

    WritableValue<Path> path();

    ObservableValue<Integer> order();

    Application app();

    default AppComponentPeer peer() {
        return peer(true);
    }

    AppComponentPeer peer(boolean prepareShowing);

    Class<? extends AppComponentModel> modelType();

    Class<? extends AppComponent> componentType();

    Class<? extends AppComponentPeer> peerType();

}
