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
package net.thevpc.echo.api.components;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.echo.Application;
import net.thevpc.echo.Bounds;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.WritableTextStyle;
import net.thevpc.echo.api.AppChildConstraints;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppParentConstraints;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.iconset.WritableImage;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.Locale;

/**
 * AppComponent inherits all AppComponentModel Property implementation
 *
 * @author vpc
 */
public interface AppComponent extends Property {

    WritableValue<AppFont> font();

    WritableValue<AppColor> foregroundColor();

    AppComponentEvents events();

    AppChildConstraints childConstraints();

    AppParentConstraints parentConstraints();

    AppComponent setOptions(AppComponentOptions options);

    AppComponent parent();

    WritableValue<Path> path();

    ObservableValue<Integer> order();

    WritableValue<AppContextMenu> contextMenu();

    Application app();

    default AppComponentPeer peer() {
        return peer(true);
    }

    AppComponentPeer peer(boolean prepareShowing);

    Class<? extends AppComponent> componentType();

    Class<? extends AppComponentPeer> peerType();

    String id();

    WritableValue<Dimension> prefSize();

    WritableBoolean focused();

    WritableBoolean editing();

    WritableBoolean active();

    WritableValue<Anchor> anchor();

    WritableImage smallIcon();

    /**
     * used as a title when this component is placed into a container. a good
     * example can be the Tab title, the window title etc...
     *
     * @return title property
     */
    WritableStr title();

    WritableTextStyle titleStyle();

    WritableImage largeIcon();

    WritableInt mnemonic();

    WritableString accelerator();

    WritableBoolean enabled();

    WritableBoolean editable();

    WritableBoolean visible();

    WritableStr tooltip();

    WritableMap<Object, Object> properties();

    WritableValue<AppColor> backgroundColor();

    WritableBoolean opaque();

    WritableValue<Bounds> bounds();

    WritableValue<Locale> locale();

    WritableValue<String> iconSet();

    WritableBoolean shown();

    AppComponent copy(boolean bind);

    void requestFocus();
}
