/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import java.awt.event.ActionEvent;

import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.Application;

/**
 *
 * @author thevpc
 */
public class RedoAction extends AbstractAppAction {

    public RedoAction(Application aplctn) {
        super(aplctn, "Redo");
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().history().redoAction();
    }
    @Override
    public void refresh() {

    }

}
