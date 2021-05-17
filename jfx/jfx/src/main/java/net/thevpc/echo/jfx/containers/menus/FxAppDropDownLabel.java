//package net.thevpc.echo.jfx.containers.menus;
//
//
//import javafx.scene.control.MenuButton;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.tools.AppContainerModel;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.jfx.icons.FxAppImage;
//
//public class FxAppDropDownLabel extends FxAppBaseMenu{
//    public FxAppDropDownLabel(AppContainerModel folder, Application application, AppComponentOptions options) {
//        this(folder, new MenuButton(), application,options);
//    }
//    public FxAppDropDownLabel(AppContainerModel folder, MenuButton d, Application application, AppComponentOptions options) {
//        super(folder, d, application,options);
//        folder.title().listeners().add(x->d.setText(folder.title().get()));
//        folder.smallIcon().listeners().add(x->d.setGraphic(
//                FxAppImage.imageViewOf(folder.smallIcon().get())
//        ));
//    }
//}
