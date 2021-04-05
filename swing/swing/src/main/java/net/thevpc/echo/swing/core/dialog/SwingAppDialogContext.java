/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core.dialog;

import net.thevpc.echo.AppDialogContext;
import javax.swing.JDialog;
import net.thevpc.echo.AppDialog;

/**
 *
 * @author vpc
 */
public class SwingAppDialogContext implements AppDialogContext{
    
    private String buttonId;
    private int buttonIndex;
    private SwingAppDialog dialog;

    public SwingAppDialogContext(SwingAppDialog dialog, String buttonId, int buttonIndex) {
        this.dialog = dialog;
        this.buttonId = buttonId;
        this.buttonIndex = buttonIndex;
    }

   

    public AppDialog getDialog() {
        return dialog;
    }

    public String getButtonId() {
        return buttonId;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
    
}
