/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author vpc
 */
public interface AppToolRadioBoxBuilder<T> {

    AppToolRadioBoxBuilder<T> bind(Supplier<T> s, Consumer<T> c);

    AppToolRadioBoxBuilder<T> bind(WritableValue<T> s);

    AppToolRadioBoxBuilder<T> id(String id);

    AppToolRadioBoxBuilder<T> group(String group);

    AppToolRadioBoxBuilder<T> value(T value);

    AppToolRadioBoxBuilder<T> path(String... path);

    public AppToolRadioBox tool();

    public AppToolComponent<AppToolRadioBox> component();

    public List<AppToolComponent<AppToolRadioBox>> components();

}
