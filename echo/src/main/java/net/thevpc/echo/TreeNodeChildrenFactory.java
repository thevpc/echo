package net.thevpc.echo;


import java.util.List;

public interface TreeNodeChildrenFactory<T> {
    List<T> getChildren(T parent) ;
}
