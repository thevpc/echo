/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.helpers.actions;

import net.thevpc.echo.WindowState;
import net.thevpc.echo.Application;

import java.awt.event.ActionEvent;

/**
 *
 * @author thevpc
 */
public class SwingQuitAction extends SwingAbstractAppAction {
    public SwingQuitAction(Application aplctn) {
        super(aplctn, "Quit");
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().mainFrame().get().state().add(WindowState.CLOSED);
    }

}
