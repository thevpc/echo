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



package net.thevpc.echo.iconset;

import java.io.File;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.WritableLiMap;
import net.thevpc.common.props.WritableString;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppImage;

/**
 *
 * @author vpc
 */
public interface IconSets extends WritableLiMap<String,IconSet>{
    WritableValue<IconSetConfig> config();
    
    IconSet iconSet();
    
    WritableString id();

    ObservableValue<AppImage> icon(String id);
    AppImage icon(String id,String iconSet);

    WritableValue<AppIconResolver> resolver();

    String iconIdForFile(File f, boolean selected, boolean expanded);

    String iconIdForFileName(String f, boolean selected, boolean expanded);

    ObservableValue<AppImage> iconForFile(File f, boolean selected, boolean expanded);

    ObservableValue<AppImage> iconForFileName(String f, boolean selected, boolean expanded);
    
    IconSetBuilder add();
    
}
