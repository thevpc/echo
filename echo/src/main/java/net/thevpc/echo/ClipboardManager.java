package net.thevpc.echo;

public interface ClipboardManager {
    Clipboard system();
    Clipboard app();
    Clipboard app(String name);
    String getString();

    String getHtml();

    void putString(String value);

    void putHtml(String value);
}
