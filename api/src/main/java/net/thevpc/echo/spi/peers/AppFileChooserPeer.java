package net.thevpc.echo.spi.peers;

public interface AppFileChooserPeer extends AppComponentPeer {
    boolean showOpenDialog(Object owner) ;

    boolean showSaveDialog(Object owner) ;
}
