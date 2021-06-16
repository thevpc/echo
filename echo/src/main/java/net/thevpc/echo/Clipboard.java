package net.thevpc.echo;

public interface Clipboard {
    String getString();

    String getHtml();

    void putString(String value);

    void putHtml(String value);

}
