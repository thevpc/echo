package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextControl;
import net.thevpc.echo.swing.SwingApplicationUtils;

import java.awt.*;

public class SwingMenuButtonPeer implements SwingPeer{
    private JDropDownButton jcomponent;
    @Override
    public void install(AppComponent comp) {
        jcomponent = new JDropDownButton();
        SwingApplicationUtils.prepareAbstractButton(jcomponent,(AppTextControl) comp, comp.app(), false);
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add((Component) o,index);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.remove(index);
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

}
