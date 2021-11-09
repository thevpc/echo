/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.echo.api.AppImage;

/**
 *
 * @author thevpc
 */
public interface StarFieldRenderer<T extends Number> {

    AppImage createImage(StarFieldRendererContext<T> context);
    
}
