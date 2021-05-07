package net.thevpc.echo;

public class DefaultAppComponentRendererFactory implements AppComponentRendererFactory {
    private _ClassMap<AppComponentRenderer> renderers = new _ClassMap<AppComponentRenderer>(AppComponent.class, AppComponentRenderer.class);
    private _ClassMap<AppComponentRenderer> trenderers = new _ClassMap<AppComponentRenderer>(AppTool.class, AppComponentRenderer.class);

    public <T extends AppTool> void setToolRenderer(Class<T> type, AppComponentRenderer renderer) {
        if (renderer == null) {
            trenderers.remove(type);
        } else {
            trenderers.put(type, renderer);
        }
    }

    public <T extends AppComponent> void setComponentRenderer(Class<T> type, AppComponentRenderer renderer) {
        if (renderer == null) {
            renderers.remove(type);
        } else {
            renderers.put(type, renderer);
        }
    }

    @Override
    public AppComponentRenderer getRenderer(AppComponent a) {
        if(a instanceof AppToolComponent){
            AppTool tool = ((AppToolComponent) a).tool();
            return trenderers.get(tool.getClass());
        }
        return renderers.get(a.getClass());
    }
}
