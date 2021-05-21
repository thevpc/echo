package net.thevpc.echo.spi.peers;

import net.thevpc.echo.api.AppColor;

public interface AppTextAreaPeer extends AppComponentPeer{
    void removeAllHighlights(Object userObjectKey);

    void highlight(int from, int to, AppColor c, Object userObjectKey);

    void runSelectLine();

    void runTextDuplicate();

    void registerAccelerator(String actionId, String accelerator, Runnable action);

    String getText(int from, int to);

    int getTextLength() ;
}
