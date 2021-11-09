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
 * @author thevpc
 */
public class ValuesMenuRendererContextImpl<T> implements ValuesMenuRendererContext<T> {
    
    private final ValuesMenu<T> sf;
    private final int i;
    private final T value;
    private Button button;

    public ValuesMenuRendererContextImpl(ValuesMenu<T> sf, int i, T value, Button button) {
        this.sf = sf;
        this.i = i;
        this.value = value;
        this.button = button;
    }

    @Override
    public Application getApplication() {
        return getValuesMenu().app();
    }

    @Override
    public ValuesMenu<T> getValuesMenu() {
        return sf;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public int getPosition() {
        return i;
    }

    @Override
    public void setText(Str str) {
        button.text().set(str);
    }

    @Override
    public void setTextFont(AppFont font) {
        button.textStyle().font().set(font);
    }

    @Override
    public void setTextAlign(Anchor align) {
        button.textStyle().align().set(align);
    }

    @Override
    public void setTextUnderline(boolean undeline) {
        button.textStyle().underline().set(undeline);
    }

    @Override
    public void setTextStrikethrough(boolean strikethrough) {
        button.textStyle().strikethrough().set(strikethrough);
    }

    @Override
    public void setTextStrokeSize(int size) {
        button.textStyle().strokeSize().set(size);
    }

    @Override
    public void setIcon(AppImage image) {
        button.icon().set(image);
    }
    
}
