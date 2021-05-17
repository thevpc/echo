//package net.thevpc.echo.jfx.containers.menus;
//
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Window;
//import net.thevpc.common.props.PropertyEvent;
//import net.thevpc.common.props.PropertyListener;
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.components.AppMenu;
//import net.thevpc.echo.api.tools.AppContainerModel;
//import net.thevpc.echo.jfx.FxPeer;
//
//public class FxAppBaseMenu extends FxAppToolContainerImpl implements AppMenu {
//
//    public FxAppBaseMenu(AppContainerModel folder, Window comp, Application application, AppComponentOptions options) {
//        super(folder, comp, application, options);
//        tool().visible().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                getApplication().toolkit().runUILater(() -> {
//                    if (!(Boolean) event.getNewValue()) {
//                        ((Window) guiElement()).hide();
//                    } else {
//                        //((Window) guiElement()).show();
//                    }
//                });
//            }
//        });
//
//    }
//
//    public FxAppBaseMenu(AppContainerModel folder, Node comp, Application application, AppComponentOptions options) {
//        super(folder, comp, application, options);
//        tool().visible().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                getApplication().toolkit().runUILater(() -> {
//                    ((Node) guiElement()).setVisible((Boolean) event.getNewValue());
//                });
//            }
//        });
//    }
//
//    public FxAppBaseMenu(AppContainerModel folder, MenuItem comp, Application application, AppComponentOptions options) {
//        super(folder, comp, application, options);
//        tool().visible().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                getApplication().toolkit().runUILater(() -> {
//                    ((Node) guiElement()).setVisible((Boolean) event.getNewValue());
//                });
//
//            }
//        });
//    }
//
//    @Override
//    public void show(Object source, int x, int y) {
//        getApplication().toolkit().runUILater(() -> {
//            Object g = guiElement();
//            if (g instanceof ContextMenu) {
//                ContextMenu c = (ContextMenu) g;
//                c.show((Node) source, x, y);
//            }
//            if (g instanceof AnchorPane) {
//                AnchorPane c = (AnchorPane) g;
//                //c.show((Node) source, x, y);
//            }
//            if (g instanceof Menu) {
//                Menu c = (Menu) g;
//                //c.set
//                c.show();
//                //c.setPopupMenuVisible(true);
//            }
//            if (g instanceof MenuButton) {
//                MenuButton c = (MenuButton) g;
////            c.setMenuLocation(x,y);
////            c.setPopupMenuVisible(true);
//                c.show();
//            }
////        if(g instanceof JDropDownLabel) {
////            JDropDownLabel c = (JDropDownLabel) g;
//////            c.setMenuLocation(x,y);
//////            c.setPopupMenuVisible(true);
////        }
//        });
//    }
//
//    @Override
//    public boolean isActionable() {
//        return _isActionable(guiElement());
//    }
//
//    @Override
//    public void removeImmediateImpl(String name, int index, AppComponent childComponent) {
//        super.removeImmediateImpl(name, index, childComponent);
//        getApplication().toolkit().runUILater(() -> {
//            Object o = guiElement();
//            if (o instanceof ContextMenu) {
//                ContextMenu p = (ContextMenu) o;
//                p.getItems().remove(index);
//            }
//            if (o instanceof MenuBar) {
//                MenuBar p = (MenuBar) o;
//                p.getMenus().remove(index);
//            }
//            if (o instanceof Menu) {
//                Menu p = (Menu) o;
//                p.getItems().remove(index);
//            }
//            if (o instanceof MenuButton) {
//                MenuButton p = (MenuButton) o;
//                p.getItems().remove(index);
//            }
//        });
//    }
//
//    @Override
//    protected void addImmediateImpl(int index, AppComponent childComponent) {
//        Object c2 = FxPeer.of((AppComponent) childComponent).toolkitComponent();
//        getApplication().toolkit().runUILater(() -> {
//            Object o = guiElement();
//            Object c = FxPeer.of((AppComponent) childComponent).toolkitComponent();
//            if (o instanceof ContextMenu) {
//                ContextMenu p = (ContextMenu) o;
//                p.getItems().add(index, (MenuItem) c);
//            }
//            if (o instanceof MenuBar) {
//                MenuBar p = (MenuBar) o;
//                p.getMenus().add(index, (Menu) c);
//            }
//            if (o instanceof Menu) {
//                Menu p = (Menu) o;
//                p.getItems().add(index, (MenuItem) c);
//            }
//            if (o instanceof MenuButton) {
//                MenuButton p = (MenuButton) o;
//                p.getItems().add(index, (MenuItem) c);
//            }
//        });
////        if (o instanceof JDropDownLabel) {
////            JDropDownLabel p = (JDropDownLabel) o;
////            p.add(c,index);
////        }
//    }
//
//    protected boolean _isActionable(Object o) {
//        if (o instanceof ContextMenu) {
//            ContextMenu p = (ContextMenu) o;
//            for (MenuItem subElement : p.getItems()) {
//                if (_isActionable(subElement)) {
//                    return true;
//                }
//            }
//        }
//        if (o instanceof MenuItem) {
//            MenuItem p = (MenuItem) o;
//            Node c = p.getGraphic();
//            if (c.isVisible() && !c.isDisabled()) {
//                return true;
//            }
//            if (o instanceof Menu) {
//                Menu p2 = (Menu) o;
//                for (MenuItem subElement : p2.getItems()) {
//                    if (_isActionable(subElement)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        if (o instanceof Node) {
//            Node c = (Node) o;
//            if (c.isVisible() && !c.isDisabled()) {
//                return true;
//            }
//            if (o instanceof Parent) {
//                for (Node subElement : ((Parent) c).getChildrenUnmodifiable()) {
//                    if (_isActionable(subElement)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        if (o instanceof MenuButton) {
//            MenuButton c = (MenuButton) o;
//            if (c.isVisible() && !c.isDisabled()) {
//                return true;
//            }
//            if (o instanceof Parent) {
//                for (Node subElement : ((Parent) c).getChildrenUnmodifiable()) {
//                    if (_isActionable(subElement)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//}
