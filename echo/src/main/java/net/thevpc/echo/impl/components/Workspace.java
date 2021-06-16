///**
// * ====================================================================
// * Echo : Simple Desktop Application Framework
// * <br>
// * Echo is a simple Desktop Application Framework witj productivity in mind.
// * Currently Echo has two ports : swing and javafx
// * <br>
// * <p>
// * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
// * or agreed to in writing, software distributed under the License is
// * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied. See the License for the specific language
// * governing permissions and limitations under the License.
// * <br> ====================================================================
// */
//
//
//package net.thevpc.echo.impl.components;
//
//import net.thevpc.echo.AppWorkspace;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.peers.AppWorkspacePeer;
//import net.thevpc.echo.api.model.AppContainerModel;
//import net.thevpc.echo.api.model.AppWindowModel;
//import net.thevpc.echo.impl.model.ContainerModel;
//
///**
// * @author vpc
// */
//public class Workspace extends WindowContainerBase implements AppWorkspace {
//
////    private WritableMap<String, AppWindow> windows = Props.of("toolWindows").mapOf(String.class, AppWindow.class);
//    private boolean dockingEnabled = true;
//    private boolean desktopEnabled = true;
//    private boolean dockingSupported;
//    private boolean desktopSupported;
//
//    public Workspace(Application app) {
//        this(new ContainerModel(app));
//    }
//
//    public Workspace(AppContainerModel folder) {
//        super(folder, AppWindowModel.class, AppWorkspace.class, AppWorkspacePeer.class);
//    }
//
//    public boolean isDockingSupported() {
//        return dockingSupported;
//    }
//
//    public Workspace setDockingSupported(boolean dockingSupported) {
//        this.dockingSupported = dockingSupported;
//        return this;
//    }
//
//    public boolean isDesktopSupported() {
//        return desktopSupported;
//    }
//
//    public Workspace setDesktopSupported(boolean desktopSupported) {
//        this.desktopSupported = desktopSupported;
//        return this;
//    }
//
//    @Override
//    public boolean dockingEnabled() {
//        return dockingEnabled && dockingSupported;
//    }
//
//    @Override
//    public boolean desktopEnabled() {
//        return desktopEnabled && desktopSupported;
//    }
//
//    @Override
//    public void tileDesktop(boolean vertical) {
//        app().toolkit().runUI(() -> {
//            peer().tileDesktop(vertical);
//        });
//    }
//
//    @Override
//    public void iconDesktop(boolean iconify) {
//        app().toolkit().runUI(() -> {
//            peer().iconDesktop(iconify);
//        });
//    }
//
//    @Override
//    public void closeAllDesktop() {
//        app().toolkit().runUI(() -> {
//            peer().closeAllDesktop();
//        });
//    }
//
//    public AppWorkspacePeer peer() {
//        return (AppWorkspacePeer) super.peer();
//    }
//}
