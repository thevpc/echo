package net.thevpc.echo;

import net.thevpc.echo.api.AppImage;

public interface FolderViewThumbResolver {
    AppImage resolveFastThumb(String filePath, FolderView.FileType fileType, FolderView folderView);

    AppImage resolveThumb(String filePath, FolderView.FileType fileType, FolderView folderView);

}
