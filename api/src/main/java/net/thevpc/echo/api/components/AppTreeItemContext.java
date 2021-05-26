package net.thevpc.echo.api.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.constraints.Anchor;

public interface AppTreeItemContext<T> {

    Application getApplication();

    boolean isExpanded();

    boolean isLeaf();

    AppTree<T> getTree();

    T getValue();

    AppTreeNode<T> getNode();

    void setText(String text);

    void setOpaque(boolean opaque);

    void setTextColor(AppColor color);

    void setTextFont(AppFont font);

    void setTextUnderline(boolean underline);

    void setTextStrikeThrough(boolean strikeThrough);

    void setTextStrokeSize(int size);

    void setTextAlign(Anchor align);

    void setIcon(AppImage icon);

    boolean isSelected();

    boolean isFocused();

    AppFont getFont();

    AppColor getColor();

    AppColor getBackgroundColor();

    void setBackgroundColor(AppColor color);

    boolean isOpaque();

    void setBackgroundNonSelectionColor(AppColor c);

    void setBackgroundSelectionColor(AppColor backgroundSelectionColor);

    void setTextNonSelectionColor(AppColor textNonSelectionColor);

    void setTextSelectionColor(AppColor textSelectionColor);

    void renderDefaults();
}
