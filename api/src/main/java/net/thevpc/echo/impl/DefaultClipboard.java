package net.thevpc.echo.impl;

import net.thevpc.echo.Application;
import net.thevpc.echo.Clipboard;

import java.util.HashMap;
import java.util.Map;

public class DefaultClipboard implements Clipboard {
    private String name;
    private Application app;
    private Data value;

    private static class Data{
        String contentType;
        Object value;

        public Data(String contentType, Object value) {
            this.contentType = contentType;
            this.value = value;
        }
    }
    public DefaultClipboard(String name, Application app) {
        this.name = name;
        this.app = app;
    }

    @Override
    public void putString(String value) {
        this.value=new Data("text/plain",value);
    }

    @Override
    public void putHtml(String value) {
        this.value=new Data("text/html",value);
    }

    @Override
    public String getString() {
        if(value==null){
            return null;
        }
        return value.value==null?null:value.value.toString();
    }

    @Override
    public String getHtml() {
        if(value==null){
            return null;
        }
        return value.value==null?null:value.value.toString();
    }
}
