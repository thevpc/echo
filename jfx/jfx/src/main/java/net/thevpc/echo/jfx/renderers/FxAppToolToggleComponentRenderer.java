//package net.thevpc.echo.jfx.renderers;
//
//import javafx.scene.control.ButtonBase;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.CheckMenuItem;
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuButton;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.RadioMenuItem;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.control.ToolBar;
//import javafx.scene.layout.AnchorPane;
//import net.thevpc.echo.*;
//
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppComponentType;
//import net.thevpc.echo.api.tools.AppToolToggle;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.ToolBarGroup;
//import net.thevpc.echo.jfx.containers.menus.FxToggleButtonControl;
//import net.thevpc.echo.jfx.raw.MenuLabel;
//import net.thevpc.echo.jfx.raw.StatusBarGroup;
//
//public class FxAppToolToggleComponentRenderer implements AppComponentRenderer {
//
//    @Override
//    public AppComponent createComponent(AppComponentRendererContext context) {
//        Object parentGuiElement = ((FxPeer) context.getParent()).fxComponent();
//        Application application = context.getApplication();
//        AppToolToggle tool = (AppToolToggle) context.getTool();
//        AppComponentOptions options = context.getOptions();
//        AppComponentType componentType = options == null ? null : options.componentType();
//        boolean inMenu = false;
//        boolean withText = true;
//        if (parentGuiElement instanceof ToolBar
//                || parentGuiElement instanceof ToolBarGroup
//                || parentGuiElement instanceof StatusBarGroup) {
//            inMenu = false;
//            withText = false;
//        } else if (parentGuiElement instanceof MenuBar) {
//            inMenu = false;
//        } else if (parentGuiElement instanceof Menu
//                || parentGuiElement instanceof ContextMenu
//                || parentGuiElement instanceof AnchorPane
//                || parentGuiElement instanceof MenuButton
//                || parentGuiElement instanceof MenuLabel) {
//            inMenu = true;
//            if (componentType == null) {
//                if (tool.group().get() == null) {
//                    componentType = AppComponentType.CHECK;
//                } else {
//                    componentType = AppComponentType.RADIO;
//                }
//            }
//        }
//        if (componentType == null) {
//            componentType = AppComponentType.BUTTON;
//        }
//        Object m = null;
//        switch (componentType) {
//            case BUTTON: {
//                if (inMenu) {
//                    m = new CheckMenuItem();
//                } else {
//                    m = new ToggleButton();
//                }
//                break;
//            }
//            case CHECK: {
//                if (inMenu) {
//                    m = new CheckMenuItem();
//                } else {
//                    m = new CheckBox();
//                }
//                break;
//            }
//            case RADIO: {
//                if (inMenu) {
//                    m = new RadioMenuItem();
//                } else {
//                    m = new RadioButton();
//                }
//                break;
//            }
//            default: {
//                throw new IllegalArgumentException("unsupported button type " + componentType);
//            }
//        }
//        if (m instanceof ButtonBase) {
//            return new FxToggleButtonControl(tool, context.getPath(), (ButtonBase) m, application, withText, context.getOptions());
//        }
//        return new FxToggleButtonControl(tool, context.getPath(), (MenuItem) m, application, withText, context.getOptions());
//    }
//}
