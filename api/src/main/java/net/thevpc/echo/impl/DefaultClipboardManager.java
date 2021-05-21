package net.thevpc.echo.impl;

import net.thevpc.echo.Application;
import net.thevpc.echo.Clipboard;
import net.thevpc.echo.ClipboardManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultClipboardManager implements ClipboardManager {
    private Clipboard sys;
    private Application app;
    private Map<String,Clipboard> user=new LinkedHashMap<>();

    public DefaultClipboardManager(Application app) {
        this.app = app;
    }

    @Override
    public Clipboard system() {
        if(sys==null){
            sys=app.toolkit().systemClipboard();
        }
        return sys;
    }

    @Override
    public Clipboard app() {
        return app("app");
    }

    @Override
    public Clipboard app(String name) {
        return user.computeIfAbsent(name,n->new DefaultClipboard(n,app));
    }
    private Clipboard def(){
        return system();
    }

    @Override
    public String getString() {
        return def().getString();
    }

    @Override
    public String getHtml() {
        return def().getHtml();
    }

    @Override
    public void putString(String value) {
        def().putString(value);
    }

    @Override
    public void putHtml(String value) {
        def().putHtml(value);
    }
}
