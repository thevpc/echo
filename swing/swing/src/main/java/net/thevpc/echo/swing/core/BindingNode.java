package net.thevpc.echo.swing.core;

import net.thevpc.echo.swing.core.swing.BindingNodeFactory;
import net.thevpc.echo.swing.core.swing.DefaultNodeSupplierContext;
import net.thevpc.echo.swing.core.tools.AppToolFolderImpl;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppNode;
import net.thevpc.echo.AppComponent;
import net.thevpc.echo.ItemPath;

import java.util.ArrayList;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.AppTool;
import net.thevpc.echo.AppToolContainer;
import net.thevpc.echo.AppTools;

public class BindingNode implements AppNode {

    protected Application application;
    protected BindingNode parent;
    protected Object guiElement;
    protected AppComponent appComponent;
    protected AppToolComponent binding;
    protected java.util.List<BindingNode> children = new ArrayList<>();
    private WritableList<AppComponent> components;
    private BindingNodeFactory factory;
    private GuiComponentNavigator supplier;
    private AppTools tools;

    public BindingNode(BindingNode parent, Object guiElement,
            AppToolComponent binding, AppComponent appComponent,
            Application application, WritableList<AppComponent> components,
            BindingNodeFactory factory, GuiComponentNavigator navigator, AppTools tools) {
        this.parent = parent;
        this.tools = tools;
        this.guiElement = guiElement;
        this.binding = binding;
        this.appComponent = appComponent;
        this.application = application;
        this.components = components;
        this.factory = factory;
        this.supplier = navigator;
    }

    public BindingNode getParent() {
        return parent;
    }

    public Object getGuiElement() {
        return guiElement;
    }

    public AppToolComponent getBinding() {
        return binding;
    }

    public String name() {
        return binding.path().name();
    }

    public BindingNode add(AppToolComponent b) {
        if (!b.path().startsWith(path())) {
            throw new IllegalArgumentException("Invalid path");
        }
        ItemPath subPath = b.path().subPath(path().size());
        if (subPath.isEmpty()) {
            throw new IllegalArgumentException("Invalid path");
        }
        ItemPath parentPath = subPath.parent();
        BindingNode goodNode = this;
        if (!this.binding.path().equals(parentPath)) {
            goodNode = get(parentPath);
        }
        int u = goodNode.getItemCount();
        int index = -1;
        for (int i = 0; i < u; i++) {
            BindingNode child = goodNode.children.get(i);
            int o = child.binding.order();
            if (o <= b.order()) {
                index = i;
            }
        }
        if (index >= 0 && index < u - 1) {
            return goodNode.addChildItem(index + 1, b);
        } else {
            return goodNode.addChildItem(u, b);
        }
    }

    public BindingNode addChildItem(int i, AppToolComponent toolComponent) {
        Object ii = addChildItemGui(i, toolComponent);
        BindingNode bn = factory.createBindingNode(this, ii, toolComponent, toolComponent, application, components, tools);
        children.add(i, bn);
        components.add(toolComponent);
//        application.i18n().locale().listeners().add(e -> {
//            SwingApplicationsHelper.updateToolComponent(toolComponent, application);
//        });
//        SwingApplicationsHelper.updateToolComponent(toolComponent, application);

        return bn;
    }

    public BindingNode get(ItemPath path) {
        if (path.size() == 0) {
            return this;
        }
        for (BindingNode child : children) {
            if (child.name() != null && child.name().equals(path.first())) {
                if (path.size() == 1) {
                    return child.get(path.skipFirst());
                }
                return child.get(path.skipFirst());
            }
        }
        if (path.size() == 1) {

        }
        ItemPath absPath = binding.path().child(path.first());
        BindingNode n = addChildItem(children.size(),
                AppToolComponent.of(new AppToolFolderImpl(absPath.toString(), application,
                        tools
                ), absPath.toString())
        );
        if (path.size() == 1) {
            return n;
        }
        return n.get(path.skipFirst());
    }

    public Object addChildItemGui(int index, AppToolComponent b) {
        return supplier.addChildItem(index, b, new DefaultNodeSupplierContext(guiElement, application, factory));
    }

    public int getItemCount() {
        return supplier.getItemCount(new DefaultNodeSupplierContext(guiElement, application, factory));
    }

    public Object getItemAt(int index) {
        return supplier.getItemAt(index, new DefaultNodeSupplierContext(guiElement, application, factory));
    }

    @Override
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public int getOrder() {
        return binding.order();
    }

    @Override
    public ItemPath path() {
        return binding.path();
    }

    @Override
    public BindingNode[] getChildren() {
        return children.toArray(new BindingNode[0]);
    }

    @Override
    public String toString() {
        return "BindingNode{" + binding + '}';
    }

    public AppTool getToolByPath(ItemPath path0) {
        if (path0.equals(path()) && getBinding() != null) {
            return getBinding().tool();
        }
        if (path0.startsWith(path())) {
            for (BindingNode node : getChildren()) {
                AppTool x = node.getToolByPath(path0);
                if(x!=null){
                    return x;
                }
            }
        }
        return null;
    }
}
