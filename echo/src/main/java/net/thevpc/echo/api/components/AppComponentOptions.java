package net.thevpc.echo.api.components;

public interface AppComponentOptions {

    Integer order();

    AppComponentOptions order(Integer value);

    Class<? extends AppComponent> componentType();

    AppComponentOptions componentType(Class<? extends AppComponent> value);

    AppComponentOptions componentTypeIfNull(Class<? extends AppComponent> value);

    void copyFrom(AppComponentOptions other,boolean nonNull);

    AppComponentOptions copy();
}
