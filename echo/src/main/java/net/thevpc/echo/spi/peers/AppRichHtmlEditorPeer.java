package net.thevpc.echo.spi.peers;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.TextAlignment;
import net.thevpc.echo.model.InsertImageInfo;
import net.thevpc.echo.model.InsertTableInfo;

public interface AppRichHtmlEditorPeer extends AppComponentPeer {

    void runTextFont(AppFont font);

    void runTextBold();

    void runTextItalic();

    void runTextUnderline();

    void runTextStrikeThrough();

    void runTextAlignment(TextAlignment value);

    void removeAllHighlights(Object userObjectKey);

    void highlight(int from, int to, AppColor c, Object userObjectKey);

    void runSelectLine();

    void runTextDuplicate();

    void registerAccelerator(String actionId, String accelerator, Runnable action);

    boolean runAction(String action);

    String getText(int from, int to);

    int getTextLength();

    void replaceSelection(String newValue);

    void runTextForegroundColor(AppColor color);

    void runTextBackgroundColor(AppColor color);

    void runInsertImage(InsertImageInfo info);

    void runDeleteTable();

    void runInsertTable(InsertTableInfo info);

    void runInsertTableRow();

    void runInsertTableColumn();

    void runDeleteTableRow();

    void runDeleteTableColumn();
}

