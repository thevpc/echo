//package net.thevpc.echo.jfx.controls;
//
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.tools.AppTool;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.AppPath;
//import net.thevpc.echo.impl.components.AppComponentBase;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.jfx.FxApplicationUtils;
//
//public class FxControlBase extends AppComponentBase implements FxPeer {
//    protected Object jcomponent;
//    protected Application app;
//    public FxControlBase(AppTool tool, AppPath path, Object jcomponent, Application app, AppComponentOptions options) {
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
