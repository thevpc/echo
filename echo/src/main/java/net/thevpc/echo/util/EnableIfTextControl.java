/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.util;

import java.util.function.Consumer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppEditTextControl;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.impl.Applications;

/**
 *
 * @author thevpc
 */
public class EnableIfTextControl implements Consumer<AppComponent> {
    
    private final AppFrame frame;

    public EnableIfTextControl(AppComponent component) {
        AppFrame f = Applications.frameOf(component);
        if (f == null) {
            throw new IllegalArgumentException("unable to resolve frame");
        }
        this.frame = f;
    }

    public EnableIfTextControl(AppFrame frame) {
        this.frame = frame;
    }

    @Override
    public void accept(AppComponent x) {
        frame.app().toolkit().focusOwner().onChangeAndInit(() -> {
            AppComponent fo = frame.app().toolkit().focusOwner().get();
            boolean copyEnabled = false;
            if (fo instanceof AppEditTextControl && Applications.isDeepChildOf(fo, frame)) {
                copyEnabled = true;
            }
            x.enabled().set(copyEnabled);
            x.visible().set(!frame.app().hideDisabled().get() || copyEnabled);
        });
    }
    
}
