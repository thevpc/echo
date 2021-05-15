//package net.thevpc.echo.jfx.controls;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuItem;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.jfx.FxPeer;
//
//public class FxLabelPeer implements FxPeer {
//    private Object fxlabel;
//
//    public FxLabelPeer() {
//    }
//
//    @Override
//    public void install(AppComponent component) {
//        net.thevpc.echo.impl.components.Label label=(net.thevpc.echo.impl.components.Label) component;
//        Object sParent = component.parent()==null?null:component.parent().peer().toolkitComponent();
//        if(sParent instanceof Menu){
//            MenuItem mLabel = new MenuItem();
//            this.fxlabel = mLabel;
//            label.tool().text().listeners().addInstall(() -> mLabel.setText(label.tool().text().get()));
//        }else {
//            Label mLabel = new Label();
//            this.fxlabel = mLabel;
//            label.tool().text().listeners().addInstall(() -> mLabel.setText(label.tool().text().get()));
//        }
//    }
//
//
//    @Override
//    public Object toolkitComponent() {
//        return fxlabel;
//    }
//
//}
