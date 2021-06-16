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


package net.thevpc.echo.iconset;

import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.WritableLiMap;
import net.thevpc.common.props.WritableString;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppImage;

import java.io.File;

/**
 *
 * @author vpc
 */
public interface IconSets extends WritableLiMap<String, IconSet> {
    WritableValue<IconConfig> config();

    IconSet iconSet();

    WritableString id();

    ObservableValue<AppImage> icon(String id);

    AppImage icon(String id, IconSetAware iconSetAware);

    WritableValue<AppIconResolver> resolver();

    String iconIdForFile(File f, boolean selected, boolean expanded);

    String iconIdForFileName(String f, boolean selected, boolean expanded);

    AppImage iconForFile(File f, boolean selected, boolean expanded, IconSetAware iconSetAware);

    AppImage iconForFileName(String f, boolean selected, boolean expanded, IconSetAware iconSetAware);

    IconSetBuilder add();

}
