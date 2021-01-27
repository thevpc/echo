package net.thevpc.echo;

public class AppComponentRendererContext {
    private Object parentGuiElement;
    private AppComponent appComponent;
    private Application application;

    public AppComponentRendererContext(Object parentGuiElement, AppComponent appComponent, Application application) {
        this.parentGuiElement = parentGuiElement;
        this.appComponent = appComponent;
        this.application = application;
    }

    public Object getParentGuiElement() {
        return parentGuiElement;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public Application getApplication() {
        return application;
    }
}
