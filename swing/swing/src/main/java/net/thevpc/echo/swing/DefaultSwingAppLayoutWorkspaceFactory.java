/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing;

import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.WindowWorkspaceOptions;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.api.peers.AppWorkspacePeer;
import net.thevpc.echo.swing.containers.ws.SwingWorkspacePeer;
import net.thevpc.echo.*;

/**
 *
 * @author vpc
 */
public class DefaultSwingAppLayoutWorkspaceFactory implements AppLayoutWorkspaceFactory {

    public DefaultSwingAppLayoutWorkspaceFactory() {
    }

    @Override
    public SupportSupplier<AppWorkspacePeer> createWorkspace(AppToolFolder tool, AppFrame frame, WindowWorkspaceOptions options) {
        if (!(frame.app().toolkit() instanceof SwingApplicationToolkit)) {
            return null;
        }
        if (options != null && options.enabledDocking()) {
            return null;
        }
        return new SupportSupplier<AppWorkspacePeer>() {
            @Override
            public int getSupportLevel() {
                return 1;
            }

            @Override
            public AppWorkspacePeer get() {
                return new SwingWorkspacePeer();
            }
        };
    }

}
