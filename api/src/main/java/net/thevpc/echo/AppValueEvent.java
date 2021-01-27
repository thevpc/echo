package net.thevpc.echo;


public interface AppValueEvent<T> extends AppEvent {
    T getValue();
}
