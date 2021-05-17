/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx;

import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppToggleModel;
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
import net.thevpc.echo.api.components.Action;
import net.thevpc.echo.api.tools.AppToolButtonModel;
import net.thevpc.echo.Application;

/**
 *
 * @author vpc
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
    
    public static void prepareAbstractButton(MenuItem button, AppComponentModel tool, Application app, boolean text) {
        if (text) {
            tool.title().listeners().add((PropertyEvent event) -> {
                button.setText((String) event.getNewValue());
            });
            button.setText(tool.title().getOr(v->v==null?null:v.getValue(app.i18n())));
        } else {
            button.setText(null);
//            tool.title().listeners().add((PropertyEvent event) -> {
//                button.setToolTipText((String) event.getNewValue());
//            });
//            button.setToolTipText(tool.title().get());
        }
        tool.enabled().listeners().add((PropertyEvent event) -> {
            button.setDisable(!(Boolean) event.getNewValue());
        });
        button.setDisable(!tool.enabled().get());
        tool.visible().listeners().add((PropertyEvent event) -> {
            button.setVisible((Boolean) event.getNewValue());
        });
        button.setVisible(tool.visible().get());

        tool.smallIcon().listeners().add((PropertyEvent event) -> {
            button.setGraphic(FxAppImage.imageViewOf(event.getNewValue()));
        });
//        tool.smallIcon().reevalValue();
        button.setGraphic(FxAppImage.imageViewOf(tool.smallIcon().get()));

        tool.mnemonic().listeners().add((PropertyEvent event) -> {
            //button.setMnemonicParsing(true);
            //should repace mnemonic with text!!
            //button.setMnemonic((Integer) event.getNewValue());
        });
//        button.setMnemonic(tool.mnemonic().get());

        if (button instanceof MenuItem && !(button instanceof Menu)) {
            tool.accelerator().listeners().add((PropertyEvent event) -> {
                String s = (String) event.getNewValue();
//                ((MenuItem) button).setAccelerator(
//                        (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
//                );
            });
            String s = tool.accelerator().get();
//            ((MenuItem) button).setAccelerator(
//                    (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
//            );
        }

        if (tool instanceof AppToolButtonModel) {
            AppToolButtonModel action = (AppToolButtonModel) tool;
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    Action a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultActionEvent(app, tool, t.getSource(), t));
                    }
                }
            });
        }

        if (tool instanceof Toggle) {
            Toggle tg = (Toggle) button;
            _prepareToggle(tg, tool, app);
        }

    }

    public static void prepareAbstractButton(ButtonBase button, AppComponentModel tool, Application app, boolean text) {
        if (text) {
            tool.title().listeners().add((PropertyEvent event) -> {
                button.setText((String) event.getNewValue());
            });
            button.setText(tool.title().getOr(x->x==null?null:x.getValue(app.i18n())));
        } else {
            button.setText(null);
//            tool.title().listeners().add((PropertyEvent event) -> {
//                button.setToolTipText((String) event.getNewValue());
//            });
//            button.setToolTipText(tool.title().get());
        }
        tool.enabled().listeners().add((PropertyEvent event) -> {
            button.setDisable(!(Boolean) event.getNewValue());
        });
        button.setDisable(!tool.enabled().get());
        tool.visible().listeners().add((PropertyEvent event) -> {
            button.setVisible((Boolean) event.getNewValue());
        });
        button.setVisible(tool.visible().get());

        tool.smallIcon().listeners().add((PropertyEvent event) -> {
            button.setGraphic(FxAppImage.imageViewOf(event.getNewValue()));
        });
//        tool.smallIcon().reevalValue();
        button.setGraphic(FxAppImage.imageViewOf(tool.smallIcon().get()));

        tool.mnemonic().listeners().add((PropertyEvent event) -> {
            //button.setMnemonicParsing(true);
            //should repace mnemonic with text!!
            //button.setMnemonic((Integer) event.getNewValue());
        });
//        button.setMnemonic(tool.mnemonic().get());

        if (tool instanceof AppToolButtonModel) {
            AppToolButtonModel action = (AppToolButtonModel) tool;
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    Action a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultActionEvent(app, tool, t.getSource(), t));
                    }
                }
            });
        }

        if (tool instanceof Toggle) {
            Toggle tg = (Toggle) button;
            _prepareToggle(tg, tool, app);
        }
    }

    public static void _prepareToggle(Toggle tg, AppComponentModel tool, Application application) {
        ObservableValue<String> group = null;
        AppToggleModel cc = (AppToggleModel) tool;
        group = cc.group();
        if (group != null) {
            String s = group.get();
            FxApplicationToolkit tk = (FxApplicationToolkit) application.toolkit();
            if (s != null) {
                tg.setToggleGroup(tk.getButtonGroup(s));
            }
            group.listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    tg.setToggleGroup(tk.getButtonGroup((String) event.getNewValue()));
                }
            });
        }

        tg.toggleGroupProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                ((AppToggleModel) tool).selected().set(tg.isSelected());
            }
        });
        tg.setSelected(cc.selected().get());
        cc.selected().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                tg.setSelected(event.getNewValue());
            }
        });
    }

}
