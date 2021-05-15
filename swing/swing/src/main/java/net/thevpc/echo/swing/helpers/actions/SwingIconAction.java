/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.helpers.actions;

import java.awt.event.ActionEvent;

import net.thevpc.echo.Application;

/**
 *
 * @author thevpc
 */
public class SwingIconAction extends SwingAbstractAppAction {
    
    private String iconSet;

    public SwingIconAction(Application aplctn, String iconSet) {
        super(aplctn, "IconSet_" + iconSet);
        this.iconSet = iconSet;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        getApplication().iconSets().id().set(iconSet);
    }
        @Override
    public void refresh() {

    }

}
