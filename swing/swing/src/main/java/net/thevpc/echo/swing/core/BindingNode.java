package net.thevpc.echo.swing.core;

import net.thevpc.echo.swing.core.swing.BindingNodeFactory;
import net.thevpc.echo.swing.core.swing.DefaultNodeSupplierContext;
import net.thevpc.echo.swing.core.tools.AppToolFolderImpl;
import net.thevpc.common.props.WritablePList;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppNode;
import net.thevpc.echo.AppComponent;
import net.thevpc.echo.ItemPath;

import java.util.ArrayList;

public class BindingNode implements AppNode {
    protected Application application;
    protected BindingNode parent;
    protected Object guiElement;
    protected AppComponent appComponent;
    protected AppToolComponent binding;
    protected java.util.List<BindingNode> children = new ArrayList<>();
    private WritablePList<AppComponent> components;
    private BindingNodeFactory factory;
    private GuiComponentNavigator supplier;

    public BindingNode(BindingNode parent, Object guiElement, AppToolComponent binding, AppComponent appComponent, Application application, WritablePList<AppComponent> components, BindingNodeFactory factory, GuiComponentNavigator navigator) {
        this.parent = parent;
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

    public ItemPath path() {
        return binding.path();
    }

    public String name() {
        return binding.path().name();
    }

    public BindingNode add(AppToolComponent b) {
        if (b.path().size() == 0) {
            throw new IllegalArgumentException("Invalid path");
        }
        ItemPath parentPath = b.path().parent();
        BindingNode goodNode = this;
        if (!this.binding.path().equals(parentPath)) {
            goodNode = get(parentPath);
        }
        BindingNode last = null;
        for (BindingNode child : goodNode.children) {
            int o = child.binding.order();
            if (o <= b.order()) {
                last = child;
            }
        }
        if (last != null) {
            int c = goodNode.getItemCount();
            for (int i = 0; i < c; i++) {
                Object curr = goodNode.getItemAt(i);
                if ((curr == null && last.binding.tool() instanceof AppToolSeparator) || curr == last.guiElement) {
                    return goodNode.addChildItem(i + 1, b);
                }
            }
            for (int i = 0; i < c; i++) {
                if (goodNode.getItemAt(i) == last.guiElement) {
                    return goodNode.addChildItem(i + 1, b);
                }
            }
        } else {
            int u = goodNode.getItemCount();
            return goodNode.addChildItem(u, b);
        }
        throw new IllegalArgumentException("Unsupported");
    }

    public BindingNode addChildItem(int i, AppToolComponent b) {
        Object ii = addChildItemGui(i, b);
        BindingNode bn = factory.createBindingNode(this, ii, b, b, application, components);
        children.add(i, bn);
        components.add(b);
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
        if(path.size()==1){
            
        }
        ItemPath absPath = binding.path().child(path.first());
        BindingNode n = addChildItem(children.size(),
                AppToolComponent.of(new AppToolFolderImpl(absPath.toString()), absPath.toString())
        );
        if(path.size()==1){
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
    public ItemPath getPath() {
        return binding.path();
    }

    @Override
    public BindingNode[] getChildren() {
        return children.toArray(new BindingNode[0]);
    }

}
