package net.thevpc.echo.spi.peers;

public interface AppScrollPanePeer extends AppComponentPeer{
    public int getMaxX() ;

    public int getMaxY() ;

    public void scrollTo(Float x, Float y) ;

    public void scrollTo(Integer x, Integer y) ;
}
