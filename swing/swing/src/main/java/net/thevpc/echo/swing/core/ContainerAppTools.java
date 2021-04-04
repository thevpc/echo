package net.thevpc.echo.swing.core;

import net.thevpc.echo.Application;
import net.thevpc.echo.AppToolContainer;
import net.thevpc.echo.AppNode;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.ItemPath;

import java.util.*;

public class ContainerAppTools extends AbstractAppToolsBase {

    public ContainerAppTools(Application application) {
        super(application);
    }

    @Override
    public void addTool(AppToolComponent tool) {
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

}
