package net.thevpc.echo.impl;

import net.thevpc.echo.api.components.AppComponentEvent;
import net.thevpc.echo.api.components.AppComponentEventListener;
import net.thevpc.echo.api.components.AppComponentEvents;
import net.thevpc.echo.api.components.AppEventType;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DefaultAppComponentEvents implements AppComponentEvents {
    private final Map<AppEventType, Set<AppComponentEventListener>> listeners = new HashMap<>();

    public void add(AppComponentEventListener listener, AppEventType eventType, AppEventType... handles) {
        if (eventType == null) {
            throw new IllegalArgumentException("null event type");
        }
        for (AppEventType handle : handles) {
            if (handle == null) {
                throw new IllegalArgumentException("null event type");
            }
        }
        synchronized (listeners) {
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
        synchronized (listeners) {
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
        synchronized (listeners) {
            return getAppComponentEventListeners(eventType).toArray(new AppComponentEventListener[0]);
        }
    }

    private Set<AppComponentEventListener> getAppComponentEventListeners(AppEventType eventType) {
        return listeners.computeIfAbsent(eventType, k -> new LinkedHashSet<>());
    }

    public void fire(AppEventType eventType, AppComponentEvent event) {
        for (AppComponentEventListener li : getAll(eventType)) {
            li.onEvent(event);
        }
    }
}
