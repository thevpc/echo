/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.props;

import javax.swing.Icon;
import net.thevpc.common.props.Props.PropsBuilder;
import net.thevpc.echo.Application;

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

        public AppWritableString strOf(String value) {
            AppWritableString p = new AppWritableString(name, app);
            p.set(value);
            prepare(p);
            return p;
        }

        public AppWritableString strIdOf(String value) {
            AppWritableString p = new AppWritableString(name, app);
            p.setId(value);
            prepare(p);
            return p;
        }

        public AppWritableIcon iconOf(Icon value) {
            AppWritableIcon p = new AppWritableIcon(name, app);
            p.set(value);
            prepare(p);
            return p;
        }

        public AppWritableIcon iconIdOf(String value) {
            AppWritableIcon p = new AppWritableIcon(name, app);
            p.setId(value);
            prepare(p);
            return p;
        }

    }
}
