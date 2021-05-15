/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.api;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;

/**
 *
 * @author vpc
 */
public class WritableStr extends WritableValueImpl<Str> {
    public WritableStr(String name) {
        super(name, PropertyType.of(Str.class), null);
    }
}
