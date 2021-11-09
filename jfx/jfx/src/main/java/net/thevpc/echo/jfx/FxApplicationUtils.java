/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToggleControl;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.DefaultActionEvent;
import net.thevpc.echo.jfx.icons.FxAppImage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Toggle;
import javafx.stage.Window;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.api.Action;
import net.thevpc.echo.Application;

/**
 *
 * @author thevpc
 */
public class FxApplicationUtils {

    public static FxPeer extractBindAppComponent(Object jcomponent) {
        if(jcomponent instanceof Node){
            Node jc=(Node) jcomponent;
            return (FxPeer) jc.getProperties().get(FxPeer.class.getName());
        }
        if(jcomponent instanceof Window){
            Window jc=(Window) jcomponent;
            return (FxPeer) jc.getProperties().get(FxPeer.class.getName());
        }
        if(jcomponent instanceof MenuItem){
            MenuItem jc=(MenuItem) jcomponent;
            return (FxPeer) jc.getProperties().get(FxPeer.class.getName());
        }
        return null;
    }
    
    public static void bindAppComponent(Object jcomponent, FxPeer instance) {
        if(jcomponent instanceof Node){
            Node jc=(Node) jcomponent;
            FxPeer e =(FxPeer) jc.getProperties().get(FxPeer.class.getName());
            if(e==null){
                jc.getProperties().put(FxPeer.class.getName(),instance);
            }
        }
        if(jcomponent instanceof Window){
            Window jc=(Window) jcomponent;
            FxPeer e =(FxPeer) jc.getProperties().get(FxPeer.class.getName());
            if(e==null){
                jc.getProperties().put(FxPeer.class.getName(),instance);
            }
        }
        if(jcomponent instanceof MenuItem){
            MenuItem jc=(MenuItem) jcomponent;
            FxPeer e =(FxPeer) jc.getProperties().get(FxPeer.class.getName());
            if(e==null){
                jc.getProperties().put(FxPeer.class.getName(),instance);
            }
        }
    }
    
    public static void prepareAbstractButton(MenuItem button, AppComponent tool, Application app, boolean text) {
        WritableStr tp = Applications.resolveTextProperty(tool);
        if (text) {
            if(tp!=null) {
                tp.onChange((PropertyEvent event) -> {
                    button.setText((String) event.newValue());
                });
                button.setText(Applications.rawString(tp,tool));
            }
        } else {
            button.setText(null);
//            tool.title().onChange((PropertyEvent event) -> {
//                button.setToolTipText((String) event.getNewValue());
//            });
//            button.setToolTipText(tool.title().get());
        }
        tool.enabled().onChange((PropertyEvent event) -> {
            button.setDisable(!(Boolean) event.newValue());
        });
        button.setDisable(!tool.enabled().get());
        tool.visible().onChange((PropertyEvent event) -> {
            button.setVisible((Boolean) event.newValue());
        });
        button.setVisible(tool.visible().get());

        tool.icon().onChange((PropertyEvent event) -> {
            button.setGraphic(FxAppImage.imageViewOf(event.newValue()));
        });
        tool.iconConfig().onChange((PropertyEvent event) -> {
            button.setGraphic(FxAppImage.imageViewOf(event.newValue()));
        });

        //        tool.smallIcon().reevalValue();
        button.setGraphic(FxAppImage.imageViewOf(tool.icon().get()));

        tool.mnemonic().onChange((PropertyEvent event) -> {
            //button.setMnemonicParsing(true);
            //should repace mnemonic with text!!
            //button.setMnemonic((Integer) event.getNewValue());
        });
//        button.setMnemonic(tool.mnemonic().get());

        if (button instanceof MenuItem && !(button instanceof Menu)) {
            tool.accelerator().onChange((PropertyEvent event) -> {
                String s = (String) event.newValue();
//                ((MenuItem) button).setAccelerator(
//                        (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
//                );
            });
            String s = tool.accelerator().get();
//            ((MenuItem) button).setAccelerator(
//                    (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
//            );
        }

        if (tool instanceof AppButton) {
            AppButton action = (AppButton) tool;
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    Action a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultActionEvent(app, tool, t.getSource(), t,action.value().get()));
                    }
                }
            });
        }

        if (tool instanceof Toggle) {
            Toggle tg = (Toggle) button;
            _prepareToggle(tg, tool, app);
        }

    }

    public static void prepareAbstractButton(ButtonBase button, AppComponent tool, Application app, boolean text) {
        if (text) {
            WritableStr tp = Applications.resolveTextProperty(tool);
            if(tp!=null) {
                tp.onChange((PropertyEvent event) -> {
                    button.setText((String) event.newValue());
                });
                button.setText(Applications.rawString(tp,tool));
            }
        } else {
            button.setText(null);
//            tool.title().onChange((PropertyEvent event) -> {
//                button.setToolTipText((String) event.getNewValue());
//            });
//            button.setToolTipText(tool.title().get());
        }
        tool.enabled().onChange((PropertyEvent event) -> {
            button.setDisable(!(Boolean) event.newValue());
        });
        button.setDisable(!tool.enabled().get());
        tool.visible().onChange((PropertyEvent event) -> {
            button.setVisible((Boolean) event.newValue());
        });
        button.setVisible(tool.visible().get());

        tool.icon().onChange((PropertyEvent event) -> {
            button.setGraphic(FxAppImage.imageViewOf(event.newValue()));
        });
//        tool.smallIcon().reevalValue();
        button.setGraphic(FxAppImage.imageViewOf(tool.icon().get()));

        tool.mnemonic().onChange((PropertyEvent event) -> {
            //button.setMnemonicParsing(true);
            //should repace mnemonic with text!!
            //button.setMnemonic((Integer) event.getNewValue());
        });
//        button.setMnemonic(tool.mnemonic().get());

        if (tool instanceof AppButton) {
            AppButton action = (AppButton) tool;
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    Action a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultActionEvent(app, tool, t.getSource(), t,action.value().get()));
                    }
                }
            });
        }

        if (tool instanceof Toggle) {
            Toggle tg = (Toggle) button;
            _prepareToggle(tg, tool, app);
        }
    }

    public static void _prepareToggle(Toggle tg, AppComponent tool, Application application) {
        ObservableValue<String> group = null;
        AppToggleControl cc = (AppToggleControl) tool;
        group = cc.group();
        if (group != null) {
            String s = group.get();
            FxApplicationToolkit tk = (FxApplicationToolkit) application.toolkit();
            if (s != null) {
                tg.setToggleGroup(tk.getButtonGroup(s));
            }
            group.onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    tg.setToggleGroup(tk.getButtonGroup((String) event.newValue()));
                }
            });
        }

        tg.toggleGroupProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                ((AppToggleControl) tool).selected().set(tg.isSelected());
            }
        });
        tg.setSelected(cc.selected().get());
        cc.selected().onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                tg.setSelected(event.newValue());
            }
        });
    }

}
