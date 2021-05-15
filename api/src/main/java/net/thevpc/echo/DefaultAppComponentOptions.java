package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;

public class DefaultAppComponentOptions implements AppComponentOptions {

    private Integer order;
    private Class<? extends AppComponent> componentType;

    public static AppComponentOptions copy(AppComponentOptions o) {
        if(o==null){
            return new DefaultAppComponentOptions();
        }
        return o.copy();
    }

    public Integer order() {
        return order;
    }

    public AppComponentOptions order(Integer value) {
        this.order = value;
        return this;
    }

    public Class<? extends AppComponent> componentType() {
        return componentType;
    }

    public AppComponentOptions componentType(Class<? extends AppComponent> value) {
        this.componentType = value;
        return this;
    }

    @Override
    public AppComponentOptions componentTypeIfNull(Class<? extends AppComponent> value) {
        if(componentType==null && value!=null){
            this.componentType=value;
        }
        return this;
    }

    @Override
    public void copyFrom(AppComponentOptions other, boolean nonNull) {
        if (other != null) {
            if (!nonNull || other.order() != null) {
                order = other.order();
            }
            if (!nonNull || other.componentType() != null) {
                componentType = other.componentType();
            }
        }
    }

    @Override
    public AppComponentOptions copy() {
        try {
            return ((AppComponentOptions) clone());
        } catch (CloneNotSupportedException ex) {
            throw new IllegalArgumentException("must implement cloneable");
        }
    }

    @Override
    public String toString() {
        return "DefaultAppComponentOptions{"
                + "order=" + order
                + '}';
    }

}
