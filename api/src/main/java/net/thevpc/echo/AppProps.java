/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.props.Props.PropsBuilder;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppImage;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.iconset.WritableImage;

/**
 *
 * @author vpc
 */
public class AppProps {

    public static AppPropsBuilder of(String name, Application app) {
        return new AppPropsBuilder(name, app);
    }

    public static class AppPropsBuilder extends PropsBuilder {

        private Application app;

        public AppPropsBuilder(String name, Application app) {
            super(name);
            this.app = app;
        }

        public WritableStr strOf(Str value) {
            WritableStr p = new WritableStr(name);
            p.set(value);
            prepare(p);
            return p;
        }

        public WritableImage iconOf(AppImage value) {
            WritableImage p = new WritableImage(name, app);
            p.set(value);
            prepare(p);
            return p;
        }

        public WritableImage iconOf(Str value) {
            WritableImage p = new WritableImage(name, app);
            p.set(value);
            prepare(p);
            return p;
        }

    }
}
