package net.thevpc.echo.swing;

import net.thevpc.echo.AppUIPlaf;
import net.thevpc.swing.plaf.UIPlaf;

class SwingUIPlaf implements AppUIPlaf {

    UIPlaf p;

    public SwingUIPlaf(UIPlaf p) {
        this.p = p;
    }

    @Override
    public boolean isSystem() {
        return p.isSystem();
    }

    @Override
    public boolean isDark() {
        return p.isDark();
    }

    @Override
    public boolean isLight() {
        return p.isLight();
    }

    @Override
    public boolean isContrast() {
        return p.isContrast();
    }

    @Override
    public String getId() {
        return p.getId();
    }

}
