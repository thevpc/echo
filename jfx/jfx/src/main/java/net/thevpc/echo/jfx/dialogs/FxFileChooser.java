///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.jfx.dialogs;
//
//import java.io.File;
//import java.util.List;
//
//import javafx.stage.Window;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFileChooser;
//import net.thevpc.echo.Application;
//import net.thevpc.echo.api.tools.AppToolFileFilter;
//import net.thevpc.echo.impl.components.FileChooser;
//import net.thevpc.echo.api.peers.AppFileChooserPeer;
//import net.thevpc.echo.jfx.FxPeer;
//
///**
// *
// * @author vpc
// */
//public class FxFileChooser implements AppFileChooserPeer, FxPeer {
//
//    private Application app;
//    private FileChooser fileChooser;
//
//    public void install(AppComponent comp) {
//        this.fileChooser = (FileChooser) comp;
//        this.app = fileChooser.app();
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return fileChooser;
//    }
//
//    private javafx.stage.FileChooser newInstance() {
//        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
//        fc.initialDirectoryProperty().set(
//                fileChooser.tool().currentDirectory().getOr(x->x==null?null:new File(x))
//        );
//        return fc;
//    }
//
//    @Override
//    public boolean showOpenDialog(Object owner) {
//        javafx.stage.FileChooser fc = newInstance();
//        if (fileChooser.tool().multipleValues().get()) {
//            List<File> a = fc.showOpenMultipleDialog(resolveWindowOwner(owner));
//            fileChooser.tool().selection().setAll(
//                    a.stream().map(x -> x.getPath()).toArray(String[]::new)
//            );
//        } else {
//            File a = fc.showOpenDialog(resolveWindowOwner(owner));
//            if (a != null) {
//                fileChooser.tool().selection().setAll(a.getPath());
//            }else{
//                fileChooser.tool().selection().clear();
//            }
//        }
//        return fileChooser.tool().selection().size() > 0;
//    }
//
//    @Override
//    public boolean showSaveDialog(Object owner) {
//        javafx.stage.FileChooser fc = newInstance();
//        File a = fc.showSaveDialog(resolveWindowOwner(owner));
//        fileChooser.tool().selection().clear();
//        if (a != null) {
//            fileChooser.tool().selection().setAll(a.getPath());
//        }else{
//            fileChooser.tool().selection().clear();
//        }
//        return fileChooser.tool().selection().isEmpty();
//    }
//
//    private Window resolveWindowOwner(Object owner) {
//        if (owner == null) {
//            Object c = app.mainFrame().get().peer().toolkitComponent();
//            if (c instanceof Window) {
//                return (Window) c;
//            }
//        }
//        return null;
//    }
//
//}
