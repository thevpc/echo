package net.thevpc.echo.api.peers;

public interface AppFileChooserPeer extends AppComponentPeer {
    boolean showOpenDialog(Object owner) ;

    boolean showSaveDialog(Object owner) ;
}
