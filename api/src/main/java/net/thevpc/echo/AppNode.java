package net.thevpc.echo;

public interface AppNode {

    AppComponent getComponent();

    int getOrder();

    ItemPath path();

    AppNode[] getChildren();

    AppNode get(ItemPath path);

}
