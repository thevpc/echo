/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.constraints.Anchor;

/**
 *
 * @author vpc
 */
public interface ValuesMenuRendererContext<T> {

    public Application getApplication();

    public ValuesMenu<T> getValuesMenu();

    public T getValue();

    public int getPosition();

    void setText(Str str);

    public void setTextFont(AppFont font);

    public void setTextAlign(Anchor align);

    public void setTextUnderline(boolean undeline);

    public void setTextStrikethrough(boolean strikethrough);

    public void setTextStrokeSize(int size);

    public void setIcon(AppImage image);
    
}
