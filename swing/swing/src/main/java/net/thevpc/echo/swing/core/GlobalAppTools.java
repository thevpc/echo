package net.thevpc.echo.swing.core;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.AppToolContainer;
import net.thevpc.echo.AppNode;
import net.thevpc.echo.AppState;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.ItemPath;

import java.util.*;

public class GlobalAppTools extends AbstractAppToolsBase {

    private List<AppToolComponent> tools0 = new ArrayList<>();

    public GlobalAppTools(DefaultApplication application) {
        super(application);
        this.application.state().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppState s = (AppState) event.getNewValue();
                if (s == AppState.INIT) {
                    for (Iterator<AppToolComponent> iterator = tools0.iterator(); iterator.hasNext();) {
                        AppToolComponent tool = iterator.next();
                        iterator.remove();
                        addTool(tool);
                    }
                }
            }
        });
    }

    @Override
    public void addTool(AppToolComponent tool) {
        if (this.application.state().get() == AppState.NONE) {
            this.tools0.add(tool);
            return;
        }
//        String first = tool.path().first();
//        AppToolComponent subBinding = AppToolComponent.of(tool.tool(), tool.path().skipFirst().toString(), tool.order(), tool.renderer());
        Set<String> available = new HashSet<>();
        for (AppNode node : this.application.rootNode().getChildren()) {
            AppToolContainer c = (AppToolContainer) node.getComponent();
            ItemPath path = c.rootNode().path();
            available.add(path.toString());
            if (tool.path().startsWith(path)) {
                c.tools().addTool(tool);
                return;
            }
        }
        throw new IllegalArgumentException("Unable to resolve to a valid path '" + tool.path() + "' . root nodes start with one of : " + available);
    }

    @Override
    public void removeTool(AppToolComponent tool) {

    }

//    protected void addRootContainer(AppToolContainer c) {
//        if (c != null) {
//            c.components().listeners().add(toolMapResolverAppPropertyListener);
//        }
//    }
//
//    protected void removeRootContainer(AppToolContainer c) {
//        if (c != null) {
//            c.components().listeners().add(toolMapResolverAppPropertyListener);
//        }
//    }
}
