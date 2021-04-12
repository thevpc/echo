/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.actions;

import java.awt.event.ActionEvent;

import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.Application;
import net.thevpc.swing.plaf.UIPlafManager;

/**
 *
 * @author thevpc
 */
public class FontSizeAbsoluteAction extends AbstractAppAction {

    private float size;

    public FontSizeAbsoluteAction(Application aplctn, float size) {
        super(aplctn, "FontSize_" + size);
        this.size = size;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) {
        UIPlafManager.getCurrentManager().resizeAbsoluteFonts(size);
    }

    @Override
    public void refresh() {

    }

}
