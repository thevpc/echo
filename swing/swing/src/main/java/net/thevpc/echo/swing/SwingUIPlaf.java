package net.thevpc.echo.swing;

import net.thevpc.echo.api.AppUIPlaf;
import net.thevpc.swing.plaf.UIPlaf;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwingUIPlaf that = (SwingUIPlaf) o;
        return Objects.equals(p, that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
