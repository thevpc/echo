package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolAction;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.Props;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.thevpc.echo.Application;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppTools;

public class AppToolActionImpl extends AbstractAppTool implements AppToolAction {

    private WritableValue<ActionListener> action = Props.of("action").valueOf(ActionListener.class, null);
    private SwingToProp swingToProp = new SwingToProp();

    public AppToolActionImpl(String id, ActionListener action,Application app,AppTools tools) {
        super(id, app,tools);
        this.action.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                switch (event.getAction()) {
                    case UPDATE: {
                        Object oldAction = event.getOldValue();
                        if (oldAction instanceof Action) {
                            ((Action) oldAction).removePropertyChangeListener(swingToProp);
                            title().listeners().removeIf(x -> x instanceof PropToSwing);
                        }
                        Object newAction = event.getNewValue();
                        if (newAction instanceof Action) {
                            swingToPropNow((Action) newAction);
                        }
                        break;
                    }
                }
            }
        });
        if (action instanceof Action) {
            swingToPropNow((Action) action);
        }
        this.action.set(action);

    }

    private void swingToPropNow(Action a) {
//        ImageIcon smallIcon = (ImageIcon) a.getValue(Action.SMALL_ICON);
        title().set((String) a.getValue(Action.NAME));
//        ImageIcon smallIcon = (ImageIcon) a.getValue(Action.SMALL_ICON);
        tooltip().set((String) a.getValue(Action.SHORT_DESCRIPTION));
//        smallIcon().set(smallIcon);
//            description().set((String) a.getValue(Action.SHORT_DESCRIPTION));

        a.addPropertyChangeListener(swingToProp);
        title().listeners().add(new PropToSwing(a));
    }

    @Override
    public WritableValue<ActionListener> action() {
        return action;
    }

    private static class PropToSwing implements PropertyListener {

        private final Object newAction;

        public PropToSwing(Object newAction) {
            this.newAction = newAction;
        }

        @Override
        public void propertyUpdated(PropertyEvent event) {
            switch (event.getProperty().name()) {
//                case "description": {
//                    ((Action) newAction).putValue(Action.SHORT_DESCRIPTION, event.getNewValue());
//                    break;
//                }
                case "smallIcon": {
                    ((Action) newAction).putValue(Action.SMALL_ICON, event.getNewValue());
                    break;
                }
                case "enabled": {
                    ((Action) newAction).setEnabled((Boolean)event.getNewValue());
                    break;
                }
                case "visible": {
                    ((Action) newAction).putValue("visible",(Boolean)event.getNewValue());
                    break;
                }
                case "title": {
                    ((Action) newAction).putValue(Action.NAME, event.getNewValue());
                    break;
                }
                case "tooltip": {
                    ((Action) newAction).putValue(Action.SHORT_DESCRIPTION,(Boolean)event.getNewValue());
                    break;
                }
                case "mnemonic": {
                    ((Action) newAction).putValue(Action.MNEMONIC_KEY,(Integer)event.getNewValue());
                    break;
                }
                case "accelerator": {
                    String s=event.getNewValue();
                    KeyStroke ks=null;
                    if(s!=null && s.length()>0){
                        ks=KeyStroke.getKeyStroke(s);
                    }
                    ((Action) newAction).putValue(Action.ACCELERATOR_KEY,ks);
                    break;
                }
            }
        }
    }

    private class SwingToProp implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            switch (evt.getPropertyName()) {
                case Action.NAME: {
                    title().set((String) evt.getNewValue());
                    break;
                }
                case Action.SHORT_DESCRIPTION: {
                    //description().set((String) evt.getNewValue());
                    break;
                }
                case Action.SMALL_ICON: {
                    Icon newValue = (Icon) evt.getNewValue();
//                    if (newValue == null || (newValue instanceof ImageIcon)) {
//                        smallIcon().set((ImageIcon) newValue);
//                    }
                    break;
                }
                case "enabled": {
                    Boolean newValue = (Boolean) evt.getNewValue();
                    enabled().set(newValue);
                    break;
                }
                case "visible": {
                    Boolean newValue = (Boolean) evt.getNewValue();
                    visible().set(newValue);
                    break;
                }
                case Action.MNEMONIC_KEY: {
                    Integer newValue = (Integer) evt.getNewValue();
                    mnemonic().set(newValue);
                    break;
                }
                case Action.ACCELERATOR_KEY: {
                    KeyStroke newValue = (KeyStroke) evt.getNewValue();
                    accelerator().set(newValue==null?null:newValue.toString());
                    break;
                }
            }
        }
    }
}
