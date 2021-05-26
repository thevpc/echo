package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToolBarGroup;
import net.thevpc.echo.constraints.AllAnchors;
import net.thevpc.echo.spi.peers.AppToolBarGroupPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.raw.JToolBarGroup;

import java.awt.*;

public class SwingToolBarGroupPeer implements SwingPeer, AppToolBarGroupPeer {
    private AppToolBarGroup appToolBarGroup;
    private JToolBarGroup swingComponent;

    @Override
    public void install(AppComponent comp) {
        JToolBarGroup jcomponent = new JToolBarGroup();
        this.swingComponent = jcomponent;
        this.appToolBarGroup = (AppToolBarGroup) comp;
        SwingPeerHelper.installComponent(appToolBarGroup,swingComponent);
        appToolBarGroup.parentConstraints().onChangeAndInit(()->{
            AllAnchors q = appToolBarGroup.parentConstraints().get(AllAnchors.class);
            this.swingComponent.setLayout(new FlowLayout(
                    (q == null || q == AllAnchors.LEFT || q == AllAnchors.TOP_LEFT || q == AllAnchors.BOTTOM_LEFT) ? FlowLayout.LEFT :
                            (q == AllAnchors.RIGHT || q == AllAnchors.TOP_RIGHT || q == AllAnchors.BOTTOM_RIGHT) ? FlowLayout.RIGHT :
                                    (q == AllAnchors.CENTER || q == AllAnchors.TOP || q == AllAnchors.BOTTOM) ? FlowLayout.CENTER :
                                            FlowLayout.LEFT
            ));
        });
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        swingComponent.add((Component) o, index);
        SwingHelpers.refreshPanel(swingComponent, 2);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        swingComponent.remove(index);
        SwingHelpers.refreshPanel(swingComponent, 2);
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

}
