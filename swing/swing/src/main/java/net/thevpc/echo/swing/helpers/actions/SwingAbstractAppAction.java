package net.thevpc.echo.swing.helpers.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.Application;

public abstract class SwingAbstractAppAction extends AbstractAction {

    protected Application application;
    protected String id;

    public SwingAbstractAppAction(Application application, String id) {
        this(application,id,null,null,null);
    }

    public SwingAbstractAppAction(Application application, String id,String name,String iconId,String description) {
        this.application = application;
        this.id = id;
        if(name==null) {
            name=application.i18n().getString("Action." + id + ".name");
        }
        if(description==null) {
            description=application.i18n().getString("Action." + id + ".description", x -> "");
        }
        if(iconId==null) {
            iconId = application.i18n().getString("$Action." + id + ".icon", x -> null); //the dollar meens the the icon key is resolved from i18n
        }
        if (iconId == null) {
            iconId = id;
        }
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, description);
        putValue("SmallIconId", iconId);
        putValue(SMALL_ICON, application.iconSets().icon(iconId).get());
        application.iconSets().icon(iconId).listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                putValue(SMALL_ICON, event.getNewValue());
            }
        });
    }

    public String getId() {
        return id;
    }

    public Component getMainComponent() {
        Object o = getApplication().mainFrame().get().peer().toolkitComponent();
        if (o instanceof Component) {
            return (Component) o;
        }
        return null;
    }

    public Application getApplication() {
        return application;
    }

    public void setAccessible(boolean visible) {
        setEnabled(visible);
        setVisible(visible);
    }

    public void setVisible(boolean visible) {
        putValue("visible", visible);
    }

    public boolean isVisible(boolean visible) {
        Object a = getValue("visible");
        if (a == null) {
            return true;
        }
        if (a instanceof Boolean) {
            return ((Boolean) a);
        }
        return Boolean.valueOf(String.valueOf(a));
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        if (confirmation(e)) {
            try {
                actionPerformedImpl(e);
            } catch (Exception ex) {
                application.errors().add(ex);
            }
        }
    }

    protected abstract void actionPerformedImpl(ActionEvent e);

    protected boolean confirmation(ActionEvent e) {
        return true;
    }

    public void refresh() {

    }

}
