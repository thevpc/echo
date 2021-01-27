package net.thevpc.echo;

public class DefaultAppToolComponent<T extends AppTool> implements AppToolComponent<T> {

    private T tool;

    private int order;

    private ItemPath path;
    private AppComponentRenderer renderer;

    public DefaultAppToolComponent(T tool, String path, int order, AppComponentRenderer renderer) {
        this.tool = tool;
        this.order = order;
        this.path = ItemPath.of(path);
        this.renderer = renderer;
    }

    @Override
    public T tool() {
        return tool;
    }

    @Override
    public int order() {
        return order;
    }

    @Override
    public ItemPath path() {
        return path;
    }

    @Override
    public AppComponentRenderer renderer() {
        return renderer;
    }

    @Override
    public AppToolComponent<T> copyTo(AppTools tools, String newPath) {
        AppToolComponent<T> c2 = AppToolComponent.of(tool(), newPath);
        tools.addTool(c2);
        return c2;
    }

}
