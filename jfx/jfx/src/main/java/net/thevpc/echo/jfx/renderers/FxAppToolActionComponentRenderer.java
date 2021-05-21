//package net.thevpc.echo.jfx.renderers;
//
//import java.awt.PopupMenu;
//import javafx.scene.control.Button;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuButton;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.ToolBar;
//import net.thevpc.echo.*;
//
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.model.AppComponentModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.ToolBarGroup;
//import net.thevpc.echo.jfx.controls.FxButtonControl;
//import net.thevpc.echo.jfx.raw.MenuLabel;
//import net.thevpc.echo.jfx.raw.StatusBarGroup;
//
//public class FxAppToolActionComponentRenderer implements AppComponentRenderer {
//
//    @Override
//    public AppComponent createComponent(AppComponentRendererContext context) {
//        Object parentGuiElement = ((FxPeer) context.getParent()).fxComponent();
//        AppComponentModel tool = context.getTool();
//        Path path = context.getPath();
//        Application application = context.getApplication();
//        AppComponentOptions options = context.getOptions();
//        AppComponentType componentType = options == null ? null : options.componentType();
//        boolean inMenu = false;
//        if (parentGuiElement instanceof ToolBar
//                || parentGuiElement instanceof ToolBarGroup
//                || parentGuiElement instanceof StatusBarGroup) {
//            inMenu = false;
//        } else if (parentGuiElement instanceof MenuBar) {
//            inMenu = false;
//        } else if (parentGuiElement instanceof Menu
//                || parentGuiElement instanceof PopupMenu
//                || parentGuiElement instanceof MenuButton
//                || parentGuiElement instanceof MenuLabel) {
//            inMenu = true;
//        } else {
//            inMenu = false;
//        }
//        if (componentType == null) {
//            componentType = AppComponentType.BUTTON;
//        }
//        switch (componentType) {
//            case BUTTON: {
//                if (inMenu) {
//                    return new FxButtonControl(tool, path, new MenuItem(), application, true, context.getOptions());
//                } else {
//                    return new FxButtonControl(tool, path, new Button(), application, false, context.getOptions());
//                }
//            }
//            default: {
//                throw new IllegalArgumentException("unexpected type " + componentType);
//            }
//        }
//    }
//}
