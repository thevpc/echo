package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolFolder;
import net.thevpc.echo.ItemPath;

public class AppToolFolderImpl extends AbstractAppTool implements AppToolFolder {
    private ItemPath path;

    public AppToolFolderImpl(String path) {
        super(ItemPath.of("menuBar").child(path).toString());
        this.path = ItemPath.of("menuBar").child(path);
        title().set(ItemPath.of(path).name());
        enabled().set(true);
        visible().set(true);
    }

    @Override
    public ItemPath path() {
        return path;
    }
}
