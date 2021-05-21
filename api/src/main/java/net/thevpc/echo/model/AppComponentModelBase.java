//package net.thevpc.echo.model;
//
//import net.thevpc.common.i18n.Str;
//import net.thevpc.common.i18n.WritableStr;
//import net.thevpc.common.props.*;
//import net.thevpc.common.props.impl.SimpleProperty;
//import net.thevpc.echo.AppProps;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.Dimension;
//import net.thevpc.echo.WritableTextStyle;
//import net.thevpc.echo.api.AppColor;
//import net.thevpc.echo.api.AppFont;
//import net.thevpc.echo.api.model.AppComponentModel;
//import net.thevpc.echo.constraints.Anchor;
//import net.thevpc.echo.iconset.WritableImage;
//
//import java.util.UUID;
//
//public class AppComponentModelBase extends SimpleProperty implements AppComponentModel {
//
//
//    public AppComponentModelBase(String id, Application app) {
//        this(id, app, true);
//    }
//
//    public AppComponentModelBase(Application app) {
//        this(null, app, true);
//    }
//
//    public AppComponentModelBase(String id, Application app, boolean doConfig) {
//        super(id == null ? UUID.randomUUID().toString() : id);
//    }
//
//}
