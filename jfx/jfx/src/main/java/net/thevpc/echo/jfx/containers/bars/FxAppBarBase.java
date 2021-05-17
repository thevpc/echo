//package net.thevpc.echo.jfx.containers.bars;
//
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.control.ButtonBase;
//import javafx.scene.control.ToolBar;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.stage.Window;
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.echo.*;
//
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppToolBar;
//import net.thevpc.echo.api.tools.AppContainerModel;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.raw.StatusBarGroup;
//import net.thevpc.echo.jfx.ToolBarGroup;
//
//public class FxAppBarBase extends FxAppToolContainerImpl implements AppToolBar {
//
//    public FxAppBarBase(AppContainerModel folder, Node comp, Application application, AppComponentOptions options) {
//        super(folder, comp, application, options);
//        tool().visible().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                getApplication().toolkit().runUI(() -> {
//                    if (!(Boolean) event.getNewValue()) {
//                        ((Window) guiElement()).hide();
//                    } else {
//                        //((Window) guiElement()).show();
//                    }
//                });
//
//            }
//        });
//    }
//
//    @Override
//    public void removeImmediateImpl(String name, int index, AppComponent childComponent) {
//        app().toolkit().runUIAndWait(() -> {
//            Object g = guiElement();
//            if (g instanceof ToolBarGroup) {
//                ((ToolBarGroup) g).getChildren().remove(index);
//            }
//            if (g instanceof ToolBar) {
//                ((ToolBar) g).getItems().remove(index);
//            }
//            if (g instanceof StatusBarGroup) {
//                ((StatusBarGroup) g).getChildren().remove(index);
//            }
//        });
//    }
//
//    protected void addImmediateImpl(int index, AppComponent childComponent) {
//        getApplication().toolkit().runUIAndWait(() -> {
//            Object g = guiElement();
//            if (g instanceof ToolBarGroup) {
//                Node e = FxPeer.of(childComponent).getNode();
//                HBox.setHgrow(e, Priority.SOMETIMES);
//                ((ToolBarGroup) g).getChildren().add(index, e);
//            }
//            if (g instanceof ToolBar) {
//                Node e = FxPeer.of(childComponent).getNode();
//                ((ToolBar) g).getItems().add(index, e);
//            }
//            if (g instanceof StatusBarGroup) {
//                ((StatusBarGroup) g).getChildren().add(index, FxPeer.of(childComponent).getNode());
//            }
//        });
//
//    }
//
//    //    @Override
//    public boolean isActionable() {
//        return _isActionable(guiElement());
//    }
//
//    private boolean _isActionable(Object o) {
//        if (o instanceof ToolBar) {
//            ToolBar p = (ToolBar) o;
//            for (Node subElement : p.getItems()) {
//                if (_isActionable(subElement)) {
//                    return true;
//                }
//            }
//        }
//        if (o instanceof Parent) {
//            Parent p = (Parent) o;
//            for (Node subElement : p.getChildrenUnmodifiable()) {
//                if (_isActionable(subElement)) {
//                    return true;
//                }
//            }
//        }
//        if (o instanceof ButtonBase) {
//            return !((ButtonBase) o).isDisabled();
//        }
//        return false;
//    }
//
//}
