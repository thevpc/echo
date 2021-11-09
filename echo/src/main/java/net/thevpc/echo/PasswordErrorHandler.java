/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

/**
 *
 * @author thevpc
 */
public interface PasswordErrorHandler {

    default boolean onPasswordError(PasswordDialog dialog, Exception ex) {
        return true;
    }

    public default void onStartAskPassword(PasswordDialog aThis) {
    }
    
}
