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

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableMap;
import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;
import net.thevpc.echo.impl.tools.ToolWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.api.peers.AppWorkspacePeer;

/**
 * @author vpc
 */
public class Workspace extends WindowContainerBase implements AppWorkspace {

    private WritableMap<String, AppWindow> windows = Props.of("toolWindows").mapOf(String.class, AppWindow.class);
    private boolean dockingEnabled=true;
    private boolean desktopEnabled=true;
    private boolean dockingSupported;
    private boolean desktopSupported;

    public Workspace(Application app) {
        super(new ToolFolder(app));
    }

    public Workspace(AppToolFolder tool) {
        super(tool);
    }

    public boolean isDockingSupported() {
        return dockingSupported;
    }

    public Workspace setDockingSupported(boolean dockingSupported) {
        this.dockingSupported = dockingSupported;
        return this;
    }

    public boolean isDesktopSupported() {
        return desktopSupported;
    }

    public Workspace setDesktopSupported(boolean desktopSupported) {
        this.desktopSupported = desktopSupported;
        return this;
    }

    @Override
    public boolean dockingEnabled() {
        return dockingEnabled && dockingSupported;
    }

    @Override
    public boolean desktopEnabled() {
        return desktopEnabled && desktopSupported;
    }

    @Override
    public AppWindow addWindow(String id, AppComponent component, AppWindowAnchor anchor) {
        AppWindow w = windows.get(id);
        if (w != null) {
            throw new IllegalArgumentException("already Registered");
        }
        AppWindow c = addWindowImpl(id, component, anchor);
        windows.put(id, c);
        return c;
    }

    @Override
    public WritableMap<String, AppWindow> windows() {
        return windows;
    }

    @Override
    public AppWindow getWindow(String id) {
        return windows.get(id);
    }

    @Override
    public void removeWindow(String id) {
        AppWindow w = windows.get(id);
        if (w == null) {
            throw new IllegalArgumentException("already Registered");
        }
        windows.remove(id);
        removeWindowImpl(id, w);
    }


    public AppWindow addWindowImpl(String id, AppComponent component, AppWindowAnchor anchor){
        AppWindowPeer peer = peer().addWindowImpl(id, component, anchor);
        Window ww = new Window(new ToolWindow(id, anchor, component, app()));
        ww.peer=peer;
        ww.parent=this;
        peer.install(ww);
        return ww;
    }

    public AppWorkspacePeer peer(){
        return (AppWorkspacePeer) super.peer();
    }

    protected void removeWindowImpl(String id, AppWindow a){
        peer().removeWindowImpl(id,(AppWindowPeer) a.peer());
    }

    @Override
    public void tileDesktop(boolean vertical) {
        peer().tileDesktop(vertical);
    }

    @Override
    public void iconDesktop(boolean iconify) {
        peer().iconDesktop(iconify);
    }

    @Override
    public void closeAllDesktop() {
        peer().closeAllDesktop();
    }
}
