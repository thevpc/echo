package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolFolder;
import net.thevpc.echo.AppTools;
import net.thevpc.echo.Application;
import net.thevpc.echo.ItemPath;

public class AppToolFolderImpl extends AbstractAppTool implements AppToolFolder {

    private ItemPath path;

    public AppToolFolderImpl(String path, Application app, AppTools tools) {
        super(
                //                ItemPath.of("menuBar").child(path).toString()
                ItemPath.of(path).toString(),
                app, tools);
        this.path = ItemPath.of(path);//ItemPath.of("menuBar").child(path);
    }

    @Override
    public ItemPath path() {
        return path;
    }
}
