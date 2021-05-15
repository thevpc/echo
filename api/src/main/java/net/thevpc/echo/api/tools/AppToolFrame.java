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



package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.AppWindowDisplayMode;
import net.thevpc.echo.AppWindowStateSet;
import net.thevpc.echo.AppWindowStateSetValue;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.props.WritableImage;

/**
 * @author thevpc
 */
public interface AppToolFrame extends AppToolFolder {

    WritableBoolean active();

    WritableBoolean closable();

    WritableBoolean iconifiable();

    AppWindowStateSetValue state();

    WritableValue<AppWindowDisplayMode> displayMode();
}