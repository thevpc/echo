//package net.thevpc.echo.jfx.renderers;
//
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuButton;
//import javafx.scene.control.ToolBar;
//import net.thevpc.echo.*;
//
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.model.AppContainerModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.ToolBarGroup;
//import net.thevpc.echo.jfx.containers.bars.FxAppToolBar;
//import net.thevpc.echo.jfx.containers.bars.FxAppToolBarGroup;
//import net.thevpc.echo.jfx.containers.menus.FxAppDropDownButton;
//import net.thevpc.echo.jfx.containers.menus.FxAppMenu;
//import net.thevpc.echo.jfx.containers.menus.FxAppMenuBar;
//import net.thevpc.echo.jfx.containers.wins.FxFrameAppFrame;
//import net.thevpc.echo.jfx.raw.MenuLabel;
//import net.thevpc.echo.jfx.raw.StatusBarGroup;
//
//public class FxAppToolFolderComponentRenderer implements AppComponentRenderer {
//
//    @Override
//    public AppComponent createComponent(AppComponentRendererContext context) {
//        Object parentGuiElement = (context.getParent() instanceof FxPeer)?((FxPeer) context.getParent()).fxComponent():null;
//        AppComponentOptions options = context.getOptions();
//        AppComponentType componentType = options == null ? null : options.componentType();
//
//        AppContainerModel tool = (AppContainerModel) context.getTool();
//        Application application = context.getApplication();
//        Path path = context.getPath();
//        boolean inMenu = false;
//        if (parentGuiElement instanceof StatusBarGroup) {
//            if (componentType == null) {
//                componentType = AppComponentType.STATUS_BAR;
//            }
//        } else if (parentGuiElement instanceof ToolBarGroup) {
//            if (componentType == null) {
//                componentType = AppComponentType.TOOL_BAR;
//            }
//        } else if (parentGuiElement instanceof ToolBar) {
//            if (componentType == null) {
//                componentType = AppComponentType.BUTTON;
//            }
//        } else if (parentGuiElement instanceof Menu
//                || parentGuiElement instanceof MenuBar
//                || parentGuiElement instanceof ContextMenu
//                || parentGuiElement instanceof MenuButton
//                || parentGuiElement instanceof MenuLabel) {
//            if (componentType == null) {
//                componentType = AppComponentType.BUTTON;
//            }
//            inMenu=true;
//        } else {
//            if (componentType == null) {
//                componentType = AppComponentType.BUTTON;
//            }
//        }
//        switch (componentType) {
//            case TOOL_BAR: {
//                return new FxAppToolBar(tool, application, options);
//            }
//            case TOOL_BAR_GROUP: {
//                return new FxAppToolBarGroup(tool, application, options);
//            }
//            case STATUS_BAR_GROUP: {
//                return new FxAppStatusBarGroup(tool, application, options);
//            }
//            case STATUS_BAR: {
//                return new FxAppToolBar(tool, application, options);
//            }
//            case BUTTON: {
//                if (inMenu) {
//                    return new FxAppMenu(tool, new Menu(), application, options);
//                }
//                return new FxAppDropDownButton(tool, application, options);
//            }
//            case MENU_BAR: {
//                return new FxAppMenuBar(tool, application, options);
//            }
//            case WINDOW: {
//                return new FxFrameAppFrame(tool, application, null);
//            }
//            default: {
//                throw new IllegalArgumentException("unsupported " + componentType);
//            }
//        }
//    }
//}
