/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.helpers;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppChoiceControl;
import net.thevpc.echo.api.components.AppChoiceItemContext;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.swing.SwingApplicationUtils;

/**
 *
 * @author thevpc
 */
public abstract class SwingAppChoiceItemContext1<T> implements AppChoiceItemContext<T> {

    private final AppChoiceControl<T> appChoiceControl;
    protected JComponent jcomponent;
    private T value;
    private String text;
    private final int index;
    private Icon icon;
    private final boolean isSelected;
    private final boolean cellHasFocus;
    private final boolean disabled;
    private Boolean underline;
    private Boolean strikeThrough;

    public SwingAppChoiceItemContext1(AppChoiceControl<T> appChoiceControl, JComponent jcomponent, T value, int index, Icon icon, boolean isSelected, boolean cellHasFocus, boolean disabled) {
        this.jcomponent = jcomponent;
        this.appChoiceControl = appChoiceControl;
        this.value = value;
        this.text = value == null ? "" : value.toString();
        this.index = index;
        this.icon = icon;
        this.isSelected = isSelected;
        this.cellHasFocus = cellHasFocus;
        this.disabled = disabled;
    }

    public JComponent getJcomponent() {
        return jcomponent;
    }

    @Override
    public AppFont getFont() {
        Font f = jcomponent.getFont();
        return SwingHelpers.fromAwtFont(f, getApplication());
    }

    @Override
    public AppColor getColor() {
        Color f = jcomponent.getForeground();
        return SwingHelpers.fromAwtColor(f, getApplication());
    }

    @Override
    public AppColor getBackgroundColor() {
        Color f = jcomponent.getBackground();
        return SwingHelpers.fromAwtColor(f, getApplication());
    }

    @Override
    public AppChoiceControl<T> getChoice() {
        return appChoiceControl;
    }

    @Override
    public T getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Application getApplication() {
        return appChoiceControl.app();
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean isFocused() {
        return cellHasFocus;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        if (jcomponent instanceof JLabel) {
            ((JLabel) jcomponent).setText(text);
        } else if (jcomponent instanceof AbstractButton) {
            ((AbstractButton) jcomponent).setText(text);
        }
    }

    @Override
    public void setOpaque(boolean opaque) {
        jcomponent.setOpaque(opaque);
    }

    @Override
    public void setTextColor(AppColor color) {
        jcomponent.setForeground(
                color == null ? null : (Color) color.peer().toolkitColor()
        );
    }

    @Override
    public void setTextFont(AppFont font) {
        if (font != null) {
            SwingApplicationUtils.setComponentFont(jcomponent,
                    font, null, null, underline, strikeThrough);
        }
    }

    @Override
    public void setTextUnderline(boolean underline) {
        this.underline = underline;
        SwingApplicationUtils.setComponentFont(jcomponent,
                (AppFont) null, null, null, underline, null);
    }

    @Override
    public void setTextStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
        SwingApplicationUtils.setComponentFont(jcomponent,
                (AppFont) null, null, null, null, strikeThrough);
    }

    @Override
    public void setTextStrokeSize(int size) {
        SwingApplicationUtils.setComponentTextStrokeSize(jcomponent, size);
    }

    @Override
    public void setTextAlign(Anchor align) {
        if (jcomponent instanceof JLabel) {
            SwingApplicationUtils.setLabelTextAlign((JLabel) jcomponent, align);
        } else if (jcomponent instanceof AbstractButton) {
            SwingApplicationUtils.setLabelTextAlign((AbstractButton) jcomponent, align);
        }
    }

    @Override
    public void setIcon(Str icon) {
        AppImage image = null;
        if (icon != null) {
            String s = icon.value(getApplication().i18n(), getChoice().locale().get());
            if (s != null) {
                image = getChoice().app().iconSets().icon(s, getChoice());
            }
        }
        setIcon(image);
    }

    @Override
    public void setIcon(AppImage icon) {
        this.icon = SwingHelpers.toAwtIcon(icon);
        if (jcomponent instanceof JLabel) {
            ((JLabel) jcomponent).setIcon(this.icon);
        } else if (jcomponent instanceof AbstractButton) {
            ((AbstractButton) jcomponent).setIcon(this.icon);
        }
    }

    @Override
    public void setBackgroundColor(AppColor color) {
        jcomponent.setForeground(
                color == null ? null : (Color) color.peer().toolkitColor()
        );
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

}
