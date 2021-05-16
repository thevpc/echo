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



package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppFileChooser;
import net.thevpc.echo.api.tools.AppToolFile;
import net.thevpc.echo.impl.tools.ToolFile;
import net.thevpc.echo.api.peers.AppFileChooserPeer;

/**
 *
 * @author vpc
 */
public class FileChooser extends AppComponentBase implements AppFileChooser {

    public FileChooser(AppToolFile file) {
        super(file);
    }

    public FileChooser(Application app) {
        super(new ToolFile(app));
    }

    @Override
    public AppToolFile tool() {
        return (AppToolFile) super.tool();
    }

    @Override
    public boolean showOpenDialog(AppComponent owner) {
        return ((AppFileChooserPeer)peer()).showOpenDialog(
                owner==null?null:owner.peer().toolkitComponent()
        );
    }

    @Override
    public boolean showSaveDialog(AppComponent owner) {
        return ((AppFileChooserPeer)peer()).showOpenDialog(
                owner==null?null:owner.peer().toolkitComponent()
        );
    }
}
