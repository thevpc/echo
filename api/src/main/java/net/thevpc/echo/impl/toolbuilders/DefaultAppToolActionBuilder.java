///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.impl.toolbuilders;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.AppPath;
//import net.thevpc.echo.api.Str;
//import net.thevpc.echo.api.components.AppAction;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.toolbuilders.AppToolActionBuilder;
//import net.thevpc.echo.api.tools.AppToolAction;
//import net.thevpc.echo.impl.AbstractAppToolsBase;
//import net.thevpc.echo.impl.tools.ToolAction;
//
///**
// *
// * @author vpc
// */
//public class DefaultAppToolActionBuilder extends AbstractAppToolBuilder<
//        AppToolAction, AppComponent,
//        AppComponentOptions, AppToolActionBuilder> implements AppToolActionBuilder {
//    private AppAction al;
//    public DefaultAppToolActionBuilder(AbstractAppToolsBase tools) {
//        super(tools);
//    }
//
//    @Override
//    public AppToolActionBuilder bindUndo(AppUndoableActionSupplier a) {
//        this.al = a == null ? new AppAction() {
//            @Override
//            public void run(AppActionEvent event) {
//
//            }
//        } : new AppAction() {
//            @Override
//            public void run(AppActionEvent event) {
//                if (a != null) {
//                    UndoableAction r = null;
//                    try {
//                        r = a.supply(event);
//                    } catch (Exception ex) {
//                        tools.app().errors().add(ex);
//                    }
//                    if (r != null) {
//                        tools.app().history().doAction(r);
//                    }
//                }
//            }
//        };
//        return this;
//    }
//
//    public AppToolActionBuilder bind(AppAction a) {
//        this.al = new AppAction() {
//            @Override
//            public void run(AppActionEvent event) {
//                if (a != null) {
//                    try {
//                        a.run(event);
//                    } catch (Exception ex) {
//                        tools.app().errors().add(ex);
//                    }
//                }
//            }
//        };
//        return this;
//    }
//
//    public AppToolActionBuilder bind(Runnable a) {
//        bind(new AppAction() {
//            @Override
//            public void run(AppActionEvent event) {
//                if (a != null) {
//                    try {
//                        a.run();
//                    } catch (Exception ex) {
//                        tools.app().errors().add(ex);
//                    }
//                }
//            }
//        });
//        return this;
//    }
//
//    public AppComponent buildFirst(AppComponentOptions options) {
//        AppPath ipath = paths.get(0);
////        AppToolAction c = null;
//        String _id = id;
//        if (_id == null) {
//            _id = ipath.isEmpty() ? "Action.Unknown" : "Action." + ipath.name();
//        }
//        AppToolAction tool = new ToolAction(_id, null, tools.app(), tools);
//        tool.title().set(Str.i18n(_id));
//        tool.smallIcon().set(Str.i18n(_id));
//        AppComponent c = tools.addTool(tool, ipath, options);
//
//        tool.action().set(al == null ? new AppAction() {
//            @Override
//            public void run(AppActionEvent event) {
//            }
//        } : al);
//        return c;
//    }
//
//
//}
