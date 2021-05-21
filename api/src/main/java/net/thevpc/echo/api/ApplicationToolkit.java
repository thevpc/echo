/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 * <p>
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

package net.thevpc.echo.api;

import net.thevpc.echo.Clipboard;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.spi.peers.AppColorPeer;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.spi.peers.AppFontPeer;
import net.thevpc.echo.spi.peers.AppImagePeer;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * @author vpc
 */
public interface ApplicationToolkit {

    String id();

    AppComponent createComponent(Object object);

    AppImagePeer createImagePeer(InputStream source);

    AppImagePeer createImagePeer(double width, double height, AppColor color);

    AppImagePeer createImagePeer(URL url);

    AppUIPlaf getPlaf(String id);

    AppUIPlaf[] loadAvailablePlafs();

    void applyPlaf(String plaf);

    IconTransform createReplaceColorTransform(AppColor from, AppColor to);

//    AppComponent createComponent(AppComponentModel tool, AppComponent parent, String name, AppComponentOptions options);

    void runUI(Runnable run);

    void runWorker(Runnable run);

    void runUILater(Runnable run);

    void runUIAndWait(Runnable run);

    <T> T callUIAndWait(Callable<T> run);

    AppComponentPeer createComponentPeer(AppComponent component);

    AppColorPeer createColorPeer(AppColor color);

    AppFontPeer createFontPeer(AppFont font);

    int parseColor(String colorText);

    Clipboard systemClipboard();
}
