package net.thevpc.echo;

import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.WindowWorkspaceOptions;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.api.peers.AppWorkspacePeer;

public interface AppLayoutWorkspaceFactory {
    SupportSupplier<AppWorkspacePeer> createWorkspace(AppToolFolder tool, AppFrame frame, WindowWorkspaceOptions options);
}
