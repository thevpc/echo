/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.util.Objects;

/**
 *
 * @author vpc
 */
public interface AppDialogResult {

    public String getButtonId();

    public <T> T getValue();

    public default boolean isBlankValue() {
        Object o = getValue();
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).trim().isEmpty();
        }
        return false;
    }

    public default boolean isButton(String ok) {
        return Objects.equals(ok, getButtonId());
    }
    
}
