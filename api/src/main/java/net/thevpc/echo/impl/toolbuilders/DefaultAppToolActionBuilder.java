///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.impl.toolbuilders;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.Str;
//import net.thevpc.echo.api.components.Action;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppComponentOptions;
//import net.thevpc.echo.api.toolbuilders.AppToolActionBuilder;
//import net.thevpc.echo.api.tools.AppToolButtonModel;
//import net.thevpc.echo.impl.AbstractAppToolsBase;
//import net.thevpc.echo.impl.tools.ButtonModel;
//
///**
// *
// * @author vpc
// */
//public class DefaultAppToolActionBuilder extends AbstractAppToolBuilder<
//        AppToolButtonModel, AppComponent,
//        AppComponentOptions, AppToolActionBuilder> implements AppToolActionBuilder {
//    private Action al;
//    public DefaultAppToolActionBuilder(AbstractAppToolsBase tools) {
//        super(tools);
//    }
//
//    @Override
//    public AppToolActionBuilder bindUndo(AppUndoableActionSupplier a) {
//        this.al = a == null ? new Action() {
//            @Override
//            public void run(ActionEvent event) {
//
//            }
//        } : new Action() {
//            @Override
//            public void run(ActionEvent event) {
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
//    public AppToolActionBuilder bind(Action a) {
//        this.al = new Action() {
//            @Override
//            public void run(ActionEvent event) {
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
//        bind(new Action() {
//            @Override
//            public void run(ActionEvent event) {
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
//        Path ipath = paths.get(0);
////        AppToolButtonModel c = null;
//        String _id = id;
//        if (_id == null) {
//            _id = ipath.isEmpty() ? "Action.Unknown" : "Action." + ipath.name();
//        }
//        AppToolButtonModel tool = new ButtonModel(_id, null, tools.app(), tools);
//        tool.title().set(Str.i18n(_id));
//        tool.smallIcon().set(Str.i18n(_id));
//        AppComponent c = tools.addTool(tool, ipath, options);
//
//        tool.action().set(al == null ? new Action() {
//            @Override
//            public void run(ActionEvent event) {
//            }
//        } : al);
//        return c;
//    }
//
//
//}
