package net.thevpc.echo;

public interface AppNode {
    AppComponent getComponent();

    int getOrder();

    ItemPath getPath();

    AppNode[] getChildren();

}
