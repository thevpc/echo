///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.swing.mydoggy;
//
//import net.thevpc.echo.*;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppDock;
//import net.thevpc.echo.api.peers.AppComponentPeer;
//import net.thevpc.echo.api.model.AppContainerModel;
//import net.thevpc.echo.swing.SwingApplicationToolkit;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author thevpc
// */
//public class MyDoggyAppLayoutWorkspaceFactory implements AppComponentPeerFactory {
//
//    public MyDoggyAppLayoutWorkspaceFactory() {
//    }
//
//    @Override
//    public List<Class> getSupportedComponentTypes() {
//        return Arrays.asList(AppDock.class);
//    }
//
//    public SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component) {
//        AppContainerModel tool=(AppContainerModel) component.model();
//        if (!(component.app().toolkit() instanceof SwingApplicationToolkit)) {
//            return null;
//        }
//        return new SupportSupplier<AppComponentPeer>() {
//            @Override
//            public int getSupportLevel() {
////                AppWorkspace wc=(AppWorkspace) component;
////                if (options != null && !options.enabledDocking()) {
////                    return -1;
////                }
//                return 2;
//            }
//
//            @Override
//            public AppComponentPeer get() {
//                return new MyDoggyDockPeer();
//            }
//        };
//    }
//
//}
