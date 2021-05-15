package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.swing.icons.SwingAppImage;

import java.awt.*;

public class SwingMenuLabelPeer implements SwingPeer{
    private JDropDownLabel jcomponent;
    @Override
    public void install(AppComponent comp) {
        jcomponent = new JDropDownLabel();
        AppTool folder = comp.tool();
        folder.title().listeners().add(x->jcomponent.setText(
                folder.title().get()==null?null:
                folder.title().get().getValue(comp.app())
        ));
        folder.smallIcon().listeners().add(x->jcomponent.setIcon(
                SwingAppImage.iconOf(folder.smallIcon().get())
        ));
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
