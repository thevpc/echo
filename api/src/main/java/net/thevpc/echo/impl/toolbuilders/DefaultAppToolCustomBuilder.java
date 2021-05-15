///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.impl.toolbuilders;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.toolbuilders.AppToolCustomBuilder;
//import net.thevpc.echo.api.tools.AppTool;
//import net.thevpc.echo.api.tools.AppToolCustom;
//import net.thevpc.echo.impl.AbstractAppToolsBase;
//import net.thevpc.echo.impl.tools.AppToolCustomImpl;
//
//import java.util.function.Supplier;
//
///**
// *
// * @author vpc
// */
//public class DefaultAppToolCustomBuilder
//        extends AbstractAppToolBuilder<
//                AppTool, AppComponent,
//                AppComponentOptions, AppToolCustomBuilder>
//        implements AppToolCustomBuilder {
//
//    private AppComponentRenderer renderer;
//    public DefaultAppToolCustomBuilder(AbstractAppToolsBase tools) {
//        super(tools);
//    }
//
//    @Override
//    public AppToolCustomBuilder renderer(Supplier<?> m) {
//        this.renderer=m==null?null:(
//                (x)->tools.app().toolkit().createComponent(
//                        m.get()
//                )
//                );
//        return this;
//    }
//
//
//    public AppComponent buildFirst(AppComponentOptions options) {
//        ItemPath ipath = paths.get(0);
//        String _id = id;
//        if (_id == null) {
//            _id = ipath.isEmpty() ? "Action.Unknown" : "Action." + ipath.name();
//        }
//        AppToolCustom tool = new AppToolCustomImpl(_id, renderer, tools.app(), tools);
//        tool.title().setId(_id);
//        tool.smallIcon().setId("$" + _id + ".icon"); //the dollar meens the the icon key is resolved from i18n
//        return tools.addTool(tool, ipath, options);
//    }
//
//}
