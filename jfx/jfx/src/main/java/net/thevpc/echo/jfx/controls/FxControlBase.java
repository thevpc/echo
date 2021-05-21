//package net.thevpc.echo.jfx.controls;
//
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.model.AppComponentModel;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.impl.components.ComponentBase;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.FxApplicationUtils;
//
//public class FxControlBase extends ComponentBase implements FxPeer {
//    protected Object jcomponent;
//    protected Application app;
//    public FxControlBase(AppComponentModel tool, Path path, Object jcomponent, Application app, AppComponentOptions options) {
//        super(tool,path,options);
//        FxApplicationUtils.bindAppComponent(jcomponent, this);
//        this.app=app;
//        this.jcomponent=jcomponent;
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return jcomponent;
//    }
//}
