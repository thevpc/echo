package net.thevpc.echo.api.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.constraints.Anchor;

public interface AppChoiceItemContext<T> {

    AppChoiceControl<T> getChoice();

    Application getApplication();

    int getIndex();

    T getValue();

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

    void renderDefault();

    void setValue(Object value);

}
