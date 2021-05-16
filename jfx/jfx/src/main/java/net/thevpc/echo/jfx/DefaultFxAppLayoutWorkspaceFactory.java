///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.jfx;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppFrame;
//import net.thevpc.echo.api.components.WindowWorkspaceOptions;
//import net.thevpc.echo.api.tools.AppToolFolder;
//import net.thevpc.echo.jfx.containers.ws.DefaultFxAppWorkspace;
//
///**
// *
// * @author vpc
// */
//public class DefaultFxAppLayoutWorkspaceFactory implements AppLayoutWorkspaceFactory {
//
//    public DefaultFxAppLayoutWorkspaceFactory() {
//    }
//
//    @Override
//    public SupportSupplier<AppWorkspace> createWorkspace(AppToolFolder tool, AppFrame frame, WindowWorkspaceOptions options) {
//        if (!(frame.getApplication().toolkit() instanceof FxApplicationToolkit)) {
//            return null;
//        }
//        return new SupportSupplier<AppWorkspace>() {
//            @Override
//            public int getSupportLevel() {
//                return 1;
//            }
//
//            @Override
//            public AppWorkspace get() {
//                return new DefaultFxAppWorkspace(tool, frame.getApplication(), options, frame);
//            }
//        };
//    }
//
//}