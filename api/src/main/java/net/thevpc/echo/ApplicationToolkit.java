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

import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.api.peers.*;

import java.awt.*;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 *
 * @author vpc
 */
public interface ApplicationToolkit {

    AppComponent createComponent(Object object);

    AppImagePeer createImagePeer(URL url);

    AppUIPlaf[] loadAvailablePlafs();

    void applyPlaf(String plaf);

    IconTransform createReplaceColorTransform(Color from, Color to);

    AppComponent createComponent(AppTool tool, AppComponent parent, String name, AppComponentOptions options);

    void runUI(Runnable run);

    void runWorker(Runnable run);

    void runUILater(Runnable run);

    void runUIAndWait(Runnable run);

    <T> T callUIAndWait(Callable<T> run);

    AppComponentPeer createComponentPeer(AppComponent component);

    AppColorPeer createColorPeer(int rgba, boolean hasAlpha);
}
