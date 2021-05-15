package net.thevpc.echo.api.components;

import net.thevpc.echo.DefaultAppComponentOptions;

public class WindowWorkspaceOptions extends DefaultAppComponentOptions implements Cloneable {

    private Boolean enabledDesktop;
    private Boolean enabledDocking;

    public Boolean enabledDesktop() {
        return enabledDesktop;
    }

    public WindowWorkspaceOptions enabledDesktop(Boolean enabledDesktop) {
        this.enabledDesktop = enabledDesktop;
        return this;
    }

    public boolean enabledDocking() {
        return enabledDocking;
    }

    public WindowWorkspaceOptions enabledDocking(Boolean enabledDocking) {
        this.enabledDocking = enabledDocking;
        return this;
    }

    @Override
    public void copyFrom(AppComponentOptions other, boolean nonNull) {
        super.copyFrom(other, nonNull);
        if (other instanceof WindowWorkspaceOptions) {
            WindowWorkspaceOptions o = (WindowWorkspaceOptions) other;
            if (!nonNull || o.enabledDesktop != null) {
                this.enabledDesktop = o.enabledDesktop;
            }
            if (!nonNull || o.enabledDocking != null) {
                this.enabledDocking = o.enabledDocking;
            }
        }
    }

    @Override
    public String toString() {
        return "WindowWorkspaceOptions{"
                + "order=" + order()
                + "enabledDesktop=" + enabledDesktop()
                + ", enabledDocking=" + enabledDocking()
                + '}';
    }
}
