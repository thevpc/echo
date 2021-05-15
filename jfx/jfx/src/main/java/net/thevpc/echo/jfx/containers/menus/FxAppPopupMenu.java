//package net.thevpc.echo.jfx.containers.menus;
//
//import javafx.scene.Node;
//import javafx.scene.control.ContextMenu;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppMenu;
//import net.thevpc.echo.api.tools.AppToolFolder;
//import net.thevpc.echo.Application;
//
//public class FxAppPopupMenu extends FxAppBaseMenu implements AppMenu {
//    public FxAppPopupMenu(AppToolFolder folder, Application application, AppComponentOptions options) {
//        super(folder, new ContextMenu(), application, options);
//    }
//
//    public FxAppPopupMenu(AppToolFolder folder, ContextMenu button, Application application, AppComponentOptions options) {
//        super(folder, button, application, options);
//        //FxApplicationsUtils.prepareAbstractButton(button, folder, application, false);
//
//    }
//
//    @Override
//    public void show(Object source, int x, int y) {
//        ContextMenu c = (ContextMenu) guiElement();
//        c.show((Node) source, x, y);
//    }
//}
