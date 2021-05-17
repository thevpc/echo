//package net.thevpc.echo.jfx.renderers;
//
//import net.thevpc.echo.*;
//
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuButton;
//import javafx.scene.control.Separator;
//import javafx.scene.control.SeparatorMenuItem;
//import javafx.scene.control.ToolBar;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.VBox;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.tools.AppSeparatorModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.ToolBarGroup;
//import net.thevpc.echo.jfx.controls.FxSeparatorControl;
//import net.thevpc.echo.jfx.raw.MenuLabel;
//import net.thevpc.echo.jfx.raw.StatusBarGroup;
//
//public class FxAppToolSeparatorComponentRenderer implements AppComponentRenderer {
//
//    @Override
//    public AppComponent createComponent(AppComponentRendererContext context) {
//        Object parentGuiElement = ((FxPeer) context.getParent()).fxComponent();
//        AppSeparatorModel tool = (AppSeparatorModel) context.getTool();
//        Application application = context.getApplication();
//        double height = ((Number) tool.height().get()).doubleValue();
//        double width = ((Number) tool.width().get()).doubleValue();
//        if (parentGuiElement instanceof ToolBar
//                || parentGuiElement instanceof MenuBar) {
//            Separator m = new Separator();
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        if (parentGuiElement instanceof ToolBarGroup || parentGuiElement instanceof StatusBarGroup) {
//            return createSpacer(width, height, tool, context, application);
//        }
//        if (parentGuiElement instanceof Menu
//                || parentGuiElement instanceof ContextMenu
//                || parentGuiElement instanceof MenuButton
//                || parentGuiElement instanceof MenuLabel) {
//            SeparatorMenuItem m = new SeparatorMenuItem();
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        return createSpacer(width, height, tool, context, application);
//    }
//
//    protected AppComponent createSpacer(double width, double height, AppSeparatorModel tool, AppComponentRendererContext context, Application application) {
//        if (isMax(width) && isMax(height)) {
//            Region m = new Region();
//            HBox.setHgrow(m, Priority.ALWAYS);
//            VBox.setVgrow(m, Priority.ALWAYS);
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        if (isMax(width)) {
//            Region m = new Region();
//            if (height > 0) {
//                m.setPrefHeight(height);
//            }
//            HBox.setHgrow(m, Priority.ALWAYS);
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        if (isMax(height)) {
//            Region m = new Region();
//            if (width > 0) {
//                m.setPrefWidth(width);
//            }
//            VBox.setVgrow(m, Priority.ALWAYS);
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        if (height == 0 && width == 0) {
//            Region m = new Region();
//            return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//        }
//        Region m = new Region();
//        m.setPrefWidth(width);
//        m.setPrefHeight(width);
//        return new FxSeparatorControl(tool, context.getPath(), m, application, context.getOptions());
//    }
//
//    boolean isMax(Double d) {
//        return d.equals(Double.MAX_VALUE) || d.compareTo((double) Integer.MAX_VALUE) >= 0;
//    }
//}
