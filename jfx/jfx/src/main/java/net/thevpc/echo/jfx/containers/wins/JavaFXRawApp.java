///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.jfx.containers.wins;
//
//import java.util.concurrent.CountDownLatch;
//import javafx.stage.Stage;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.UncheckedException;
//
///**
// *
// * @author thevpc
// */
//public class JavaFXRawApp extends javafx.application.Application {
//
//    private Stage stage0;
//
//    private static JavaFXRawApp STATIC_CLASS;
//
//    static final CountDownLatch countDown = new CountDownLatch(1);
//
//    public static JavaFXRawApp t;
//
//    public static Stage newStage(Application app) {
//        if (STATIC_CLASS == null) {
//            class ThrowableWrapper {
//
//                Throwable t;
//            }
//            final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
//            new Thread() {
//                public void run() {
//                    try {
//                        JavaFXRawApp.launch(JavaFXRawApp.class, new String[0]);
//                    } catch (Throwable e) {
//                        e.printStackTrace();
//                        throwableWrapper.t = e;
//                    }
//                }
//            }.start();
//            try {
//                countDown.await();
//            } catch (Exception ex) {
//                throw UncheckedException.wrap(ex);
//            }
//            return t.getStage0();
//        } else {
//            return app.toolkit().callUIAndWait(() -> new Stage());
//        }
//    }
//
//    public JavaFXRawApp() {
//        t = this;
//    }
//
//    public Stage getStage0() {
//        return stage0;
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        this.stage0 = stage;
//        countDown.countDown();
//    }
//}
