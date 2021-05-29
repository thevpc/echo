package net.thevpc.echo.impl;

import net.thevpc.echo.api.components.AppComponentEvent;
import net.thevpc.echo.api.components.AppComponentEventListener;
import net.thevpc.echo.api.components.AppComponentEvents;
import net.thevpc.echo.api.components.AppEventType;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import net.thevpc.common.props.impl.DefaultPropertyListeners;

public class DefaultAppComponentEvents extends DefaultPropertyListeners implements AppComponentEvents {

    private final Map<AppEventType, Set<AppComponentEventListener>> componentListeners = new HashMap<>();

    public DefaultAppComponentEvents(Object source) {
        super(source);
    }

    public void add(AppComponentEventListener listener, AppEventType eventType, AppEventType... handles) {
        if (eventType == null) {
            throw new IllegalArgumentException("null event type");
        }
        for (AppEventType handle : handles) {
            if (handle == null) {
                throw new IllegalArgumentException("null event type");
            }
        }
        synchronized (componentListeners) {
            getAppComponentEventListeners(eventType).add(listener);
            for (AppEventType appEventType : handles) {
                getAppComponentEventListeners(appEventType).add(listener);
            }
        }
    }

    public void remove(AppComponentEventListener listener, AppEventType eventType, AppEventType... handles) {
        if (eventType == null) {
            throw new IllegalArgumentException("null event type");
        }
        for (AppEventType handle : handles) {
            if (handle == null) {
                throw new IllegalArgumentException("null event type");
            }
        }
        synchronized (componentListeners) {
            getAppComponentEventListeners(eventType).remove(listener);
            for (AppEventType appEventType : handles) {
                getAppComponentEventListeners(appEventType).remove(listener);
            }
        }
    }

    public AppComponentEventListener[] getAll(AppEventType eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("null event type");
        }
        synchronized (componentListeners) {
            return getAppComponentEventListeners(eventType).toArray(new AppComponentEventListener[0]);
        }
    }

    private Set<AppComponentEventListener> getAppComponentEventListeners(AppEventType eventType) {
        return componentListeners.computeIfAbsent(eventType, k -> new LinkedHashSet<>());
    }

    public void fire(AppComponentEvent event) {
        for (AppComponentEventListener li : getAll(event.eventType())) {
            li.onEvent(event);
        }
    }
    @Override
    public void clear() {
        super.clear();
        componentListeners.clear();
    }

}
