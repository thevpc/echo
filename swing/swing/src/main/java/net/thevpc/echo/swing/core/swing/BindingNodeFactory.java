package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;
import net.thevpc.echo.*;
import net.thevpc.echo.swing.core.BindingNode;
import net.thevpc.echo.swing.core.GuiComponentNavigator;

import javax.swing.*;
import net.thevpc.common.props.WritableList;

public class BindingNodeFactory {

    private GuiComponentNavigator<JPopupMenu> _JPopupMenu = new JPopupMenuGuiComponentNavigator();
    private GuiComponentNavigator<JMenuBar> _JMenuBar = new JMenuBarGuiComponentNavigator();
    private GuiComponentNavigator<JToolBar> _JToolBar = new JToolBarGuiComponentNavigator();
    private GuiComponentNavigator<JToolbarGroup> _JToolbarGroup = new JToolbarGroupGuiComponentNavigator();
    private GuiComponentNavigator<JStatusBarGroup> _JStatusBarGroup = new JStatusBarGroupGuiComponentNavigator();
    private GuiComponentNavigator<JMenu> _JMenu = new JMenuGuiComponentNavigator();
    private GuiComponentNavigator<JComponent> _Fallback = new JComponentGuiComponentNavigator();

    public BindingNode createBindingNode(BindingNode parent, Object guiElement, AppToolComponent binding, AppComponent appComponent, Application application, WritableList<AppComponent> components,AppTools tools) {

        return new BindingNode(parent, guiElement, binding, appComponent, application, components, this, getNavigator(guiElement),tools);
    }

    private GuiComponentNavigator getNavigator(Object guiElement) {
        if (guiElement instanceof JMenu) {
            return _JMenu;
        }
        if (guiElement instanceof JMenuBar) {
            return _JMenuBar;
        }
        if (guiElement instanceof JPopupMenu) {
            return _JPopupMenu;
        }
        if (guiElement instanceof JToolBar) {
            return _JToolBar;
        }
        if (guiElement instanceof JToolbarGroup) {
            return _JToolbarGroup;
        }
        if (guiElement instanceof JStatusBarGroup) {
            return _JStatusBarGroup;
        }
        if (guiElement instanceof JComponent) {
            return _Fallback;
        }
        throw new IllegalArgumentException("Unsupported BindingNodeFactory for " + guiElement.getClass().getName());
    }

    public Object createGuiComponent(AppComponentRendererContext context) {
        AppComponentRenderer r = context.getAppComponent().renderer();
        Object guiComponent = null;
        if (r != null) {
            guiComponent = r.createGuiComponent(context);
            if (guiComponent != null) {
                return guiComponent;
            }
        }
        AppComponentRenderer renderer = context.getApplication().componentRendererFactory().getBuilder(context.getAppComponent());
        guiComponent = renderer.createGuiComponent(context);
        if (guiComponent != null) {
            return guiComponent;
        }
        throw new IllegalArgumentException("Unsupported BindingNodeFactory for " + context.getAppComponent().getClass().getSimpleName() + " to gui " + context.getParentGuiElement().getClass().getSimpleName());
    }

}
