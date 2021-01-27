/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.AppWindowState;
import net.thevpc.echo.Application;
import net.thevpc.common.swing.win.InternalWindowsHelper;

import java.awt.event.ActionEvent;

/**
 *
 * @author thevpc
 */
public class QuitAction extends AbstractAppAction {
    public QuitAction(Application aplctn) {
        super(aplctn, "Quit");
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().mainWindow().get().state().add(AppWindowState.CLOSED);
    }

}
