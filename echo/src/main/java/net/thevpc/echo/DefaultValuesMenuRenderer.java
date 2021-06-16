/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;

/**
 *
 * @author vpc
 */
public class DefaultValuesMenuRenderer<T> implements ValuesMenuRenderer<T> {

    private static DefaultValuesMenuRenderer INSTANCE = new DefaultValuesMenuRenderer();

    public static <T> ValuesMenuRenderer<T> defaultRenderer() {
        return INSTANCE;
    }

    public DefaultValuesMenuRenderer() {
    }

    @Override
    public void render(ValuesMenuRendererContext<T> context) {
        context.setText(Str.of(String.valueOf(context.getValue())));
    }

}
