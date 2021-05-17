//package net.thevpc.echo.jfx.containers.menus;
//
//
//import javafx.scene.control.MenuButton;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.tools.AppContainerModel;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.jfx.icons.FxAppImage;
//
//public class FxAppDropDownButton extends FxAppBaseMenu{
//    public FxAppDropDownButton(AppContainerModel folder, Application application, AppComponentOptions options) {
//        super(folder, new MenuButton(), application,options);
//    }
//    public FxAppDropDownButton(AppContainerModel folder, MenuButton button, Application application, AppComponentOptions options) {
//        super(folder, button, application,options);
//        folder.title().listeners().add(x->button.setText(folder.title().get()));
//        folder.smallIcon().listeners().add(x->button.setGraphic(
//                FxAppImage.imageViewOf(folder.smallIcon().get())
//        ));
//    }
//}
