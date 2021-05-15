package net.thevpc.echo.api.peers;

import net.thevpc.echo.api.components.AppComponent;

public interface AppComponentPeer {
    /**
     * prepare the peer and adds to parent
     *  @param comp   component
     *
     */
    void install(AppComponent comp);

    /**
     * remove from parent
     *
     */
    default void uninstall(){

    }

    default void addChild(AppComponent other, int index) {
        //throw new IllegalArgumentException("unsupported addChild");
    }

    default void removeChild(AppComponent other, int index) {
        //throw new IllegalArgumentException("unsupported removeChild");
    }

    Object toolkitComponent();
}
